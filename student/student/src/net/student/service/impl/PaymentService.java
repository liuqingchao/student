package net.student.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpSession;

import net.student.constants.Constants;
import net.student.constants.CustomerException;
import net.student.model.FeeItem;
import net.student.model.PaidLog;
import net.student.model.PayStat;
import net.student.model.Payment;
import net.student.model.PaymentOrder;
import net.student.model.Student;
import net.student.model.User;
import net.student.request.JqGridFilter;
import net.student.request.JqGridQuerier;
import net.student.request.JqGridRule;
import net.student.response.JsonResult;
import net.student.response.PaymentView;
import net.student.response.QueryResult;
import net.student.service.IPaymentService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
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
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

/**
 * 账单Service实现类
 * 
 * @author liuqingchao
 *
 */
@Service
public class PaymentService implements IPaymentService {
    static Logger logger = Logger.getLogger(PaymentService.class);
	@Autowired
	private Dao<Payment, Integer> paymentDao;
	@Autowired
	private Dao<Student, String> studentDao;
	@Autowired
	private Dao<FeeItem, String> feeItemDao;
	@Autowired
	private Dao<PaidLog, Integer> paidLogDao;
	@Autowired
	private Dao<PayStat, Integer> payStatDao;
	@Autowired
    private Dao<PaymentOrder, Integer> paymentOrderDao;


