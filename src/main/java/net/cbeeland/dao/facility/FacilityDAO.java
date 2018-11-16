package net.cbeeland.dao.facility;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Java class for facilityDAO complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="facilityDAO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dailyProcessingCapacity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dailyProcessingCost" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="transportationLinks" type="{}transportationLinksDAO"/>
 *         &lt;element name="inventory" type="{}inventoryDAO"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "facilityDAO", propOrder = {"location", "dailyProcessingCapacity", "dailyProcessingCost", "transportationLinks", "inventory"})
public class FacilityDAO {

  @XmlElement(required = true)
  protected String location;
  protected int dailyProcessingCapacity;
  protected int dailyProcessingCost;
  @XmlElement(required = true)
  protected TransportationLinksDAO transportationLinks;
  @XmlElement(required = true)
  protected InventoryDAO inventory;

  /**
   * Gets the value of the location property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getLocation() {
    return location;
  }

  /**
   * Sets the value of the location property.
   * 
   * @param newLocation allowed object is {@link String }
   * 
   */
  public void setLocation(String newLocation) {
    location = newLocation;
  }

  /**
   * Gets the value of the dailyProcessingCapacity property.
   * 
   */
  public int getDailyProcessingCapacity() {
    return dailyProcessingCapacity;
  }

  /**
   * Sets the value of the dailyProcessingCapacity property.
   * 
   */
  public void setDailyProcessingCapacity(int newDailyProcessingCapacity) {
    dailyProcessingCapacity = newDailyProcessingCapacity;
  }

  /**
   * Gets the value of the dailyProcessingCost property.
   * 
   */
  public int getDailyProcessingCost() {
    return dailyProcessingCost;
  }

  /**
   * Sets the value of the dailyProcessingCost property.
   * 
   */
  public void setDailyProcessingCost(int newDailyProcessingCost) {
    dailyProcessingCost = newDailyProcessingCost;
  }

  /**
   * Gets the value of the transportationLinks property.
   * 
   * @return possible object is {@link TransportationLinksDAO }
   * 
   */
  public TransportationLinksDAO getTransportationLinks() {
    return transportationLinks;
  }

  /**
   * Sets the value of the transportationLinks property.
   * 
   * @param {@link TransportationLinksDAO } newTransportationLinks
   * 
   */
  public void setTransportationLinks(TransportationLinksDAO newTransportationLinks) {
    transportationLinks = newTransportationLinks;
  }

  /**
   * Gets the value of the inventory property.
   * 
   * @return {@link InventoryDAO }
   * 
   */
  public InventoryDAO getInventory() {
    return inventory;
  }

  /**
   * Sets the value of the inventory property.
   * 
   * @param {@link InventoryDAO } newInventory
   * 
   */
  public void setInventory(InventoryDAO newInventory) {
    inventory = newInventory;
  }

}
