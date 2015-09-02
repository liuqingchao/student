package net.student.request;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import com.alibaba.fastjson.JSONObject;
import com.j256.ormlite.stmt.Where;

/**
 * jqGrid查询参数类
 * @author liuqingchao
 */
public class JqGridQuerier<T, PK> {
    public static final String ASC = "asc";
    public static final String DESC = "desc";
    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String OPER_ADD = "add";
    public static final String OPER_UPDATE = "edit";
    public static final String OPER_DEL = "del";
    public static final String EQ = "eq";
    public static final String NE = "ne";
    public static final String LT = "lt";
    public static final String LE = "le";
    public static final String GT = "gt";
    public static final String GE = "ge";
    public static final String CN = "cn";
    public static final String NC = "nc";
    private String _search;
    private String sidx;// 排序字段
    private String sord;// 排序顺序asc/desc
    private Long page;// 查询页数
    private Long rows;// 查询每页记录数
    private String filters;// 过滤条件

    public int transferQueryCondition(Where<T, PK> where) {
        if (StringUtils.isNotBlank(filters)) {
            JSONObject jsonFilter = JSONObject.parseObject(this.filters);
            JqGridFilter filter = JSONObject.toJavaObject(jsonFilter, JqGridFilter.class);
            if(filter.getRules() != null && !filter.getRules().isEmpty()) {
            	try {
    				this.toCriterion(filter, where);
    				return filter.getRules().size();
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
            }
        }
        return 0;
    }

    private void toCriterion(JqGridFilter filter, Where<T, PK> where) throws Exception {
        for (JqGridRule rule : filter.getRules()) {
        	if (rule.getField().startsWith("_")) {
        		continue;
        	}
            if (rule.getOp().equals(EQ) || rule.getOp().equals(NE) || rule.getOp().equals(LT)
                || rule.getOp().equals(LE) || rule.getOp().equals(GT) || rule.getOp().equals(GE)) {
                this.getDateCritrion(rule, where);
            } else if (rule.getOp().equals(CN)) {
            	where.like(rule.getField(), "%" + rule.getData().toString() + "%");
            } else if (rule.getOp().equals(NC)) {
            	where.not(where.like(rule.getField(), "%" + rule.getData().toString() + "%"));
            }
        }
        if (filter.getGroupOp().equals(AND)) {
        	where.and(filter.getRules().size());
        } else {
        	where.or(filter.getRules().size());
        }
    }

    @SuppressWarnings("unchecked")
	private void getDateCritrion(JqGridRule rule, Where<T, PK> where) throws Exception {
        if (rule.getField().endsWith("Date")) {
        	Date startDate = DateUtils.parseDate(rule.getData().toString() + " 00:00:00", new String[]{"yyyy-MM-dd HH:mm:ss"});
            String startDateStr = DateFormatUtils.formatUTC(startDate, "yyyy-MM-dd HH:mm:ss");
            Date utcStartDate = DateUtils.parseDate(startDateStr, new String[]{"yyyy-MM-dd HH:mm:ss"});
            Date endDate = DateUtils.parseDate(rule.getData().toString() + " 23:59:59", new String[]{"yyyy-MM-dd HH:mm:ss"});
            String enDateStr = DateFormatUtils.formatUTC(endDate, "yyyy-MM-dd HH:mm:ss");
            Date utcEndDate = DateUtils.parseDate(enDateStr, new String[]{"yyyy-MM-dd HH:mm:ss"});
            if (rule.getOp().equals(EQ)) {
            	where.and(where.ge(rule.getField(), utcStartDate), where.le(rule.getField(), utcEndDate));
            } else if (rule.getOp().equals(NE)) {
            	where.or(where.lt(rule.getField(), utcStartDate), where.gt(rule.getField(), utcEndDate));
            } else if (rule.getOp().equals(LT)) {
            	where.lt(rule.getField(), utcStartDate);
            } else if (rule.getOp().equals(LE)) {
            	where.le(rule.getField(), utcEndDate);
            } else if (rule.getOp().equals(GT)) {
            	where.gt(rule.getField(), utcEndDate);
            } else if (rule.getOp().equals(GE)) {
            	where.ge(rule.getField(), utcStartDate);
            }
        } else {
            Object data = rule.getData();
            if (rule.getField().equals("status") || rule.getField().endsWith("Type")) {
                data = Integer.valueOf(data.toString());
            }
            if (rule.getOp().equals(EQ)) {
            	where.eq(rule.getField(), data);
            } else if (rule.getOp().equals(NE)) {
            	where.ne(rule.getField(), data);
            } else if (rule.getOp().equals(LT)) {
            	where.lt(rule.getField(), data);
            } else if (rule.getOp().equals(LE)) {
            	where.le(rule.getField(), data);
            } else if (rule.getOp().equals(GT)) {
            	where.gt(rule.getField(), data);
            } else if (rule.getOp().equals(GE)) {
            	where.ge(rule.getField(), data);
            }
        }
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getRows() {
        return rows;
    }

    public void setRows(Long rows) {
        this.rows = rows;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String get_search() {
        return _search;
    }

    public void set_search(String _search) {
        this._search = _search;
    }
}