	@Override
	public QueryResult<PaymentView> queryPayments(JqGridQuerier<Payment, Integer> querier, User user, boolean limit) throws Exception {
		Long page = querier.getPage();
		Long rows = querier.getRows();
		Long start = (page - 1) * rows;
		String sql1 = "select count(1),sum(p.price),sum(p.paidfee) from payment p,student s,department d,feeitem f"
				+ " where p.studentid=s.studentid and s.departmentid=d.departmentid and p.itemid=f.itemid";
		String sql2 = "select p.paymentid,p.studentid,s.name,d.departmentname,f.itemid,f.remark,p.price,p.paidfee,"
				+ "p.startdate,p.enddate,p.createddate,p.lastupdateddate,f.itemname"
				+ " from payment p,student s,department d,feeitem f"
				+ " where p.studentid=s.studentid and s.departmentid=d.departmentid and p.itemid=f.itemid";
		String str = "";
		if (user.getUserType() == 1) {
			if(user.getUserDepartments() != null && user.getUserDepartments().size() > 0) {
				str+= " and exists (select 1 from userdepartment ud where ud.departmentid=d.departmentid and userid="+user.getUserId()+")";
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
        			if (rule.getField().equals("_isPayDate")) {
        				if (rule.getData().equals("0")) {
        					rules[i] = " p.enddate<=CURRENT_TIMESTAMP";
        				} else {
        					rules[i] = " p.enddate>CURRENT_TIMESTAMP";
        				}
        			} else if (rule.getField().equals("studentId")) {
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
			orderBy = " order by p.paymentid desc";
		}
		QueryResult<PaymentView> queryResult = new QueryResult<PaymentView>();
		GenericRawResults<String[]> totalRaws = paymentDao.queryRaw(sql1 + str);
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
		GenericRawResults<String[]> rawResults = paymentDao.queryRaw(sql2 + str + orderBy + (limit ? (" LIMIT " + start+ "," + rows) : ""));
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
			paymentView.setStartDate(raws[8].substring(0,10));
			paymentView.setEndDate(raws[9].substring(0,10));
			paymentView.setCreatedDate(raws[10].substring(0,19));
			paymentView.setLastUpdateDate(StringUtils.isNotBlank(raws[11]) ? raws[11].substring(0,19) : "");
			paymentView.setItemName(raws[12]);
			list.add(paymentView);
		}
		queryResult.setRecords(totalRecord);
		queryResult.setRows(list);
		queryResult.setPage(page);
		return queryResult;
	}

	@Override
	public JsonResult importPayments(InputStream is, User user, HttpSession session) throws Exception {
		JsonResult result = new JsonResult();
		JSONObject jsonObject  = new JSONObject();
		JSONArray errorArray = new JSONArray();
		final List<Payment> payments = new ArrayList<Payment>();
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
				Payment payment = new Payment();
				Student student = new Student();
				HSSFCell cell0 = row.getCell(0);
				HSSFCell cell1 = row.getCell(1);
				HSSFCell cell2 = row.getCell(2);
				HSSFCell cell3 = row.getCell(3);
				HSSFCell cell4 = row.getCell(4);
				HSSFCell cell5 = row.getCell(5);
				if (cell0 == null || cell1 == null || cell2 == null || cell3 == null || cell4 == null || cell5 == null) {
					errorArray.add("第" + (j + 1) + "行数据不完整");
					continue;
				}
				if (cell0.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					student.setStudentId(StringUtils.trim(BigDecimal.valueOf(cell0.getNumericCellValue()).toPlainString()));
				} else {
					student.setStudentId(StringUtils.trim(cell0.getStringCellValue()));
				}
				payment.setStudent(student);
				FeeItem feeItem = new FeeItem();
				feeItem.setItemId(StringUtils.trim(cell1.getStringCellValue()));
				payment.setFeeItem(feeItem);
				if (cell2.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					payment.setPrice(Math.round(cell2.getNumericCellValue() * 1000));
				} else if (StringUtils.isNotBlank(cell2.getStringCellValue())){
					payment.setPrice(Math.round(Double.valueOf(cell2.getStringCellValue()) * 1000));
				}
				if (cell3.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					payment.setPaidFee(Math.round(cell3.getNumericCellValue() * 1000));
				} else if (StringUtils.isNotBlank(cell3.getStringCellValue())) {
					payment.setPaidFee(Math.round(Double.valueOf(cell3.getStringCellValue()) * 1000));
				}
				try {
					payment.setStartDate(DateUtils.parseDate(cell4.getStringCellValue() + " 00:00:00", new String[]{"yyyy-MM-dd HH:mm:ss"}));
					payment.setEndDate(DateUtils.parseDate(cell5.getStringCellValue() + " 23:59:59", new String[]{"yyyy-MM-dd HH:mm:ss"}));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (StringUtils.isBlank(payment.getStudent().getStudentId()) || StringUtils.isBlank(payment.getFeeItem().getItemId())
						|| payment.getStartDate() == null || payment.getEndDate() == null) {
					errorArray.add("第" + (j + 1) + "行数据不完整");
					continue;
				}
				if (!students.contains(student.getStudentId())) {
					errorArray.add("第" + (j + 1) + "行学号不存在:"+student.getStudentId());
					continue;
				}
				if (!feeItems.contains(feeItem)) {
					errorArray.add("第" + (j + 1) + "行费用项目不存在:"+feeItem.getItemId());
					continue;
				}
				if(payment.getPrice() == null) {
					payment.setPrice(feeItems.get(feeItems.indexOf(feeItem)).getPrice());
				}
				if(payment.getPaidFee() == null) {
					payment.setPaidFee(0L);
				}
				payments.add(payment);
			}
		}
		wb.close();
		is.close();
		Date now = new Date();
		if (payments.isEmpty()) {
			result.setSuccess(false);
			jsonObject.put("msg", "excel文件数据不合法");
		} else {
			List<Payment> finalPaymentList = new ArrayList<Payment>();
			for (Payment payment : payments) {
				long count = paymentDao.queryRawValue("select count(1) from payment where studentid=? and itemid=?",
						payment.getStudent().getStudentId(), payment.getFeeItem().getItemId());
				if (count > 0) {
					errorArray.add("学号["+payment.getStudent().getStudentId()+"]，费用项目["+payment.getFeeItem().getItemId()+"]已存在");
					continue;
				}
				count = paidLogDao.queryRawValue("select count(1) from paidlog where studentid=? and itemid=?",
						payment.getStudent().getStudentId(), payment.getFeeItem().getItemId());
				if (count > 0) {
					errorArray.add("学号["+payment.getStudent().getStudentId()+"]，费用项目["+payment.getFeeItem().getItemId()+"]已缴费");
					continue;
				}
				payment.setCreatedDate(now);
				payment.setCreatedBy(user);
				finalPaymentList.add(payment);
			}
			if (finalPaymentList.size() > 0) {
				session.setAttribute(Constants.SESSION_IMPORT_PAYMENT_NAME, finalPaymentList);
				result.setSuccess(true);
				jsonObject.put("msg", "将要生成"+finalPaymentList.size()+"条未缴费信息，请确认?");
			} else {
				result.setSuccess(false);
			}
		}
		jsonObject.put("errors", errorArray);
		result.setInfo(jsonObject);
		return result;
	}

