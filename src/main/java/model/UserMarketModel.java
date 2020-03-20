package model;

import entities.main.Category;
import entities.main.Order;
import entities.main.Product;
import entities.supporting.CartItem;
import entities.supporting.OrderInfo;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserMarketModel {
    List<Category> getCategories();

    List<Product> getProducts();

    Product getProduct(int productID);

    List<Product> getProductsByCategory(int categoryID);

    List<Product> getProductsBySearch(String searchRequest);

    List<Product> getProductsBySearch(String searchRequest, int categoryID);

    void buyProduct(int productID, String userID);

    int getProductNumber(String userID);

    List<CartItem> getCart(String userID);

    List<CartItem> validateOrder(List<CartItem> cart);

    boolean isOrderValid(List<CartItem> cart);

    int getOrderCost(List<CartItem> cart);

    void removeItemFromOrder(String userID, int productID) throws IllegalAccessException;

    void incrementItemNumber(String userID, int productID) throws IllegalAccessException;

    void decrementItemNumber(String userID, int productID) throws IllegalAccessException;

    boolean confirmOrder(String userID);

    List<OrderInfo> getOrdersHistory(String userID);


}
