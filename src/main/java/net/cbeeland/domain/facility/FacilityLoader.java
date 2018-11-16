package net.cbeeland.domain.facility;

import java.io.File;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.cbeeland.dao.facility.FacilitiesDAO;
import net.cbeeland.dao.facility.FacilityDAO;
import net.cbeeland.dao.facility.InventoryDAO;
import net.cbeeland.dao.facility.FacilityItemDAO;
import net.cbeeland.dao.facility.LinkedFacilityDAO;
import net.cbeeland.dao.facility.TransportationLinksDAO;
import net.cbeeland.domain.catalog.CatalogManager;
import net.cbeeland.exception.DataValidationException;

public class FacilityLoader {

  public HashMap<String, Facility> loadFacilitiesXML(String xmlFilePath) {
    try {

      File file = new File(xmlFilePath);
      JAXBContext jaxbContext = JAXBContext.newInstance(FacilitiesDAO.class);

      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      FacilitiesDAO facilitiesDao = (FacilitiesDAO) jaxbUnmarshaller.unmarshal(file);

      return processFacilitiesDaoList(facilitiesDao);

    } catch (JAXBException e) {
      System.out.println("ERROR: JAXBException occurred while loading facilities into DAOs from XML file: " + xmlFilePath);
      e.printStackTrace();
    }

    // If XML load fails, log error and return a null facility
    return null;
  }

  private HashMap<String, Facility> processFacilitiesDaoList(FacilitiesDAO facilitiesDao) {
    HashMap<String, Facility> facilitiesMap = new HashMap<String, Facility>();

    for (FacilityDAO facilityDao : facilitiesDao.getFacility()) {
      Facility facility = new FacilityImpl();

      try {
        facility.setLocation(facilityDao.getLocation());
        facility.setRate(facilityDao.getDailyProcessingCapacity());
        facility.setCost(facilityDao.getDailyProcessingCost());

        processInventory(facility, facilityDao.getInventory());
        processTransportationLinks(facility, facilityDao.getTransportationLinks());

        facilitiesMap.put(facility.getLocation(), facility);
      } catch (DataValidationException e) {
        System.out.println("ERROR: Invalid facility record found when loading " + facility.getLocation() + " facility from XML");
        e.printStackTrace();
      }

    }

    return facilitiesMap;
  }

  private void processInventory(Facility facility, InventoryDAO inventoryDao) throws DataValidationException {

    for (FacilityItemDAO itemDao : inventoryDao.getItem()) {

      try {
        // Validate incoming items via CatalogManager
        if (CatalogManager.getInstance().isItemInCatalog(itemDao.getId())) {
          facility.addItemToInventory(itemDao.getId(), itemDao.getQuantity());
        } else {
          System.out
              .println("Error loading Item " + itemDao.getId() + " from " + facility.getLocation() + " facility in facilities XML. Item is not in Catalog.");
        }
      } catch (DataValidationException e) {
        throw new DataValidationException("ERROR: Invalid Item record found when loading " + facility.getLocation() + " facility inventory from XML", e);
      }

    }

  }

  private void processTransportationLinks(Facility facility, TransportationLinksDAO transportationLinksDao) throws DataValidationException {
    try {
      for (LinkedFacilityDAO linkedFacilityDao : transportationLinksDao.getLinkedFacility()) {
        facility.addDirectLinkToFacility(linkedFacilityDao.getLocation(), linkedFacilityDao.getDistance());
      }
    } catch (DataValidationException e) {
      throw new DataValidationException(
          "ERROR: Invalid Facility Link record found when loading " + facility.getLocation() + " facility transportation links from XML", e);
    }
  }

}
