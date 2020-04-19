package test;

import entities.main.Category;
import entities.main.Product;
import model.database.DBFactory;
import model.database.ProductAndCategoryManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import servlets.DAOException;

import static org.mockito.Mockito.*;

import java.util.List;

public class TestValidate {

    private ProductAndCategoryManager manager = new ProductAndCategoryManager();

    @BeforeAll
    static void init() {
        DBFactory.init();
    }

    @Test
    public void testValidateCreateOrEditProductWithCorrectParameters() throws DAOException {
        Product correctProduct = new Product(2, "Product", 2000, 1, "NEW");
        Assertions.assertDoesNotThrow(() -> manager.validateProduct(correctProduct));
    }

    @Test
    public void testValidateCreateOrEditProductWithWrongParameters() throws DAOException {
        Product incorrectProduct = new Product(2, "Product", 1, 1, "NEW");
        Assertions.assertThrows(DAOException.class, () -> manager.validateProduct(incorrectProduct));
    }

    @Test
    public void testValidateCreateCategoryWithWrongParameters() throws DAOException {
        Category incorrectCategory = new Category("");
        Assertions.assertThrows(DAOException.class, () -> manager.validateCreateCategory(incorrectCategory));
    }

    @Test
    public void testValidateCreateCategoryWithCorrectParameters() throws DAOException {
        Category correctCategory = new Category("Category");
        Assertions.assertDoesNotThrow(() -> manager.validateCreateCategory(correctCategory));
    }

    @Test
    public void testValidateEditCategoryWithWrongParameters() throws DAOException {
        Category category = new Category(1,"Category");
        Category duplicate = new Category(category.getName());
        Assertions.assertThrows(DAOException.class, () -> manager.validateEditCategory(category, duplicate));
    }

    @Test
    public void testValidateEditCategoryWithCorrectParameters() throws DAOException {
        Category category = new Category("Category");
        Category duplicate = new Category("AnotherName");
        Assertions.assertDoesNotThrow(() -> manager.validateEditCategory(category, duplicate));
    }
}
