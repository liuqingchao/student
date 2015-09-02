package net.student.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import net.student.constants.Constants;
import net.student.constants.CustomerException;
import net.student.model.FeeItem;
import net.student.model.User;
import net.student.request.JqGridQuerier;
import net.student.response.JsonResult;
import net.student.response.QueryResult;
import net.student.service.IFeeItemService;
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
    public JSON selectFeeItems(User user) throws Exception {
    	JSONArray selectResults = new JSONArray();
        JSONObject nameResult = new JSONObject();
        JSONObject priceResult = new JSONObject();
        List<FeeItem> list = null;
        if (user.getUserType() == User.USERTYPE_ADMIN) {
            list = feeItemDao.queryForAll();
        } else {
        	list = new ArrayList<FeeItem>();
        	GenericRawResults<String[]> rawResult = feeItemDao.queryRaw("select f.itemid, f.itemname, f.price from feeitem f"+
        			" where exists (select 1 from userfeeitem u where u.userid=? and u.itemid=f.itemid)", user.getUserId().toString());
        	for (String[] raws : rawResult) {
        		FeeItem feeItem = new FeeItem();
        		feeItem.setItemId(raws[0]);
        		feeItem.setItemName(raws[1]);
        		feeItem.setPrice(Long.valueOf(raws[2]));
        		list.add(feeItem);
        	}
        }
        if (list != null) {
        	DecimalFormat df = new DecimalFormat("0.00");
            for (FeeItem feeItem : list) {
            	nameResult.put(feeItem.getItemId(), feeItem.getItemName());
            	double price = feeItem.getPrice()/1000.00;
            	priceResult.put(feeItem.getItemId(), df.format(price));
            }
            selectResults.add(nameResult);
            selectResults.add(priceResult);
        }
        return selectResults;
    }

	@Override
	public JsonResult importFeeItems(InputStream is, HttpSession session) throws Exception {
		JsonResult result = new JsonResult();
		JSONObject jsonObject  = new JSONObject();
		JSONArray errorArray = new JSONArray();
		JSONArray updateArray = new JSONArray();
		final List<FeeItem> feeItems = new ArrayList<FeeItem>();
		HSSFWorkbook wb = new HSSFWorkbook(is);
		int sheetCount = wb.getNumberOfSheets();
		for (int i = 0; i < sheetCount; i++) {
			HSSFSheet sh = wb.getSheetAt(i);
			int rowCount = sh.getLastRowNum();
			for (int j = 1; j < rowCount + 1; j++) {
				HSSFRow row = sh.getRow(j);
				if(row == null) {
					continue;
				}
				FeeItem feeItem = new FeeItem();
				HSSFCell cell0 = row.getCell(0);
				HSSFCell cell1 = row.getCell(1);
				HSSFCell cell2 = row.getCell(2);
				HSSFCell cell3 = row.getCell(3);
				if (cell0 == null || cell1 == null || cell2 == null) {
					errorArray.add("第" + (j + 1) + "行数据不完整");
					continue;
				}
				if (cell0.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					feeItem.setItemId(StringUtils.trim(BigDecimal.valueOf(cell0.getNumericCellValue()).toPlainString()));
				} else {
					feeItem.setItemId(StringUtils.trim(cell0.getStringCellValue()));
				}
				feeItem.setItemName(StringUtils.trim(row.getCell(1).getStringCellValue()));
				if (cell2.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					feeItem.setPrice(Math.round(cell2.getNumericCellValue() * 1000));
				} else if (StringUtils.isNotBlank(cell2.getStringCellValue())){
					feeItem.setPrice(Math.round(Double.valueOf(cell2.getStringCellValue()) * 1000));
				}
				feeItem.setRemark(StringUtils.trim(cell3.getStringCellValue()));
				if (StringUtils.isBlank(feeItem.getItemId()) || StringUtils.isBlank(feeItem.getItemName())
						|| feeItem.getPrice() == null) {
					errorArray.add("第" + (j + 1) + "行数据不完整");
					continue;
				}
				feeItems.add(feeItem);
			}
		}
		wb.close();
		is.close();
		Date now = new Date();
		if (feeItems.isEmpty()) {
			result.setSuccess(false);
			jsonObject.put("msg", "excel文件数据不合法");
		} else {
			List<FeeItem> oldFeeItems = feeItemDao.queryForAll();
			int newCount = 0;
			for (FeeItem feeItem : feeItems) {
				int index = oldFeeItems.indexOf(feeItem);
				if (index != -1) {
					FeeItem oldOne = oldFeeItems.get(index);
					feeItem.setCreatedDate(oldOne.getCreatedDate());
					feeItem.setLastUpdatedDate(now);
					updateArray.add(feeItem.getItemId());
				} else {
					feeItem.setCreatedDate(now);
					newCount++;
				}
			}
			if (updateArray.size() > 0) {
				session.setAttribute(Constants.SESSION_IMPORT_FEEITEM_NAME, feeItems);
				result.setSuccess(false);
			} else {
				TransactionManager.callInTransaction(feeItemDao.getConnectionSource(), new Callable<Void>() {
		            public Void call() throws Exception {
		            	for (FeeItem feeItem : feeItems) {
		            		feeItemDao.create(feeItem);
		        		}
		                return null;
		            }
		        });
				result.setSuccess(true);
				jsonObject.put("msg", "新增收费项目" + newCount + "条");
			}
		}
		jsonObject.put("errors", errorArray);
		jsonObject.put("confirms", updateArray);
		result.setInfo(jsonObject);
		return result;
	}

	@Override
	public JsonResult importFeeItems(final List<FeeItem> feeItems) throws Exception {
		JsonResult result = new JsonResult();
	    List<FeeItem> oldFeeItemList = feeItemDao.queryForAll();
        int newCount = 0,updateCount = 0;
        Date now = new Date();
        for (FeeItem feeItem : feeItems) {
            int index = oldFeeItemList.indexOf(feeItem);
            if (index != -1) {
            	FeeItem oldOne = oldFeeItemList.get(index);
            	feeItem.setCreatedDate(oldOne.getCreatedDate());
            	feeItem.setLastUpdatedDate(now);
                updateCount++;
            } else {
            	feeItem.setCreatedDate(now);
                newCount++;
            }
        }
        TransactionManager.callInTransaction(feeItemDao.getConnectionSource(), new Callable<Void>() {
            public Void call() throws Exception {
                for (FeeItem feeItem : feeItems) {
                	feeItemDao.createOrUpdate(feeItem);
                }
                return null;
            }
        });
        result.setSuccess(true);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "新增收费项目" + newCount + "条, 更新收费项目" + updateCount + "条");
        result.setInfo(jsonObject);
		return result;
	}
}
