package net.student.service;

import net.student.model.PaidLog;
import net.student.model.PayStat;
import net.student.model.User;
import net.student.request.JqGridQuerier;
import net.student.response.PaymentView;
import net.student.response.QueryResult;

/**
 * 账单Service接口类
 * @author liuqingchao
 *
 */
public interface IPaidLogService {
    /**
     * 查询账单
     * @param querier
     * @return
     */
    public QueryResult<PaymentView> queryPaidLogs(JqGridQuerier<PaidLog, Integer> querier, User user, boolean limit) throws Exception;
    /**
     * 查询统计
     * @param querier
     * @return
     */
    public QueryResult<PayStat> queryPayStats(JqGridQuerier<PayStat, Integer> querier, User user) throws Exception;
}
