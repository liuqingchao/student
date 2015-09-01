package net.student.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 操作员可操作的费用实体类
 * @author 果冻
 *
 */
@DatabaseTable(tableName = "userfeeitem")
public class UserFeeItem {
	@DatabaseField(generatedId = true, canBeNull = false, columnName = "ufiid")
	private Integer ufiId;
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, columnName = "userid")
	private User user;
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, columnName = "itemid")
	private FeeItem feeItem;
	
	public UserFeeItem() {
		super();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getUfiId() {
		return ufiId;
	}

	public void setUfiId(Integer ufiId) {
		this.ufiId = ufiId;
	}

	public FeeItem getFeeItem() {
		return feeItem;
	}

	public void setFeeItem(FeeItem feeItem) {
		this.feeItem = feeItem;
	}
}
