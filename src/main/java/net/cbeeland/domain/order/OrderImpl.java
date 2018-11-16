package net.cbeeland.domain.order;

import java.util.ArrayList;
import java.util.List;

import net.cbeeland.domain.order.item.OrderItem;
import net.cbeeland.domain.order.item.OrderItemImpl;
import net.cbeeland.domain.order.logisticsrecord.LogisticsRecord;
import net.cbeeland.exception.DataValidationException;
import net.cbeeland.util.InputValidator;

public class OrderImpl implements Order {

  private String id;
  private String destination;
  private Integer startDate;
  private LogisticsRecord logisticsRecord;

  private List<OrderItem> orderItems;

  public OrderImpl() {
    orderItems = new ArrayList<OrderItem>();
  }

  public void setOrderId(String orderId) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(orderId, "orderId", "Order", "setOrderId(String orderId)");
    id = orderId;
  }


  public String getOrderId() {
    return id;
  }


  public void setDestination(String orderDestination) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(orderDestination, "orderDestination", "Order", "setDestination(String orderDestination)");
    destination = orderDestination;
  }


  public String getDestination() {
    return destination;
  }


  public void setStartDate(Integer orderStartDate) throws DataValidationException {
    InputValidator.validatePositiveInteger(orderStartDate, "orderStartDate", "Order", "setOrderStartDate(Integer orderStartDate)");
    startDate = orderStartDate;
  }


  public int getStartDate() {
    return startDate.intValue();
  }

  public void addOrderItem(String itemId, int quantity) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "Order", "addOrderItem(String itemId, int quantity)");
    InputValidator.validatePositiveInteger(quantity, "quantity", "Order", "addOrderItem(String itemId, int quantity)");

    OrderItem newItem = new OrderItemImpl(itemId, quantity);
    orderItems.add(newItem);
  }

  public List<OrderItem> getOrderItems() {
    List<OrderItem> orderItemListCopy = new ArrayList<OrderItem>();

    for (OrderItem orderItem : orderItems) {
      orderItemListCopy.add(orderItem.getCopy());
    }

    return orderItemListCopy;
  }

  public String getOrderItemDetails() {
    StringBuilder formattedOrderItemInformation = new StringBuilder();

    formattedOrderItemInformation.append("  List of Order Items: \n");

    for (int x = 0; x < orderItems.size(); x++) {
      OrderItem currentItem = orderItems.get(x);
      formattedOrderItemInformation.append("       " + (x + 1) + ")  ");
      formattedOrderItemInformation.append(currentItem.toString());
    }

    return formattedOrderItemInformation.toString();
  }

  public String toString() {
    StringBuilder formattedOrderInformation = new StringBuilder();

    formattedOrderInformation.append("  Order Id:      " + id + "\n");
    formattedOrderInformation.append("  Order Time:    Day " + startDate + "\n");
    formattedOrderInformation.append("  Destination:   " + destination + "\n\n");
    formattedOrderInformation.append(getOrderItemDetails());

    return formattedOrderInformation.toString();
  }

  @Override
  public void addLogisticsRecord(LogisticsRecord orderLogisticsRecord) throws DataValidationException {
    InputValidator.validateInstantiatedObject(orderLogisticsRecord, "orderLogisticsRecord", "Order",
        "addLogisticsRecord(LogisticsRecord orderLogisticsRecord)");
    logisticsRecord = orderLogisticsRecord;
  }

  public String getLogisticsRecordDetails() {
    return (logisticsRecord != null) ? logisticsRecord.generateLogisticsRecordDetails() : "Order " + id + " currently has no Logistics Record.";
  }

}