	@Override
	public void savePayment(Payment payment) throws Exception {
		long count = studentDao.queryRawValue("select count(1) from student where studentid=?", payment.getStudent().getStudentId());
		if (count == 0) {
			throw new CustomerException("student.message.notExist");
		}
		count = paymentDao.queryRawValue("select count(1) from payment where studentid=? and itemid=?",
				payment.getStudent().getStudentId(), payment.getFeeItem().getItemId());
		if (count > 0) {
			throw new CustomerException("payment.message.duplicate");
		}
		count = paidLogDao.queryRawValue("select count(1) from paidlog where studentid=? and itemid=?",
				payment.getStudent().getStudentId(), payment.getFeeItem().getItemId());
		if (count > 0) {
			throw new CustomerException("payment.message.alreadyPaid");
		}
		count = paidLogDao.queryRawValue("select count(1) from offlinepaidlog where studentid=? and itemid=?",
				payment.getStudent().getStudentId(), payment.getFeeItem().getItemId());
		if (count > 0) {
			throw new CustomerException("payment.message.alreadyPaid");
		}
		paymentDao.create(payment);
	}

	@Override
	public void updatePayment(Payment payment) throws Exception {
		long count = studentDao.queryRawValue("select count(1) from student where studentid=?", payment.getStudent().getStudentId());
		if (count == 0) {
			throw new CustomerException("student.message.notExist");
		}
		count = paymentDao.queryRawValue("select count(1) from payment where studentid=? and itemid=? and paymentid<>?",
				payment.getStudent().getStudentId(), payment.getFeeItem().getItemId(), payment.getPaymentId().toString());
		if (count > 0) {
			throw new CustomerException("payment.message.duplicate");
		}
		count = paidLogDao.queryRawValue("select count(1) from paidlog where studentid=? and itemid=?",
				payment.getStudent().getStudentId(), payment.getFeeItem().getItemId());
		if (count > 0) {
			throw new CustomerException("payment.message.alreadyPaid");
		}
		count = paidLogDao.queryRawValue("select count(1) from offlinepaidlog where studentid=? and itemid=?",
				payment.getStudent().getStudentId(), payment.getFeeItem().getItemId());
		if (count > 0) {
			throw new CustomerException("payment.message.alreadyPaid");
		}
		Payment oldPayment = paymentDao.queryForId(payment.getPaymentId());
		payment.setCreatedDate(oldPayment.getCreatedDate());
		payment.setCreatedBy(oldPayment.getCreatedBy());
		paymentDao.update(payment);
	}
	
