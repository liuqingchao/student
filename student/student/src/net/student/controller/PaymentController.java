package net.student.controller;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.student.constants.Constants;
import net.student.constants.CustomerException;
import net.student.model.FeeItem;
import net.student.model.Payment;
import net.student.model.Student;
import net.student.model.User;
import net.student.request.JqGridQuerier;
import net.student.response.JsonResult;
import net.student.response.PaymentView;
import net.student.response.QueryResult;
import net.student.service.IPaymentService;
/**
 * 账单Controller类
 * @author liuqingchao
 *
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {
    static Logger logger = Logger.getLogger(PaymentController.class);
    @Autowired
    private IPaymentService paymentService;
    @Autowired
    private ResourceBundleMessageSource messageSource;
    /**
     * 查询账单
     * @param request
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public QueryResult<PaymentView> getPayments(JqGridQuerier<Payment, Integer> querier, HttpServletRequest request) {
    	User user = (User) request.getSession().getAttribute(Constants.SESSION_NAME);
        try {
            return paymentService.queryPayments(querier, user, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 导入账单
     * @param user
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/import", method = RequestMethod.POST)
    public JsonResult importPayments(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        JsonResult result = new JsonResult();
        User user = (User) request.getSession().getAttribute(Constants.SESSION_NAME);
        String confirmStr = request.getParameter("confirm");
        boolean confirm = false;
        if (StringUtils.isNotBlank(confirmStr) && confirmStr.equals("true")) {
        	confirm = true;
        }
        try {
        	if (confirm) {
        	    result = paymentService.importPayments((List<Payment>) request.getSession().getAttribute(Constants.SESSION_IMPORT_PAYMENT_NAME));
        	} else {
        	    result = paymentService.importPayments(file.getInputStream(), user, request.getSession());
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
     * 编辑账单信息
     * @param payment
     * @param request
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public JsonResult editPayment(String oper, HttpServletRequest request) {
    	User user = (User) request.getSession().getAttribute(Constants.SESSION_NAME);
        JsonResult result = new JsonResult();
        Payment payment = new Payment();
        if(oper.equals(JqGridQuerier.OPER_UPDATE) || oper.equals(JqGridQuerier.OPER_ADD)) {
        	String paymentId = request.getParameter("paymentId");
            String studentId = request.getParameter("studentId");
            String feeItemId = request.getParameter("itemId");
            String price = request.getParameter("price");
            String paidFee = request.getParameter("paidFee");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            if(oper.equals(JqGridQuerier.OPER_UPDATE)) {
            	payment.setPaymentId(Integer.valueOf(paymentId));
            	payment.setLastUpdatedBy(user);
            	payment.setLastUpdatedDate(new Date());
            } else if (oper.equals(JqGridQuerier.OPER_ADD)) {
            	payment.setCreatedBy(user);
            	payment.setCreatedDate(new Date());
            }
            Student student = new Student();
            student.setStudentId(studentId);
            payment.setStudent(student);
            FeeItem feeItem = new FeeItem();
            feeItem.setItemId(feeItemId);
            payment.setFeeItem(feeItem);
            payment.setPrice(Math.round(Double.valueOf(price)*1000));
            payment.setPaidFee(Math.round(Double.valueOf(paidFee)*1000));
            try {
    			payment.setStartDate(DateUtils.parseDate(startDate + " 00:00:00", new String[]{"yyyy-MM-dd HH:mm:ss"}));
    			payment.setEndDate(DateUtils.parseDate(endDate + " 23:59:59", new String[]{"yyyy-MM-dd HH:mm:ss"}));
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
        }
        try {
            if (oper.equals(JqGridQuerier.OPER_ADD)) {
            	paymentService.savePayment(payment);
            } else if (oper.equals(JqGridQuerier.OPER_UPDATE)) {
            	paymentService.updatePayment(payment);
            } else if (oper.equals(JqGridQuerier.OPER_DEL)) {
            	paymentService.deletePayment(request.getParameter("id"));
            }
            result.setSuccess(true);
        } catch (CustomerException e) {
            result.setSuccess(false);
            JSONObject json = new JSONObject();
            json.put("msg", messageSource.getMessage(e.getMessage(), null, request.getLocale()));
            if (e.getMessage().equals("payment.message.duplicateId")) {
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
     * 批量生成账单信息
     * @param payment
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/batch", method = RequestMethod.POST)
    public JsonResult batchPayment(HttpServletRequest request) {
        JsonResult result = new JsonResult();
        JSONObject json = new JSONObject();
        String confirm = request.getParameter("confirm");
        try {
            if ("true".equals(confirm)) {
            	List<Payment> list = (List<Payment>) WebUtils.getSessionAttribute(request, Constants.SESSION_BATCH_PAYMENT_NAME);
            	paymentService.batchPayments(list);
            	json.put("msg", "成功产生" + list.size() + "条未缴费信息");
            	result.setInfo(json);
            } else {
            	Map<String, Object> params = WebUtils.getParametersStartingWith(request, "");
            	User user = (User) request.getSession().getAttribute(Constants.SESSION_NAME);
            	params.put(Constants.SESSION_NAME, user);
            	Map<String, Object> returnObj = paymentService.generatePayments(params);
            	if (returnObj == null) {
            		result.setSuccess(false);
            		json.put("msg", messageSource.getMessage("student.message.notExist", null, request.getLocale()));
            		result.setInfo(json);
            		return result;
            	}
            	List<Payment> list = (List<Payment>) returnObj.get(Constants.SESSION_BATCH_PAYMENT_NAME);
            	int count = 0;
            	if (list != null && !list.isEmpty()) {
            		count = list.size();
            		result.setSuccess(true);
            		JSONArray studentIds = (JSONArray) returnObj.get("studentIds");
            		if (studentIds.size() == 0) {
            			json.put("msg", "产生未缴费信息"+count+"条,是否确认生成？");
            		} else {
            			json.put("msg", "产生未缴费信息"+count+"条，学号["+studentIds.toString()+"]已存在缴费信息,是否确认生成？");
            		}
            		WebUtils.setSessionAttribute(request, Constants.SESSION_BATCH_PAYMENT_NAME, list);
            	} else {
            		result.setSuccess(false);
            		json.put("msg", "未缴费信息已存在");
            	}
            	result.setInfo(json);
        		return result;
            }
            result.setSuccess(true);
        } catch (CustomerException e) {
            result.setSuccess(false);
            json.put("msg", messageSource.getMessage(e.getMessage(), null, request.getLocale()));
            result.setInfo(json);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setSuccess(false);
            json.put("msg", ExceptionUtils.getRootCauseMessage(e));
            result.setInfo(json);
        }
        return result;
    }
    /**
     * 支付完成
     * @param payment
     * @param request
     * @return
     */
    @RequestMapping(value = "/paid", method = RequestMethod.POST)
    public JsonResult savePaid(String orderId, HttpServletRequest request) {
    	JsonResult result = new JsonResult();
    	Object user = request.getSession().getAttribute(Constants.SESSION_NAME);
    	if (user == null) {
    		result.setSuccess(false);
            JSONObject json = new JSONObject();
            json.put("msg", messageSource.getMessage("payment.message.notExist", null, request.getLocale()));
            result.setInfo(json);
            logger.error("&&&&&&&&&&&&orderid["+orderId+"] paid falid, because no session");
            return result;
    	}
        try {
        	paymentService.savePaidLog(orderId, request.getLocale());
            result.setSuccess(true);
            logger.info("&&&&&&&&&&&&orderid["+orderId+"] paid succeed");
        } catch (CustomerException e) {
            result.setSuccess(false);
            JSONObject json = new JSONObject();
            json.put("msg", messageSource.getMessage(e.getMessage(), null, request.getLocale()));
            result.setInfo(json);
            logger.error("&&&&&&&&&&&&orderid["+orderId+"] paid falid", e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setSuccess(false);
            JSONObject json = new JSONObject();
            json.put("msg", ExceptionUtils.getRootCauseMessage(e));
            result.setInfo(json);
            logger.error("&&&&&&&&&&&&orderid["+orderId+"] paid falid", e);
        }
        return result;
    }
    /**
     * 支付完成
     * @param payment
     * @param request
     * @return
     */
    @RequestMapping(value = "/excel", method = RequestMethod.POST)
    public JsonResult export(JqGridQuerier<Payment, Integer> querier, HttpServletRequest request) {
    	JsonResult result = new JsonResult();
    	User user = (User) request.getSession().getAttribute(Constants.SESSION_NAME);
        try {
        	QueryResult<PaymentView> queryResult = paymentService.queryPayments(querier, user, false);
        	if (queryResult.getTotal() > 0) {
        		List<PaymentView> list = queryResult.getRows();
        		HSSFWorkbook wb = new HSSFWorkbook();
        		HSSFSheet sheet = wb.createSheet("未缴费信息列表");
        		HSSFRow firstRow = sheet.createRow(0);
        		firstRow.createCell(0).setCellValue("学号");
        		firstRow.createCell(1).setCellValue("姓名");
        		firstRow.createCell(2).setCellValue("部门");
        		firstRow.createCell(3).setCellValue("费用项目");
        		firstRow.createCell(4).setCellValue("应收金额(元)");
        		firstRow.createCell(5).setCellValue("已交金额(元)");
        		firstRow.createCell(6).setCellValue("缴费开始日期");
        		firstRow.createCell(7).setCellValue("缴费结束日期");
        		firstRow.createCell(8).setCellValue("创建时间");
        		int i = 1;
        		for (PaymentView paymentView : list) {
        			HSSFRow row = sheet.createRow(i);
        			row.createCell(0, HSSFCell.CELL_TYPE_STRING).setCellValue(paymentView.getStudentId());
        			row.createCell(1, HSSFCell.CELL_TYPE_STRING).setCellValue(paymentView.getStudentName());
        			row.createCell(2, HSSFCell.CELL_TYPE_STRING).setCellValue(paymentView.getDepartmentName());
        			row.createCell(3, HSSFCell.CELL_TYPE_STRING).setCellValue(paymentView.getItemName());
        			row.createCell(4, HSSFCell.CELL_TYPE_NUMERIC).setCellValue(BigDecimal.valueOf(Long.valueOf(paymentView.getPrice())/1000.00).toPlainString());
        			row.createCell(5, HSSFCell.CELL_TYPE_NUMERIC).setCellValue(BigDecimal.valueOf(Long.valueOf(paymentView.getPaidFee())/1000.00).toPlainString());
        			row.createCell(6, HSSFCell.CELL_TYPE_STRING).setCellValue(paymentView.getStartDate().substring(0, 10));;
        			row.createCell(7, HSSFCell.CELL_TYPE_STRING).setCellValue(paymentView.getEndDate().substring(0, 10));;
        			row.createCell(8, HSSFCell.CELL_TYPE_STRING).setCellValue(paymentView.getCreatedDate().substring(0, 19));;
        			i++;
        		}
        		sheet.setDefaultColumnWidth(20);
        		String fileName = "export_payment_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".xls";
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
                json.put("msg", messageSource.getMessage("payment.message.notExist", null, request.getLocale()));
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
