package net.student.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 操作员日志实体类
 * @author liuqingchao
 *
 */
@DatabaseTable(tableName = "userlog")
public class UserLog {
    public static final int OPERATETYPE_AUTH = 0;
    public static final int OPERATETYPE_UNAUTH = 1;
    
    @DatabaseField(generatedId = true)
    private Integer userLogId;
    @DatabaseField(canBeNull = false, foreign = true)
    private User user;
    @DatabaseField(canBeNull = false)
    private Date authDate;
    @DatabaseField(canBeNull = false)
    private Integer operateType;
    @DatabaseField
    private String remark;
    
    public Integer getUserLogId() {
        return userLogId;
    }
    public void setUserLogId(Integer userLogId) {
        this.userLogId = userLogId;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getAuthDate() {
        return authDate;
    }
    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
    }
    public Integer getOperateType() {
        return operateType;
    }
    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
