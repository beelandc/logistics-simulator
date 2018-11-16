package net.cbeeland.domain.order;

import java.util.List;

import net.cbeeland.domain.order.item.OrderItem;
import net.cbeeland.domain.order.logisticsrecord.LogisticsRecord;
import net.cbeeland.exception.DataValidationException;

public interface Order {

  public void setOrderId(String orderId) throws DataValidationException;

  public String getOrderId();

  public void setDestination(String destination) throws DataValidationException;

  public String getDestination();

  public void setStartDate(Integer orderStart) throws DataValidationException;

  public int getStartDate();

  public void addOrderItem(String itemId, int quantity) throws DataValidationException;

  public void addLogisticsRecord(LogisticsRecord orderLogisticsRecord) throws DataValidationException;

  /**
   * Returns a list containing copies of all OrderItems associated with the order
   */
  public List<OrderItem> getOrderItems();

  public String getLogisticsRecordDetails();

}
