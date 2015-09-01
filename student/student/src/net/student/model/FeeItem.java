package net.student.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 产品实体类
 * @author 果冻
 *
 */
@DatabaseTable(tableName = "feeitem")
public class FeeItem {
	@DatabaseField(id = true, canBeNull = false, columnName = "itemid")
	private String itemId;
	@DatabaseField(canBeNull = false, columnName = "itemname")
	private String itemName;
	@DatabaseField(canBeNull = false, columnName = "price")
	private Long price;
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
	
	public FeeItem() {
		super();
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
		return "Product [productId=" + itemId + ", productName=" + itemName + ", totalPrice=" + price
				+ ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", lastUpdatedDate=" + lastUpdatedDate
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", remark=" + remark + "]";
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}
}
