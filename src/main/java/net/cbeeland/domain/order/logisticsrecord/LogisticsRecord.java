package net.cbeeland.domain.order.logisticsrecord;

import net.cbeeland.domain.order.facilityrecord.OrderFacilityRecord;
import net.cbeeland.exception.DataValidationException;

public interface LogisticsRecord {

  public void addOrderItemFacilityRecord(String itemId, OrderFacilityRecord orderFacilityRecord) throws DataValidationException;

  public String generateLogisticsRecordDetails();

}