	@Override
	public JsonResult importPayments(final List<Payment> payments) throws Exception {
	    JsonResult result = new JsonResult();
        TransactionManager.callInTransaction(paymentDao.getConnectionSource(), new Callable<Void>() {
            public Void call() throws Exception {
                for (Payment payment : payments) {
                    paymentDao.createOrUpdate(payment);
                }
                return null;
            }
        });
        result.setSuccess(true);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "新增未缴费信息" + payments.size() + "条");
        result.setInfo(jsonObject);
		return result;
	}

    @Override
    public void deletePayment(String ids) throws Exception {
    	String[] paymentIds = ids.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		for (String paymentId : paymentIds) {
			idList.add(Integer.parseInt(paymentId));
		}
		DeleteBuilder<Payment, Integer> db = paymentDao.deleteBuilder();
		db.where().in("paymentid", idList);
		DeleteBuilder<PaymentOrder, Integer> db2 = paymentOrderDao.deleteBuilder();
        db.where().in("paymentid", idList);
		paymentDao.delete(db.prepare());
		paymentOrderDao.delete(db2.prepare());
    }

	@Override
	public Map<String, Object> generatePayments(Map<String, Object> params) throws Exception {
		Map<String, Object> returnObj = new HashMap<String, Object>();
		String departmentIds = (String) params.get("departmentIds");
        String startStudentId = (String) params.get("studentIds_start");
        String endStudentId = (String) params.get("studentIds_end");
        String feeItemId = (String) params.get("feeItemId");
        String startDate = (String) params.get("startDate");
        String endDate = (String) params.get("endDate");
        User user = (User) params.get(Constants.SESSION_NAME);
        QueryBuilder<Student, String> stuQuery = studentDao.queryBuilder();
        Where<Student, String> stuWhere = stuQuery.where();
        stuWhere.eq("status", 0);
        if (StringUtils.isNotBlank(departmentIds)) {
        	stuWhere.and();
        	stuWhere.in("departmentid", departmentIds.split(","));
        } else {
        	if (StringUtils.isNotBlank(startStudentId)) {
        		stuWhere.and();
        		stuWhere.ge("studentId", startStudentId);
        	}
        	if (StringUtils.isNotBlank(endStudentId)) {
        		stuWhere.and();
        		stuWhere.le("studentId", startStudentId);
        	}
        }
        List<Student> students = studentDao.query(stuWhere.prepare());
        if (students == null || students.isEmpty()) {
        	return null;
        }
        List<Payment> list = new ArrayList<Payment>();
        Date now = new Date();
        JSONArray info = new JSONArray();
        for (Student student : students) {
        	long count = paymentDao.queryRawValue("select count(1) from payment where studentid=? and itemid=?",
    				student.getStudentId(), feeItemId);
    		if (count > 0) {
    			info.add(student.getStudentId());
    			continue;
    		} else {
    			count = paidLogDao.queryRawValue("select count(1) from paidlog where studentid=? and itemid=?",
    					student.getStudentId(), feeItemId);
    			if (count > 0) {
            		info.add(student.getStudentId());
            		continue;
            	} else {
            		count = paidLogDao.queryRawValue("select count(1) from offlinepaidlog where studentid=? and itemid=?",
        					student.getStudentId(), feeItemId);
            		if (count > 0) {
                		info.add(student.getStudentId());
                		continue;
                	}
            	}
    		}
    		Payment payment = new Payment();
    		payment.setStudent(student);
    		FeeItem feeItem = feeItemDao.queryForId(feeItemId);
    		payment.setFeeItem(feeItem);
    		payment.setPrice(feeItem.getPrice());
    		payment.setPaidFee(0L);
    		payment.setStartDate(DateUtils.parseDate(startDate + " 00:00:00", new String[]{"yyyy-MM-dd HH:mm:ss"}));
    		payment.setEndDate(DateUtils.parseDate(endDate + " 23:59:59", new String[]{"yyyy-MM-dd HH:mm:ss"}));
    		payment.setCreatedBy(user);
    		payment.setCreatedDate(now);
    		list.add(payment);
        }
        returnObj.put(Constants.SESSION_BATCH_PAYMENT_NAME, list);
        returnObj.put("studentIds", info);
		return returnObj;
	}

	@Override
	public void batchPayments(final List<Payment> list) throws Exception {
		TransactionManager.callInTransaction(paymentDao.getConnectionSource(), new Callable<Void>() {
            public Void call() throws Exception {
                for (Payment payment : list) {
                    paymentDao.create(payment);
                }
                return null;
            }
        });
	}

	@Override
	public JSONObject queryPaymentInfo(String studentId, Locale locale) throws Exception {
		Student student = studentDao.queryForId(studentId);
		JSONObject info = new JSONObject();
		if (student.getStatus() == 0) {
			JSONArray items = new JSONArray();
			List<Payment> list = paymentDao.queryForEq("studentid", studentId);
			Date now = new Date();
			for (Payment payment : list) {
					JSONObject item = new JSONObject();
					item.put("paymentId", payment.getPaymentId());
					item.put("itemName", payment.getFeeItem().getItemName());
					item.put("amount", payment.getPrice() - payment.getPaidFee());
					item.put("desc", payment.getFeeItem().getRemark());
					item.put("startDate", DateFormatUtils.format(payment.getStartDate(), "yyyy-MM-dd", locale));
					item.put("endDate", DateFormatUtils.format(payment.getEndDate(), "yyyy-MM-dd", locale));
					if (payment.getStartDate().compareTo(now) <= 0 && payment.getEndDate().compareTo(now) >=0) {
						item.put("active", true);
					} else if (payment.getEndDate().compareTo(now) < 0) {
						item.put("active", false);
					} else {
						continue;
					}
					items.add(item);
			}
			info.put("to_do", items);
		}
		GenericRawResults<String[]> raws = paidLogDao.queryRaw("select p.paydate paydate,f.itemname,f.remark,"
				+ "p.price,p.paidfee,p.serialno from paidlog p,feeitem f where p.itemid=f.itemid and p.studentid=?"
				+ " union select p.paydate paydate,f.itemname,f.remark,p.price,p.paidfee,p.serialno"
				+ " from offlinepaidlog p,feeitem f where p.itemid=f.itemid and p.studentid=? order by paydate desc", studentId, studentId);
		JSONArray items = new JSONArray();
		for (String[] raw : raws) {
			JSONObject item = new JSONObject();
			item.put("payDate", raw[0].substring(0,  19));
			item.put("itemName", raw[1]);
			item.put("desc", raw[2]);
			item.put("amount", Long.valueOf(raw[3]) - Long.valueOf(raw[4]));
			item.put("online", StringUtils.isNotBlank(raw[5]));
			items.add(item);
		}
		
		info.put("history", items);
		return info;
	}

	@Override
	public Payment getPaymentById(Integer paymentId) throws Exception {
		return paymentDao.queryForId(paymentId);
	}

	@Override
	public void updatePaymentSimple(Payment payment) throws Exception {
		paymentDao.update(payment);
	}

	@Override
	public void savePaidLog(String orderId, Locale locale) throws Exception {
		Payment payment = paymentDao.queryForFirst(paymentDao.queryBuilder().where().eq("orderid", orderId).prepare());
		if (payment == null) {
		    logger.info("*********in savePaidLog, payment == null by orderId ,maybe merged,query agaign by paymentId");
		    payment = paymentDao.queryForId(Integer.valueOf(orderId.substring(0, orderId.length() - 13)));
		    if (payment == null) {
		        throw new CustomerException("payment.message.notExist");
		    } else {
		        logger.info("*********in savePaidLog, payment found by paymentId");
		    }
		}
		Date now = new Date();
		final PaidLog paidLog = new PaidLog();
    	paidLog.setStudent(payment.getStudent());
    	paidLog.setFeeItem(payment.getFeeItem());
    	paidLog.setPrice(payment.getPrice());
    	paidLog.setPaidFee(payment.getPaidFee());
        long orderCount =
            paymentOrderDao.countOf(paymentOrderDao.queryBuilder().setCountOf(true).where().eq("paymentid", payment.getPaymentId())
                .prepare());
        if (orderCount == 0) {
            paidLog.setPayDate(payment.getLastCheckDate() == null ? now : payment.getLastCheckDate());
        } else {
            paidLog.setPayDate(now);
        }
    	paidLog.setCreatedDate(now);
    	paidLog.setSerialNo(orderId);
    	String statDay = DateFormatUtils.format(now, "yyyy-MM-dd", locale);
    	PayStat payStat = payStatDao.queryForFirst(payStatDao.queryBuilder().where().eq("statday", statDay).and().eq("itemid", payment.getFeeItem().getItemId()).prepare());
    	if (payStat == null) {
    		payStat = new PayStat();
    		payStat.setStatDay(statDay);
    		payStat.setFeeItemId(payment.getFeeItem().getItemId());
    		payStat.setCount(0);
    		payStat.setAmount(0L);
    	}
    	payStat.setCount(payStat.getCount() + 1);
    	payStat.setAmount(payStat.getAmount() + payment.getPrice() - payment.getPaidFee());
    	payStat.setLastOrderId(orderId);
    	payStat.setLastPayDate(now);
    	final PayStat fPayStat = payStat;
    	final Payment fPayment = payment;
    	final DeleteBuilder<PaymentOrder, Integer> db = paymentOrderDao.deleteBuilder();
        db.where().eq("paymentid", payment.getPaymentId());
    	TransactionManager.callInTransaction(paymentDao.getConnectionSource(), new Callable<Void>() {
            public Void call() throws Exception {
            	paidLogDao.create(paidLog);
            	paymentOrderDao.delete(db.prepare());
            	paymentDao.delete(fPayment);
            	payStatDao.createOrUpdate(fPayStat);
                return null;
            }
        });
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

    @Override
    public void savePaymentOrder(PaymentOrder paymentOrder) throws Exception {
        paymentOrderDao.create(paymentOrder);
    }

    @Override
    public PaymentOrder getLastPaymentOrder(Integer paymentId) throws Exception {
        QueryBuilder<PaymentOrder, Integer> qb = paymentOrderDao.queryBuilder();
        qb.where().eq("paymentid", paymentId);
        qb.orderBy("paymentorderid", false);
        return paymentOrderDao.queryForFirst(qb.prepare());
    }
}
