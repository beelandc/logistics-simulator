package net.cbeeland.domain.catalog;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.cbeeland.dao.catalog.ItemCatalogDAO;
import net.cbeeland.exception.DataValidationException;
import net.cbeeland.dao.catalog.CatalogItemDAO;

public class CatalogLoader {

  public Catalog loadCatalogXML(String xmlFilePath) {
    try {

      File file = new File(xmlFilePath);
      JAXBContext jaxbContext = JAXBContext.newInstance(ItemCatalogDAO.class);

      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      ItemCatalogDAO catalogDao = (ItemCatalogDAO) jaxbUnmarshaller.unmarshal(file);

      return processCatalogDao(catalogDao);

    } catch (JAXBException e) {
      System.out.println("ERROR: JAXBException occurred while loading the item catalog into DAOs from XML file: " + xmlFilePath);
      e.printStackTrace();
    }

    // If XML load fails, log error and return a null catalog
    return null;
  }

  private Catalog processCatalogDao(ItemCatalogDAO catalogDao) {
    Catalog catalog = new CatalogImpl();

    for (CatalogItemDAO itemDao : catalogDao.getItem()) {
      try {
        catalog.addItem(itemDao.getId(), itemDao.getPrice());
      } catch (DataValidationException e) {
        System.out.println("ERROR: Invalid Item record found when loading catalog from XML: " + itemDao.getId());
        e.printStackTrace();
      }
    }

    return catalog;
  }

}
