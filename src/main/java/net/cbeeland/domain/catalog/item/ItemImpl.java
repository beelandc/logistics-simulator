package net.cbeeland.domain.catalog.item;

import java.text.NumberFormat;

import net.cbeeland.exception.DataValidationException;
import net.cbeeland.util.InputValidator;

public class ItemImpl implements Item {

  private String id;
  private Integer price;

  public void setId(String itemId) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "Item", "setId(String itemId");
    id = itemId;
  }

  public String getId() {
    return id;
  }

  public int getPrice() {
    return price.intValue();
  }

  public void setPrice(int newPrice) throws DataValidationException {
    InputValidator.validatePositiveInteger(newPrice, "newPrice", "Item", "setPrice(int newPrice)");
    price = newPrice;
  }

  public String toString() {
    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    String moneyFormattedPrice = formatter.format(price);
    String printFormattedPrice = moneyFormattedPrice.substring(0, moneyFormattedPrice.lastIndexOf("."));
    return String.format("%-8s: %-6s", id, printFormattedPrice);
  }

  @Override
  public int compareTo(Item otherItem) {
    return id.compareTo(otherItem.getId());
  }

}
