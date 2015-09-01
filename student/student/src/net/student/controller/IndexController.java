package net.student.controller;

import net.student.response.JsonResult;
import net.student.service.IIndexService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
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
    
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public JsonResult stat(String page) {
        JsonResult result = new JsonResult();
        try {
            JSONObject json = indexService.getStatInfo();
            result.setSuccess(true);
            result.setInfo(json);
        } catch (Exception e) {
            logger.error(e);
            result.setSuccess(false);
        }
        return result;
    }
}
