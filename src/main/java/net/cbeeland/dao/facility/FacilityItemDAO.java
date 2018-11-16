package net.cbeeland.dao.facility;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Java class for itemDAO complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="itemDAO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "itemDAO", propOrder = {"id", "quantity"})
public class FacilityItemDAO {

  @XmlElement(required = true)
  protected String id;
  protected int quantity;

  /**
   * Gets the value of the id property.
   * 
   * @return {@link String }
   * 
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the value of the id property.
   * 
   * @param {@link String } newId
   * 
   */
  public void setId(String newId) {
    id = newId;
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
  public void setQuantity(int newQuantity) {
    quantity = newQuantity;
  }

}
