package net.student.controller;

import javax.servlet.http.HttpServletRequest;

import net.student.constants.CustomerException;
import net.student.model.User;
import net.student.request.JqGridQuerier;
import net.student.response.JsonResult;
import net.student.response.QueryResult;
import net.student.service.IUserService;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
/**
 * 操作员Controller类
 * @author liuqingchao
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {
    static Logger logger = Logger.getLogger(UserController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private ResourceBundleMessageSource messageSource;
    /**
     * 查询操作员
     * @param request
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public QueryResult<User> getUsers(JqGridQuerier<User, Integer> querier) {
        try {
            return userService.queryUsers(querier);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 新增操作员
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public JsonResult saveUser(User user, String oper, HttpServletRequest request) {
        JsonResult result = new JsonResult();
        try {
            if (oper.equals(JqGridQuerier.OPER_ADD)) {
                userService.saveUser(user);
            } else if (oper.equals(JqGridQuerier.OPER_UPDATE)) {
                userService.updateUser(user);
            } else if (oper.equals(JqGridQuerier.OPER_DEL)) {
                userService.delUser(request.getParameter("id"));
            }
            result.setSuccess(true);
        } catch (CustomerException e) {
            result.setSuccess(false);
            JSONObject json = new JSONObject();
            json.put("msg", messageSource.getMessage(e.getMessage(), null, request.getLocale()));
            result.setInfo(json);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setSuccess(false);
            JSONObject json = new JSONObject();
            json.put("msg", ExceptionUtils.getRootCauseMessage(e));
            result.setInfo(json);
        }
        return result;
    }
}
