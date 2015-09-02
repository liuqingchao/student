package net.student.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 缴费统计实体类
 * @author 果冻
 *
 */
@DatabaseTable(tableName = "paystat")
public class PayStat {
	@DatabaseField(generatedId = true, canBeNull = false, columnName = "statid")
	public Integer statId;
	@DatabaseField(canBeNull = false, columnName = "statday")
	public String statDay;
	@DatabaseField(canBeNull = false, columnName = "itemid")
	public String feeItemId;
	public String feeItemName;
	public String feeItemDesc;
	@DatabaseField(canBeNull = false, columnName = "count")
	public Integer count;
	@DatabaseField(canBeNull = false, columnName = "amount")
	public Long amount;
	@DatabaseField(canBeNull = false, dataType = DataType.DATE_STRING, columnName = "lastpaydate")
	public Date lastPayDate;
	@DatabaseField(canBeNull = false, columnName = "lastorderid")
	public String lastOrderId;
	public Integer getStatId() {
		return statId;
	}
	public void setStatId(Integer statId) {
		this.statId = statId;
	}
	public String getStatDay() {
		return statDay;
	}
	public void setStatDay(String statDay) {
		this.statDay = statDay;
	}
	public String getFeeItemId() {
		return feeItemId;
	}
	public void setFeeItemId(String feeItemId) {
		this.feeItemId = feeItemId;
	}
	public String getFeeItemName() {
		return feeItemName;
	}
	public void setFeeItemName(String feeItemName) {
		this.feeItemName = feeItemName;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	@JsonFormat(pattern="HH:mm:ss", timezone = "GMT+8")
	public Date getLastPayDate() {
		return lastPayDate;
	}
	public void setLastPayDate(Date lastPayDate) {
		this.lastPayDate = lastPayDate;
	}
	public String getLastOrderId() {
		return lastOrderId;
	}
	public void setLastOrderId(String lastOrderId) {
		this.lastOrderId = lastOrderId;
	}
	public String getFeeItemDesc() {
		return feeItemDesc;
	}
	public void setFeeItemDesc(String feeItemDesc) {
		this.feeItemDesc = feeItemDesc;
	}
}
