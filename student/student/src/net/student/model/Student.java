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
	private Integer sex;
	@DatabaseField(canBeNull = false, dataType = DataType.DATE_STRING, columnName = "createddate")
	private Date createdDate;
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, columnName = "createdby")
	private User createdBy;
	@DatabaseField(dataType = DataType.DATE_STRING, columnName = "lastupdateddate")
	private Date lastUpdatedDate;
	@DatabaseField(foreign = true, columnName = "lastupdatedby")
	private User lastUpdatedBy;
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
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public User getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(User lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", idCardNumber=" + idCardNum + ", name=" + name + ", sex=" + sex
				+ ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", lastUpdatedDate=" + lastUpdatedDate
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", remark=" + remark + "]";
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
}
