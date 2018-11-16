package net.cbeeland.dto.order;

import java.util.List;

import net.cbeeland.domain.order.item.OrderItem;

/**
 * Data Transfer Object (DTO) for Order data
 * 
 * @author cbeeland
 */
public class OrderDTO {
  public String id;
  public String destination;
  public Integer startDate;
  public List<OrderItem> orderItems;
}
