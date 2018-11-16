package net.cbeeland.driver;

import net.cbeeland.domain.facility.FacilityManager;
import net.cbeeland.domain.order.OrderManager;

public class Main {

  public static void main(String[] args) {

    System.out.println("Facility Status Report:");
    FacilityManager.getInstance().printFacilitiesReport();

    OrderManager.getInstance().processCurrentOrders();
    OrderManager.getInstance().printProcessedOrders();

    System.out.println("Facility Status Report:");
    FacilityManager.getInstance().printFacilitiesReport();

  }

}
