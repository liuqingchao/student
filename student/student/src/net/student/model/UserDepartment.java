package net.student.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 操作员可操作的部门实体类
 * @author 果冻
 *
 */
@DatabaseTable(tableName = "userdepartment")
public class UserDepartment {
	@DatabaseField(generatedId = true, canBeNull = false, columnName = "udid")
	private Integer udId;
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, columnName = "userid")
	private User user;
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, columnName = "departmentid")
	private Department department;
	
	public UserDepartment() {
		super();
	}

	public Integer getUdId() {
		return udId;
	}

	public void setUdId(Integer udId) {
		this.udId = udId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
}
