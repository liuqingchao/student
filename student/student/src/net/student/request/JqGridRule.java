package net.student.request;

import java.io.Serializable;

public class JqGridRule {
    private String field;//过滤字段
    private String op;//过滤关系['eq(等于)','ne(不等于)','lt(小于)','le(小于等于)',
                        //'gt(大于)','ge(大于等于)','bw(以此开始)','bn(不以此开始)',
                        //'in(在此范围内)','ni(不在此范围内)','ew(以此结束)','en(不以此结束)',
                        //'cn(包含)','nc(不包含)']
    private Serializable data;//过滤值
    
    public JqGridRule() {
        
    }

    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
    public String getOp() {
        return op;
    }
    public void setOp(String op) {
        this.op = op;
    }
    public Serializable getData() {
        return data;
    }
    public void setData(Serializable data) {
        this.data = data;
    }
}
