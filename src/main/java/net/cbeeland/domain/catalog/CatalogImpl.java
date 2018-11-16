package net.cbeeland.domain.catalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import net.cbeeland.domain.catalog.item.Item;
import net.cbeeland.domain.catalog.item.ItemImpl;
import net.cbeeland.exception.DataValidationException;
import net.cbeeland.util.InputValidator;

public class CatalogImpl implements Catalog {

  private HashMap<String, Item> items;

  public CatalogImpl() {
    items = new HashMap<String, Item>();
  }

  public void addItem(String itemId, int price) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "Catalog", "addItem(String itemId, int price)");
    InputValidator.validatePositiveInteger(price, "price", "Catalog", "addItem(String itemId, int price)");

    Item newItem = new ItemImpl();
    newItem.setId(itemId);
    newItem.setPrice(price);
    items.put(itemId, newItem);
  }

  public void addItem(Item newItem) throws DataValidationException {
    InputValidator.validateInstantiatedObject(newItem, "newItem", "Catalog", "addItem(Item newItem");
    if (items.containsKey(newItem.getId())) {
      throw new DataValidationException(
          "Catalog already contains an Item with the itemId of the Item passed into Catalog.addItem(Item newItem): " + newItem.getId());
    }

    items.put(newItem.getId(), newItem);
  }

  public boolean isItemInCatalog(String itemId) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "Catalog", "isItemInCatalog(String itemId)");
    return items.containsKey(itemId);
  }

  public int getItemPrice(String itemId) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "Catalog", "getItemPrice(String itemId)");

    if (!items.containsKey(itemId)) {
      throw new DataValidationException("Catalog does not contain an Item with the itemId of: " + itemId);
    }

    return items.get(itemId).getPrice();
  }

  public void updateItemPrice(String itemId, int newPrice) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "Catalog", "updateItemPrice(String itemId, int price)");
    InputValidator.validatePositiveInteger(newPrice, "price", "Catalog", "updateItemPrice(String itemId, int price)");

    if (!items.containsKey(itemId)) {
      throw new DataValidationException("Catalog does not contain an Item with the itemId of: " + itemId);
    }

    Item updatedItem = items.get(itemId);
    updatedItem.setPrice(newPrice);
    items.put(itemId, updatedItem);
  }

  public void removeItem(String itemId) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "Catalog", "removeItem(String itemId)");
    items.remove(itemId);
  }

  public String toString() {
    StringBuilder formattedCatalogInformation = new StringBuilder();
    List<Item> itemsList = new ArrayList<Item>(items.values());
    Collections.sort(itemsList);

    for (int i = 0; i < itemsList.size(); i++) {
      // Format to 4 columns
      if (i > 0) {
        if ((i % 4) == 0) {
          formattedCatalogInformation.append("\n");
        } else {
          formattedCatalogInformation.append("    ");
        }
      }
      Item item = itemsList.get(i);
      formattedCatalogInformation.append(item.toString());
    }

    return formattedCatalogInformation.toString();
  }

}
