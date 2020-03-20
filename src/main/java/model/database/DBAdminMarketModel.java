package model.database;

import entities.jaxbready.RawCategory;
import entities.jaxbready.RawProduct;
import entities.jaxbready.ShopContent;
import entities.main.Category;
import entities.main.Product;
import model.AdminMarketModel;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class DBAdminMarketModel implements AdminMarketModel {
    public List<Category> getCategories() {
        return CategoryDataBase.select();
    }

    public List<Product> getProducts() {
        return ProductDataBase.select();
    }

    @Override
    public Product getProduct(int productID) {
        return ProductDataBase.selectOne(productID);
    }

    @Override
    public void createProduct(Product newProduct) { //mb duplicate
        ProductDataBase.insert(newProduct);
    }

    @Override
    public void editProduct(Product product) {
        ProductDataBase.update(product);
    }

    @Override
    public void deleteProduct(int productID) {
        if(!canProductBeRemoved(productID))
            throw new IllegalArgumentException();
        ProductDataBase.delete(productID);
    }

    @Override
    public boolean canProductBeRemoved(int productID) {
        return  !OrderContentDataBase.doOrdersContain(productID);
    }

    @Override
    public Category getCategory(int categoryID) {
        return CategoryDataBase.selectOne(categoryID);
    }

    @Override
    public Category getCategory(String categoryName) {
        return CategoryDataBase.searchByName(categoryName);
    }

    @Override
    public void createCategory(Category newCategory) {
        if (CategoryDataBase.searchByName(newCategory.getName()) != null)
            throw new IllegalArgumentException();////////////////////////// not cool
        CategoryDataBase.insert(newCategory);
    }

    @Override
    public void editCategory(Category category) {
        Category duplicate = getCategory(category.getName());
        if (category.getName().equalsIgnoreCase(duplicate.getName()) &&
                category.getIdCategory() != duplicate.getIdCategory())
            throw new IllegalArgumentException();////////////////////////// not cool
        CategoryDataBase.update(category);
    }

    @Override
    public void deleteCategory(int categoryID) {
        if (!canCategoryBeRemoved(categoryID))
            throw new IllegalArgumentException();
        else
            CategoryDataBase.delete(categoryID);
    }

    @Override
    public boolean canCategoryBeRemoved(int categoryID) {
        List<Product> products = ProductDataBase.selectByCategory(categoryID);
        return products.isEmpty();
    }

    public void insertUpdateIgnoreDuplicates(ShopContent shopContent) {
        List<Category> categories = insertAndPrepareCategories(shopContent.getCategories().getRawCategory());
        for (RawProduct rawProduct : shopContent.getProducts().getRawProduct()) {
            int categoryID = getCategoryID(categories, rawProduct.getIdCategory());
            if (categoryID < 0)
                insertProductWithNewCategory(rawProduct, categories);
            else {
                Product product = transformProduct(rawProduct, categoryID);
                if (!ProductDataBase.isContain(product))
                    ProductDataBase.insert(product);
            }
        }
    }

    public void insertUpdateOverwriteDuplicates(ShopContent shopContent) {
        List<Category> categories = insertAndPrepareCategories(shopContent.getCategories().getRawCategory());
        for (RawProduct rawProduct : shopContent.getProducts().getRawProduct()) {
            int categoryID = getCategoryID(categories, rawProduct.getIdCategory());
            if (categoryID < 0)
                insertProductWithNewCategory(rawProduct, categories);
            else {
                Product product = transformProduct(rawProduct, categoryID);
                if (!ProductDataBase.isContain(product))
                    ProductDataBase.insert(product);
                else
                    ProductDataBase.update(product);
            }
        }
    }

    public void insertUpdateWithDuplicates(ShopContent shopContent) {
        List<Category> categories = insertAndPrepareCategories(shopContent.getCategories().getRawCategory());
        for (RawProduct rawProduct : shopContent.getProducts().getRawProduct()) {
            int categoryID = getCategoryID(categories, rawProduct.getIdCategory());
            if (categoryID < 0)
                insertProductWithNewCategory(rawProduct, categories);
            else {
                Product product = transformProduct(rawProduct, categoryID);
                ProductDataBase.insert(product);
            }
        }
    }

    @Override
    public void changeProductStatus(int id) {
        Product product = ProductDataBase.selectOne(id);
        if (product.getStatus() == 1) ProductDataBase.archive(product);
        else ProductDataBase.deArchive(product);
    }

    private Product transformProduct(RawProduct rawProduct, int categoryID) {
        return new Product(categoryID,
                rawProduct.getName(),
                rawProduct.getPrice(),
                rawProduct.getAmount(),
                rawProduct.getDescription());
    }

    private void insertProductWithNewCategory(RawProduct rawProduct, List<Category> categories) {
        Category category = new Category(rawProduct.getIdCategory());
        CategoryDataBase.insert(category);
        category = CategoryDataBase.searchByName(rawProduct.getIdCategory());
        categories.add(category);
        ProductDataBase.insert(transformProduct(rawProduct, category.getIdCategory()));
    }

    private int getCategoryID(List<Category> categories, String categoryName) {
        for (Category category : categories) {
            if (categoryName.equalsIgnoreCase(category.getName()))
                return category.getIdCategory();
        }
        return -1;
    }

    private List<Category> insertAndPrepareCategories(List<RawCategory> rawCategories) {
        List<Category> categories = new ArrayList<>();
        for (RawCategory rawCategory : rawCategories) {
            String categoryName = rawCategory.getName();
            Category category = CategoryDataBase.searchByName(categoryName);
            if (category == null) {
                category = new Category(categoryName);
                CategoryDataBase.insert(category);
                category = CategoryDataBase.searchByName(categoryName);
            }
            categories.add(category);
        }
        return categories;
    }
}
