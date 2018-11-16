package net.cbeeland.domain.facility.inventory;

import net.cbeeland.exception.DataValidationException;

public interface Inventory {

  // Should be a positive quantity
  public void addItem(String itemId, int quantity) throws DataValidationException;

  public void removeItem(String itemId) throws DataValidationException;

  public boolean isItemInInventory(String itemId) throws DataValidationException;

  public boolean isItemStockedInInventory(String itemId) throws DataValidationException;

  public Integer getItemQuantity(String itemId) throws DataValidationException;

  // Should be non-negative quantity
  public void updateItemQuantity(String itemId, int quantity) throws DataValidationException;

  public String getActiveInventoryDetails();

  public String getDepletedInventoryDetails();

}
