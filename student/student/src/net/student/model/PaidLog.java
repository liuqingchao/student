package net.student.model;

import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 待缴费账单实体类
 * @author 果冻
 *
 */
@DatabaseTable(tableName = "paidlog")
public class PaidLog {
	@DatabaseField(generatedId = true, canBeNull = false, columnName = "logid")
	private Integer logId;
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, columnName = "studentid")
	private Student student;
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, columnName = "itemid")
	private FeeItem feeItem;
	@DatabaseField(canBeNull = false, columnName = "price")
	private Long price;
	@DatabaseField(canBeNull = false, dataType = DataType.DATE_STRING, columnName = "createddate")
	private Date createdDate;
	@DatabaseField(dataType = DataType.DATE_STRING, columnName = "paydate")
	private Date payDate;
	@DatabaseField(columnName = "serialno")
	private String serialNo;
	public Integer getLogId() {
		return logId;
	}
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public FeeItem getFeeItem() {
		return feeItem;
	}
	public void setFeeItem(FeeItem feeItem) {
		this.feeItem = feeItem;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
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
		return "PaidLog [logId=" + logId + ", student=" + student + ", feeItem=" + feeItem + ", price=" + price
				+ ", createdDate=" + createdDate + ", payDate=" + payDate + ", serialNo=" + serialNo + "]";
	}
	
}
