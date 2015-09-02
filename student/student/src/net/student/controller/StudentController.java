package net.student.controller;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis2.client.Options;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.CommonsTransportHeaders;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.wsdl.WSDLConstants;
import org.apache.commons.httpclient.Header;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;

import axis2.service.server.ICBCServiceStub;
import cn.edu.huat.pay.GetPayInfo;
import cn.edu.huat.pay.GetPayInfoResponse;
import cn.edu.huat.pay.PostOrder;
import cn.edu.huat.pay.PostOrderResponse;
import cn.edu.huat.pay.Verify;
import cn.edu.huat.pay.VerifyResponse;
import net.student.constants.Constants;
import net.student.constants.CustomerException;
import net.student.model.Payment;
import net.student.model.Student;
import net.student.model.User;
import net.student.request.JqGridQuerier;
import net.student.response.JsonResult;
import net.student.response.QueryResult;
import net.student.service.IPaymentService;
import net.student.service.IStudentService;
import net.student.service.impl.IndexService;
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
    private IPaymentService paymentService;
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
            return studentService.queryStudents(querier, true);
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
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/import", method = RequestMethod.POST)
    public JsonResult importStudents(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        JsonResult result = new JsonResult();
        User user = (User) request.getSession().getAttribute(Constants.SESSION_NAME);
        String confirmStr = request.getParameter("confirm");
        boolean confirm = false;
        if (StringUtils.isNotBlank(confirmStr) && confirmStr.equals("true")) {
        	confirm = true;
        }
        try {
        	if (confirm) {
        	    result = studentService.importStudents((List<Student>) request.getSession().getAttribute(Constants.SESSION_IMPORT_STUDENT_NAME));
        	} else {
        	    result = studentService.importStudents(file.getInputStream(), user, request.getSession());
        	}
        } catch (CustomerException e) {
            result.setSuccess(false);
            JSONObject json = new JSONObject();
            json.put("msg", messageSource.getMessage(e.getMessage(), null, request.getLocale()));
            result.setInfo(json);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            result.setSuccess(false);
            JSONObject json = new JSONObject();
            json.put("msg", ExceptionUtils.getRootCauseMessage(e));
            result.setInfo(json);
        }
        return result;
    }
    /**
     * 编辑学生信息
     * @param student
     * @param request
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public JsonResult saveStudent(Student student, String oper, HttpServletRequest request) {
        JsonResult result = new JsonResult();
        try {
            if (oper.equals(JqGridQuerier.OPER_ADD)) {
            	studentService.saveStudent(student);
            } else if (oper.equals(JqGridQuerier.OPER_UPDATE)) {
            	studentService.updateStudent(student);
            }
            result.setSuccess(true);
        } catch (CustomerException e) {
            result.setSuccess(false);
            JSONObject json = new JSONObject();
            json.put("msg", messageSource.getMessage(e.getMessage(), null, request.getLocale()));
            if (e.getMessage().equals("student.message.duplicateId")) {
            	json.put("confirm", true);
            }
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
    /**
     * 更新学生状态
     * @param student
     * @param request
     * @return
     */
    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public JsonResult updateStatus(String status, String studentIds, String departmentId, String year, HttpServletRequest request) {
        JsonResult result = new JsonResult();
        try {
            studentService.updateStudentStatus(status, studentIds, departmentId, year);
            result.setSuccess(true);
        } catch (CustomerException e) {
            result.setSuccess(false);
            JSONObject json = new JSONObject();
            json.put("msg", messageSource.getMessage(e.getMessage(), null, request.getLocale()));
            if (e.getMessage().equals("student.message.duplicateId")) {
                json.put("confirm", true);
            }
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
            JSONObject info = (JSONObject) JSONObject.toJSON(student);
            try {
				info.put("paymentInfo", paymentService.queryPaymentInfo(student.getStudentId(), request.getLocale()));
			} catch (Exception e) {
				e.printStackTrace();
				result.setSuccess(false);
			}
            result.setInfo(info);
            return result;
        }
    }
    /**
     * 转向订购
     * @param request
     * @return
     */
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public ModelAndView order(HttpServletRequest request) {
        if (request.getSession().getAttribute(Constants.SESSION_NAME) == null) {
            return new ModelAndView("redirect:/slogin");
        } else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("order");
            return mv;
        }
    }
    /**
     * 获得订购返回
     * @param request
     * @return
     */
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public Object getOrder(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute(Constants.SESSION_NAME) == null) {
            return new ModelAndView("redirect:/slogin");
        } else {
            String paymentId = request.getParameter("paymentId");
            try {
				Payment payment = paymentService.getPaymentById(Integer.valueOf(paymentId));
				ICBCServiceStub stub = new ICBCServiceStub();
				Verify verify = new Verify();
				verify.setClienID(Constants.WEBSERVICE_CLIENTID);
	            verify.setPasswrd(Constants.WEBSERVICE_PASSWORD);
	            VerifyResponse res = stub.verify(verify);
	            if (res.getVerifyResult()) {
	            	MessageContext msgCtx = stub._getServiceClient().getLastOperationContext().getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE);
	                CommonsTransportHeaders cth = (CommonsTransportHeaders) msgCtx.getProperty(MessageContext.TRANSPORT_HEADERS);
	                String cookie = (String) cth.get("Set-Cookie");
	                List<Object> list = new ArrayList<Object>();
	                Header header = new Header();  
	                header.setName("Cookie");  
	                header.setValue(cookie);      
	                list.add(header);  
	                Options options = stub._getServiceClient().getOptions();
	                options.setProperty(HTTPConstants.HTTP_HEADERS, list);  
	                stub._getServiceClient().setOptions(options);
	                if (StringUtils.isNotBlank(payment.getOrderId())) {
	                	GetPayInfo getPayInfo = new GetPayInfo();
	                    getPayInfo.setOrderID(payment.getOrderId());
	                    GetPayInfoResponse getPayInfoResponse = stub.getPayInfo(getPayInfo);
	                    if (getPayInfoResponse.getGetPayInfoResult()) {
	                    	paymentService.savePaidLog(payment.getOrderId(), request.getLocale());
	                    	response.setContentType("text/html;charset=UTF-8");
	    	                response.getWriter().write("<script  type=\"text/javascript\">alert(\"账单已支付，请关闭本页面并刷新首页\");</script>");
	                    	return null;
	                    }
	                }
	                PostOrder postOrder = new PostOrder();
	                postOrder.setOrderID(""+payment.getPaymentId() + (new Date()).getTime());
	                postOrder.setOrderUser(payment.getStudent().getStudentId());
	                postOrder.setItemID(payment.getFeeItem().getItemId());
	                postOrder.setItemName(payment.getStudent().getName() + ":" + payment.getFeeItem().getItemName());
	                postOrder.setAmount(String.valueOf((payment.getPrice() - payment.getPaidFee())/1000.00));
	                PostOrderResponse res2 = stub.postOrder(postOrder);
	                String returnHtml = res2.getPostOrderResult();
	                if (StringUtils.isNotBlank(returnHtml) && returnHtml.startsWith("<")) {
	                	payment.setOrderId(postOrder.getOrderID());
	                	payment.setLastCheckDate(new Date());
	                	paymentService.updatePaymentSimple(payment);
	                }
	                response.setContentType("text/html;charset=UTF-8");
	                response.getWriter().write(returnHtml);
	            }
			} catch (Exception e) {
				e.printStackTrace();
			}
        	return null;
        }
    }
    /**
     * 查询学生姓名
     * @param request
     * @return
     */
    @RequestMapping(value = "/name/{id}", method = RequestMethod.GET)
    public JsonResult getStudent(@PathVariable String id) {
    	JsonResult result = new JsonResult();
    	JSONObject info = new JSONObject();
        try {
        	Student student = studentService.getStudentBySId(id);
        	result.setSuccess(student != null);
        	if (student == null) {
        		info.put("name", "未找到匹配的学生姓名");
        	} else {
        		info.put("name", student.getName());
        	}
        	result.setInfo(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 导出账单
     * @param querier
     * @param request
     * @return
     */
    @RequestMapping(value = "/excel", method = RequestMethod.POST)
    public JsonResult export(JqGridQuerier<Student, String> querier, HttpServletRequest request) {
    	JsonResult result = new JsonResult();
        try {
        	QueryResult<Student> queryResult = studentService.queryStudents(querier, false);
        	if (queryResult.getTotal() > 0) {
        		List<Student> list = queryResult.getRows();
        		HSSFWorkbook wb = new HSSFWorkbook();
        		HSSFSheet sheet = wb.createSheet("学生信息列表");
        		HSSFRow firstRow = sheet.createRow(0);
        		firstRow.createCell(0).setCellValue("学号");
        		firstRow.createCell(1).setCellValue("姓名");
        		firstRow.createCell(2).setCellValue("身份证号");
        		firstRow.createCell(3).setCellValue("部门");
        		firstRow.createCell(4).setCellValue("状态");
        		firstRow.createCell(5).setCellValue("创建时间");
        		int i = 1;
        		for (Student student : list) {
        			HSSFRow row = sheet.createRow(i);
        			row.createCell(0, HSSFCell.CELL_TYPE_STRING).setCellValue(student.getStudentId());
        			row.createCell(1, HSSFCell.CELL_TYPE_STRING).setCellValue(student.getName());
        			row.createCell(2, HSSFCell.CELL_TYPE_STRING).setCellValue(student.getIdCardNum());
        			row.createCell(3, HSSFCell.CELL_TYPE_STRING).setCellValue(student.getDepartment().getDepartmentName());
        			row.createCell(4, HSSFCell.CELL_TYPE_STRING).setCellValue(IndexService.STUDENT_STATUS[student.getStatus()]);
        			row.createCell(5, HSSFCell.CELL_TYPE_STRING).setCellValue(DateFormatUtils.format(student.getCreatedDate(), "yyyy-MM-dd HH:mm:ss", request.getLocale()));
        			i++;
        		}
        		sheet.setDefaultColumnWidth(20);
        		String fileName = "export_student_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".xls";
        		OutputStream fileOut = new FileOutputStream(WebUtils.getRealPath(request.getServletContext(), "/") + "/" + fileName);
        		wb.write(fileOut);
        		fileOut.close();
        		wb.close();
        		result.setSuccess(true);
        		JSONObject json = new JSONObject();
                json.put("url", fileName);
                result.setInfo(json);
        	} else {
        		result.setSuccess(false);
                JSONObject json = new JSONObject();
                json.put("msg", messageSource.getMessage("student.message.notExist", null, request.getLocale()));
                result.setInfo(json);
        	}
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
