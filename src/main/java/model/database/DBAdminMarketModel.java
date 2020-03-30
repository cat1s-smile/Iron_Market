package model.database;

import entities.jaxbready.*;
import entities.main.Category;
import entities.main.Product;
import model.AdminMarketModel;
import org.xml.sax.SAXException;
import servlets.DAOException;

import javax.ejb.Stateless;
import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class DBAdminMarketModel implements AdminMarketModel {
    @Override
    public List<Category> getCategories() {
        List<Category> categories = CategoryDataBase.select();
        for (Category category : categories)
            category.setAvailableToDelete(canCategoryBeRemoved(category.getId()));
        return categories;
    }

    @Override
    public List<Product> getProducts() {
        List<Category> categories = CategoryDataBase.select();
        List<Product> products = setCategoryNames(ProductDataBase.select(), categories);
        for(Product product : products) {
            product.setAvailableToDelete(canProductBeRemoved(product.getId()));
        }
        return products;
    }

    @Override
    public List<Product> getProducts(List<Integer> ids) {
        return setCategoryNames(ProductDataBase.select(ids), CategoryDataBase.select());
    }

    private List<Product> setCategoryNames(List<Product> products, List<Category> categories) {
        for(Product product : products) {
            product.setAvailableToDelete(canProductBeRemoved(product.getId()));
            product.setCategoryName(getCategoryName(product.getCategory(), categories));
        }
        return products;
    }

    private String getCategoryName(int id, List<Category> categories) {
        for(Category category : categories) {
            if (id == category.getId())
                return category.getName();
        }
        return null;
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
    public void deleteProduct(int productID) throws DAOException {
        if(!canProductBeRemoved(productID))
            throw new DAOException("Product is contained in orders");
        ProductDataBase.delete(productID);
    }

    @Override
    public void editProducts(List<Product> products, int newPrice) {
        for (Product product : products) {
            product.setPrice(newPrice);
        }
        ProductDataBase.update(products);
    }

    @Override
    public void editProducts(List<Product> products, String newCategory) throws DAOException {
        int categoryID = getCategoryID(newCategory);
        for (Product product : products) {
            product.setCategory(categoryID);
        }
        ProductDataBase.update(products);
    }

    @Override
    public void editProducts(List<Product> products, int newPrice, String newCategory) throws DAOException {
        int categoryID = getCategoryID(newCategory);
        for (Product product : products) {
            product.setPrice(newPrice);
            product.setCategory(categoryID);
        }
        ProductDataBase.update(products);
    }

    private int getCategoryID(String categoryName) throws DAOException {
        Category category = getCategory(categoryName);
        if(category == null) {
            createCategory(new Category(categoryName));
            return getCategory(categoryName).getId();
        }
        else return category.getId();
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
    public void createCategory(Category newCategory) throws DAOException {
        if (CategoryDataBase.searchByName(newCategory.getName()) != null)
            throw new DAOException("Category has this name already exists");
        CategoryDataBase.insert(newCategory);
    }

    @Override
    public void editCategory(Category category) throws DAOException {
        Category duplicate = getCategory(category.getName());
        if (category.getName().equalsIgnoreCase(duplicate.getName()) &&
                category.getId() != duplicate.getId())
            throw new DAOException("Category has this name already exists");
        CategoryDataBase.update(category);
    }

    @Override
    public void deleteCategory(int categoryID) throws DAOException {
        if (!canCategoryBeRemoved(categoryID))
            throw new DAOException("Category has some products can not be deleted");
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

    @Override
    public void toXmlFile(ShopContent shopContent, String xmlFilePath) throws DAOException {
        try {
            JAXBContext context = JAXBContext.newInstance(ShopContent.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(xmlFilePath));
            marshaller.marshal(shopContent, fileOutputStream);
        } catch (JAXBException | FileNotFoundException e) {
            throw new DAOException("Can not marshal in xml", e);
        }
    }

    @Override
    public void toXmlFile(ShopContent shopContent, OutputStream out) throws DAOException {
        try {
            JAXBContext context = JAXBContext.newInstance(ShopContent.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(shopContent, out);
        } catch (JAXBException e) {
            throw new DAOException("Can not marshal in xml", e);
        }

    }

    @Override
    public String xslTransform(InputStream src, Source xsl, String xmlOutPath) throws DAOException {
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            OutputStream tempXmlOut = new BufferedOutputStream(new FileOutputStream(xmlOutPath));
            int readBytes = 0;
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
}
