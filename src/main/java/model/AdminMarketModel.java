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
    List<Category> getCategories() throws DAOException;

    List<Product> getProducts() throws DAOException;

    List<Product> getProducts(List<Integer> ids) throws DAOException;

    Product getProduct(int ProductID) throws DAOException;

    void createProduct(Product newProduct) throws DAOException;

    void editProduct(Product product) throws DAOException;

    void editProducts(List<Product> product, int newPrice) throws DAOException;

    void editProducts(List<Product> product, String newCategory) throws DAOException;

    void editProducts(List<Product> product, int newPrice, String newCategory) throws DAOException;

    void deleteProduct(int productID) throws DAOException;

    boolean canProductBeRemoved(int productID) throws DAOException;

    Category getCategory(int categoryID) throws DAOException;

    Category getCategory(String categoryName) throws DAOException;

    void createCategory(Category newCategory) throws DAOException;

    void editCategory(Category category) throws DAOException;

    void deleteCategory(int categoryID) throws DAOException;

    boolean canCategoryBeRemoved(int categoryID) throws DAOException;

    void insertUpdateIgnoreDuplicates(ShopContent shopContent) throws DAOException;

    void insertUpdateOverwriteDuplicates(ShopContent shopContent) throws DAOException;

    void insertUpdateWithDuplicates(ShopContent shopContent) throws DAOException;

    ShopContent createShopContent(String xmlFilePath, Source xsdSchema) throws DAOException;

    void toXmlFile(ShopContent shopContent, String xmlFilePath) throws DAOException;

    void toXmlFile(ShopContent shopContent, OutputStream out) throws DAOException;

    String xslTransform(InputStream src, Source xsl, String xmlOutPath) throws DAOException;

    ShopContent getAllProducts() throws DAOException;

    ShopContent getProductsOnSale() throws DAOException;

    ShopContent getArchivedProducts() throws DAOException;

    void changeProductStatus(int id) throws DAOException;

    void setExistingCategoryID(Product product) throws DAOException;
}
