package net.student.job;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.Callable;

import net.student.constants.Constants;
import net.student.model.PaidLog;
import net.student.model.PayStat;
import net.student.model.Payment;
import net.student.model.PaymentOrder;

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
import com.j256.ormlite.stmt.DeleteBuilder;

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
    @Autowired
    private Dao<PaymentOrder, Integer> paymentOrderDao;

    
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
                    logger.info("*********in job, payment[" + payment.getPaymentId() + "], orderId["+payment.getOrderId()+"] check payinfo = "
                        + (getPayInfoResponse.getGetPayInfoResult()));
                    if (getPayInfoResponse.getGetPayInfoResult()) {
                        savePaid(payment, payment.getOrderId());
                        logger.info("*********in job, payment[" + payment.getPaymentId() + "] succeed");
                    } else {
                        List<PaymentOrder> orderList = paymentOrderDao.queryForEq("paymentid", payment.getPaymentId());
                        logger.info("*********in job, when payment[" + payment.getPaymentId() + "] check payinfo = false, check payment order list");
                        if (orderList != null && !orderList.isEmpty()) {
                            for (PaymentOrder paymentOrder : orderList) {
                                GetPayInfo getSubPayInfo = new GetPayInfo();
                                getSubPayInfo.setOrderID(paymentOrder.getOrderId());
                                GetPayInfoResponse getSubPayInfoResponse = stub.getPayInfo(getSubPayInfo);
                                logger.info("*********in job, in payment order list payment[" + payment.getPaymentId() + "], orderId["+paymentOrder.getOrderId()+"] check payinfo = "
                                    + (getSubPayInfoResponse.getGetPayInfoResult()));
                                if (getSubPayInfoResponse.getGetPayInfoResult()) {
                                    savePaid(payment, paymentOrder.getOrderId());
                                    logger.info("*********in job, payment[" + payment.getPaymentId() + "] succeed by payment order list");
                                    break;
                                }
                            }
                        } else {
                            logger.info("*********in job, payment[" + payment.getPaymentId() + "] has no payment order list");
                        }
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
        try {
            int[] paymentIds = new int[2];
            paymentIds[0] = 10356;
            paymentIds[1] = 10428;
            String[] orderIds = new String[]{"103561443176859709","104281443070270366"};
            String[] paydays = new String[]{"2015-09-25","2015-09-24"};
            for (int i =0;i<2;i++) {
                Payment payment = paymentDao.queryForId(paymentIds[i]);
                if (payment == null) {
                    continue;
                }
                PaidLog paidLog = new PaidLog();
                paidLog.setStudent(payment.getStudent());
                paidLog.setFeeItem(payment.getFeeItem());
                paidLog.setPrice(payment.getPrice());
                paidLog.setPaidFee(payment.getPaidFee());
                paidLog.setPayDate(payment.getLastCheckDate() == null ? payment.getCreatedDate() : payment.getLastCheckDate());
                paidLog.setCreatedDate(new Date());
                paidLog.setSerialNo(orderIds[i]);
                PayStat payStat =
                    payStatDao.queryForFirst(payStatDao.queryBuilder().where().eq("statday", paydays[i]).and()
                        .eq("itemid", payment.getFeeItem().getItemId()).prepare());
                if (payStat == null) {
                    payStat = new PayStat();
                    payStat.setStatDay(paydays[i]);
                    payStat.setFeeItemId(payment.getFeeItem().getItemId());
                    payStat.setCount(0);
                    payStat.setAmount(0L);
                }
                payStat.setCount(payStat.getCount() + 1);
                payStat.setAmount(payStat.getAmount() + payment.getPrice() - payment.getPaidFee());
                paidLogDao.create(paidLog);
                paymentDao.delete(payment);
                payStatDao.createOrUpdate(payStat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void savePaid(Payment payment, String orderId) throws Exception {
        Date now = new Date();
        final PaidLog paidLog = new PaidLog();
        paidLog.setStudent(payment.getStudent());
        paidLog.setFeeItem(payment.getFeeItem());
        paidLog.setPrice(payment.getPrice());
        paidLog.setPaidFee(payment.getPaidFee());
        paidLog.setPayDate(payment.getLastCheckDate());
        paidLog.setCreatedDate(now);
        paidLog.setSerialNo(orderId);
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
        payStat.setLastOrderId(orderId);
        payStat.setLastPayDate(now);
        final PayStat fPayStat = payStat;
        final DeleteBuilder<PaymentOrder, Integer> db = paymentOrderDao.deleteBuilder();
        db.where().eq("paymentid", payment.getPaymentId());
        TransactionManager.callInTransaction(paymentDao.getConnectionSource(), new Callable<Void>() {
            public Void call() throws Exception {
                paidLogDao.create(paidLog);
                paymentOrderDao.delete(db.prepare());
                paymentDao.delete(fPayment);
                payStatDao.createOrUpdate(fPayStat);
                return null;
            }
        });
    }
    
    public static void main(String[] args) {
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
//        File file = new File("f:/export_paidlog_20150928112924.xls");
//        File file2 = new File("f:/信息中心数据.xls");
//        try {
//            InputStream is = FileUtils.openInputStream(file);
//            InputStream is2 = FileUtils.openInputStream(file2);
//            HSSFWorkbook wb = new HSSFWorkbook(is);
//            HSSFWorkbook wb2 = new HSSFWorkbook(is2);
//            HSSFSheet sh1 = wb.getSheetAt(0);
////            HSSFSheet sh2 = wb.getSheetAt(2);
//            HSSFSheet sh2 = wb2.getSheetAt(3);
//            int rowCount1 = sh1.getLastRowNum();
//            List<String> orderList = new ArrayList<String>();
//            for (int j = 1; j < rowCount1 + 1; j++) {
//                HSSFRow row = sh1.getRow(j);
//                HSSFCell cell = row.getCell(9);
//                orderList.add(cell.getStringCellValue());
//            }
//            int rowCount2 = sh2.getLastRowNum();
//            for (int j = 1; j < rowCount2 + 1; j++) {
//                HSSFRow row = sh2.getRow(j);
//                HSSFCell cell = row.getCell(0);
////                String ss = cell.getStringCellValue();
////                String orderId = ss.substring(25, ss.indexOf("用途")).trim();
//                if (!orderList.contains(cell.getStringCellValue())) {
//                    System.out.println("第"+(j+1)+"行，" + cell.getStringCellValue());
//                }
//            }
//            wb.close();
//            is.close();
//            wb2.close();
//            is2.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String ss = "92851443106998397";
//        System.out.println(ss.substring(0, ss.length() - 13));
    }
    
}
