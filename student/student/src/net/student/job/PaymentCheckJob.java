package net.student.job;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.Callable;

import net.student.constants.Constants;
import net.student.model.PaidLog;
import net.student.model.PayStat;
import net.student.model.Payment;

import org.apache.axis2.client.Options;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.CommonsTransportHeaders;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.wsdl.WSDLConstants;
import org.apache.commons.httpclient.Header;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import axis2.service.server.ICBCServiceStub;
import cn.edu.huat.pay.GetPayInfo;
import cn.edu.huat.pay.GetPayInfoResponse;
import cn.edu.huat.pay.Verify;
import cn.edu.huat.pay.VerifyResponse;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;

/**
 * 校验账单支付情况
 * @author 果冻
 */
@Component
public class PaymentCheckJob {
    private static Logger logger = Logger.getLogger(PaymentCheckJob.class);
    @Autowired
    private Dao<Payment, Integer> paymentDao;
    @Autowired
    private Dao<PaidLog, Integer> paidLogDao;
    @Autowired
    private Dao<PayStat, Integer> payStatDao;
    
//    private int time = 0;

    @Scheduled(fixedRate = 1000 * 60 * 10)
    // 每10分钟执行一次
    public void paymentCheck() {
        try {
            List<Payment> payments =
                paymentDao.query(paymentDao.queryBuilder().setCountOf(false).where().isNotNull("orderid").and()
                    .raw("strftime('%s',datetime('now','localtime'))-strftime('%s',lastcheckdate)<1800").prepare());
            if(payments == null || payments.isEmpty()) {
                return;
            }
            ICBCServiceStub stub = new ICBCServiceStub();
            Verify verify = new Verify();
            verify.setClienID(Constants.WEBSERVICE_CLIENTID);
            verify.setPasswrd(Constants.WEBSERVICE_PASSWORD);
            VerifyResponse res = stub.verify(verify);
            if (res.getVerifyResult()) {
                MessageContext msgCtx =
                    stub._getServiceClient().getLastOperationContext()
                        .getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                CommonsTransportHeaders cth =
                    (CommonsTransportHeaders) msgCtx.getProperty(MessageContext.TRANSPORT_HEADERS);
                String cookie = (String) cth.get("Set-Cookie");
                List<Object> list = new ArrayList<Object>();
                Header header = new Header();
                header.setName("Cookie");
                header.setValue(cookie);
                list.add(header);
                Options options = stub._getServiceClient().getOptions();
                options.setProperty(HTTPConstants.HTTP_HEADERS, list);
                stub._getServiceClient().setOptions(options);
                for (Payment payment : payments) {
                    GetPayInfo getPayInfo = new GetPayInfo();
                    getPayInfo.setOrderID(payment.getOrderId());
                    GetPayInfoResponse getPayInfoResponse = stub.getPayInfo(getPayInfo);
                    Date now = new Date();
                    logger.info("*********in job, payment[" + payment.getPaymentId() + "] check payinfo = "
                        + (getPayInfoResponse.getGetPayInfoResult()));
                    if (getPayInfoResponse.getGetPayInfoResult()) {
                        final PaidLog paidLog = new PaidLog();
                        paidLog.setStudent(payment.getStudent());
                        paidLog.setFeeItem(payment.getFeeItem());
                        paidLog.setPrice(payment.getPrice());
                        paidLog.setPaidFee(payment.getPaidFee());
                        paidLog.setPayDate(payment.getLastCheckDate());
                        paidLog.setCreatedDate(now);
                        paidLog.setSerialNo(payment.getOrderId());
                        final Payment fPayment = payment;
                        String statDay =
                            DateFormatUtils.format(now, "yyyy-MM-dd", TimeZone.getTimeZone("Asia/Shanghai"));
                        PayStat payStat =
                            payStatDao.queryForFirst(payStatDao.queryBuilder().where().eq("statday", statDay).and()
                                .eq("itemid", payment.getFeeItem().getItemId()).prepare());
                        if (payStat == null) {
                            payStat = new PayStat();
                            payStat.setStatDay(statDay);
                            payStat.setFeeItemId(payment.getFeeItem().getItemId());
                            payStat.setCount(0);
                            payStat.setAmount(0L);
                        }
                        payStat.setCount(payStat.getCount() + 1);
                        payStat.setAmount(payStat.getAmount() + payment.getPrice() - payment.getPaidFee());
                        payStat.setLastOrderId(payment.getOrderId());
                        payStat.setLastPayDate(now);
                        final PayStat fPayStat = payStat;
                        TransactionManager.callInTransaction(paymentDao.getConnectionSource(), new Callable<Void>() {
                            public Void call() throws Exception {
                                paidLogDao.create(paidLog);
                                paymentDao.delete(fPayment);
                                payStatDao.createOrUpdate(fPayStat);
                                return null;
                            }
                        });
                        logger.info("*********in job, payment[" + payment.getPaymentId() + "] succeed");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("************in job payment faild", e);
        }
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void backupDataBase() {
        String path = this.getClass().getClassLoader().getResource("/").getPath();
        File databaseFile = new File(path, "student.db");
        File backupFile = new File(path, "student" + DateFormatUtils.format(new Date(), "yyyy-MM-dd") + ".db");
        try {
            FileUtils.copyFile(databaseFile, backupFile);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("backup database failed:", e);
        }
    }
    @Scheduled(fixedRate = 24*60*60*1000)
    public void repairData(){
        
    }

//    public static void main(String[] args) {
//        try {
//            ICBCServiceStub stub = new ICBCServiceStub();
//            Verify verify = new Verify();
//            verify.setClienID(Constants.WEBSERVICE_CLIENTID);
//            verify.setPasswrd(Constants.WEBSERVICE_PASSWORD);
//            VerifyResponse res = stub.verify(verify);
//            if (res.getVerifyResult()) {
//                MessageContext msgCtx =
//                    stub._getServiceClient().getLastOperationContext()
//                        .getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE);
//                CommonsTransportHeaders cth =
//                    (CommonsTransportHeaders) msgCtx.getProperty(MessageContext.TRANSPORT_HEADERS);
//                String cookie = (String) cth.get("Set-Cookie");
//                List<Object> list = new ArrayList<Object>();
//                Header header = new Header();
//                header.setName("Cookie");
//                header.setValue(cookie);
//                list.add(header);
//                Options options = stub._getServiceClient().getOptions();
//                options.setProperty(HTTPConstants.HTTP_HEADERS, list);
//                stub._getServiceClient().setOptions(options);
//                GetPayInfo getPayInfo = new GetPayInfo();
//                getPayInfo.setOrderID("5271442489468557");
//                GetPayInfoResponse getPayInfoResponse = stub.getPayInfo(getPayInfo);
//                System.out.println("*********" + getPayInfoResponse.getGetPayInfoResult());
//            }
//        } catch (AxisFault e) {
//            e.printStackTrace();
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
}
