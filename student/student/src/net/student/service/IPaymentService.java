package net.student.service;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.student.model.Payment;
import net.student.model.PaymentOrder;
import net.student.model.User;
import net.student.request.JqGridQuerier;
import net.student.response.JsonResult;
import net.student.response.PaymentView;
import net.student.response.QueryResult;

import com.alibaba.fastjson.JSONObject;

/**
 * 账单Service接口类
 * @author liuqingchao
 *
 */
public interface IPaymentService {
    /**
     * 查询账单
     * @param querier
     * @return
     */
    public QueryResult<PaymentView> queryPayments(JqGridQuerier<Payment, Integer> querier, User user, boolean limit) throws Exception;
    
    /**
     * 导入账单信息
     * @param is
     * @throws Exception
     */
    public JsonResult importPayments(InputStream is, User user, HttpSession session) throws Exception;
    /**
     * 确认导入账单信息
     * @param is
     * @throws Exception
     */
    public JsonResult importPayments(List<Payment> payments) throws Exception;
    /**
     * 保存账单信息
     * @param payment
     * @throws Exception
     */
    public void savePayment(Payment payment) throws Exception;
    /**
     * 修改账单信息
     * @param payment
     * @throws Exception
     */
    public void updatePayment(Payment payment) throws Exception;
    /**
     * 删除账单信息
     * @param payment
     * @throws Exception
     */
    public void deletePayment(String paymentIds) throws Exception;
    /**
     * 批量产生账单
     * @param params
     * @return
     * @throws Exception
     */
    public Map<String, Object> generatePayments(Map<String, Object> params) throws Exception;
    /**
     * 批量产生账单
     * @param params
     * @throws Exception
     */
    public void batchPayments(List<Payment> list) throws Exception;
    /**
     * 查询某个学生的缴费情况
     * @param studentId
     * @return
     * @throws Exception
     */
    public JSONObject queryPaymentInfo(String studentId, Locale locale) throws Exception;
    /**
     * 获得账单
     * @param paymentId
     * @return
     * @throws Exception
     */
    public Payment getPaymentById(Integer paymentId) throws Exception;
    /**
     * 修改账单信息
     * @param payment
     * @throws Exception
     */
    public void updatePaymentSimple(Payment payment) throws Exception;
    /**
     * 账单完成
     * @param orderId
     * @throws Exception
     */
    public void savePaidLog(String orderId, Locale locale) throws Exception;
    /**
     * 增加订单号
     * @param paymentOrder
     * @throws Exception
     */
    public void savePaymentOrder(PaymentOrder paymentOrder) throws Exception;
    /**
     * 获得最新的订单
     * @param paymentId
     * @return
     * @throws Exception
     */
    public PaymentOrder getLastPaymentOrder(Integer paymentId) throws Exception;
}
