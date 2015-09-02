package net.student.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import net.student.response.JsonResult;
import net.student.service.IIndexService;
/**
 * 页面跳转控制类
 * @author liuqingchao
 *
 */
@RestController
public class IndexController {
    static Logger logger = Logger.getLogger(IndexController.class);
    @Autowired
    private IIndexService indexService;
    /**
     * 控制菜单跳转
     * @param page
     * @return
     */
    @RequestMapping(value = "/ajax", method = RequestMethod.GET)
    public ModelAndView init(String page) {
        ModelAndView mv = new ModelAndView();
        if (StringUtils.isBlank(page)) {
            mv.setViewName("ajax");
        }
        else {
            mv.setViewName(page);
        }
        return mv;
    }
    /**
     * 统计概述
     * @param request
     * @return
     */
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public JsonResult stat(HttpServletRequest request) {
        JsonResult result = new JsonResult();
        try {
            JSONObject json = indexService.getStatInfo(request.getLocale());
            result.setSuccess(true);
            result.setInfo(json);
        } catch (Exception e) {
            logger.error(e);
            result.setSuccess(false);
        }
        return result;
    }
    
    @RequestMapping(value = "/stat/student", method = RequestMethod.GET)
    public JsonResult statStudent(String type, HttpServletRequest request) {
        JsonResult result = new JsonResult();
        try {
            JSONObject json = indexService.getStudentInfo(type);
            result.setSuccess(true);
            result.setInfo(json);
        } catch (Exception e) {
            logger.error(e);
            result.setSuccess(false);
        }
        return result;
    }
    /**
     * 收费项目统计
     * @param type
     * @param request
     * @return
     */
    @RequestMapping(value = "/stat/feeitem", method = RequestMethod.GET)
    public JsonResult statFeeItem(HttpServletRequest request) {
        JsonResult result = new JsonResult();
        try {
            JSONObject json = indexService.getFeeItemInfo(request.getLocale());
            result.setSuccess(true);
            result.setInfo(json);
        } catch (Exception e) {
            logger.error(e);
            result.setSuccess(false);
        }
        return result;
    }
}
