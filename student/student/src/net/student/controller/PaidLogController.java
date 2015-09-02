package net.student.controller;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;

import net.student.constants.Constants;
import net.student.constants.CustomerException;
import net.student.model.PaidLog;
import net.student.model.PayStat;
import net.student.model.User;
import net.student.request.JqGridQuerier;
import net.student.response.JsonResult;
import net.student.response.PaymentView;
import net.student.response.QueryResult;
import net.student.service.IPaidLogService;
/**
 * 账单Controller类
 * @author liuqingchao
 *
 */
@RestController
public class PaidLogController {
    static Logger logger = Logger.getLogger(PaidLogController.class);
    @Autowired
    private IPaidLogService paidLogService;
    @Autowired
    private ResourceBundleMessageSource messageSource;
    /**
     * 查询账单
     * @param request
     * @return
     */
    @RequestMapping(value = "/paidlog", method = RequestMethod.GET)
    public QueryResult<PaymentView> getPaidLogs(JqGridQuerier<PaidLog, Integer> querier, HttpServletRequest request) {
    	User user = (User) request.getSession().getAttribute(Constants.SESSION_NAME);
        try {
            return paidLogService.queryPaidLogs(querier, user, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 导出Excel
     * @param payment
     * @param request
     * @return
     */
    @RequestMapping(value = "/paidlog/excel", method = RequestMethod.POST)
    public JsonResult export(JqGridQuerier<PaidLog, Integer> querier, HttpServletRequest request) {
    	JsonResult result = new JsonResult();
    	User user = (User) request.getSession().getAttribute(Constants.SESSION_NAME);
        try {
        	QueryResult<PaymentView> queryResult = paidLogService.queryPaidLogs(querier, user, false);
        	if (queryResult.getTotal() > 0) {
        		List<PaymentView> list = queryResult.getRows();
        		HSSFWorkbook wb = new HSSFWorkbook();
        		HSSFSheet sheet = wb.createSheet("缴费信息列表");
        		HSSFRow firstRow = sheet.createRow(0);
        		firstRow.createCell(0).setCellValue("学号");
        		firstRow.createCell(1).setCellValue("姓名");
        		firstRow.createCell(2).setCellValue("部门");
        		firstRow.createCell(3).setCellValue("费用项目");
        		firstRow.createCell(4).setCellValue("原账单应缴金额(元)");
        		firstRow.createCell(5).setCellValue("原账单已缴金额(元)");
        		firstRow.createCell(6).setCellValue("账单金额(元)");
        		firstRow.createCell(7).setCellValue("支付日期");
        		firstRow.createCell(8).setCellValue("创建时间");
        		firstRow.createCell(9).setCellValue("支付订单号");
        		int i = 1;
        		for (PaymentView paymentView : list) {
        			HSSFRow row = sheet.createRow(i);
        			row.createCell(0, HSSFCell.CELL_TYPE_STRING).setCellValue(paymentView.getStudentId());
        			row.createCell(1, HSSFCell.CELL_TYPE_STRING).setCellValue(paymentView.getStudentName());
        			row.createCell(2, HSSFCell.CELL_TYPE_STRING).setCellValue(paymentView.getDepartmentName());
        			row.createCell(3, HSSFCell.CELL_TYPE_STRING).setCellValue(paymentView.getItemName());
        			row.createCell(4, HSSFCell.CELL_TYPE_NUMERIC).setCellValue(BigDecimal.valueOf(Long.valueOf(paymentView.getPrice())/1000.00).toPlainString());
        			row.createCell(5, HSSFCell.CELL_TYPE_NUMERIC).setCellValue(BigDecimal.valueOf(Long.valueOf(paymentView.getPaidFee())/1000.00).toPlainString());
        			row.createCell(6, HSSFCell.CELL_TYPE_NUMERIC).setCellValue(BigDecimal.valueOf((Long.valueOf(paymentView.getPrice())-Long.valueOf(paymentView.getPaidFee()))/1000.00).toPlainString());
        			row.createCell(7, HSSFCell.CELL_TYPE_STRING).setCellValue(paymentView.getPayDate().substring(0, 19));
        			row.createCell(8, HSSFCell.CELL_TYPE_STRING).setCellValue(paymentView.getCreatedDate().substring(0, 19));
        			row.createCell(9, HSSFCell.CELL_TYPE_STRING).setCellValue(paymentView.getSerialNo());
        			i++;
        		}
        		sheet.setDefaultColumnWidth(20);
        		String fileName = "export_paidlog_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".xls";
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
    /**
     * 查询统计
     * @param request
     * @return
     */
    @RequestMapping(value = "/paystat", method = RequestMethod.GET)
    public QueryResult<PayStat> getPayStats(JqGridQuerier<PayStat, Integer> querier, HttpServletRequest request) {
    	User user = (User) request.getSession().getAttribute(Constants.SESSION_NAME);
        try {
            return paidLogService.queryPayStats(querier, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 导出Excel
     * @param payment
     * @param request
     * @return
     */
    @RequestMapping(value = "/paystat/excel", method = RequestMethod.POST)
    public JsonResult exportPayStat(JqGridQuerier<PayStat, Integer> querier, HttpServletRequest request) {
    	JsonResult result = new JsonResult();
    	User user = (User) request.getSession().getAttribute(Constants.SESSION_NAME);
        try {
        	QueryResult<PayStat> queryResult = paidLogService.queryPayStats(querier, user);
        	if (queryResult.getTotal() > 0) {
        		List<PayStat> list = queryResult.getRows();
        		HSSFWorkbook wb = new HSSFWorkbook();
        		HSSFSheet sheet = wb.createSheet("缴费统计列表");
        		HSSFRow firstRow = sheet.createRow(0);
        		firstRow.createCell(0).setCellValue("统计日");
        		firstRow.createCell(1).setCellValue("费用项目");
        		firstRow.createCell(2).setCellValue("总金额(元)");
        		firstRow.createCell(3).setCellValue("总数");
        		firstRow.createCell(4).setCellValue("最近支付日期");
        		firstRow.createCell(5).setCellValue("最近支付订单号");
        		int i = 1;
        		for (PayStat payStat : list) {
        			HSSFRow row = sheet.createRow(i);
        			row.createCell(0, HSSFCell.CELL_TYPE_STRING).setCellValue(payStat.getStatDay());
        			row.createCell(1, HSSFCell.CELL_TYPE_STRING).setCellValue(payStat.getFeeItemName());
        			row.createCell(2, HSSFCell.CELL_TYPE_NUMERIC).setCellValue(BigDecimal.valueOf(Long.valueOf(payStat.getAmount())/1000.00).toPlainString());
        			row.createCell(3, HSSFCell.CELL_TYPE_NUMERIC).setCellValue(String.valueOf(payStat.getCount()));
        			row.createCell(4, HSSFCell.CELL_TYPE_STRING).setCellValue(DateFormatUtils.format(payStat.getLastPayDate(), "HH:mm:ss", request.getLocale()));
        			row.createCell(5, HSSFCell.CELL_TYPE_STRING).setCellValue(payStat.getLastOrderId());
        			i++;
        		}
        		sheet.setDefaultColumnWidth(20);
        		String fileName = "export_paystat_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".xls";
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
