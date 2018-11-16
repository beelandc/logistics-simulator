package net.cbeeland.domain.facility;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.cbeeland.domain.facility.inventory.Inventory;
import net.cbeeland.domain.facility.inventory.InventoryImpl;
import net.cbeeland.domain.facility.schedule.Schedule;
import net.cbeeland.domain.facility.schedule.ScheduleImpl;
import net.cbeeland.exception.DataValidationException;
import net.cbeeland.util.InputValidator;

public class FacilityImpl implements Facility {

  private String location;
  private Integer cost;
  private HashMap<String, Integer> linkedFacilities;
  private Inventory inventory;
  private Schedule schedule;

  public FacilityImpl() {
    linkedFacilities = new HashMap<String, Integer>();
    inventory = new InventoryImpl();
    schedule = new ScheduleImpl();
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String newLocation) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(newLocation, "newLocation", "Facility", "setLocation(String newLocation)");
    location = newLocation;
  }

  public int getRate() {
    return schedule.getRate();
  }

  public void setRate(Integer rate) throws DataValidationException {
    InputValidator.validatePositiveInteger(rate, "rate", "Facility", "setRate(Integer rate)");
    schedule.setRate(rate);
  }

  public Integer getCost() {
    return cost.intValue();
  }

  public void setCost(Integer newCost) throws DataValidationException {
    InputValidator.validatePositiveInteger(newCost, "newCost", "Facility", "setCost(Integer newCost)");
    cost = newCost;
  }

  public void addItemToInventory(String itemId, int quantity) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "Facility", "addItemToInventory(String itemId, int quantity)");
    InputValidator.validatePositiveInteger(quantity, "quantity", "Facility", "addItemToInventory(String itemId, int quantity)");
    inventory.addItem(itemId, quantity);
  }

  public void removeItemFromInventory(String itemId) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "Facility", "removeItemFromInventory(String itemId)");
    inventory.removeItem(itemId);
  }

  public boolean isItemStockedInInventory(String itemId) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "Facility", "isItemStockedInInventory(String itemId)");
    return inventory.isItemStockedInInventory(itemId);
  }

  public int getItemQuantity(String itemId) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "Facility", "getItemQuantity(String itemId)");
    return inventory.getItemQuantity(itemId);
  }

  public void reduceItemInventory(String itemId, int itemQuantity) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "Facility", "reduceItemInventory(String itemId, int itemQuantity)");
    InputValidator.validatePositiveInteger(itemQuantity, "itemQuantity", "Facility", "reduceItemInventory(String itemId, int itemQuantity)");
    inventory.updateItemQuantity(itemId, (inventory.getItemQuantity(itemId) - itemQuantity));
  }

  public void addDirectLinkToFacility(String facilityLocation, int distance) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(facilityLocation, "facilityLocation", "Facility",
        "addDirectLinkToFacility(String facilityLocation, int distance)");
    InputValidator.validatePositiveInteger(distance, "distance", "Facility", "addDirectLinkToFacility(String facilityLocation, int distance)");
    linkedFacilities.put(facilityLocation, distance);
  }

  public void removeDirectLinkToFacility(String facilityLocation) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(facilityLocation, "facilityLocation", "Facility", "removeDirectLinkToFacility(String facilityLocation)");
    linkedFacilities.remove(facilityLocation);
  }

  public boolean isFacilityDirectLink(String facilityLocation) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(facilityLocation, "facilityLocation", "Facility", "isFacilityDirectLink(String facilityLocation)");
    return linkedFacilities.get(facilityLocation) != null;
  }

  public Integer getDistanceInMiles(String otherFacilityLocation) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(otherFacilityLocation, "otherFacilityLocation", "Facility", "getDistanceInMiles(String otherFacilityLocation)");
    return linkedFacilities.get(otherFacilityLocation);
  }

  public Double getTravelTimeInDays(String otherFacilityLocation, int drivingHoursPerDay, int averageMilesPerHour) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(otherFacilityLocation, "otherFacilityLocation", "Facility",
        "getTravelTimeInDays(String otherFacilityLocation, int drivingHoursPerDay, int averageMilesPerHour)");
    InputValidator.validatePositiveInteger(drivingHoursPerDay, "drivingHoursPerDay", "Facility",
        "getTravelTimeInDays(String otherFacilityLocation, int drivingHoursPerDay, int averageMilesPerHour)");
    InputValidator.validatePositiveInteger(averageMilesPerHour, "averageMilesPerHour", "Facility",
        "getTravelTimeInDays(String otherFacilityLocation, int drivingHoursPerDay, int averageMilesPerHour)");

    Double distanceInMiles = Double.valueOf(getDistanceInMiles(otherFacilityLocation));
    Double travelTime = (distanceInMiles / (drivingHoursPerDay * averageMilesPerHour));

    return travelTime;
  }

  public HashMap<String, Integer> getLinkedFacilities() {
    HashMap<String, Integer> linkedFacilitiesCopy = new HashMap<String, Integer>();
    for (Map.Entry<String, Integer> entry : linkedFacilities.entrySet()) {
      linkedFacilitiesCopy.put(new String(entry.getKey()), new Integer(entry.getValue()));
    }
    return linkedFacilitiesCopy;
  }

  public int calculateProcessingEndDay(int startDate, int itemQuantity) throws DataValidationException {
    InputValidator.validatePositiveInteger(startDate, "startDate", "Facility", "calculateProcessingEndDay(int startDate)");
    InputValidator.validatePositiveInteger(itemQuantity, "itemQuantity", "Facility", "calculateProcessingEndDay(int startDate)");

    return schedule.calculateProcessingEndDay(startDate, itemQuantity);
  }

  public void scheduleItemProcessing(int startDate, int itemQuantity) throws DataValidationException {
    InputValidator.validatePositiveInteger(startDate, "startDate", "Facility", "calculateProcessingEndDay(int startDate)");
    InputValidator.validatePositiveInteger(itemQuantity, "itemQuantity", "Facility", "calculateProcessingEndDay(int startDate)");

    schedule.scheduleItemProcessing(startDate, itemQuantity);
  }

  public String getFacilityDetails() {
    StringBuilder formattedFacilityDetails = new StringBuilder();
    formattedFacilityDetails.append(location + "\n");
    formattedFacilityDetails.append("-------\n");
    formattedFacilityDetails.append("Rate per Day: " + schedule.getRate() + "\n");
    formattedFacilityDetails.append("Cost per Day: $" + cost + "\n");

    return formattedFacilityDetails.toString();
  }

  public String getLinkedFacilitiesDetailsWithDistanceInMiles() {
    StringBuilder formattedLinkedFacilitiesDetails = new StringBuilder();
    formattedLinkedFacilitiesDetails.append("Direct Links: \n");

    for (Entry<String, Integer> entry : linkedFacilities.entrySet()) {
      String location = entry.getKey();
      Integer distance = entry.getValue();
      formattedLinkedFacilitiesDetails.append(location + " (" + distance + "mi); ");
    }

    return formattedLinkedFacilitiesDetails.toString();
  }

  public String getLinkedFacilitiesDetailsWithTravelTimeInDays(int drivingHoursPerDay, int averageMilesPerHour) throws DataValidationException {
    InputValidator.validatePositiveInteger(drivingHoursPerDay, "drivingHoursPerDay", "Facility",
        "getLinkedFacilitiesDetailsWithTravelTimeInDays(int drivingHoursPerDay, int averageMilesPerHour)");
    InputValidator.validatePositiveInteger(averageMilesPerHour, "averageMilesPerHour", "Facility",
        "getLinkedFacilitiesDetailsWithTravelTimeInDays(int drivingHoursPerDay, int averageMilesPerHour)");
    StringBuilder formattedLinkedFacilitiesDetails = new StringBuilder();
    DecimalFormat oneDecimalFormat = new DecimalFormat("0.0");
    formattedLinkedFacilitiesDetails.append("Direct Links: \n");

    List<String> linkedFacilitiesList = new ArrayList<String>(linkedFacilities.keySet());
    Collections.sort(linkedFacilitiesList);
    for (String linkedFacility : linkedFacilitiesList) {
      formattedLinkedFacilitiesDetails
          .append(linkedFacility + " (" + oneDecimalFormat.format(getTravelTimeInDays(linkedFacility, drivingHoursPerDay, averageMilesPerHour)) + "d); ");
    }

    return formattedLinkedFacilitiesDetails.toString();
  }

  public String getInventoryDetails() {
    return inventory.toString();
  }

  public String getScheduleDetails() {
    return schedule.toString();
  }

  public String toString() {
    StringBuilder formattedFacilityInformation = new StringBuilder();

    formattedFacilityInformation.append("----------------------------------------------------------------------------------\n");
    formattedFacilityInformation.append(getFacilityDetails());
    formattedFacilityInformation.append("\n");
    formattedFacilityInformation.append(getLinkedFacilitiesDetailsWithDistanceInMiles());
    formattedFacilityInformation.append("\n\n");
    formattedFacilityInformation.append(getInventoryDetails());
    formattedFacilityInformation.append("\n\n");
    formattedFacilityInformation.append(getScheduleDetails());
    formattedFacilityInformation.append("----------------------------------------------------------------------------------\n");

    return formattedFacilityInformation.toString();
  }

  @Override
  public int compareTo(Facility otherFacility) {
    return location.compareTo(otherFacility.getLocation());
  }

}
