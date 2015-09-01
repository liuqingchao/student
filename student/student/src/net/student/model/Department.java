package net.student.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 部门实体类
 * @author 果冻
 *
 */
@DatabaseTable(tableName = "department")
public class Department {
	@DatabaseField(id = true, canBeNull = false, columnName = "departmentid")
	private Integer departmentId;
	@DatabaseField(canBeNull = false, columnName = "departmentname", uniqueIndex = true, uniqueIndexName = "department_name_idx")
	private String departmentName;
	
	public Department() {
		super();
	}
	
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}
