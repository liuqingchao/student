package net.student.service.impl;

import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;

import net.student.model.PaidLog;
import net.student.model.PayStat;
import net.student.model.Payment;
import net.student.model.Student;
import net.student.service.IIndexService;
/**
 * 控制台信息实现类
 * @author liuqingchao
 *
 */
@Service
public class IndexService implements IIndexService {
	@Autowired
	private Dao<Student, String> studentDao;
	@Autowired
	private Dao<Payment, Integer> paymentDao;
	@Autowired
	private Dao<PaidLog, Integer> paidLogDao;
	@Autowired
	private Dao<PayStat, Integer> payStatDao;
	
	private static final String[] CHART_COLORS = new String[]{"#68BC31", "#2091CF", "#AF4E96", "#DA5430", "#FEE074"};
	public static final String[] STUDENT_STATUS = new String[]{"在校", "休学", "退学", "毕业"}; 
    
    @Override
    public JSONObject getStatInfo(Locale locale) throws Exception {
        JSONObject json = new JSONObject();
        json.put("studentCount", studentDao.countOf());
        json.put("paymentCount", paymentDao.countOf());
        json.put("paymentAmount", paymentDao.queryRawValue("select sum(price)-sum(paidfee) from payment"));
        Date now = new Date();
        String statDay = DateFormatUtils.format(now, "yyyy-MM-dd", locale);
        GenericRawResults<String[]> rawResults = payStatDao.queryRaw("select sum(count),sum(amount),max(lastpaydate) from paystat where statday=?", statDay);
        String[] results = rawResults.getFirstResult();
        json.put("todayCount", StringUtils.isNotBlank(results[0]) ? results[0] : "0");
        json.put("todayAmount", StringUtils.isNotBlank(results[1]) ? results[1] : "0");
        json.put("lastPayDate", results[2]);
        if (StringUtils.isNotBlank(results[2])) {
        	String lastOrderId = payStatDao.queryRaw("select max(lastorderid) from paystat where lastpaydate=?", results[2]).getFirstResult()[0];
        	json.put("lastOrderId", lastOrderId);
        	PaidLog paidLog = paidLogDao.queryForFirst(paidLogDao.queryBuilder().where().eq("serialno", lastOrderId).prepare());
        	json.put("studentId", paidLog.getStudent().getStudentId());
        	json.put("studentName", paidLog.getStudent().getName());
        	json.put("idCardNum", paidLog.getStudent().getIdCardNum());
        	json.put("departmentName", paidLog.getStudent().getDepartment().getDepartmentName());
        	json.put("feeItemName", paidLog.getFeeItem().getItemName());
        	json.put("feeItemDesc", paidLog.getFeeItem().getRemark());
        	json.put("price", paidLog.getPrice());
        	json.put("paidfee", paidLog.getPaidFee());
        	json.put("payDate", DateFormatUtils.format(paidLog.getPayDate(), "HH:mm:ss", locale));
        } else {
        	json.put("lastOrderId", "");
        }
        return json;
    }

	@Override
	public JSONObject getStudentInfo(String type) throws Exception {
		JSONObject data = new JSONObject();
		JSONArray array = new JSONArray();
		GenericRawResults<String[]> rawResults = null;
		int i = 0;
		if (type.equals("status")) {
			rawResults = payStatDao.queryRaw("select status,count(1) ss from student group by status order by ss desc");
			for (String[] items : rawResults) {
				JSONObject item = new JSONObject();
				item.put("label", STUDENT_STATUS[Integer.valueOf(items[0])]);
				item.put("data", items[1]);
				item.put("color", CHART_COLORS[i]);
				array.add(item);
				i++;
			}
		} else if (type.equals("year")) {
			rawResults = payStatDao.queryRaw("select substr(studentid,0,5) st,count(1) ss from student group by st order by ss desc");
			JSONObject other = new JSONObject();
			other.put("label", "其他");
			other.put("data", 0);
			other.put("color", CHART_COLORS[4]);
			for (String[] items : rawResults) {
				if (i >= 4) {
					other.put("data", Integer.valueOf(items[1]) + other.getIntValue("data"));
				} else {
					JSONObject item = new JSONObject();
					item.put("label", items[0]);
					item.put("data", Integer.valueOf(items[1]));
					item.put("color", CHART_COLORS[i]);
					array.add(item);
				}
				i++;
			}
			array.add(other);
		} else {
			rawResults = payStatDao.queryRaw("select d.departmentname,count(1) ss from student s,department d where s.departmentid=d.departmentid group by d.departmentname order by ss desc");
			JSONObject other = new JSONObject();
			other.put("label", "其他");
			other.put("data", 0);
			other.put("color", CHART_COLORS[4]);
			for (String[] items : rawResults) {
				if (i >= 4) {
					other.put("data", Integer.valueOf(items[1]) + other.getIntValue("data"));
				} else {
					JSONObject item = new JSONObject();
					item.put("label", items[0]);
					item.put("data", Integer.valueOf(items[1]));
					item.put("color", CHART_COLORS[i]);
					array.add(item);
				}
				i++;
			}
			array.add(other);
		}
		data.put("data", array);
		return data;
	}

	@Override
	public JSONObject getFeeItemInfo(Locale locale) throws Exception {
		JSONObject data = new JSONObject();
		JSONArray array = new JSONArray();
		Date now = new Date();
        String statDay = DateFormatUtils.format(now, "yyyy-MM-dd", locale);
        GenericRawResults<String[]> rawResults = payStatDao.queryRaw("select f.itemname,f.remark,p.amount,p.count from paystat p,feeitem f where p.itemid=f.itemid and statday=?", statDay);
        for (String[] items : rawResults) {
        	JSONObject item = new JSONObject();
        	item.put("feeItemName", items[0]);
        	item.put("feeItemDesc", items[1]);
        	item.put("amount", items[2]);
        	item.put("count", items[3]);
        	array.add(item);
        }
        data.put("data", array);
        return data;
	}

}
