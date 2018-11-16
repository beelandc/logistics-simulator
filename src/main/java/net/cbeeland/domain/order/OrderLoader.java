package net.cbeeland.domain.order;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.cbeeland.dao.order.OrderDAO;
import net.cbeeland.dao.order.OrderItemDAO;
import net.cbeeland.dao.order.OrderItemsDAO;
import net.cbeeland.dao.order.OrdersDAO;
import net.cbeeland.domain.facility.FacilityManager;
import net.cbeeland.exception.DataValidationException;

public class OrderLoader {

  public List<Order> loadOrderXML(String xmlFilePath) {
    try {

      File file = new File(xmlFilePath);
      JAXBContext jaxbContext = JAXBContext.newInstance(OrdersDAO.class);

      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      OrdersDAO ordersDao = (OrdersDAO) jaxbUnmarshaller.unmarshal(file);

      return processOrdersDao(ordersDao);

    } catch (JAXBException e) {
      System.out.println("ERROR: JAXBException occurred while loading the orders into DAOs from XML file: " + xmlFilePath);
      e.printStackTrace();
    }

    // If XML load fails, log error and return a null orders list
    return null;
  }

  private List<Order> processOrdersDao(OrdersDAO ordersDao) {
    List<Order> ordersList = new ArrayList<Order>();

    for (OrderDAO orderDao : ordersDao.getOrder()) {
      Order order = new OrderImpl();

      try {
        order.setOrderId(orderDao.getOrderId());
        order.setStartDate(orderDao.getOrderTime());

        // Validate order destination
        if (FacilityManager.getInstance().doesFacilityExist(orderDao.getDestination())) {
          order.setDestination(orderDao.getDestination());
        } else {
          throw new DataValidationException(
              "ERROR: Invalid destination facility found when loading " + orderDao.getOrderId() + "order from XML: " + orderDao.getDestination());
        }

        processOrderItems(order, orderDao.getOrderItems());
        ordersList.add(order);

      } catch (DataValidationException e) {
        System.out.println("ERROR: Invalid order record found when loading " + orderDao.getOrderId() + " order from XML");
        e.printStackTrace();
      }

    }

    return ordersList;
  }

  private void processOrderItems(Order order, OrderItemsDAO orderItems) throws DataValidationException {
    try {
      for (OrderItemDAO orderItemDao : orderItems.getOrderItem()) {
        order.addOrderItem(orderItemDao.getItemId(), orderItemDao.getQuantity());
      }
    } catch (DataValidationException e) {
      throw new DataValidationException("ERROR: Invalid Order Item record found when loading " + order.getOrderId() + " order items from XML", e);
    }

  }

}
