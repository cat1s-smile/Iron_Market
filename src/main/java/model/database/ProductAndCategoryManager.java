package model.database;

import entities.jaxbready.*;
import entities.main.Category;
import entities.main.Product;
import org.apache.log4j.Logger;
import org.hibernate.exception.JDBCConnectionException;
import org.xml.sax.SAXException;
import servlets.DAOException;

import javax.ejb.Stateless;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ProductAndCategoryManager {

    private static final Logger logger = Logger.getLogger("file");
    private static final Logger searchLogger = Logger.getLogger("searchFile");

    public List<Category> getCategories() throws DAOException {
        try {
            List<Category> categories = CategoryDataBase.select();
            for (Category category : categories)
                category.setAvailableToDelete(canCategoryBeRemoved(category.getId()));
            return categories;
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    public List<Product> getProducts() throws DAOException {
        try {
            List<Category> categories = CategoryDataBase.select();
            List<Product> products = setCategoryNames(ProductDataBase.select(), categories);
            for (Product product : products) {
                product.setAvailableToDelete(canProductBeRemoved(product.getId()));
            }
            return products;
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    public List<Product> getProductsOnMarket() throws DAOException {
        try {
            List<Category> categories = CategoryDataBase.select();
            List<Product> products = setCategoryNames(ProductDataBase.selectOnSale(), categories);
            for (Product product : products) {
                product.setAvailableToDelete(canProductBeRemoved(product.getId()));
            }
            return products;
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    public List<Product> getProducts(List<Integer> ids) throws DAOException {
        try {
            List<Product> products = ProductDataBase.select(ids);
            if (products == null)
                throw new DAOException("Illegal ID detected");
            return setCategoryNames(products, CategoryDataBase.select());
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    private List<Product> setCategoryNames(List<Product> products, List<Category> categories) throws DAOException {
        for (Product product : products) {
            product.setAvailableToDelete(canProductBeRemoved(product.getId()));
            product.setCategoryName(getCategoryName(product.getCategory(), categories));
        }
        return products;
    }

    private String getCategoryName(int id, List<Category> categories) {
        for (Category category : categories) {
            if (id == category.getId())
                return category.getName();
        }
        return null;
    }

    public Product getProduct(int productID) throws DAOException {
        try {
            return ProductDataBase.selectOne(productID);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    public boolean canProductBeRemoved(int productID) throws DAOException {
        try {
            return !OrderContentDataBase.doOrdersContain(productID);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    public boolean canCategoryBeRemoved(int categoryID) throws DAOException {
        try {
            List<Product> products = ProductDataBase.selectByCategory(categoryID);
            return products.isEmpty();
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    public List<Product> getProductsByCategory(int categoryID) throws DAOException {
        try {
            List<Category> categories = CategoryDataBase.select();
            return setCategoryNames(ProductDataBase.selectByCategoryOnSale(categoryID), categories);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    public List<Product> getProductsBySearch(String searchRequest) throws DAOException {
        try {
            searchLogger.info("Search by " + searchRequest);
            List<Category> categories = CategoryDataBase.select();
            return setCategoryNames(ProductDataBase.selectBySearch(searchRequest), categories);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    public List<Product> getProductsBySearch(String searchRequest, int categoryID) throws DAOException {
        try {
            searchLogger.info("Search by " + searchRequest + " in category " + categoryID);
            List<Category> categories = CategoryDataBase.select();
            return setCategoryNames(ProductDataBase.selectBySearch(searchRequest, categoryID), categories);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    public void createProduct(Product newProduct) throws DAOException {
        logger.info("Create product");
        try {
            if (newProduct.getName().equals(""))
                throw new DAOException("Empty name is not allowed");
            if (getCategory(newProduct.getCategory()) == null)
                throw new DAOException("Incorrect category");
            if (newProduct.getPrice() < Product.MIN_PRICE || newProduct.getPrice() > Product.MAX_PRICE)
                throw new DAOException("Incorrect price value");
            if (newProduct.getAmount() < 0)
                throw new DAOException("Incorrect 'count' value");
            ProductDataBase.insert(newProduct);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    public void setExistingCategoryID(Product product) throws DAOException {
        Category category = getCategory(product.getCategoryName());
        if (category == null) {
            logger.warn("Category does not exist", new DAOException("Category does not exist"));
            throw new DAOException("Category does not exist");
        }
        product.setCategory(category.getId());
    }

    public void editProduct(Product product) throws DAOException {
        try {
            if (product.getName().equals(""))
                throw new DAOException("Empty name is not allowed");
            logger.info("Edit product id=" + product.getId());
            ProductDataBase.update(product);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        } catch (IllegalArgumentException e) {
            throw new DAOException("Incorrect id detected");
        }
    }

    public void deleteProduct(int productID) throws DAOException {
        try {
            logger.info("Delete product id=" + productID);
            if (!canProductBeRemoved(productID)) {
                logger.warn("Product is contained in orders", new DAOException("Product is contained in orders"));
                throw new DAOException("Product is contained in orders");
            }
            ProductDataBase.delete(productID);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        } catch (IllegalArgumentException e) {
            throw new DAOException("Incorrect id detected");
        }
    }

    public void editProducts(List<Product> products, int newPrice) throws DAOException {
        if (newPrice < Product.MIN_PRICE || newPrice > Product.MAX_PRICE)
            throw new DAOException("Incorrect price value");
        try {
            for (Product product : products) {
                logger.info("Change price on " + newPrice + " Product id=" + product.getId());
                product.setPrice(newPrice);
            }
            ProductDataBase.update(products);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    public void editProducts(List<Product> products, String newCategory) throws DAOException {
        try {
            int categoryID = getCategoryID(newCategory);
            for (Product product : products) {
                logger.info("Change category on " + newCategory + " Product id=" + product.getId());
                product.setCategory(categoryID);
            }
            ProductDataBase.update(products);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }


    public void editProducts(List<Product> products, int newPrice, String newCategory) throws DAOException {
        if (newPrice < Product.MIN_PRICE || newPrice > Product.MAX_PRICE)
            throw new DAOException("Incorrect price value");
        try {
            int categoryID = getCategoryID(newCategory);
            for (Product product : products) {
                logger.info("Change price on" + newPrice + " and category on " + newCategory + " Product id=" + product.getId());
                product.setPrice(newPrice);
                product.setCategory(categoryID);
            }
            ProductDataBase.update(products);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    private int getCategoryID(String categoryName) throws DAOException {
        Category category = getCategory(categoryName);
        if (category == null) {
            throw new DAOException("Category with this name does not exists");
        } else return category.getId();
    }

    public Category getCategory(int categoryID) throws DAOException {
        try {
            return CategoryDataBase.selectOne(categoryID);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    public Category getCategory(String categoryName) throws DAOException {
        try {
            return CategoryDataBase.searchByName(categoryName);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    public void createCategory(Category newCategory) throws DAOException {
        try {
            logger.info("Create category");
            if (newCategory.getName().equals(""))
                throw new DAOException("Empty name is not allowed");
            if (CategoryDataBase.searchByName(newCategory.getName()) != null) {
                logger.warn("Category has this name already exists", new DAOException("Category has this name already exists"));
                throw new DAOException("Category with this name already exists");
            }
            CategoryDataBase.insert(newCategory);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    public void editCategory(Category category) throws DAOException {
        try {
            logger.info("Edit category id=" + category.getId());
            Category duplicate = getCategory(category.getName());
            if (category.getName().equals(""))
                throw new DAOException("Empty name is not allowed");
            if (duplicate != null && category.getId() != duplicate.getId()) {
                logger.warn("Category has this name already exists", new DAOException("Category has this name already exists"));
                throw new DAOException("Category has this name already exists");
            }
            CategoryDataBase.update(category);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        } catch (IllegalArgumentException e) {
            throw new DAOException("Incorrect id detected");
        }
    }

    public void deleteCategory(int categoryID) throws DAOException {
        try {
            logger.info("Delete category id=" + categoryID);
            if (!canCategoryBeRemoved(categoryID)) {
                logger.warn("Category has some products can not be deleted", new DAOException("Category has some products can not be deleted"));
                throw new DAOException("Category has some products can not be deleted");
            } else
                CategoryDataBase.delete(categoryID);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        } catch (IllegalArgumentException e) {
            throw new DAOException("Incorrect id detected");
        }
    }

    public void insertUpdateIgnoreDuplicates(ShopContent shopContent) throws DAOException {
        try {
            logger.info("Insert with ignore duplicates");
            List<Category> categories = shopContent.getCategories() == null ?
                    getCategories()
                    : insertAndPrepareCategories(shopContent.getCategories().getRawCategory());
            if (shopContent.getProducts() != null) {
                for (RawProduct rawProduct : shopContent.getProducts().getRawProduct()) {
                    int categoryID = getCategoryID(categories, rawProduct.getIdCategory());
                    if (categoryID < 0)
                        insertProductWithNewCategory(rawProduct, categories);
                    else {
                        Product product = transformProduct(rawProduct, categoryID);
                        if (ProductDataBase.getProduct(product) == null)
                            ProductDataBase.insert(product);
                    }
                }
            }
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    public void insertUpdateOverwriteDuplicates(ShopContent shopContent) throws DAOException {
        try {
            logger.info("Insert with overwrite duplicates");
            List<Category> categories = shopContent.getCategories() == null ?
                    getCategories()
                    : insertAndPrepareCategories(shopContent.getCategories().getRawCategory());
            if (shopContent.getProducts() != null) {
                for (RawProduct rawProduct : shopContent.getProducts().getRawProduct()) {
                    int categoryID = getCategoryID(categories, rawProduct.getIdCategory());
                    if (categoryID < 0)
                        insertProductWithNewCategory(rawProduct, categories);
                    else {
                        Product product = transformProduct(rawProduct, categoryID);
                        Product duplicate = ProductDataBase.getProduct(product);
                        if (duplicate == null)
                            ProductDataBase.insert(product);
                        else {
                            product.setId(duplicate.getId());
                            ProductDataBase.update(product);
                        }
                    }
                }
            }
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    public void insertUpdateWithDuplicates(ShopContent shopContent) throws DAOException {
        try {
            logger.info("Insert with duplicates");
            List<Category> categories = shopContent.getCategories() == null ?
                    getCategories()
                    : insertAndPrepareCategories(shopContent.getCategories().getRawCategory());
            if (shopContent.getProducts() != null) {
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
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    private List<Category> insertAndPrepareCategories(List<RawCategory> rawCategories) throws DAOException {
        try {
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
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    public void changeProductStatus(int id) throws DAOException {
        try {
            Product product = ProductDataBase.selectOne(id);
            if (product.getStatus() == 1) {
                logger.info("Archive product id=" + product.getId());
                ProductDataBase.archive(product);
            } else {
                logger.info("De archive product id= " + product.getId());
                ProductDataBase.deArchive(product);
            }
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    private Product transformProduct(RawProduct rawProduct, int categoryID) {
        return new Product(categoryID,
                rawProduct.getName(),
                rawProduct.getPrice(),
                rawProduct.getAmount(),
                rawProduct.getDescription());
    }

    private void insertProductWithNewCategory(RawProduct rawProduct, List<Category> categories) throws DAOException {
        try {
            Category category = new Category(rawProduct.getIdCategory());
            CategoryDataBase.insert(category);
            category = CategoryDataBase.searchByName(rawProduct.getIdCategory());
            categories.add(category);
            ProductDataBase.insert(transformProduct(rawProduct, category.getId()));
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    public ShopContent createShopContent(String xmlFilePath, Source xsdSchema) throws DAOException {
        try {
            JAXBContext context = JAXBContext.newInstance(ShopContent.class);

            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(xsdSchema);

            Unmarshaller unmarshaller = context.createUnmarshaller();

            unmarshaller.setSchema(schema);
            // unmarshaller.setEventHandler(new EmployeeValidationEventHandler());

            return (ShopContent) unmarshaller.unmarshal(new File(xmlFilePath));
        } catch (SAXException | JAXBException e) {
            throw new DAOException("Can not unmarshal file", e);
        }
    }

    public void toXmlFile(ShopContent shopContent, String xmlFilePath) throws DAOException {
        try {
            logger.info("Start export");
            JAXBContext context = JAXBContext.newInstance(ShopContent.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(xmlFilePath));
            marshaller.marshal(shopContent, fileOutputStream);
        } catch (JAXBException | FileNotFoundException e) {
            logger.error("Can not marshal in xml", e);
            throw new DAOException("Can not marshal in xml", e);
        }
    }

    public void toXmlFile(ShopContent shopContent, OutputStream out) throws DAOException {
        try {
            logger.info("Start export");
            JAXBContext context = JAXBContext.newInstance(ShopContent.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(shopContent, out);
        } catch (JAXBException e) {
            logger.error("Can not marshal in xml", e);
            throw new DAOException("Can not marshal in xml", e);
        }

    }

    public String xslTransform(InputStream src, Source xsl, String xmlOutPath) throws DAOException {
        try {
            logger.info("Start import");
            TransformerFactory tFactory = TransformerFactory.newInstance();
            OutputStream tempXmlOut = new BufferedOutputStream(new FileOutputStream(xmlOutPath));
            int readBytes;
            while ((readBytes = src.read()) != -1)
                tempXmlOut.write(readBytes);
            tempXmlOut.flush();
            tempXmlOut.close();
            Transformer trasform = tFactory.newTransformer(xsl);
            StringWriter resultWriter = new StringWriter();
            trasform.transform(new StreamSource(xmlOutPath), new StreamResult(resultWriter));
            String result = resultWriter.getBuffer().toString();
            resultWriter.close();
            return result;
        } catch (TransformerException | IOException e) {
            logger.error("Incorrect file", e);
            throw new DAOException("Can not transform xml to html", e);
        }

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
            prod.setIdCategory(getCategoryName(product.getCategory(), categories));
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

    public ShopContent getAllProducts() throws DAOException {
        try {
            logger.info("Export all products");
            return createShopContent(ProductDataBase.select(), CategoryDataBase.select());
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    public ShopContent getProductsOnSale() throws DAOException {
        try {
            logger.info("Export products on sale");
            return createShopContent(ProductDataBase.selectOnSale(), CategoryDataBase.select());
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    public ShopContent getArchivedProducts() throws DAOException {
        try {
            logger.info("Export archived products");
            return createShopContent(ProductDataBase.selectArchivedProducts(), CategoryDataBase.select());
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    private int getCategoryID(List<Category> categories, String categoryName) {
        for (Category category : categories) {
            if (categoryName.equalsIgnoreCase(category.getName()))
                return category.getId();
        }
        return -1;
    }
}
