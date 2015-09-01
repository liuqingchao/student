package net.student.service;

import net.student.model.UserLog;
import net.student.request.JqGridQuerier;
import net.student.response.QueryResult;

/**
 * 操作日志Service接口类
 * @author liuqingchao
 *
 */
public interface IUserLogService {
    /**
     * 查询操作日志
     * @param querier
     * @return
     */
    public QueryResult<UserLog> queryUserLogs(JqGridQuerier querier) throws Exception;

}
