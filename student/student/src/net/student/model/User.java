package net.student.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * 操作员实体类
 * @author liuqingchao
 *
 */
@DatabaseTable(tableName = "user")
public class User {
    public final static int USERTYPE_ADMIN = 0;
    public final static int USERTYPE_NORMAL = 1;
    @DatabaseField(generatedId = true, canBeNull = false, columnName = "userid")
    private Integer userId;
    @DatabaseField(canBeNull = false, columnName = "usercode", uniqueIndex = true, uniqueIndexName = "user_code_idx")
    private String userCode;
    @DatabaseField(canBeNull = false, columnName = "username")
    private String userName;
    @DatabaseField(canBeNull = false)
    private String password;
    @DatabaseField(canBeNull = false, dataType = DataType.DATE_STRING, columnName = "createddate")
    private Date createdDate;
    @DatabaseField(canBeNull = false, columnName = "usertype")
    private Integer userType;
    @DatabaseField(canBeNull = false)
    private Integer status;
    @DatabaseField(columnName = "userdesc")
    private String userDesc;
    @DatabaseField(dataType = DataType.DATE_STRING, columnName = "lastupdatedate")
    private Date lastUpdateDate;
    
    @ForeignCollectionField(eager = false)
    private ForeignCollection<UserDepartment> userDepartments;
    @ForeignCollectionField(eager = false)
    private ForeignCollection<UserFeeItem> userFeeItems;
    
    private String departments;
    private String feeitems;
    
    public User() {
		super();
	}
    
	public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getUserCode() {
        return userCode;
    }
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public Integer getUserType() {
        return userType;
    }
    public void setUserType(Integer userType) {
        this.userType = userType;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getUserDesc() {
        return userDesc;
    }
    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	@JsonIgnore
	public ForeignCollection<UserDepartment> getUserDepartments() {
		return userDepartments;
	}

	public void setUserDepartments(ForeignCollection<UserDepartment> userDepartments) {
		this.userDepartments = userDepartments;
	}
	@JsonIgnore
	public ForeignCollection<UserFeeItem> getUserFeeItems() {
		return userFeeItems;
	}

	public void setUserFeeItems(ForeignCollection<UserFeeItem> userFeeItems) {
		this.userFeeItems = userFeeItems;
	}

    public String getDepartments() {
        return departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments;
    }

    public String getFeeitems() {
        return feeitems;
    }

    public void setFeeitems(String feeitems) {
        this.feeitems = feeitems;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }
}
