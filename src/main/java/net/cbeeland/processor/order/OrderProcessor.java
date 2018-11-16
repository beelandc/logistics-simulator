package net.cbeeland.processor.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.cbeeland.domain.catalog.CatalogManager;
import net.cbeeland.domain.facility.FacilityManager;
import net.cbeeland.domain.order.facilityrecord.OrderFacilityRecord;
import net.cbeeland.domain.order.facilityrecord.OrderFacilityRecordImpl;
import net.cbeeland.domain.order.item.OrderItem;
import net.cbeeland.domain.order.logisticsrecord.LogisticsRecord;
import net.cbeeland.domain.order.logisticsrecord.LogisticsRecordImpl;
import net.cbeeland.dto.order.OrderDTO;
import net.cbeeland.exception.DataValidationException;

public class OrderProcessor {

  private volatile static OrderProcessor instance;

  private OrderProcessor() {}

  public static OrderProcessor getInstance() {
    if (instance == null) {
      synchronized (OrderProcessor.class) {
        if (instance == null) {
          instance = new OrderProcessor();
        }
      }
    }
    return instance;
  }

  public LogisticsRecord processOrder(OrderDTO orderDTO) throws DataValidationException {
    LogisticsRecord orderLogisticsRecord = new LogisticsRecordImpl();

    for (OrderItem orderItem : orderDTO.orderItems) {

      // If item is not in catalog, reject item and continue
      if (!CatalogManager.getInstance().isItemInCatalog(orderItem.getItemId())) {
        System.out.println("ERROR: Invalid Item [" + orderItem.getItemId() + "] found while processing order: " + orderDTO.id + ". Item skipped.");
      } else {

        while (orderItem.getItemQuantity() > 0) {
          processOrderItem(orderDTO, orderItem, orderLogisticsRecord);
        }
      }

    }

    return orderLogisticsRecord;
  }

  private void processOrderItem(OrderDTO orderDTO, OrderItem orderItem, LogisticsRecord orderLogisticsRecord) throws DataValidationException {

    List<String> facilitiesWithItem = getListOfFacilitiesWithStockedItem(orderDTO, orderItem);

    // If no facilities have a remaining amount of the desired item, log backorder and skip item
    if (facilitiesWithItem.isEmpty()) {
      System.out.println("INFO: No facilities have a remaining supply of  item: " + orderItem.getItemId() + " -- Backorder required for remaining amount: "
          + orderItem.getItemQuantity());
    } else {

      List<OrderFacilityRecord> facilityRecordList = generateFacilityRecords(orderDTO, orderItem, facilitiesWithItem);

      // Sort Facility Records by Earliest Arrival Day (AD)
      Collections.sort(facilityRecordList);

      // Process the top Facility Record (earliest arrival date)
      processTopFacilityRecord(facilityRecordList.get(0), orderLogisticsRecord, orderItem);
    }
  }

  private void processTopFacilityRecord(OrderFacilityRecord orderFacilityRecord, LogisticsRecord orderLogisticsRecord, OrderItem orderItem)
      throws DataValidationException {

    // Reduce the inventory of the selected site by the number of items in the Facility Record
    FacilityManager.getInstance().reduceFacilityInventory(orderFacilityRecord.getFacilityLocation(), orderFacilityRecord.getItemId(),
        orderFacilityRecord.getItemQuantity());

    // Reduce the Order Item quantity needed by the number of items processed in the Facility Record
    orderItem.setItemQuantity(orderItem.getItemQuantity() - orderFacilityRecord.getItemQuantity());

    // Update the schedule of the selected site (book the time represented in the Site Record)
    FacilityManager.getInstance().scheduleItemProcessing(orderFacilityRecord.getFacilityLocation(), orderFacilityRecord.getOrderStartDay(),
        orderFacilityRecord.getItemQuantity());

    // Add this Facility Record to the order Item solution list
    orderLogisticsRecord.addOrderItemFacilityRecord(orderItem.getItemId(), orderFacilityRecord);

  }

  private List<String> getListOfFacilitiesWithStockedItem(OrderDTO orderDTO, OrderItem orderItem) throws DataValidationException {
    List<String> facilitiesWithItem = FacilityManager.getInstance().getFacilitiesWithItem(orderItem.getItemId());
    int destinationFacilityIndex = facilitiesWithItem.indexOf(orderDTO.destination);

    // If destination facility is in list of facilities with item stocked
    if (destinationFacilityIndex > -1) {
      facilitiesWithItem.remove(destinationFacilityIndex);
    }

    return facilitiesWithItem;
  }

  private List<OrderFacilityRecord> generateFacilityRecords(OrderDTO orderDTO, OrderItem orderItem, List<String> facilitiesWithItem)
      throws DataValidationException {
    List<OrderFacilityRecord> facilityRecordsList = new ArrayList<OrderFacilityRecord>();

    for (String facility : facilitiesWithItem) {
      OrderFacilityRecord newFacilityRecord = new OrderFacilityRecordImpl();
      newFacilityRecord.populateOrderFacilityRecord(orderDTO, orderItem, facility);
      facilityRecordsList.add(newFacilityRecord);
    }

    return facilityRecordsList;
  }

}
