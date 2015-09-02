package net.student.controller;

import java.util.Date;

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
import net.student.model.FeeItem;
import net.student.model.User;
import net.student.request.JqGridQuerier;
import net.student.response.JsonResult;
import net.student.response.QueryResult;
import net.student.service.IFeeItemService;
/**
 * 费用Controller类
 * @author liuqingchao
 *
 */
@RestController
@RequestMapping("/feeitem")
public class FeeItemController {
    static Logger logger = Logger.getLogger(FeeItemController.class);
    @Autowired
    private IFeeItemService feeItemService;
    @Autowired
    private ResourceBundleMessageSource messageSource;
    /**
     * 查询费用
     * @param request
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public QueryResult<FeeItem> getFeeItems(JqGridQuerier<FeeItem, String> querier) {
        try {
            return feeItemService.queryFeeItems(querier);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 编辑费用
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public JsonResult saveFeeItem(FeeItem feeItem, String oper, HttpServletRequest request) {
        JsonResult result = new JsonResult();
        try {
            if (oper.equals(JqGridQuerier.OPER_ADD)) {
            	feeItem.setCreatedDate(new Date());
                feeItemService.saveFeeItem(feeItem);
            } else if (oper.equals(JqGridQuerier.OPER_UPDATE)) {
            	feeItem.setLastUpdatedDate(new Date());
                feeItemService.updateFeeItem(feeItem);
            } else if (oper.equals(JqGridQuerier.OPER_DEL)) {
                feeItemService.delFeeItem(request.getParameter("id"));
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
     * 查询全部费用，用于下拉框选择
     * @param request
     * @return
     */
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public JSONObject getFeeItemsForSelect(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.SESSION_NAME);
        try {
            return feeItemService.selectFeeItems(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
