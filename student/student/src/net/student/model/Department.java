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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((departmentId == null) ? 0 : departmentId.hashCode());
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
        Department other = (Department) obj;
        if (departmentId == null) {
            if (other.departmentId != null)
                return false;
        } else if (!departmentId.equals(other.departmentId))
            return false;
        return true;
    }
}
