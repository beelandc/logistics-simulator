package net.cbeeland.domain.order.logisticsrecord;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import net.cbeeland.domain.order.facilityrecord.OrderFacilityRecord;
import net.cbeeland.exception.DataValidationException;
import net.cbeeland.util.InputValidator;

public class LogisticsRecordImpl implements LogisticsRecord {

  HashMap<String, List<OrderFacilityRecord>> orderItemFacilityRecordsMap;
  Double totalOrderCost;

  public LogisticsRecordImpl() {
    orderItemFacilityRecordsMap = new HashMap<String, List<OrderFacilityRecord>>();
    totalOrderCost = 0.0;
  }

  public void addOrderItemFacilityRecord(String itemId, OrderFacilityRecord orderFacilityRecord) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "LogisticsRecord",
        "addOrderItemFacilityRecord(String itemId, OrderFacilityRecord orderFacilityRecord)");
    InputValidator.validateInstantiatedObject(orderFacilityRecord, "orderFacilityRecord", "LogisticsRecord",
        "addOrderItemFacilityRecord(String itemId, OrderFacilityRecord orderFacilityRecord)");

    List<OrderFacilityRecord> itemFacilityRecordsList = orderItemFacilityRecordsMap.get(itemId);

    if (itemFacilityRecordsList == null) {
      itemFacilityRecordsList = new ArrayList<OrderFacilityRecord>();
    }

    itemFacilityRecordsList.add(orderFacilityRecord);
    orderItemFacilityRecordsMap.put(itemId, itemFacilityRecordsList);

    totalOrderCost += orderFacilityRecord.getTotalOverallCost();

  }

  public String generateLogisticsRecordDetails() {
    StringBuilder formattedLogisticsRecordInformation = new StringBuilder();
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
    Map<String, List<OrderFacilityRecord>> sortedOrderItemFacilityRecordsMap = new TreeMap<String, List<OrderFacilityRecord>>(orderItemFacilityRecordsMap);

    formattedLogisticsRecordInformation.append("  Processing Solution:\n\n");

    for (Entry<String, List<OrderFacilityRecord>> itemEntry : sortedOrderItemFacilityRecordsMap.entrySet()) {
      String itemId = itemEntry.getKey();
      List<OrderFacilityRecord> itemOrderFacilityRecordList = itemEntry.getValue();
      int totalItemQuantity = 0;
      double totalItemCost = 0.0;
      int minArrivalDay = 0;
      int maxArrivalDay = 0;

      try {
        minArrivalDay = getMinArrivalDay(itemId);
        maxArrivalDay = getMaxArrivalDay(itemId);
      } catch (DataValidationException e) {
        System.out.println("ERROR: Unexpected DataValidationException in LogisticsRecord.toString()");
        e.printStackTrace();
      }

      formattedLogisticsRecordInformation.append("  " + itemId + ":\n");
      formattedLogisticsRecordInformation.append("           ");
      formattedLogisticsRecordInformation.append("Facility                      ");
      formattedLogisticsRecordInformation.append("Quantity    ");
      formattedLogisticsRecordInformation.append("Cost              ");
      formattedLogisticsRecordInformation.append("Arrival Day \n");

      for (int x = 0; x < itemOrderFacilityRecordList.size(); x++) {
        OrderFacilityRecord itemOrderFacilityRecord = itemOrderFacilityRecordList.get(x);
        totalItemQuantity += itemOrderFacilityRecord.getItemQuantity();
        totalItemCost += itemOrderFacilityRecord.getTotalOverallCost();

        formattedLogisticsRecordInformation.append("       " + (x + 1) + ")  ");
        formattedLogisticsRecordInformation.append(String.format("%-30s", itemOrderFacilityRecord.getFacilityLocation()));
        formattedLogisticsRecordInformation.append(String.format("%-12s", itemOrderFacilityRecord.getItemQuantity()));
        formattedLogisticsRecordInformation.append(String.format("%-18s", currencyFormatter.format(itemOrderFacilityRecord.getTotalOverallCost())));
        formattedLogisticsRecordInformation.append(itemOrderFacilityRecord.getArrivalDay());
        formattedLogisticsRecordInformation.append("\n");
      }

      formattedLogisticsRecordInformation.append("           ");
      formattedLogisticsRecordInformation.append(String.format("%-30s", "TOTAL"));
      formattedLogisticsRecordInformation.append(String.format("%-12s", totalItemQuantity));
      formattedLogisticsRecordInformation.append(String.format("%-18s", currencyFormatter.format(totalItemCost)));
      if (minArrivalDay == maxArrivalDay) {
        formattedLogisticsRecordInformation.append("[" + minArrivalDay + "]");
      } else {
        formattedLogisticsRecordInformation.append("[" + minArrivalDay + " - " + maxArrivalDay + "]");
      }

      formattedLogisticsRecordInformation.append("\n\n");
    }

    formattedLogisticsRecordInformation.append("  Total Cost:        " + currencyFormatter.format(totalOrderCost));

    return formattedLogisticsRecordInformation.toString();
  }

  private int getMinArrivalDay(String itemId) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "LogisticsRecord", "getMinArrivalDay(String itemId)");
    Integer minArrivalDay = null;

    List<OrderFacilityRecord> itemOrderFacilityRecordList = orderItemFacilityRecordsMap.get(itemId);

    if (itemOrderFacilityRecordList == null) {
      throw new DataValidationException("Item " + itemId + " is not in Logistics Record");
    }

    for (OrderFacilityRecord orderFacilityRecord : itemOrderFacilityRecordList) {
      int orderFacilityRecordArrivalDay = orderFacilityRecord.getArrivalDay();
      if ((minArrivalDay == null) || (orderFacilityRecordArrivalDay < minArrivalDay)) {
        minArrivalDay = orderFacilityRecordArrivalDay;
      }
    }

    return minArrivalDay.intValue();
  }

  private int getMaxArrivalDay(String itemId) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "LogisticsRecord", "getMaxArrivalDay(String itemId)");
    Integer maxArrivalDay = null;

    List<OrderFacilityRecord> itemOrderFacilityRecordList = orderItemFacilityRecordsMap.get(itemId);

    if (itemOrderFacilityRecordList == null) {
      throw new DataValidationException("Item " + itemId + " is not in Logistics Record");
    }

    for (OrderFacilityRecord orderFacilityRecord : itemOrderFacilityRecordList) {
      int orderFacilityRecordArrivalDay = orderFacilityRecord.getArrivalDay();
      if ((maxArrivalDay == null) || (orderFacilityRecordArrivalDay > maxArrivalDay)) {
        maxArrivalDay = orderFacilityRecordArrivalDay;
      }
    }

    return maxArrivalDay.intValue();
  }

}
