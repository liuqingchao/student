package net.student.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.DeleteBuilder;

import net.student.constants.Constants;
import net.student.constants.CustomerException;
import net.student.model.FeeItem;
import net.student.model.OffLinePaidLog;
import net.student.model.PaidLog;
import net.student.model.Payment;
import net.student.model.Student;
import net.student.model.User;
import net.student.request.JqGridFilter;
import net.student.request.JqGridQuerier;
import net.student.request.JqGridRule;
import net.student.response.JsonResult;
import net.student.response.PaymentView;
import net.student.response.QueryResult;
import net.student.service.IOffLinePaidLogService;

/**
 * 账单Service实现类
 * 
 * @author liuqingchao
 *
 */
@Service
public class OffLinePaidLogService implements IOffLinePaidLogService {
	@Autowired
	private Dao<OffLinePaidLog, Integer> offLinePaidLogDao;
	@Autowired
	private Dao<Student, String> studentDao;
	@Autowired
	private Dao<FeeItem, String> feeItemDao;
	@Autowired
	private Dao<Payment, Integer> paymentDao;
	@Autowired
	private Dao<PaidLog, Integer> paidLogDao;

	@Override
	public QueryResult<PaymentView> queryOffLinePaidLogs(JqGridQuerier<OffLinePaidLog, Integer> querier, User user, boolean limit) throws Exception {
		Long page = querier.getPage();
		Long rows = querier.getRows();
		Long start = (page - 1) * rows;
		String sql1 = "select count(1),sum(p.price),sum(p.paidfee) from offlinepaidlog p,student s,department d,feeitem f"
				+ " where p.studentid=s.studentid and s.departmentid=d.departmentid and p.itemid=f.itemid";
		String sql2 = "select p.logid,p.studentid,s.name,d.departmentname,f.itemid,f.remark,p.price,p.paidfee,"
				+ "p.paydate,p.createddate,f.itemname"
				+ " from offlinepaidlog p,student s,department d,feeitem f"
				+ " where p.studentid=s.studentid and s.departmentid=d.departmentid and p.itemid=f.itemid";
		String str = "";
		if (user.getUserType() == 1) {
			if(user.getUserDepartments() != null && user.getUserDepartments().size() > 0) {
				str+= " and exists (select 1 from userdepartment ud where ud.departmentid=d.departmentid and ud.userid="+user.getUserId()+")";
			}
			if(user.getUserFeeItems() != null && user.getUserFeeItems().size() > 0) {
				str+= " and exists (select 1 from userfeeitem uf where uf.itemid=p.itemid and uf.userid="+user.getUserId()+")";
			}
		}
		if (StringUtils.isNotBlank(querier.getFilters())) {
            JSONObject jsonFilter = JSONObject.parseObject(querier.getFilters());
            JqGridFilter filter = JSONObject.toJavaObject(jsonFilter, JqGridFilter.class);
            if(filter.getRules() != null && !filter.getRules().isEmpty()) {
            	String[] rules = new String[filter.getRules().size()];
            	int i = 0;
            	for (JqGridRule rule : filter.getRules()) {
        			if (rule.getField().equals("studentid")) {
        				rules[i] = "p.studentid" + this.returnQueryParamValue(rule);;
        			} else if (rule.getField().equals("studentname")) {
        				rules[i] = "s.name" + this.returnQueryParamValue(rule);;
        			} else if (rule.getField().equals("departmentname")) {
        				rules[i] = "d.departmentid" + this.returnQueryParamValue(rule);;
        			} else if (rule.getField().equals("itemname")) {
        				rules[i] = "f.itemname" + this.returnQueryParamValue(rule);;
        			}
        			i++;
        		}
            	if (filter.getGroupOp().equals(JqGridQuerier.AND)) {
                	str = str + " and " + StringUtils.join(rules, " and ");
                } else {
                	str = str + " and (" + StringUtils.join(rules, " or ") + ")";
                }
            }
        }
		String orderBy = "";
		if (StringUtils.isNotBlank(querier.getSidx()) && StringUtils.isNotBlank(querier.getSord())) {
			orderBy = " order by ";
			if (querier.getSidx().equalsIgnoreCase("studentname")) {
				orderBy += "s.name ";
			} else if (querier.getSidx().equalsIgnoreCase("departmentname")) {
				orderBy += "d.departmentname ";
			} else if (querier.getSidx().equalsIgnoreCase("itemname")) {
				orderBy += "f.itemname ";
			} else {
				orderBy += "p."+querier.getSidx()+" ";
			}
			orderBy += querier.getSord();
		} else {
			orderBy = " order by p.logid desc";
		}
		QueryResult<PaymentView> queryResult = new QueryResult<PaymentView>();
		GenericRawResults<String[]> totalRaws = offLinePaidLogDao.queryRaw(sql1 + str);
		String[] totalRaw = totalRaws.getFirstResult();
		long totalRecord = Long.parseLong(totalRaw[0]);
		long totalPage = totalRecord % rows == 0 ? totalRecord / rows : totalRecord / rows + 1;
		queryResult.setTotal(totalPage);
		JSONObject userData = new JSONObject();
		userData.put("studentId", "总计:");
		userData.put("price", totalRaw[1]);
		userData.put("paidFee", totalRaw[2]);
		queryResult.setUserData(userData);
		List<PaymentView> list = new ArrayList<PaymentView>();
		GenericRawResults<String[]> rawResults = offLinePaidLogDao.queryRaw(sql2 + str + orderBy + (limit ? (" LIMIT " + start+ "," + rows) : ""));
		for (String[] raws : rawResults) {
			PaymentView paymentView = new PaymentView();
			paymentView.setPaymentId(raws[0]);
			paymentView.setStudentId(raws[1]);
			paymentView.setStudentName(raws[2]);
			paymentView.setDepartmentName(raws[3]);
			paymentView.setItemId(raws[4]);
			paymentView.setItemDesc(raws[5]);
			paymentView.setPrice(raws[6]);
			paymentView.setPaidFee(raws[7]);
			paymentView.setPayDate(raws[8].substring(0,10));
			paymentView.setCreatedDate(raws[9].substring(0,19));
			paymentView.setItemName(raws[10]);
			list.add(paymentView);
		}
		queryResult.setRecords(totalRecord);
		queryResult.setRows(list);
		queryResult.setPage(page);
		return queryResult;
	}

