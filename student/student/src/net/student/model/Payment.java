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
@DatabaseTable(tableName = "feeitem")
public class Payment {
	@DatabaseField(generatedId = true, canBeNull = false, columnName = "detailid")
	private Integer paymentId;
	@DatabaseField(canBeNull = false, foreign = true, columnName = "studentid")
	private Student student;
	@DatabaseField(canBeNull = false, foreign = true, columnName = "itemid")
	private FeeItem feeItem;
	@DatabaseField(canBeNull = false, columnName = "price")
	private Long price;
	@DatabaseField(canBeNull = false, dataType = DataType.DATE_STRING, columnName = "startdate")
	private Date startDate;
	@DatabaseField(canBeNull = false, dataType = DataType.DATE_STRING, columnName = "enddate")
	private Date endDate;
	@DatabaseField(canBeNull = false, dataType = DataType.DATE_STRING, columnName = "createddate")
	private Date createdDate;
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, columnName = "createdby")
	private User createdBy;
	@DatabaseField(dataType = DataType.DATE_STRING, columnName = "lastupdateddate")
	private Date lastUpdatedDate;
	@DatabaseField(foreign = true, columnName = "lastUpdatedby")
	private User lastUpdatedBy;
	public Integer getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
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
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", student=" + student + ", feeItem=" + feeItem + ", price=" + price
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", createdDate=" + createdDate + ", createdBy="
				+ createdBy + ", lastUpdatedDate=" + lastUpdatedDate + ", lastUpdatedBy=" + lastUpdatedBy + "]";
	}
}
