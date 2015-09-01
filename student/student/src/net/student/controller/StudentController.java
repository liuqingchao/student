package net.student.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import net.student.constants.Constants;
import net.student.constants.CustomerException;
import net.student.model.Student;
import net.student.model.User;
import net.student.request.JqGridQuerier;
import net.student.response.JsonResult;
import net.student.response.QueryResult;
import net.student.service.IStudentService;
/**
 * 学生Controller类
 * @author liuqingchao
 *
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    static Logger logger = Logger.getLogger(StudentController.class);
    @Autowired
    private IStudentService studentService;
    @Autowired
    private ResourceBundleMessageSource messageSource;
    /**
     * 查询学生
     * @param request
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public QueryResult<Student> getStudents(JqGridQuerier<Student, String> querier) {
        try {
            return studentService.queryStudents(querier);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 导入学生
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public JsonResult importStudents(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        JsonResult result = new JsonResult();
        User user = (User) request.getSession().getAttribute(Constants.SESSION_NAME);
        try {
            studentService.importStudents(file.getInputStream(), user);
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
     * 转向学生主页
     * @param request
     * @return
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView init(HttpServletRequest request) {
        if (request.getSession().getAttribute(Constants.SESSION_NAME) == null) {
            return new ModelAndView("redirect:/slogin");
        } else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("profile");
            return mv;
        }
    }
    /**
     * 获取学生信息及缴费情况
     * @param request
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Object info(HttpServletRequest request) {
        Object object = request.getSession().getAttribute(Constants.SESSION_NAME);
        if (object instanceof User) {
        	return new ModelAndView("redirect:/login");
        } else {
        	Student student = (Student) object;
        	JsonResult result = new JsonResult();
            result.setSuccess(true);
            result.setInfo((JSONObject) JSONObject.toJSON(student));
            return result;
        }
    }
}