	@Override
	public JsonResult importOffLinePaidLogs(InputStream is, User user, HttpSession session) throws Exception {
		JsonResult result = new JsonResult();
		JSONObject jsonObject  = new JSONObject();
		JSONArray errorArray = new JSONArray();
		final List<OffLinePaidLog> offLinePaidLogs = new ArrayList<OffLinePaidLog>();
		GenericRawResults<String[]> studentIds = studentDao.queryRaw("select studentid from student where status=0");
		List<String> students = new ArrayList<String>();
		for (String[] studentId : studentIds) {
			students.add(studentId[0]);
		}
		List<FeeItem> feeItems = feeItemDao.queryForAll();
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
				OffLinePaidLog offLinePaidLog = new OffLinePaidLog();
				HSSFCell cell0 = row.getCell(0);
				HSSFCell cell1 = row.getCell(1);
				HSSFCell cell2 = row.getCell(2);
				HSSFCell cell3 = row.getCell(3);
				if (cell0 == null || cell1 == null || cell2 == null || cell3 == null) {
					errorArray.add("第" + (j + 1) + "行数据不完整");
					continue;
				}
				if (cell0.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					offLinePaidLog.setStudentId(StringUtils.trim(BigDecimal.valueOf(cell0.getNumericCellValue()).toPlainString()));
				} else {
					offLinePaidLog.setStudentId(StringUtils.trim(cell0.getStringCellValue()));
				}
				offLinePaidLog.setFeeItemId(StringUtils.trim(cell1.getStringCellValue()));
				if (cell2.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					offLinePaidLog.setPrice(Math.round(cell2.getNumericCellValue() * 1000));
				} else if (StringUtils.isNotBlank(cell2.getStringCellValue())){
					offLinePaidLog.setPrice(Math.round(Double.valueOf(cell2.getStringCellValue()) * 1000));
				}
				try {
					offLinePaidLog.setPayDate(DateUtils.parseDate(cell3.getStringCellValue() + " 00:00:00", new String[]{"yyyy-MM-dd HH:mm:ss"}));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (StringUtils.isBlank(offLinePaidLog.getStudentId()) || StringUtils.isBlank(offLinePaidLog.getFeeItemId())
						|| offLinePaidLog.getPayDate() == null) {
					errorArray.add("第" + (j + 1) + "行数据不完整");
					continue;
				}
				if (!students.contains(offLinePaidLog.getStudentId())) {
					errorArray.add("第" + (j + 1) + "行学号不存在:"+offLinePaidLog.getStudentId());
					continue;
				}
				FeeItem feeItem = new FeeItem();
				feeItem.setItemId(offLinePaidLog.getFeeItemId());
				if (!feeItems.contains(feeItem)) {
					errorArray.add("第" + (j + 1) + "行费用项目不存在:"+feeItem.getItemId());
					continue;
				}
				if(offLinePaidLog.getPrice() == null) {
					offLinePaidLog.setPrice(feeItems.get(feeItems.indexOf(feeItem)).getPrice());
				}
				if(offLinePaidLog.getPaidFee() == null) {
					offLinePaidLog.setPaidFee(0L);
				}
				offLinePaidLogs.add(offLinePaidLog);
			}
		}
		wb.close();
		is.close();
		Date now = new Date();
		if (offLinePaidLogs.isEmpty()) {
			result.setSuccess(false);
			jsonObject.put("msg", "excel文件数据不合法");
		} else {
			List<OffLinePaidLog> finalOffLinePaidLogList = new ArrayList<OffLinePaidLog>();
			for (OffLinePaidLog offLinePaidLog : offLinePaidLogs) {
				long count = offLinePaidLogDao.queryRawValue("select count(1) from offlinepaidlog where studentid=? and itemid=?",
						offLinePaidLog.getStudentId(), offLinePaidLog.getFeeItemId());
				if (count > 0) {
					errorArray.add("学号["+offLinePaidLog.getStudentId()+"]，费用项目["+offLinePaidLog.getFeeItemId()+"]已存在");
					continue;
				}
				count = paidLogDao.queryRawValue("select count(1) from paidlog where studentid=? and itemid=?",
						offLinePaidLog.getStudentId(), offLinePaidLog.getFeeItemId());
				if (count > 0) {
					errorArray.add("学号["+offLinePaidLog.getStudentId()+"]，费用项目["+offLinePaidLog.getFeeItemId()+"]已缴费");
					continue;
				}
				count = paymentDao.queryRawValue("select count(1) from payment where studentid=? and itemid=?",
						offLinePaidLog.getStudentId(), offLinePaidLog.getFeeItemId());
				if (count > 0) {
					errorArray.add("学号["+offLinePaidLog.getStudentId()+"]，费用项目["+offLinePaidLog.getFeeItemId()+"]存在未缴费记录");
					continue;
				}
				offLinePaidLog.setCreatedDate(now);
				finalOffLinePaidLogList.add(offLinePaidLog);
			}
			if (finalOffLinePaidLogList.size() > 0) {
				session.setAttribute(Constants.SESSION_IMPORT_OFFLINE_NAME, finalOffLinePaidLogList);
				result.setSuccess(true);
				jsonObject.put("msg", "将要生成"+finalOffLinePaidLogList.size()+"条未缴费信息，请确认?");
			} else {
				result.setSuccess(false);
			}
		}
		jsonObject.put("errors", errorArray);
		result.setInfo(jsonObject);
		return result;
	}

	@Override
	public void saveOffLinePaidLog(OffLinePaidLog offLinePaidLog) throws Exception {
		long count = studentDao.queryRawValue("select count(1) from student where studentid=?", offLinePaidLog.getStudentId());
		if (count == 0) {
			throw new CustomerException("student.message.notExist");
		}
		count = paymentDao.queryRawValue("select count(1) from payment where studentid=? and itemid=?",
				offLinePaidLog.getStudentId(), offLinePaidLog.getFeeItemId());
		if (count > 0) {
			throw new CustomerException("payment.message.duplicate");
		}
		count = paidLogDao.queryRawValue("select count(1) from paidlog where studentid=? and itemid=?",
				offLinePaidLog.getStudentId(), offLinePaidLog.getFeeItemId());
		if (count > 0) {
			throw new CustomerException("payment.message.alreadyPaid");
		}
		count = offLinePaidLogDao.queryRawValue("select count(1) from offlinepaidlog where studentid=? and itemid=?",
				offLinePaidLog.getStudentId(), offLinePaidLog.getFeeItemId());
		if (count > 0) {
			throw new CustomerException("payment.message.alreadyPaid");
		}
		offLinePaidLogDao.create(offLinePaidLog);
	}

	@Override
	public void updateOffLinePaidLog(OffLinePaidLog offLinePaidLog) throws Exception {
		long count = studentDao.queryRawValue("select count(1) from student where studentid=?", offLinePaidLog.getStudentId());
		if (count == 0) {
			throw new CustomerException("student.message.notExist");
		}
		count = paymentDao.queryRawValue("select count(1) from payment where studentid=? and itemid=?",
				offLinePaidLog.getStudentId(), offLinePaidLog.getFeeItemId());
		if (count > 0) {
			throw new CustomerException("payment.message.duplicate");
		}
		count = paidLogDao.queryRawValue("select count(1) from paidlog where studentid=? and itemid=?",
				offLinePaidLog.getStudentId(), offLinePaidLog.getFeeItemId());
		if (count > 0) {
			throw new CustomerException("payment.message.alreadyPaid");
		}
		count = offLinePaidLogDao.queryRawValue("select count(1) from offlinepaidlog where studentid=? and itemid=? and logid<>?",
				offLinePaidLog.getStudentId(), offLinePaidLog.getFeeItemId(), offLinePaidLog.getLogId().toString());
		if (count > 0) {
			throw new CustomerException("payment.message.alreadyPaid");
		}
		OffLinePaidLog oldOffLinePaidLog = offLinePaidLogDao.queryForId(offLinePaidLog.getLogId());
		offLinePaidLog.setCreatedDate(oldOffLinePaidLog.getCreatedDate());
		offLinePaidLogDao.update(offLinePaidLog);
	}
	
	@Override
	public JsonResult importOffLinePaidLogs(final List<OffLinePaidLog> offLinePaidLogs) throws Exception {
	    JsonResult result = new JsonResult();
        TransactionManager.callInTransaction(offLinePaidLogDao.getConnectionSource(), new Callable<Void>() {
            public Void call() throws Exception {
                for (OffLinePaidLog offLinePaidLog : offLinePaidLogs) {
                    offLinePaidLogDao.createOrUpdate(offLinePaidLog);
                }
                return null;
            }
        });
        result.setSuccess(true);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "新增线下缴费信息" + offLinePaidLogs.size() + "条");
        result.setInfo(jsonObject);
		return result;
	}

    @Override
    public void deleteOffLinePaidLog(String ids) throws Exception {
    	String[] offLinePaidLogIds = ids.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		for (String offLinePaidLogId : offLinePaidLogIds) {
			idList.add(Integer.parseInt(offLinePaidLogId));
		}
		DeleteBuilder<OffLinePaidLog, Integer> db = offLinePaidLogDao.deleteBuilder();
		db.where().in("logid", idList);
		offLinePaidLogDao.delete(db.prepare());
    }
	private String returnQueryParamValue(JqGridRule rule) {
		String val = "";
		if (rule.getOp().equals(JqGridQuerier.EQ)) {
			val = "='" + rule.getData() + "'";
        } else if (rule.getOp().equals(JqGridQuerier.NE)) {
        	val = "<>'" + rule.getData() + "'";
        } else if (rule.getOp().equals(JqGridQuerier.LT)) {
        	val = "<'" + rule.getData() + "'";
        } else if (rule.getOp().equals(JqGridQuerier.LE)) {
        	val = "<='" + rule.getData() + "'";
        } else if (rule.getOp().equals(JqGridQuerier.GT)) {
        	val = ">'" + rule.getData() + "'";
        } else if (rule.getOp().equals(JqGridQuerier.GE)) {
        	val = ">='" + rule.getData() + "'";
        } else if (rule.getOp().equals(JqGridQuerier.CN)) {
        	val = " like '%" + rule.getData() + "%'";
        } else if (rule.getOp().equals(JqGridQuerier.NC)) {
        	val = " not like '%" + rule.getData() + "%'";
        }
		return val;
	}
}
