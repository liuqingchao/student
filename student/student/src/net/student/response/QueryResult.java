package net.student.response;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * jqGrid返回结果
 * @author liuqingchao
 *
 */
public class QueryResult<T> {
    private Long total;//总页数
    private Long page;//当前页
    private Long records;
    private JSONObject userData;
    private List<T> rows;
    
    public Long getTotal() {
        return total;
    }
    public void setTotal(Long total) {
        this.total = total;
    }
    public Long getPage() {
        return page;
    }
    public void setPage(Long page) {
        this.page = page;
    }
    public Long getRecords() {
        return records;
    }
    public void setRecords(Long records) {
        this.records = records;
    }
    public List<T> getRows() {
        return rows;
    }
    public void setRows(List<T> rows) {
        this.rows = rows;
    }
	public JSONObject getUserData() {
		return userData;
	}
	public void setUserData(JSONObject userData) {
		this.userData = userData;
	}
    
}
