//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2020.03.15 at 10:00:27 PM SAMT
//


package entities.jaxbready;

import entities.main.Category;
import entities.main.Product;

import javax.xml.bind.annotation.XmlRegistry;
import java.util.ArrayList;
import java.util.List;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the generated package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ShopContent }
     *
     */
    public ShopContent createShopContent() {
        return new ShopContent();
    }

    /**
     * Create an instance of {@link Products }
     *
     */
    public Products createProducts() {
        return new Products();
    }

    /**
     * Create an instance of {@link Categories }
     *
     */
    public Categories createCategories() {
        return new Categories();
    }

    /**
     * Create an instance of {@link RawProduct }
     *
     */
    public RawProduct createProduct() {
        return new RawProduct();
    }

    /**
     * Create an instance of {@link RawCategory }
     *
     */
    public RawCategory createCategory() {
        return new RawCategory();
    }

    public ShopContent createShopContent(List<Product> products, List<Category> categories) {
        Categories jaxbCategories = createCategories();
        jaxbCategories.rawCategory = new ArrayList<>();
        for (Category category : categories) {
            RawCategory cat = createCategory();
            cat.name = category.getName();
            cat.id = category.getIdCategory();
            jaxbCategories.rawCategory.add(cat);
        }
        Products jaxbProducts = createProducts();
        jaxbProducts.rawProduct = new ArrayList<>();
        for (Product product : products) {
            RawProduct prod = createProduct();
            prod.name = product.getName();
            for(Category cat : categories) {
                if (product.getIdCategory() == cat.getIdCategory()) {
                    prod.idCategory = cat.getName();
                    break;
                }
            }
            prod.id = product.getIdProduct();
            prod.amount = product.getAmount();
            prod.price = product.getPrice();
            prod.description = product.getDescription();
            jaxbProducts.rawProduct.add(prod);
        }
        ShopContent shopContent = createShopContent();
        shopContent.products = jaxbProducts;
        shopContent.categories = jaxbCategories;
        return shopContent;
    }

  /*  public List<entities.RawCategory> getRawCategories(ShopContent shopContent) {
        List<RawCategory> categories = shopContent.categories.rawCategory;
        List<entities.RawCategory> categoryList = new ArrayList<>();
        for (RawCategory rawCategory : categories) {
            categoryList.add(new entities.RawCategory(rawCategory.id, rawCategory.name));
        }
        return categoryList;
    } */
}