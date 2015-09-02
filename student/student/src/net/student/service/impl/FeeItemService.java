package net.student.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.student.constants.CustomerException;
import net.student.model.FeeItem;
import net.student.model.User;
import net.student.request.JqGridQuerier;
import net.student.response.QueryResult;
import net.student.service.IFeeItemService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
/**
 * 费用Service实现类
 * @author 果冻
 *
 */
@Service
public class FeeItemService implements IFeeItemService {
	@Autowired
	private Dao<FeeItem, String> feeItemDao;
	
	@Override
	public QueryResult<FeeItem> queryFeeItems(JqGridQuerier<FeeItem, String> querier) throws Exception {
		Long page = querier.getPage();
		Long rows = querier.getRows();
		Long start = (page - 1) * rows;
		QueryBuilder<FeeItem, String> queryBuilder = feeItemDao.queryBuilder();
		Where<FeeItem, String> where = queryBuilder.where();
		if (StringUtils.isNotBlank(querier.getSidx()) && StringUtils.isNotBlank(querier.getSord())) {
			if (querier.getSord().equals(JqGridQuerier.ASC)) {
				queryBuilder.orderBy(querier.getSidx(), true);
			} else {
				queryBuilder.orderBy(querier.getSidx(), false);
			}
		} else {
			queryBuilder.orderBy("itemId", false);
		}
		int count = querier.transferQueryCondition(where);
		if (count ==0) {
			where.raw("1=1");
		}
		
		QueryResult<FeeItem> queryResult = new QueryResult<FeeItem>();
		long totalRecord = feeItemDao.countOf(queryBuilder.setCountOf(true).prepare());
		long totalPage = totalRecord % rows == 0 ? totalRecord / rows : totalRecord / rows + 1;
		queryResult.setTotal(totalPage);
		List<FeeItem> list = feeItemDao.query(queryBuilder.setCountOf(false).limit(rows).offset(start).prepare());
		queryResult.setRecords(totalRecord);
		queryResult.setRows(list);
		queryResult.setPage(page);
		return queryResult;
	}

	@Override
	public void saveFeeItem(FeeItem feeItem) throws Exception {
		long count = feeItemDao.queryRawValue("select count(1) from feeitem where itemid=?", feeItem.getItemId());
		if (count > 0) {
			throw new CustomerException("feeItem.message.duplicateId");
		}
		count = feeItemDao.queryRawValue("select count(1) from feeitem where itemname=?", feeItem.getItemName());
		if (count > 0) {
			throw new CustomerException("feeItem.message.duplicateName");
		}
		feeItemDao.create(feeItem);
	}

	@Override
	public void updateFeeItem(FeeItem feeItem) throws Exception {
		long count = feeItemDao.queryRawValue("select count(1) from feeitem where itemname=? and itemid<>?", feeItem.getItemName(), feeItem.getItemId());
		if (count > 0) {
			throw new CustomerException("feeItem.message.duplicateName");
		}
		FeeItem oldOne = feeItemDao.queryForId(feeItem.getItemId());
		feeItem.setCreatedDate(oldOne.getCreatedDate());
		feeItemDao.update(feeItem);
	}

	@Override
	public void delFeeItem(String ids) throws Exception {
		String[] feeItemIds = ids.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		for (String feeItemId : feeItemIds) {
			long count = feeItemDao.queryRawValue("select count(1) from payment where itemid = " + feeItemId);
			if (count == 0) {
				count = feeItemDao.queryRawValue("select count(1) from userfeeitem where itemid = " + feeItemId);
				if (count == 0) {
					count = feeItemDao.queryRawValue("select count(1) from paidlog where itemid = " + feeItemId);
				}
				if (count > 0) {
					continue;
				}
			} else {
				continue;
			}
			idList.add(Integer.parseInt(feeItemId));
		}
		if (idList.size() == 0) {
			throw new CustomerException("commmon.message.constraintException", "");
		}
		PreparedQuery<FeeItem> criteria = feeItemDao.queryBuilder().where().in("itemId", idList).prepare();
		List<FeeItem> list = feeItemDao.query(criteria);
		feeItemDao.delete(list);
	}

    @Override
    public JSONObject selectFeeItems(User user) throws Exception {
        JSONObject selectResult = new JSONObject();
        List<FeeItem> list = null;
        if (user.getUserType() == User.USERTYPE_ADMIN) {
            list = feeItemDao.queryForAll();
        } else {
//            list = departmentDao.
        }
        if (list != null) {
            for (FeeItem feeItem : list) {
                selectResult.put(feeItem.getItemId(), feeItem.getItemName());
            }
        }
        return selectResult;
    }
}
