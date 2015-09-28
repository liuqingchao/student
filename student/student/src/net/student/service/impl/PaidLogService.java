package net.student.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;

import net.student.model.PaidLog;
import net.student.model.PayStat;
import net.student.model.User;
import net.student.request.JqGridFilter;
import net.student.request.JqGridQuerier;
import net.student.request.JqGridRule;
import net.student.response.PaymentView;
import net.student.response.QueryResult;
import net.student.service.IPaidLogService;

/**
 * 账单Service实现类
 * 
 * @author liuqingchao
 *
 */
@Service
public class PaidLogService implements IPaidLogService {
	@Autowired
	private Dao<PaidLog, Integer> paidLogDao;

	@Override
	public QueryResult<PaymentView> queryPaidLogs(JqGridQuerier<PaidLog, Integer> querier, User user, boolean limit) throws Exception {
		Long page = querier.getPage();
		Long rows = querier.getRows();
		Long start = (page - 1) * rows;
		String sql1 = "select count(1),sum(p.price),sum(p.paidfee) from paidlog p,student s,department d,feeitem f"
				+ " where p.studentid=s.studentid and s.departmentid=d.departmentid and p.itemid=f.itemid";
		String sql2 = "select p.studentid,s.name,d.departmentname,f.itemid,f.remark,p.price,p.paidfee,"
				+ "p.createddate,p.paydate, p.serialno,f.itemname from paidlog p,student s,department d,feeitem f"
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
        			if (rule.getField().equals("studentId")) {
        				rules[i] = "p.studentid" + this.returnQueryParamValue(rule);;
        			} else if (rule.getField().equals("studentname")) {
        				rules[i] = "s.name" + this.returnQueryParamValue(rule);;
        			} else if (rule.getField().equals("departmentname")) {
        				rules[i] = "d.departmentid" + this.returnQueryParamValue(rule);;
        			} else if (rule.getField().equals("itemid")) {
        				rules[i] = "f.itemid" + this.returnQueryParamValue(rule);;
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
			} else if (querier.getSidx().equalsIgnoreCase("amount")) {
				orderBy += "(p.price-p.paidfee) ";
			} else {
				orderBy += "p."+querier.getSidx()+" ";
			}
			orderBy += querier.getSord();
		} else {
			orderBy = " order by p.logid desc";
		}
		QueryResult<PaymentView> queryResult = new QueryResult<PaymentView>();
		GenericRawResults<String[]> totalRaws = paidLogDao.queryRaw(sql1 + str);
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
		GenericRawResults<String[]> rawResults = paidLogDao.queryRaw(sql2 + str + orderBy + (limit ? (" LIMIT " + start+ "," + rows) : ""));
		for (String[] raws : rawResults) {
			PaymentView paymentView = new PaymentView();
			paymentView.setStudentId(raws[0]);
			paymentView.setStudentName(raws[1]);
			paymentView.setDepartmentName(raws[2]);
			paymentView.setItemId(raws[3]);
			paymentView.setItemDesc(raws[4]);
			paymentView.setPrice(raws[5]);
			paymentView.setPaidFee(raws[6]);
			paymentView.setCreatedDate(raws[7].substring(0,19));
			paymentView.setPayDate(raws[8].substring(0,19));
			paymentView.setSerialNo(raws[9]);
			paymentView.setItemName(raws[10]);
			list.add(paymentView);
		}
		queryResult.setRecords(totalRecord);
		queryResult.setRows(list);
		queryResult.setPage(page);
		return queryResult;
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
	public QueryResult<PayStat> queryPayStats(JqGridQuerier<PayStat, Integer> querier, User user) throws Exception {
		Long page = querier.getPage();
		Long rows = querier.getRows();
		Long start = (page - 1) * rows;
		String sql1 = "select count(1) from paystat p,feeitem f where p.itemid=f.itemid";
		String sql2 = "select p.statday,f.itemid,f.remark,p.amount,p.count,"
				+ "p.lastpaydate, p.lastorderid,f.itemname from paystat p,feeitem f"
				+ " where p.itemid=f.itemid";
		String str = "";
		if (user.getUserType() == 1) {
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
        			if (rule.getField().equals("statday")) {
        				rules[i] = "p.statday" + this.returnQueryParamValue(rule);;
        			} else if (rule.getField().equals("itemname")) {
        				rules[i] = "f.itemid" + this.returnQueryParamValue(rule);;
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
			if (querier.getSidx().equalsIgnoreCase("itemname")) {
				orderBy += "f.itemname ";
			} else {
				orderBy += "p."+querier.getSidx()+" ";
			}
			orderBy += querier.getSord();
		} else {
			orderBy = " order by p.statid desc";
		}
		QueryResult<PayStat> queryResult = new QueryResult<PayStat>();
		long totalRecord = paidLogDao.queryRawValue(sql1 + str);
		long totalPage = totalRecord % rows == 0 ? totalRecord / rows : totalRecord / rows + 1;
		queryResult.setTotal(totalPage);
		List<PayStat> list = new ArrayList<PayStat>();
		GenericRawResults<String[]> rawResults = paidLogDao.queryRaw(sql2 + str + orderBy + " LIMIT " + start+ "," + rows);
		for (String[] raws : rawResults) {
			PayStat payStat = new PayStat();
			payStat.setStatDay(raws[0]);
			payStat.setFeeItemId(raws[1]);
			payStat.setFeeItemDesc(raws[2]);
			payStat.setAmount(Long.valueOf(raws[3]));
			payStat.setCount(Integer.valueOf(raws[4]));
			payStat.setLastPayDate(DateUtils.parseDate(raws[5].substring(0,19), new String[]{"yyyy-MM-dd HH:mm:ss"}));
			payStat.setLastOrderId(raws[6]);
			payStat.setFeeItemName(raws[7]);
			list.add(payStat);
		}
		queryResult.setRecords(totalRecord);
		queryResult.setRows(list);
		queryResult.setPage(page);
		return queryResult;
	}
}
