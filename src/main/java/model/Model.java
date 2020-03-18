package model;

import entities.jaxbready.RawCategory;
import entities.jaxbready.RawProduct;
import entities.jaxbready.ShopContent;
import entities.main.Category;
import entities.main.Product;
import model.database.*;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class Model {
    public static void insertUpdateIgnoreDuplicates(ShopContent shopContent) {
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

    public static void insertUpdateOverwriteDuplicates(ShopContent shopContent) {
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

    public static void insertUpdateWithDuplicates(ShopContent shopContent) {
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

    private static Product transformProduct(RawProduct rawProduct, int categoryID) {
        return new Product(categoryID,
                rawProduct.getName(),
                rawProduct.getPrice(),
                rawProduct.getAmount(),
                rawProduct.getDescription());
    }

    private static void insertProductWithNewCategory(RawProduct rawProduct, List<Category> categories) {
        Category category = new Category(rawProduct.getIdCategory());
        CategoryDataBase.insert(category);
        category = CategoryDataBase.searchByName(rawProduct.getIdCategory());
        categories.add(category);
        ProductDataBase.insert(transformProduct(rawProduct, category.getIdCategory()));
    }

    private static int getCategoryID(List<Category> categories, String categoryName) {
        for (Category category : categories) {
            if (categoryName.equalsIgnoreCase(category.getName()))
                return category.getIdCategory();
        }
        return -1;
    }

    private static List<Category> insertAndPrepareCategories(List<RawCategory> rawCategories) {
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
