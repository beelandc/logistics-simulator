package net.cbeeland.domain.order.item;

import net.cbeeland.exception.DataValidationException;

public interface OrderItem {

  public String getItemId();

  public int getItemQuantity();

  public OrderItem getCopy();

  public void setItemQuantity(int itemQuantity) throws DataValidationException;

}
