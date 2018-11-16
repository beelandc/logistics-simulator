package net.cbeeland.domain.facility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.cbeeland.exception.DataValidationException;
import net.cbeeland.util.InputValidator;

/**
 * Façade for Facilities -- Singleton
 * 
 * @author cbeeland
 *
 */
public class FacilityManager {

  private volatile static FacilityManager instance;
  private final String FACILITIES_XML_FILE_PATH = "src/main/resources/facilities.xml";
  private final int DRIVING_HOURS_PER_DAY = 8;
  private final int AVERAGE_MILES_PER_HOUR = 50;

  private FacilityLoader facilityLoader;
  private HashMap<String, Facility> facilities;

  private FacilityManager() {
    facilityLoader = new FacilityLoader();
    facilities = facilityLoader.loadFacilitiesXML(FACILITIES_XML_FILE_PATH);
  }

  public static FacilityManager getInstance() {
    if (instance == null) {
      synchronized (FacilityManager.class) {
        if (instance == null) {
          instance = new FacilityManager();
        }
      }
    }
    return instance;
  }

  public boolean doesFacilityExist(String facilityLocation) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(facilityLocation, "facilityLocation", "FacilityManager", "doesFacilityExist(String facilityLocation)");
    return facilities.containsKey(facilityLocation);
  }

  public Integer getFacilityDailyProcessingRate(String facilityLocation) throws DataValidationException {

    InputValidator.validateStringNotNullOrEmpty(facilityLocation, "facilityLocation", "FacilityManager",
        "getFacilityDailyProcessingRate(String facilityLocation)");

    Facility facility = facilities.get(facilityLocation);
    if (facility == null) {
      throw new DataValidationException("The facilityLocation [" + facilityLocation
          + "] passed into FacilityManager.getFacilityDailyProcessingRate(String facilityLocation) does not match an existing facility.");
    }

    return facility.getRate();
  }


  public Integer getFacilityDailyProcessingCost(String facilityLocation) throws DataValidationException {

    InputValidator.validateStringNotNullOrEmpty(facilityLocation, "facilityLocation", "FacilityManager",
        "getFacilityDailyProcessingCost(String facilityLocation)");

    Facility facility = facilities.get(facilityLocation);
    if (facility == null) {
      throw new DataValidationException("The facilityLocation [" + facilityLocation
          + "] passed into FacilityManager.getFacilityDailyProcessingCost(String facilityLocation) does not match an existing facility.");
    }

    return facility.getCost();
  }

  public Integer getDistanceInMiles(String facilityLocation, String otherFacilityLocation) throws DataValidationException {

    InputValidator.validateStringNotNullOrEmpty(facilityLocation, "facilityLocation", "FacilityManager",
        "getDistanceInMiles(String facilityLocation, String otherFacilityLocation)");
    InputValidator.validateStringNotNullOrEmpty(otherFacilityLocation, "otherFacilityLocation", "FacilityManager",
        "getDistanceInMiles(String facilityLocation, String otherFacilityLocation)");

    Facility facility = facilities.get(facilityLocation);
    if (facility == null) {
      throw new DataValidationException("The facilityLocation [" + facilityLocation
          + "] passed into FacilityManager.getDistanceInMiles(String facilityLocation, String otherFacilityLocation) does not match an existing facility.");
    } else if (!facility.isFacilityDirectLink(otherFacilityLocation)) {
      throw new DataValidationException("The otherFacilityLocation [" + otherFacilityLocation
          + "] passed into FacilityManager.getDistanceInMiles(String facilityLocation, String otherFacilityLocation) does not match any direct links to the facility "
          + facilityLocation);
    }

    return facility.getDistanceInMiles(otherFacilityLocation);
  }

  public Double getTravelTimeInDays(String facilityLocation, String otherFacilityLocation) throws DataValidationException {

    InputValidator.validateStringNotNullOrEmpty(facilityLocation, "facilityLocation", "FacilityManager",
        "getTravelTimeInDays(String facilityLocation, String otherFacilityLocation)");
    InputValidator.validateStringNotNullOrEmpty(otherFacilityLocation, "otherFacilityLocation", "FacilityManager",
        "getTravelTimeInDays(String facilityLocation, String otherFacilityLocation)");

    Facility facility = facilities.get(facilityLocation);
    if (facility == null) {
      throw new DataValidationException("The facilityLocation [" + facilityLocation
          + "] passed into FacilityManager.getDistanceInMiles(String facilityLocation, String otherFacilityLocation) does not match an existing facility.");
    } else if (!facility.isFacilityDirectLink(otherFacilityLocation)) {
      throw new DataValidationException("The otherFacilityLocation [" + otherFacilityLocation
          + "] passed into FacilityManager.getDistanceInMiles(String facilityLocation, String otherFacilityLocation) does not match any direct links to the facility "
          + facilityLocation);
    }

    return facility.getTravelTimeInDays(otherFacilityLocation, DRIVING_HOURS_PER_DAY, AVERAGE_MILES_PER_HOUR);
  }

  public HashMap<String, Integer> getFacilityNeighbors(String facilityLocation) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(facilityLocation, "facilityLocation", "Facility", "getFacilityNeighbors(String facilityLocation)");

    Facility facility = facilities.get(facilityLocation);
    if (facility == null) {
      throw new DataValidationException("The facilityLocation [" + facilityLocation
          + "] passed into FacilityManager.getFacilityNeighbors(String facilityLocation) does not match an existing facility.");
    }
    return facility.getLinkedFacilities();
  }

  public int getDrivingHoursPerDay() {
    return DRIVING_HOURS_PER_DAY;
  }

  public int getAverageMilesPerHour() {
    return AVERAGE_MILES_PER_HOUR;
  }

  /**
   * Returns a list of facilities that has a positive quantity of the given item.
   * 
   * @throws DataValidationException
   */
  public List<String> getFacilitiesWithItem(String itemId) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "FacilityManager", "getFacilitiesWithItem");

    List<String> facilitiesWithItem = new ArrayList<String>();

    for (Entry<String, Facility> facilityEntry : facilities.entrySet()) {
      Facility facility = facilityEntry.getValue();
      if (facility.isItemStockedInInventory(itemId)) {
        facilitiesWithItem.add(facilityEntry.getKey());
      }
    }

    return facilitiesWithItem;
  }

  public int getItemQuantity(String facilityLocation, String itemId) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(facilityLocation, "facilityLocation", "FacilityManager",
        "getItemQuantity(String facilityLocation, String itemId)");
    InputValidator.validateStringNotNullOrEmpty(facilityLocation, "facilityLocation", "FacilityManager",
        "getItemQuantity(String facilityLocation, String itemId)");

    Facility facility = facilities.get(facilityLocation);

    if (facility == null) {
      throw new DataValidationException("Facility not found");
    }

    return facility.getItemQuantity(itemId);
  }

  public void reduceFacilityInventory(String facilityLocation, String itemId, int itemQuantity) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(facilityLocation, "facilityLocation", "FacilityManager",
        "reduceFacilityInventory(String facilityLocation, String itemId, int itemQuantity)");
    InputValidator.validatePositiveInteger(itemQuantity, "itemQuantity", "FacilityManager",
        "reduceFacilityInventory(String facilityLocation, String itemId, int itemQuantity)");

    Facility facility = facilities.get(facilityLocation);

    if (facility == null) {
      throw new DataValidationException("Facility not found");
    }

    facility.reduceItemInventory(itemId, itemQuantity);

  }

  public Integer calculateProcessingEndDay(String facilityLocation, int startDate, int itemQuantity) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(facilityLocation, "facilityLocation", "FacilityManager",
        "calculateProcessingEndDay(String facilityLocation, int startDate)");
    InputValidator.validatePositiveInteger(startDate, "startDate", "FacilityManager", "calculateProcessingEndDay(String facilityLocation, int startDate)");

    Facility facility = facilities.get(facilityLocation);

    if (facility == null) {
      throw new DataValidationException("Facility not found");
    }

    return facility.calculateProcessingEndDay(startDate, itemQuantity);
  }

  public void scheduleItemProcessing(String facilityLocation, int startDate, int itemQuantity) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(facilityLocation, "facilityLocation", "FacilityManager",
        "scheduleItemProcessing(String facilityLocation, String itemId, int startDate, int itemQuantity)");
    InputValidator.validatePositiveInteger(startDate, "startDate", "FacilityManager",
        "scheduleItemProcessing(String facilityLocation, String itemId, int startDate, int itemQuantity)");
    InputValidator.validatePositiveInteger(itemQuantity, "itemQuantity", "FacilityManager",
        "scheduleItemProcessing(String facilityLocation, String itemId, int startDate, int itemQuantity)");

    Facility facility = facilities.get(facilityLocation);

    if (facility == null) {
      throw new DataValidationException("Facility not found");
    }

    facility.scheduleItemProcessing(startDate, itemQuantity);
  }

  public void printFacilityReport(String facilityLocation) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(facilityLocation, "facilityLocation", "FacilityManager", "printFacilityReport(String facilityLocation)");

    Facility facility = facilities.get(facilityLocation);

    if (facility != null) {
      System.out.println(facility.toString());
    } else {
      System.out.println(facilityLocation + " Facility not found in Transit Network");
    }

  }

  public void printFacilitiesReport() {
    StringBuilder formattedFacilitiesReport = new StringBuilder();

    try {

      List<Facility> facilitiesList = new ArrayList<Facility>(facilities.values());
      Collections.sort(facilitiesList);

      for (Facility facility : facilitiesList) {
        formattedFacilitiesReport.append("----------------------------------------------------------------------------------\n");
        formattedFacilitiesReport.append(facility.getFacilityDetails());
        formattedFacilitiesReport.append("\n");
        formattedFacilitiesReport.append(facility.getLinkedFacilitiesDetailsWithTravelTimeInDays(DRIVING_HOURS_PER_DAY, AVERAGE_MILES_PER_HOUR));
        formattedFacilitiesReport.append("\n\n");
        formattedFacilitiesReport.append(facility.getInventoryDetails());
        formattedFacilitiesReport.append("\n");
        formattedFacilitiesReport.append(facility.getScheduleDetails());
        formattedFacilitiesReport.append("----------------------------------------------------------------------------------\n\n");
      }

    } catch (DataValidationException e) {
      System.out.println("***Error generating Facility Status Report***");
      e.printStackTrace();
    }

    System.out.println(formattedFacilitiesReport.toString());
  }

}
