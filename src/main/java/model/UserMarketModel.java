package model;

import entities.main.Category;
import entities.main.Order;
import entities.main.Product;
import entities.supporting.CartItem;
import entities.supporting.OrderInfo;
import servlets.DAOException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserMarketModel {
    List<Category> getCategories() throws DAOException;

    List<Product> getProducts() throws DAOException;

    Product getProduct(int productID) throws DAOException;

    List<Product> getProductsByCategory(int categoryID) throws DAOException;

    List<Product> getProductsBySearch(String searchRequest) throws DAOException;

    List<Product> getProductsBySearch(String searchRequest, int categoryID) throws DAOException;

    void buyProduct(int productID, String userID) throws DAOException;

    int getProductNumber(String userID) throws DAOException;

    List<CartItem> getCart(String userID) throws DAOException;

    List<CartItem> validateOrder(List<CartItem> cart) throws DAOException;

    boolean isOrderValid(List<CartItem> cart) throws DAOException;

    int getOrderCost(List<CartItem> cart) throws DAOException;

    void removeItemFromOrder(String userID, int productID) throws DAOException;

    void incrementItemNumber(String userID, int productID) throws DAOException;

    void decrementItemNumber(String userID, int productID) throws DAOException;

    boolean confirmOrder(String userID) throws DAOException;

    List<OrderInfo> getOrdersHistory(String userID) throws DAOException;


}
