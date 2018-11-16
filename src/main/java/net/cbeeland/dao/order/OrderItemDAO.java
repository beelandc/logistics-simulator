package net.cbeeland.dao.order;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Java class for orderItemDAO complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="orderItemDAO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="itemId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "orderItemDAO", propOrder = {"itemId", "quantity"})
public class OrderItemDAO {

  @XmlElement(required = true)
  protected String itemId;
  protected int quantity;

  /**
   * Gets the value of the itemId property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getItemId() {
    return itemId;
  }

  /**
   * Sets the value of the itemId property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setItemId(String value) {
    this.itemId = value;
  }

  /**
   * Gets the value of the quantity property.
   * 
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Sets the value of the quantity property.
   * 
   */
  public void setQuantity(int value) {
    this.quantity = value;
  }

}
