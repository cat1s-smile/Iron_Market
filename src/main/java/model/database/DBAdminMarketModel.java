package model.database;

import entities.jaxbready.*;
import entities.main.Category;
import entities.main.Product;
import model.AdminMarketModel;
import org.xml.sax.SAXException;

import javax.ejb.Stateless;
import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
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
                category.getId() != duplicate.getId())
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
        ProductDataBase.insert(transformProduct(rawProduct, category.getId()));
    }

    @Override
    public ShopContent createShopContent(String xmlFilePath, String xsdSchemaPath) throws JAXBException, SAXException {
        JAXBContext context = JAXBContext.newInstance(ShopContent.class);

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File(xsdSchemaPath));

        Unmarshaller unmarshaller = context.createUnmarshaller();

        unmarshaller.setSchema(schema);
       // unmarshaller.setEventHandler(new EmployeeValidationEventHandler());

        ShopContent shopContent = (ShopContent) unmarshaller.unmarshal(new File(xmlFilePath));
        return shopContent;
    }

/*class EmployeeValidationEventHandler implements ValidationEventHandler {
    @Override
    public boolean handleEvent(ValidationEvent event) {
        System.out.println("\nEVENT");
        System.out.println("SEVERITY:  " + event.getSeverity());
        System.out.println("MESSAGE:  " + event.getMessage());
        System.out.println("LINKED EXCEPTION:  " + event.getLinkedException());
        System.out.println("LOCATOR");
        System.out.println("    LINE NUMBER:  " + event.getLocator().getLineNumber());
        System.out.println("    COLUMN NUMBER:  " + event.getLocator().getColumnNumber());
        System.out.println("    OFFSET:  " + event.getLocator().getOffset());
        System.out.println("    OBJECT:  " + event.getLocator().getObject());
        System.out.println("    NODE:  " + event.getLocator().getNode());
        System.out.println("    URL:  " + event.getLocator().getURL());
        return true;
    }
}
    }*/

    @Override
    public void toXmlFile(ShopContent shopContent, String xmlFilePath) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(ShopContent.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        FileOutputStream fileOutputStream = new FileOutputStream(new File(xmlFilePath));
        marshaller.marshal(shopContent, fileOutputStream);
    }

    @Override
    public void toXmlFile(ShopContent shopContent, OutputStream out) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(ShopContent.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(shopContent, out);
    }

    private ShopContent createShopContent(List<Product> products, List<Category> categories) {
        ObjectFactory factory = new ObjectFactory();
        List<RawCategory> rawCategories = new ArrayList<>();
        for (Category category : categories) {
            RawCategory cat = factory.createCategory();
            cat.setName(category.getName());
            cat.setId(category.getId());
            rawCategories.add(cat);
        }
        List<RawProduct> rawProducts = new ArrayList<>();
        for (Product product : products) {
            RawProduct prod = factory.createProduct();
            prod.setName(product.getName());
            for(Category cat : categories) {
                if (product.getCategory() == cat.getId()) {
                    prod.setIdCategory(cat.getName());
                    break;
                }
            }
            prod.setId(product.getId());
            prod.setAmount(product.getAmount());
            prod.setPrice(product.getPrice());
            prod.setDescription(product.getDescription());
            rawProducts.add(prod);
        }
        ShopContent shopContent = factory.createShopContent();
        Categories jaxbCategories = factory.createCategories();
        jaxbCategories.setRawCategory(rawCategories);
        Products jaxbProducts = factory.createProducts();
        jaxbProducts.setRawProduct(rawProducts);
        shopContent.setProducts(jaxbProducts);
        shopContent.setCategories(jaxbCategories);
        return shopContent;
    }

    @Override
    public ShopContent getAllProducts() {
        return createShopContent(ProductDataBase.select(), CategoryDataBase.select());
    }

    @Override
    public ShopContent getProductsOnSale() {
        return createShopContent(ProductDataBase.selectOnSale(), CategoryDataBase.select());
    }

    @Override
    public ShopContent getArchivedProducts() {
        return createShopContent(ProductDataBase.selectArchivedProducts(), CategoryDataBase.select());
    }



    private int getCategoryID(List<Category> categories, String categoryName) {
        for (Category category : categories) {
            if (categoryName.equalsIgnoreCase(category.getName()))
                return category.getId();
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
