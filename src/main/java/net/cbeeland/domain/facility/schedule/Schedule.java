package net.cbeeland.domain.facility.schedule;

import net.cbeeland.exception.DataValidationException;

public interface Schedule {

  public int getRate();

  public void setRate(Integer rate) throws DataValidationException;

  public int calculateProcessingEndDay(int startDate, int itemQuantity) throws DataValidationException;

  public void scheduleItemProcessing(int startDate, int itemQuantity) throws DataValidationException;

}
