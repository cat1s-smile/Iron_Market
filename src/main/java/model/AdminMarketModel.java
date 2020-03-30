package model;

import entities.jaxbready.ShopContent;
import entities.main.Category;
import entities.main.Product;
import servlets.DAOException;

import javax.ejb.Local;
import javax.xml.transform.Source;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Local
public interface AdminMarketModel {
    List<Category> getCategories();

    List<Product> getProducts();

    List<Product> getProducts(List<Integer> ids);

    Product getProduct(int ProductID);

    void createProduct(Product newProduct);

    void editProduct(Product product);

    void editProducts(List<Product> product, int newPrice);

    void editProducts(List<Product> product, String newCategory) throws DAOException;

    void editProducts(List<Product> product, int newPrice, String newCategory) throws DAOException;

    void deleteProduct(int productID) throws DAOException;

    boolean canProductBeRemoved(int productID);

    Category getCategory(int categoryID);

    Category getCategory(String categoryName);

    void createCategory(Category newCategory) throws DAOException;

    void editCategory(Category category) throws DAOException;

    void deleteCategory(int categoryID) throws DAOException;

    boolean canCategoryBeRemoved(int categoryID);

    void insertUpdateIgnoreDuplicates(ShopContent shopContent);

    void insertUpdateOverwriteDuplicates(ShopContent shopContent);

    void insertUpdateWithDuplicates(ShopContent shopContent);

    ShopContent createShopContent(String xmlFilePath, Source xsdSchema) throws DAOException;

    void toXmlFile(ShopContent shopContent, String xmlFilePath) throws DAOException;

    void toXmlFile(ShopContent shopContent, OutputStream out) throws DAOException;

    String xslTransform(InputStream src, Source xsl, String xmlOutPath) throws DAOException;

    ShopContent getAllProducts();

    ShopContent getProductsOnSale();

    ShopContent getArchivedProducts();

    void changeProductStatus(int id);

    void setExistingCategoryID(Product product) throws DAOException;
}
