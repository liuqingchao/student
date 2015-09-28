package net.student.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 待缴费账单与订单号映射实体类
 * @author 果冻
 *
 */
@DatabaseTable(tableName = "paymentorder")
public class PaymentOrder {
	@DatabaseField(generatedId = true, canBeNull = false, columnName = "paymentorderid")
	private Integer paymentOrderId;
	@DatabaseField(canBeNull = false, columnName = "paymentid", uniqueIndex = true, uniqueIndexName = "paymentorder_ui")
	private Integer paymentId;
	@DatabaseField(canBeNull = false, columnName = "orderid", uniqueIndex = true, uniqueIndexName = "paymentorder_ui")
	private String orderId;
	public Integer getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
    public Integer getPaymentOrderId() {
        return paymentOrderId;
    }
    public void setPaymentOrderId(Integer paymentOrderId) {
        this.paymentOrderId = paymentOrderId;
    }
}
