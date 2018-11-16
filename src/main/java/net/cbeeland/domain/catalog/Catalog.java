package net.cbeeland.domain.catalog;

import net.cbeeland.domain.catalog.item.Item;
import net.cbeeland.exception.DataValidationException;

public interface Catalog {

  public void addItem(String itemId, int price) throws DataValidationException;

  public void addItem(Item item) throws DataValidationException;

  public void removeItem(String itemId) throws DataValidationException;

  public void updateItemPrice(String itemId, int price) throws DataValidationException;

  public boolean isItemInCatalog(String itemId) throws DataValidationException;

  public int getItemPrice(String itemId) throws DataValidationException;

}
