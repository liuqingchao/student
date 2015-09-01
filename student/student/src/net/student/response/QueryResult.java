package net.student.response;

import java.util.List;

/**
 * jqGrid返回结果
 * @author liuqingchao
 *
 */
public class QueryResult<T> {
    private Long total;//总页数
    private Long page;//当前页
    private Long records;
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
    
}
