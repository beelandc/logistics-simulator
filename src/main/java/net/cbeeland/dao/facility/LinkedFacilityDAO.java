package net.cbeeland.dao.facility;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Java class for linkedFacilityDAO complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="linkedFacilityDAO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="distance" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "linkedFacilityDAO", propOrder = {"location", "distance"})
public class LinkedFacilityDAO {

  @XmlElement(required = true)
  protected String location;
  protected int distance;

  /**
   * Gets the value of the location property.
   * 
   * @return {@link String } location
   * 
   */
  public String getLocation() {
    return location;
  }

  /**
   * Sets the value of the location property.
   * 
   * @param {@link String } newLocation
   * 
   */
  public void setLocation(String newLocation) {
    location = newLocation;
  }

  /**
   * Gets the value of the distance property.
   * 
   */
  public int getDistance() {
    return distance;
  }

  /**
   * Sets the value of the distance property.
   * 
   */
  public void setDistance(int newDistance) {
    distance = newDistance;
  }

}
