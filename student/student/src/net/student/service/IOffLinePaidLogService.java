package net.student.service;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.student.model.OffLinePaidLog;
import net.student.model.User;
import net.student.request.JqGridQuerier;
import net.student.response.JsonResult;
import net.student.response.PaymentView;
import net.student.response.QueryResult;

/**
 * 线下账单Service接口类
 * @author liuqingchao
 *
 */
public interface IOffLinePaidLogService {
    /**
     * 查询账单
     * @param querier
     * @return
     */
    public QueryResult<PaymentView> queryOffLinePaidLogs(JqGridQuerier<OffLinePaidLog, Integer> querier, User user, boolean limit) throws Exception;
    
    /**
     * 导入账单信息
     * @param is
     * @throws Exception
     */
    public JsonResult importOffLinePaidLogs(InputStream is, User user, HttpSession session) throws Exception;
    /**
     * 确认导入账单信息
     * @param is
     * @throws Exception
     */
    public JsonResult importOffLinePaidLogs(List<OffLinePaidLog> offLinePaidLogs) throws Exception;
    /**
     * 保存账单信息
     * @param offLinePaidLog
     * @throws Exception
     */
    public void saveOffLinePaidLog(OffLinePaidLog offLinePaidLog) throws Exception;
    /**
     * 修改账单信息
     * @param offLinePaidLog
     * @throws Exception
     */
    public void updateOffLinePaidLog(OffLinePaidLog offLinePaidLog) throws Exception;
    /**
     * 删除账单信息
     * @param offLinePaidLog
     * @throws Exception
     */
    public void deleteOffLinePaidLog(String offLinePaidLogIds) throws Exception;
}
