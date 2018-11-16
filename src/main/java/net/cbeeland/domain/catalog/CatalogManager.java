package net.cbeeland.domain.catalog;

import net.cbeeland.exception.DataValidationException;
import net.cbeeland.util.InputValidator;

/**
 * Façade for Catalog -- Singleton
 * 
 * @author cbeeland
 *
 */
public class CatalogManager {

  private volatile static CatalogManager instance;
  private final String CATALOG_XML_FILE_PATH = "src/main/resources/itemCatalog.xml";

  private CatalogLoader catalogLoader;
  private Catalog catalog;

  private CatalogManager() {
    catalogLoader = new CatalogLoader();
    catalog = catalogLoader.loadCatalogXML(CATALOG_XML_FILE_PATH);
  }

  public static CatalogManager getInstance() {
    if (instance == null) {
      synchronized (CatalogManager.class) {
        if (instance == null) {
          instance = new CatalogManager();
        }
      }
    }
    return instance;
  }

  public int getItemPrice(String itemId) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "CatalogManager", "getItemPrice(String itemId)");
    return catalog.getItemPrice(itemId);
  }

  public void printCatalogReport() {
    System.out.println(catalog.toString());
  }

  public boolean isItemInCatalog(String itemId) throws DataValidationException {
    InputValidator.validateStringNotNullOrEmpty(itemId, "itemId", "CatalogManager", "isItemInCatalog(String itemId)");
    return catalog.isItemInCatalog(itemId);
  }

}
