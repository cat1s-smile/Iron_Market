package model;

import entities.jaxbready.ShopContent;
import entities.main.Category;
import entities.main.Product;

import javax.ejb.Local;
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

    void changeProductStatus(int id);
}
