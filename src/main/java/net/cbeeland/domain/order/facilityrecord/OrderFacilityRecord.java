package net.cbeeland.domain.order.facilityrecord;

import net.cbeeland.domain.order.item.OrderItem;
import net.cbeeland.dto.order.OrderDTO;
import net.cbeeland.exception.DataValidationException;

public interface OrderFacilityRecord extends Comparable<OrderFacilityRecord> {

  public void populateOrderFacilityRecord(OrderDTO orderDTO, OrderItem orderItem, String currentFacilityLocation) throws DataValidationException;

  public String getFacilityLocation();

  public String getItemId();

  public int getItemQuantity();

  public int getOrderStartDay();

  public int getProcessingEndDay();

  public int getTravelTime();

  public int getArrivalDay();

  public int getTotalItemCost();

  public int getTotalFacilityProcessingCost();

  public double getTotalTransportCost();

  public double getTotalOverallCost();
}
