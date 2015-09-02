package net.student.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 学生实体类
 * @author 果冻
 *
 */
@DatabaseTable(tableName = "student")
public class Student {
	@DatabaseField(id = true, canBeNull = false, columnName = "studentid")
	private String studentId;
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, columnName = "departmentid")
	private Department department;
	@DatabaseField(canBeNull = false, columnName = "idcardnum")
	private String idCardNum;
	@DatabaseField(canBeNull = false)
	private String name;
	@DatabaseField(canBeNull = false)
	private Integer status;
	@DatabaseField(canBeNull = false, dataType = DataType.DATE_STRING, columnName = "createddate")
	private Date createdDate;
	@DatabaseField(dataType = DataType.DATE_STRING, columnName = "lastupdateddate")
	private Date lastUpdatedDate;
	@DatabaseField
	private String remark;
	
	public Student() {
		super();
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getIdCardNum() {
		return idCardNum;
	}
	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", idCardNumber=" + idCardNum + ", name=" + name + ", "
				+ ", createdDate=" + createdDate + ", status=" + status + ", lastUpdatedDate=" + lastUpdatedDate
				+ ", remark=" + remark + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((studentId == null) ? 0 : studentId.hashCode());
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
		Student other = (Student) obj;
		if (studentId == null) {
			if (other.studentId != null)
				return false;
		} else if (!studentId.equals(other.studentId))
			return false;
		return true;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
