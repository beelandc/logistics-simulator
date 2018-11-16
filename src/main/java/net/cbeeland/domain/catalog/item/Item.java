package net.cbeeland.domain.catalog.item;

import net.cbeeland.exception.DataValidationException;

public interface Item extends Comparable<Item> {

  public String getId();

  public void setId(String itemId) throws DataValidationException;

  public int getPrice();

  public void setPrice(int newPrice) throws DataValidationException;

}
