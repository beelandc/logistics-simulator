package net.cbeeland.domain.facility.schedule;

import java.util.HashMap;

import net.cbeeland.exception.DataValidationException;
import net.cbeeland.util.InputValidator;

public class ScheduleImpl implements Schedule {

  private Integer rate;
  private HashMap<Integer, Integer> schedule;

  public ScheduleImpl() {
    schedule = new HashMap<Integer, Integer>();
  }

  public int getRate() {
    return rate.intValue();
  }

  public void setRate(Integer newRate) throws DataValidationException {
    InputValidator.validateNonNegativeInteger(newRate, "newRate", "Schedule", "setRate(Integer newRate)");
    rate = newRate;
  }

  public int calculateProcessingEndDay(int startDate, int itemQuantity) throws DataValidationException {
    InputValidator.validatePositiveInteger(startDate, "startDate", "Schedule", "calculateProcessingEndDay(int startDate, int itemQuantity)");
    InputValidator.validatePositiveInteger(itemQuantity, "itemQuantity", "Schedule", "calculateProcessingEndDay(int startDate, int itemQuantity)");

    int processingEndDay = startDate;

    // Start at "startDate" and iterate until all item processing is accounted for
    while (itemQuantity > 0) {
      Integer availableProcessingCapacity = schedule.get(processingEndDay);

      if (availableProcessingCapacity == null) {
        itemQuantity = (itemQuantity - rate);
      } else {
        itemQuantity = (itemQuantity - availableProcessingCapacity);
      }

      if (itemQuantity > 0) {
        processingEndDay++;
      }
    }

    return processingEndDay;
  }

  public void scheduleItemProcessing(int startDate, int itemQuantity) throws DataValidationException {
    InputValidator.validatePositiveInteger(startDate, "startDate", "Schedule", "calculateProcessingEndDay(int startDate, int itemQuantity)");
    InputValidator.validatePositiveInteger(itemQuantity, "itemQuantity", "Schedule", "calculateProcessingEndDay(int startDate, int itemQuantity)");

    int processingDay = startDate;

    // Start at "startDate" and iterate until all item processing is accounted for
    while (itemQuantity > 0) {
      Integer availableProcessingCapacity = schedule.get(processingDay);

      if (availableProcessingCapacity == null) {
        itemQuantity = (itemQuantity - rate);
        bookProcessingTime(itemQuantity, processingDay);
      } else {
        itemQuantity = (itemQuantity - availableProcessingCapacity);
        bookProcessingTime(itemQuantity, processingDay);
      }

      if (itemQuantity > 0) {
        processingDay++;
      }
    }

  }

  private void bookProcessingTime(int itemQuantity, int processingDay) {
    if (itemQuantity >= 0) {
      schedule.put(processingDay, 0);
    } else {
      // itemQuantity is negative
      schedule.put(processingDay, (rate - (rate + itemQuantity)));
    }
  }

  public String toString() {
    StringBuilder formattedScheduleDetails = new StringBuilder();

    formattedScheduleDetails.append("Schedule:\n");
    formattedScheduleDetails.append("Day:               ");
    for (int i = 0; i < 20; i++) {
      formattedScheduleDetails.append(String.format("%-3s", i + 1));
    }
    formattedScheduleDetails.append("\n");

    formattedScheduleDetails.append("Available:         ");
    for (int i = 0; i < 20; i++) {
      if (schedule.containsKey(i + 1)) {
        formattedScheduleDetails.append(String.format("%-3s", schedule.get(i + 1)));
      } else {
        formattedScheduleDetails.append(String.format("%-3s", rate));
      }
    }
    formattedScheduleDetails.append("\n");

    return formattedScheduleDetails.toString();
  }

}
