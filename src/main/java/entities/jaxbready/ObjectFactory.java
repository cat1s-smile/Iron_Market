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
}
