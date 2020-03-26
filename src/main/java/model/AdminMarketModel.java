package model;

import entities.jaxbready.ShopContent;
import entities.main.Category;
import entities.main.Product;
import org.xml.sax.SAXException;

import javax.ejb.Local;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.List;

@Local
public interface AdminMarketModel {
    List<Category> getCategories();

    List<Product> getProducts();

    Product getProduct(int ProductID);

    void createProduct(Product newProduct);

    void editProduct(Product product);

    void deleteProduct(int productID);

    boolean canProductBeRemoved(int productID);

    Category getCategory(int categoryID);

    Category getCategory(String categoryName);

    void createCategory(Category newCategory);

    void editCategory(Category category);

    void deleteCategory(int categoryID);

    boolean canCategoryBeRemoved(int categoryID);

    void insertUpdateIgnoreDuplicates(ShopContent shopContent);

    void insertUpdateOverwriteDuplicates(ShopContent shopContent);

    void insertUpdateWithDuplicates(ShopContent shopContent);

    ShopContent createShopContent(String xmlFilePath, String xsdSchemaPath) throws JAXBException, SAXException;

    void toXmlFile(ShopContent shopContent, String xmlFilePath) throws JAXBException, FileNotFoundException;

    void toXmlFile(ShopContent shopContent, OutputStream out) throws JAXBException, FileNotFoundException;

    ShopContent getAllProducts();

    ShopContent getProductsOnSale();

    ShopContent getArchivedProducts();

    void changeProductStatus(int id);
}
