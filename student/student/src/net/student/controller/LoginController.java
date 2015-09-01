package net.student.controller;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Producer;

import net.student.constants.Constants;
import net.student.model.Student;
import net.student.model.User;
import net.student.response.JsonResult;
import net.student.service.IStudentService;
import net.student.service.IUserService;
/**
 * 登录、登出等控制类
 * @author liuqingchao
 *
 */
@RestController
public class LoginController {
    static Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private IStudentService studentService;
    @Autowired
    private ResourceBundleMessageSource messageSource;
    @Autowired  
    private Producer captchaProducer;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView init(HttpServletRequest request) {
        if (request.getSession().getAttribute(Constants.SESSION_NAME) != null) {
            return new ModelAndView("redirect:/ajax");
        } else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("login");
            return mv;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResult login(String in_user, String in_pwd, HttpServletRequest request) {
        JsonResult result = new JsonResult();
        try {
            User user = userService.checkLoginUser(in_user, in_pwd);
            if (user == null) {
                result.setSuccess(false);
                JSONObject json = new JSONObject();
                json.put("msg", messageSource.getMessage("login.message.loginFailed", null, request.getLocale()));
                result.setInfo(json);
            } else {
                request.getSession().setAttribute(Constants.SESSION_NAME, user);
                result.setSuccess(true);
            }
        } catch (Exception e) {
            logger.error(e);
            result.setSuccess(false);
            JSONObject json = new JSONObject();
            json.put("msg", ExceptionUtils.getRootCauseMessage(e));
            result.setInfo(json);
        }
        return result;
    }

    @RequestMapping(value = "/login/current", method = RequestMethod.GET)
    public JsonResult info(HttpServletRequest request) {
        JsonResult result = new JsonResult();
        User user = (User) request.getSession().getAttribute(Constants.SESSION_NAME);
        if (user == null) {
            result.setSuccess(false);
        } else {
            result.setSuccess(true);
            JSONObject json = new JSONObject();
            json.put("userName", user.getUserName());
            json.put("userType", user.getUserType());
            json.put("pwd", user.getLastUpdateDate() == null);
            result.setInfo(json);
        }
        return result;
    }

    @RequestMapping(value = "/login/modify", method = RequestMethod.POST)
    public JsonResult modify(String old_pwd, String new_pwd, HttpServletRequest request) {
        JsonResult result = new JsonResult();
        User user = (User) request.getSession().getAttribute(Constants.SESSION_NAME);
        if (user == null) {
            result.setSuccess(false);
        } else if (!DigestUtils.md5Hex(old_pwd).equals(user.getPassword())) {
            result.setSuccess(false);
            JSONObject json = new JSONObject();
            json.put("msg", messageSource.getMessage("login.message.notSamePwd", null, request.getLocale()));
            result.setInfo(json);
        } else {
            try {
                userService.modifyPassword(user, new_pwd);
                request.getSession().setAttribute(Constants.SESSION_NAME, user);
                result.setSuccess(true);
            } catch (Exception e) {
                result.setSuccess(false);
                JSONObject json = new JSONObject();
                json.put("msg", ExceptionUtils.getRootCauseMessage(e));
                result.setInfo(json);
                logger.error(e);
            }
        }
        return result;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ModelAndView("redirect:/login");
    }
    
    /**
     * 学生登录页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/slogin", method = RequestMethod.GET)
    public ModelAndView sinit(HttpServletRequest request) {
        if (request.getSession().getAttribute(Constants.SESSION_NAME) != null) {
            return new ModelAndView("redirect:/student/profile");
        } else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("slogin");
            return mv;
        }
    }
    /**
     * 学生登录
     * @param in_user
     * @param in_pwd
     * @param request
     * @return
     */
    @RequestMapping(value = "/slogin", method = RequestMethod.POST)
    public JsonResult slogin(String in_sid, String in_idnum, String in_kaptcha, HttpServletRequest request) {
        JsonResult result = new JsonResult();
        String code = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY); 
        if (!in_kaptcha.equals(code)) {
        	result.setSuccess(false);
        	JSONObject json = new JSONObject();
            json.put("kaptchamsg", messageSource.getMessage("login.message.kaptchaFailed", null, request.getLocale()));
            result.setInfo(json);
            return result;
        }
        try {
            Student student = studentService.checkLoginStudent(in_sid, in_idnum);
            if (student == null) {
                result.setSuccess(false);
                JSONObject json = new JSONObject();
                json.put("msg", messageSource.getMessage("login.message.loginFailed", null, request.getLocale()));
                result.setInfo(json);
            } else {
                request.getSession().setAttribute(Constants.SESSION_NAME, student);
                result.setSuccess(true);
            }
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
     * 生成验证码
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/kaptcha/*") 
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {  
        HttpSession session = request.getSession();  
          
        response.setDateHeader("Expires", 0);  
          
        // Set standard HTTP/1.1 no-cache headers.  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
          
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
          
        // Set standard HTTP/1.0 no-cache header.  
        response.setHeader("Pragma", "no-cache");  
          
        // return a jpeg  
        response.setContentType("image/jpeg");  
          
        // create the text for the image  
        String capText = captchaProducer.createText();  
          
        // store the text in the session  
        session.setAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY, capText);  
          
        // create the image with the text  
        BufferedImage bi = captchaProducer.createImage(capText);  
        ServletOutputStream out = response.getOutputStream();  
          
        // write the data out  
        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }  
        return null;  
    }
    /**
     * 学生登出
     * @param request
     * @return
     */
    @RequestMapping(value = "/slogout", method = RequestMethod.GET)
    public ModelAndView slogout(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ModelAndView("redirect:/slogin");
    }
}
