package net.cbeeland.dao.order;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Java class for orderDAO complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="orderDAO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orderId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="orderTime" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="destination" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="orderItems" type="{}orderItemsDAO"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "orderDAO", propOrder = {"orderId", "orderTime", "destination", "orderItems"})
public class OrderDAO {

  @XmlElement(required = true)
  protected String orderId;
  protected int orderTime;
  @XmlElement(required = true)
  protected String destination;
  @XmlElement(required = true)
  protected OrderItemsDAO orderItems;

  /**
   * Gets the value of the orderId property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getOrderId() {
    return orderId;
  }

  /**
   * Sets the value of the orderId property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setOrderId(String value) {
    this.orderId = value;
  }

  /**
   * Gets the value of the orderTime property.
   * 
   */
  public int getOrderTime() {
    return orderTime;
  }

  /**
   * Sets the value of the orderTime property.
   * 
   */
  public void setOrderTime(int value) {
    this.orderTime = value;
  }

  /**
   * Gets the value of the destination property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getDestination() {
    return destination;
  }

  /**
   * Sets the value of the destination property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setDestination(String value) {
    this.destination = value;
  }

  /**
   * Gets the value of the orderItems property.
   * 
   * @return possible object is {@link OrderItemsDAO }
   * 
   */
  public OrderItemsDAO getOrderItems() {
    return orderItems;
  }

  /**
   * Sets the value of the orderItems property.
   * 
   * @param value allowed object is {@link OrderItemsDAO }
   * 
   */
  public void setOrderItems(OrderItemsDAO value) {
    this.orderItems = value;
  }

}
