package net.cbeeland.domain.facility.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.cbeeland.domain.catalog.CatalogManager;
import net.cbeeland.exception.DataValidationException;
import net.cbeeland.util.InputValidator;

public class InventoryImpl implements Inventory {

  private HashMap<String, Integer> inventory;

  public InventoryImpl() {
    inventory = new HashMap<String, Integer>();
  }

  public void addItem(String itemId, int quantity) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "Inventory", "addItem(String itemId, int quantity)");
    InputValidator.validatePositiveInteger(quantity, "quantity", "Inventory", "addItem(String itemId, int quantity)");

    if (CatalogManager.getInstance().isItemInCatalog(itemId)) {
      inventory.put(itemId, new Integer(quantity));
    } else {
      throw new DataValidationException("Error adding item " + itemId + " to inventory. Item is not in Catalog.");
    }

  }

  public void removeItem(String itemId) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "Inventory", "removeItem(String itemId)");
    inventory.remove(itemId);

  }

  public boolean isItemInInventory(String itemId) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "Inventory", "isItemInInventory(String itemId)");
    return inventory.containsKey(itemId);
  }

  public boolean isItemStockedInInventory(String itemId) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "Inventory", "isItemInInventory(String itemId)");

    if (inventory.containsKey(itemId)) {
      if (inventory.get(itemId) > 0) {
        return true;
      }
    }

    return false;
  }

  public Integer getItemQuantity(String itemId) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "Inventory", "getItemQuantity(String itemId)");
    return inventory.get(itemId);
  }

  public void updateItemQuantity(String itemId, int quantity) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "Inventory", "updateItemQuantity(String itemId, int quantity)");
    InputValidator.validateNonNegativeInteger(quantity, "quantity", "Inventory", "updateItemQuantity(String itemId, int quantity)");

    if (inventory.containsKey(itemId)) {
      inventory.put(itemId, new Integer(quantity));
    } else {
      throw new DataValidationException("Error updating item " + itemId + " quantity. Item is not in inventory.");
    }

  }

  public String getActiveInventoryDetails() {
    StringBuilder formattedActiveInventoryDetails = new StringBuilder();

    formattedActiveInventoryDetails.append("Active Inventory:\n");
    formattedActiveInventoryDetails.append("   Item ID     Quantity\n");

    Map<String, Integer> sortedInventoryMap = new TreeMap<String, Integer>(inventory);
    for (Entry<String, Integer> entry : sortedInventoryMap.entrySet()) {
      String itemId = entry.getKey();
      Integer quantity = entry.getValue();
      if (quantity > 0) {
        formattedActiveInventoryDetails.append("   ");
        formattedActiveInventoryDetails.append(String.format("%-12s%-8s\n", itemId, quantity));
      }
    }

    return formattedActiveInventoryDetails.toString();
  }

  public String getDepletedInventoryDetails() {
    StringBuilder formattedDepletedInventoryDetails = new StringBuilder();
    boolean depletedInventoryFlag = false;
    boolean firstLoopFlag = true;

    formattedDepletedInventoryDetails.append("Depleted (Used-Up) Inventory: ");

    Map<String, Integer> sortedInventoryMap = new TreeMap<String, Integer>(inventory);
    for (Entry<String, Integer> entry : sortedInventoryMap.entrySet()) {
      String itemId = entry.getKey();
      Integer quantity = entry.getValue();

      if (quantity == 0) {

        if (!firstLoopFlag) {
          formattedDepletedInventoryDetails.append(", ");
        }

        formattedDepletedInventoryDetails.append(itemId);
        depletedInventoryFlag = true;
        firstLoopFlag = false;
      }
    }

    if (!depletedInventoryFlag) {
      formattedDepletedInventoryDetails.append("None\n");
    } else {
      formattedDepletedInventoryDetails.append("\n");
    }

    return formattedDepletedInventoryDetails.toString();
  }

  public String toString() {
    StringBuilder formattedInventoryDetails = new StringBuilder();
    formattedInventoryDetails.append(getActiveInventoryDetails());
    formattedInventoryDetails.append("\n");
    formattedInventoryDetails.append(getDepletedInventoryDetails());
    return formattedInventoryDetails.toString();
  }

}
