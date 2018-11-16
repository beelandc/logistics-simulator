package net.cbeeland.domain.order.item;

import net.cbeeland.exception.DataValidationException;
import net.cbeeland.util.InputValidator;

public class OrderItemImpl implements OrderItem {
  private String itemId;
  private Integer quantity;

  public OrderItemImpl(String orderItemId, int orderItemQuantity) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(orderItemId, "orderItemId", "OrderItem", "OrderItem(String orderItemId, int orderItemQuantity)");
    InputValidator.validatePositiveInteger(orderItemQuantity, "orderItemQuantity", "OrderItem", "OrderItem(String orderItemId, int orderItemQuantity)");

    itemId = orderItemId;
    quantity = orderItemQuantity;
  }

  public String getItemId() {
    return itemId;
  }

  public int getItemQuantity() {
    return quantity.intValue();
  }

  public void setItemQuantity(int itemQuantity) throws DataValidationException {
    InputValidator.validateNonNegativeInteger(itemQuantity, "itemQuantity", "OrderItem", "setItemQuantity(int itemQuantity)");
    quantity = itemQuantity;
  }

  public OrderItem getCopy() {
    try {
      return new OrderItemImpl(itemId, quantity);
    } catch (DataValidationException e) {
      System.out.println("ERROR: Unexpected DataValidationException occurred while creating copy of OrderItemImpl in getCopy() -- ItemId: " + itemId
          + ", Quantity: " + quantity);
      e.printStackTrace();
    }
    // If copy fails, log error and return a null OrderItem
    return null;
  }

  public String toString() {
    StringBuilder formattedOrderItemInformation = new StringBuilder();

    formattedOrderItemInformation.append("Item ID:");
    formattedOrderItemInformation.append(String.format("%11s", itemId));
    formattedOrderItemInformation.append(",     Quantity: " + quantity + "\n");

    return formattedOrderItemInformation.toString();
  }

}
