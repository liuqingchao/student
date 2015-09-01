package net.student.service;

import com.alibaba.fastjson.JSONObject;

import net.student.model.FeeItem;
import net.student.model.User;
import net.student.request.JqGridQuerier;
import net.student.response.QueryResult;

/**
 * 费用Service接口类
 * @author liuqingchao
 *
 */
public interface IFeeItemService {
    /**
     * 查询费用
     * @param querier
     * @return
     */
    public QueryResult<FeeItem> queryFeeItems(JqGridQuerier<FeeItem, String> querier) throws Exception;
    
    /**
     * 新增费用
     * @param feeItem
     * @throws Exception
     */
    public void saveFeeItem(FeeItem feeItem) throws Exception;
    /**
     * 修改费用
     * @param feeItem
     * @throws Exception
     */
    public void updateFeeItem(FeeItem feeItem) throws Exception;
    /**
     * 删除费用
     * @param ids
     * @throws Exception
     */
    public void delFeeItem(String ids) throws Exception;
    /**
     * 费用下拉框
     * @param user
     * @return
     */
    public JSONObject selectFeeItems(User user) throws Exception;
}
