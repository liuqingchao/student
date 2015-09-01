package net.student.controller;

import net.student.model.UserLog;
import net.student.request.JqGridQuerier;
import net.student.response.QueryResult;
import net.student.service.IUserLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * 操作日志Controller类
 * @author liuqingchao
 *
 */
@RestController
@RequestMapping("/user_log")
public class UserLogController {
    @Autowired
    private IUserLogService userLogService;
    /**
     * 查询操作日志
     * @param querier
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public QueryResult<UserLog> getUserLogs(JqGridQuerier querier) {
        try {
            return userLogService.queryUserLogs(querier);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
