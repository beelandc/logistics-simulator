package net.cbeeland.domain.order;

import java.util.ArrayList;
import java.util.List;

import net.cbeeland.domain.order.logisticsrecord.LogisticsRecord;
import net.cbeeland.dto.order.OrderDTO;
import net.cbeeland.exception.DataValidationException;
import net.cbeeland.processor.order.OrderProcessor;
import net.cbeeland.util.InputValidator;

/**
 * Façade for Orders -- Singleton
 * 
 * @author cbeeland
 *
 */
public class OrderManager {

  private volatile static OrderManager instance;
  private final String ORDERS_XML_FILE_PATH = "src/main/resources/orders.xml";

  private OrderLoader orderLoader;
  private List<Order> unprocessedOrders;
  private List<Order> processedOrders;

  private OrderManager() {
    orderLoader = new OrderLoader();
    unprocessedOrders = orderLoader.loadOrderXML(ORDERS_XML_FILE_PATH);
    processedOrders = new ArrayList<Order>();

  }

  public static OrderManager getInstance() {
    if (instance == null) {
      synchronized (OrderManager.class) {
        if (instance == null) {
          instance = new OrderManager();
        }
      }
    }
    return instance;
  }

  private OrderDTO createOrderDTO(Order order) throws DataValidationException {
    InputValidator.validateInstantiatedObject(order, "order", "OrderManager", "createOrderDTO(Order order)");
    OrderDTO orderDTO = new OrderDTO();

    orderDTO.id = order.getOrderId();
    orderDTO.startDate = order.getStartDate();
    orderDTO.destination = order.getDestination();
    orderDTO.orderItems = order.getOrderItems();

    return orderDTO;
  }

  public void processCurrentOrders() {
    while (unprocessedOrders.size() > 0) {
      Order order = unprocessedOrders.get(0);

      try {
        OrderDTO orderDTO = createOrderDTO(order);

        LogisticsRecord orderLogisticsRecord = OrderProcessor.getInstance().processOrder(orderDTO);
        order.addLogisticsRecord(orderLogisticsRecord);

        processedOrders.add(order);
        unprocessedOrders.remove(0);
      } catch (DataValidationException e) {
        System.out.println("ERROR: Unexpected DataValidationException occurred while processing order: " + order.getOrderId());
        e.printStackTrace();
      }
    }
  }

  public void printUnprocessedOrders() {
    System.out.println("Unprocessed Orders: ");
    for (Order unprocessedOrder : unprocessedOrders) {
      System.out.println("----------------------------------------------------------------------------------\n");
      System.out.println(unprocessedOrder.toString());
      System.out.println("----------------------------------------------------------------------------------\n");
    }
  }

  public void printProcessedOrders() {
    System.out.println("Processed Orders: ");
    for (int x = 0; x < processedOrders.size(); x++) {
      Order processedOrder = processedOrders.get(x);
      System.out.println("----------------------------------------------------------------------------------\n");
      System.out.println("Order #" + (x + 1));
      System.out.println(processedOrder.toString());
      System.out.println(processedOrder.getLogisticsRecordDetails());
      System.out.println("----------------------------------------------------------------------------------\n");
    }
  }

}
