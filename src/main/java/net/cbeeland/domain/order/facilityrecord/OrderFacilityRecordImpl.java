package net.cbeeland.domain.order.facilityrecord;

import net.cbeeland.domain.catalog.CatalogManager;
import net.cbeeland.domain.facility.FacilityManager;
import net.cbeeland.domain.order.item.OrderItem;
import net.cbeeland.dto.order.OrderDTO;
import net.cbeeland.exception.DataValidationException;
import net.cbeeland.processor.shortestpath.Path;
import net.cbeeland.processor.shortestpath.ShortestPathProcessor;

public class OrderFacilityRecordImpl implements OrderFacilityRecord {

  private final double DAILY_TRANSPORT_COST = 500.0;

  private String facilityLocation;
  private String itemId;
  private Integer itemQuantity;
  private Integer orderStartDay;
  private Integer processingEndDay;
  private Double travelTime;
  private Integer arrivalDay;
  private Integer totalItemCost;
  private Double totalFacilityProcessingCost;
  private Double totalTransportCost;
  private Double totalOverallCost;

  public void populateOrderFacilityRecord(OrderDTO orderDTO, OrderItem orderItem, String currentFacilityLocation) throws DataValidationException {
    facilityLocation = currentFacilityLocation;
    itemId = orderItem.getItemId();
    itemQuantity = calculateItemQuantity(currentFacilityLocation, orderItem);
    orderStartDay = orderDTO.startDate;
    processingEndDay = FacilityManager.getInstance().calculateProcessingEndDay(currentFacilityLocation, orderDTO.startDate, itemQuantity);

    Path shortestPath = ShortestPathProcessor.getInstance().findBestPath(currentFacilityLocation, orderDTO.destination);

    travelTime = shortestPath.getTravelTimeInDays();

    // (PED + TT) -- Rounded-up
    arrivalDay = (int) Math.ceil((processingEndDay + travelTime));

    totalItemCost = CatalogManager.getInstance().getItemPrice(itemId) * itemQuantity;
    totalFacilityProcessingCost = calculateTotalFacilityProcessingCost(); 
    totalTransportCost = DAILY_TRANSPORT_COST * Math.ceil(travelTime);
    totalOverallCost = (totalItemCost + totalFacilityProcessingCost + totalTransportCost);

  }

  public int calculateItemQuantity(String facilityLocation, OrderItem orderItem) throws DataValidationException {
    Integer facilityInventoryQuantity = FacilityManager.getInstance().getItemQuantity(facilityLocation, orderItem.getItemId());

    // Return the MIN of the current facility quantity and the order quantity
    return ((orderItem.getItemQuantity() > facilityInventoryQuantity) ? facilityInventoryQuantity : orderItem.getItemQuantity());
  }

  public String getFacilityLocation() {
    return facilityLocation;
  }

  public String getItemId() {
    return itemId;
  }

  public int getItemQuantity() {
    return itemQuantity.intValue();
  }

  public int getOrderStartDay() {
    return orderStartDay.intValue();
  }

  public int getProcessingEndDay() {
    return processingEndDay.intValue();
  }

  public int getTravelTime() {
    return travelTime.intValue();
  }

  public int getArrivalDay() {
    return arrivalDay.intValue();
  }

  public int getTotalItemCost() {
    return totalItemCost.intValue();
  }

  public int getTotalFacilityProcessingCost() {
    return totalFacilityProcessingCost.intValue();
  }

  public double getTotalTransportCost() {
    return totalTransportCost.doubleValue();
  }

  public double getTotalOverallCost() {
    return totalOverallCost.doubleValue();
  }

  private Double calculateTotalFacilityProcessingCost() throws DataValidationException {

    Double facilityDailyProcessingRate = FacilityManager.getInstance().getFacilityDailyProcessingRate(facilityLocation).doubleValue();
    Double facilityDailyProcessingCost = FacilityManager.getInstance().getFacilityDailyProcessingCost(facilityLocation).doubleValue();
    Double totalProcessingDays = (itemQuantity.doubleValue() / facilityDailyProcessingRate);

    return (totalProcessingDays * facilityDailyProcessingCost);
  }

  @Override
  public int compareTo(OrderFacilityRecord otherFacilityRecord) {
    return arrivalDay.compareTo(otherFacilityRecord.getArrivalDay());
  }

}
