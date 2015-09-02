package net.student.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 已缴费账单实体类
 * @author 果冻
 *
 */
@DatabaseTable(tableName = "offlinepaidlog")
public class OffLinePaidLog {
	@DatabaseField(generatedId = true, canBeNull = false, columnName = "logid")
	private Integer logId;
	@DatabaseField(canBeNull = false, columnName = "studentid")
	private String studentId;
	@DatabaseField(canBeNull = false, columnName = "itemid")
	private String feeItemId;
	@DatabaseField(canBeNull = false, columnName = "paidfee")
	private Long paidFee;
	@DatabaseField(canBeNull = false, columnName = "price")
	private Long price;
	@DatabaseField(canBeNull = false, dataType = DataType.DATE_STRING, columnName = "createddate")
	private Date createdDate;
	@DatabaseField(dataType = DataType.DATE_STRING, columnName = "paydate")
	private Date payDate;
	@DatabaseField(columnName = "serialno")
	private String serialNo;
	private String studentName;
	private String feeItemName;
	public Integer getLogId() {
		return logId;
	}
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	@Override
	public String toString() {
		return "PaidLog [logId=" + logId + ", studentId=" + studentId + ", feeItemId=" + feeItemId + ", price=" + price
				+ ", createdDate=" + createdDate + ", payDate=" + payDate + ", serialNo=" + serialNo + "]";
	}
	public Long getPaidFee() {
		return paidFee;
	}
	public void setPaidFee(Long paidFee) {
		this.paidFee = paidFee;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getFeeItemId() {
		return feeItemId;
	}
	public void setFeeItemId(String feeItemId) {
		this.feeItemId = feeItemId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getFeeItemName() {
		return feeItemName;
	}
	public void setFeeItemName(String feeItemName) {
		this.feeItemName = feeItemName;
	}
	
}
