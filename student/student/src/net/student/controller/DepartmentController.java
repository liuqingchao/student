package net.student.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import net.student.constants.Constants;
import net.student.constants.CustomerException;
import net.student.model.Department;
import net.student.model.User;
import net.student.request.JqGridQuerier;
import net.student.response.JsonResult;
import net.student.response.QueryResult;
import net.student.service.IDepartmentService;
/**
 * 部门Controller类
 * @author liuqingchao
 *
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {
    static Logger logger = Logger.getLogger(DepartmentController.class);
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private ResourceBundleMessageSource messageSource;
    /**
     * 查询部门
     * @param request
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public QueryResult<Department> getDepartments(JqGridQuerier<Department, Integer> querier) {
        try {
            return departmentService.queryDepartments(querier);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 新增部门
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public JsonResult saveDepartment(Department department, String oper, HttpServletRequest request) {
        JsonResult result = new JsonResult();
        try {
            if (oper.equals(JqGridQuerier.OPER_ADD)) {
                departmentService.saveDepartment(department);
            } else if (oper.equals(JqGridQuerier.OPER_UPDATE)) {
                departmentService.updateDepartment(department);
            } else if (oper.equals(JqGridQuerier.OPER_DEL)) {
                departmentService.delDepartment(request.getParameter("id"));
            }
            result.setSuccess(true);
        } catch (CustomerException e) {
            result.setSuccess(false);
            JSONObject json = new JSONObject();
            json.put("msg", messageSource.getMessage(e.getMessage(), null, request.getLocale()));
            result.setInfo(json);
        } catch (Exception e) {
            logger.error(e);
            result.setSuccess(false);
            JSONObject json = new JSONObject();
            json.put("msg", ExceptionUtils.getRootCauseMessage(e));
            result.setInfo(json);
        }
        return result;
    }
    /**
     * 查询全部部门，用于下拉框选择
     * @param request
     * @return
     */
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public JSONObject getDepartmentsForSelect(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.SESSION_NAME);
        try {
            return departmentService.selectDepartments(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
