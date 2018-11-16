package net.cbeeland.domain.facility;

import java.util.HashMap;

import net.cbeeland.exception.DataValidationException;

public interface Facility extends Comparable<Facility> {

  public String getLocation();

  public void setLocation(String newLocation) throws DataValidationException;

  public int getRate();

  public void setRate(Integer rate) throws DataValidationException;

  public Integer getCost();

  public void setCost(Integer cost) throws DataValidationException;

  public void addItemToInventory(String itemId, int quantity) throws DataValidationException;

  public boolean isItemStockedInInventory(String itemId) throws DataValidationException;

  public int getItemQuantity(String itemId) throws DataValidationException;

  public void reduceItemInventory(String itemId, int itemQuantity) throws DataValidationException;

  public void removeItemFromInventory(String itemId) throws DataValidationException;

  public int calculateProcessingEndDay(int startDate, int itemQuantity) throws DataValidationException;

  public void scheduleItemProcessing(int startDate, int itemQuantity) throws DataValidationException;

  public boolean isFacilityDirectLink(String facilityLocation) throws DataValidationException;

  public Integer getDistanceInMiles(String otherFacilityLocation) throws DataValidationException;

  public Double getTravelTimeInDays(String otherFacilityLocation, int drivingHoursPerDay, int averageMilesPerHour) throws DataValidationException;

  public void addDirectLinkToFacility(String facilityLocation, int distance) throws DataValidationException;

  public void removeDirectLinkToFacility(String facilityLocation) throws DataValidationException;

  public HashMap<String, Integer> getLinkedFacilities();

  public String getFacilityDetails();

  public String getLinkedFacilitiesDetailsWithDistanceInMiles();

  public String getLinkedFacilitiesDetailsWithTravelTimeInDays(int drivingHoursPerDay, int averageMilesPerHour) throws DataValidationException;

  public String getInventoryDetails();

  public String getScheduleDetails();

}
