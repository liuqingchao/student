package net.student.controller;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

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

import com.alibaba.fastjson.JSONObject;

import net.student.constants.Constants;
import net.student.constants.CustomerException;
import net.student.model.OffLinePaidLog;
import net.student.model.User;
import net.student.request.JqGridQuerier;
import net.student.response.JsonResult;
import net.student.response.PaymentView;
import net.student.response.QueryResult;
import net.student.service.IOffLinePaidLogService;
/**
 * 线下账单Controller类
 * @author liuqingchao
 *
 */
@RestController
@RequestMapping("/offline")
public class OffLinePaidLogController {
    static Logger logger = Logger.getLogger(OffLinePaidLogController.class);
    @Autowired
    private IOffLinePaidLogService offLinePaidLogService;
    @Autowired
    private ResourceBundleMessageSource messageSource;
    /**
     * 查询账单
     * @param request
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public QueryResult<PaymentView> getOffLines(JqGridQuerier<OffLinePaidLog, Integer> querier, HttpServletRequest request) {
    	User user = (User) request.getSession().getAttribute(Constants.SESSION_NAME);
        try {
            return offLinePaidLogService.queryOffLinePaidLogs(querier, user, true);
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
    public JsonResult importOffLines(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        JsonResult result = new JsonResult();
        User user = (User) request.getSession().getAttribute(Constants.SESSION_NAME);
        String confirmStr = request.getParameter("confirm");
        boolean confirm = false;
        if (StringUtils.isNotBlank(confirmStr) && confirmStr.equals("true")) {
        	confirm = true;
        }
        try {
        	if (confirm) {
        	    result = offLinePaidLogService.importOffLinePaidLogs((List<OffLinePaidLog>) request.getSession().getAttribute(Constants.SESSION_IMPORT_OFFLINE_NAME));
        	} else {
        	    result = offLinePaidLogService.importOffLinePaidLogs(file.getInputStream(), user, request.getSession());
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
     * @param oper
     * @param request
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public JsonResult editOffLine(String oper, HttpServletRequest request) {
        JsonResult result = new JsonResult();
        OffLinePaidLog offLinePaidLog = new OffLinePaidLog();
        if(oper.equals(JqGridQuerier.OPER_UPDATE) || oper.equals(JqGridQuerier.OPER_ADD)) {
        	String logId = request.getParameter("logId");
            String studentId = request.getParameter("studentId");
            String itemId = request.getParameter("itemId");
            String price = request.getParameter("price");
            String payDate = request.getParameter("payDate");
            if(oper.equals(JqGridQuerier.OPER_UPDATE)) {
            	offLinePaidLog.setLogId(Integer.valueOf(logId));
            } else if (oper.equals(JqGridQuerier.OPER_ADD)) {
            	offLinePaidLog.setCreatedDate(new Date());
            }
            offLinePaidLog.setStudentId(studentId);
            offLinePaidLog.setFeeItemId(itemId);
            offLinePaidLog.setPrice(Math.round(Double.valueOf(price)*1000));
            offLinePaidLog.setPaidFee(0L);
            try {
            	offLinePaidLog.setPayDate(DateUtils.parseDate(payDate + (payDate.length() > 10 ? "" : " 00:00:00"), new String[]{"yyyy-MM-dd HH:mm:ss"}));
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
        }
        try {
            if (oper.equals(JqGridQuerier.OPER_ADD)) {
            	offLinePaidLogService.saveOffLinePaidLog(offLinePaidLog);
            } else if (oper.equals(JqGridQuerier.OPER_UPDATE)) {
            	offLinePaidLogService.updateOffLinePaidLog(offLinePaidLog);
            } else if (oper.equals(JqGridQuerier.OPER_DEL)) {
            	offLinePaidLogService.deleteOffLinePaidLog(request.getParameter("id"));
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
     * 导出账单
     * @param querier
     * @param request
     * @return
     */
    @RequestMapping(value = "/excel", method = RequestMethod.POST)
    public JsonResult export(JqGridQuerier<OffLinePaidLog, Integer> querier, HttpServletRequest request) {
    	JsonResult result = new JsonResult();
    	User user = (User) request.getSession().getAttribute(Constants.SESSION_NAME);
        try {
        	QueryResult<PaymentView> queryResult = offLinePaidLogService.queryOffLinePaidLogs(querier, user, false);
        	if (queryResult.getTotal() > 0) {
        		List<PaymentView> list = queryResult.getRows();
        		HSSFWorkbook wb = new HSSFWorkbook();
        		HSSFSheet sheet = wb.createSheet("线下缴费信息列表");
        		HSSFRow firstRow = sheet.createRow(0);
        		firstRow.createCell(0).setCellValue("学号");
        		firstRow.createCell(1).setCellValue("姓名");
        		firstRow.createCell(2).setCellValue("部门");
        		firstRow.createCell(3).setCellValue("费用项目");
        		firstRow.createCell(4).setCellValue("金额(元)");
        		firstRow.createCell(5).setCellValue("缴费日期");
        		firstRow.createCell(6).setCellValue("创建时间");
        		int i = 1;
        		for (PaymentView paymentView : list) {
        			HSSFRow row = sheet.createRow(i);
        			row.createCell(0, HSSFCell.CELL_TYPE_STRING).setCellValue(paymentView.getStudentId());
        			row.createCell(1, HSSFCell.CELL_TYPE_STRING).setCellValue(paymentView.getStudentName());
        			row.createCell(2, HSSFCell.CELL_TYPE_STRING).setCellValue(paymentView.getDepartmentName());
        			row.createCell(3, HSSFCell.CELL_TYPE_STRING).setCellValue(paymentView.getItemName());
        			row.createCell(4, HSSFCell.CELL_TYPE_NUMERIC).setCellValue(BigDecimal.valueOf(Long.valueOf(paymentView.getPrice())/1000.00).toPlainString());
        			row.createCell(5, HSSFCell.CELL_TYPE_STRING).setCellValue(paymentView.getPayDate().substring(0, 10));;
        			row.createCell(6, HSSFCell.CELL_TYPE_STRING).setCellValue(paymentView.getCreatedDate().substring(0, 19));;
        			i++;
        		}
        		sheet.setDefaultColumnWidth(20);
        		String fileName = "export_offline_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".xls";
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
