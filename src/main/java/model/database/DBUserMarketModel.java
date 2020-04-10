package model.database;

import entities.main.*;
import entities.supporting.CartItem;
import entities.supporting.OrderInfo;
import model.UserMarketModel;
import org.hibernate.exception.JDBCConnectionException;
import servlets.DAOException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

@Stateless
public class DBUserMarketModel implements UserMarketModel {

    private static final Logger searchLogger = Logger.getLogger("searchFile");

    @Override
    public List<Category> getCategories() throws DAOException {
        try {
            return CategoryDataBase.select();
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    @Override
    public List<Product> getProducts() throws DAOException {
        try {
            List<Category> categories = CategoryDataBase.select();
            return setCategoryNames(ProductDataBase.selectOnSale(), categories);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    private List<Product> setCategoryNames(List<Product> products, List<Category> categories) {
        for (Product product : products) {
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

    @Override
    public Product getProduct(int productID) throws DAOException {
        try {
            return ProductDataBase.selectOne(productID);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    @Override
    public List<Product> getProductsByCategory(int categoryID) throws DAOException {
        try {
            List<Category> categories = CategoryDataBase.select();
            return setCategoryNames(ProductDataBase.selectByCategoryOnSale(categoryID), categories);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    @Override
    public List<Product> getProductsBySearch(String searchRequest) throws DAOException {
        try {
            searchLogger.info("Search by " + searchRequest);
            List<Category> categories = CategoryDataBase.select();
            return setCategoryNames(ProductDataBase.selectBySearch(searchRequest), categories);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    @Override
    public List<Product> getProductsBySearch(String searchRequest, int categoryID) throws DAOException {
        try {
            searchLogger.info("Search by " + searchRequest + " in category " + categoryID);
            List<Category> categories = CategoryDataBase.select();
            return setCategoryNames(ProductDataBase.selectBySearch(searchRequest, categoryID), categories);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    @Override
    public void buyProduct(int productID, String userID) throws DAOException {
        try {
            Order order = null;
            order = OrderDataBase.selectActive(userID);
            if (order == null) {
                OrderDataBase.insert(new Order(userID, "0"));
                order = OrderDataBase.selectActive(userID);
                int orderID = order.getId();
                OrderContentDataBase.insert(new OrderContent(orderID, productID, 1));
            } else {
                OrderContent orderContent = null;
                orderContent = OrderContentDataBase.selectOne(order.getId(), productID);
                if (orderContent == null)
                    OrderContentDataBase.insert(new OrderContent(order.getId(), productID, 1));
                else {
                    orderContent.setAmount(orderContent.getAmount() + 1);
                    OrderContentDataBase.update(orderContent);
                }
            }
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    @Override
    public int getProductNumber(String userID) throws DAOException {
        try {
            Order order = OrderDataBase.selectActive(userID);
            int productNumber = 0;
            if (order != null) {
                List<OrderContent> cart = OrderContentDataBase.selectByOrderID(order.getId());
                for (OrderContent ord : cart) {
                    productNumber += ord.getAmount();
                }
            }
            return productNumber;
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    @Override
    public List<CartItem> getCart(String userID) throws DAOException {
        try {
            Order order = OrderDataBase.selectActive(userID);
            List<CartItem> cart = new ArrayList<>();
            if (order != null) {
                List<OrderContent> cartItems = OrderContentDataBase.selectByOrderID(order.getId());
                for (OrderContent ord : cartItems) {
                    Product product = ProductDataBase.selectOne(ord.getProduct());
                    cart.add(new CartItem(product, ord.getAmount(), 1));
                }
            }
            return cart;
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    @Override
    public List<CartItem> validateOrder(List<CartItem> cart) {
        for (CartItem cartItem : cart) {
            if (cartItem.getProduct().getAmount() < cartItem.getNumber()
                    || cartItem.getProduct().getStatus() == 0) {
                cartItem.setStatus(0);
            }
        }
        return cart;
    }

    @Override
    public boolean isOrderValid(List<CartItem> cart) {
        for (CartItem cartItem : cart) {
            if (cartItem.getStatus() == 0) {
                return false;
            }
        }
        return !cart.isEmpty();
    }

    @Override
    public int getOrderCost(List<CartItem> cart) {
        int cost = 0;
        for (CartItem cartItem : cart) {
            cost += cartItem.getProduct().getPrice() * cartItem.getNumber();
        }
        return cost;
    }

    @Override
    public void removeItemFromOrder(String userID, int productID) throws DAOException {
        try {
            Order order = OrderDataBase.selectActive(userID);
            if (order == null || order.getStatus().equals("1"))
                throw new DAOException("order can not be modified");
            OrderContentDataBase.delete(order.getId(), productID);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    @Override
    public void incrementItemNumber(String userID, int productID) throws DAOException {
        try {
            Order order = OrderDataBase.selectActive(userID);
            if (order == null || order.getStatus().equals("1"))
                throw new DAOException("order can not be modified");
            OrderContent orderContent = OrderContentDataBase.selectOne(order.getId(), productID);
            if (orderContent == null)
                throw new DAOException("order can not be modified");
            orderContent.setAmount(orderContent.getAmount() + 1);
            OrderContentDataBase.update(orderContent);
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    @Override
    public void decrementItemNumber(String userID, int productID) throws DAOException {
        try {
            Order order = OrderDataBase.selectActive(userID);
            if (order == null || order.getStatus().equals("1"))
                throw new DAOException("order can not be modified");
            OrderContent orderContent = OrderContentDataBase.selectOne(order.getId(), productID);
            if (orderContent == null)
                throw new DAOException("order can not be modified");
            if (orderContent.getAmount() == 1) {
                OrderContentDataBase.delete(order.getId(), productID);
            } else {
                orderContent.setAmount(orderContent.getAmount() - 1);
                OrderContentDataBase.update(orderContent);
            }
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    @Override
    public boolean confirmOrder(String userID) throws DAOException {
        try {
            EntityManager manager = DBFactory.getEntityManager();
            manager.getTransaction().begin();
            if (!isOrderValid(validateOrder(getCart(userID))))
                return false;
            Order order = OrderDataBase.selectActive(userID);
            if (order != null) {
                List<OrderContent> orderContents = OrderContentDataBase.selectByOrderID(order.getId());
                if (orderContents.isEmpty()) {
                    return false;
                }
                for (OrderContent content : orderContents) {
                    Product product = getProduct(content.getProduct());
                    if (product.getAmount() < content.getAmount() || product.getStatus() == 0) {
                        return false;
                    }
                }
                for (OrderContent content : orderContents) {
                    Product product = getProduct(content.getProduct());
                    product.setAmount(product.getAmount() - content.getAmount());
                    manager.merge(product);
                }
                order.setStatus("1");
                manager.merge(order);
                manager.getTransaction().commit();
                manager.close();
                return true;
            } else {
                return false;
            }
        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }

    @Override
    public List<OrderInfo> getOrdersHistory(String userID) throws DAOException {
        try {
            List<Order> orders = OrderDataBase.selectByID(userID);
            List<OrderInfo> orderInfos = new ArrayList<>();
            if (!orders.isEmpty()) {
                for (Order order : orders) {
                    List<String> content = new ArrayList<>();
                    int totalCost = 0;
                    List<OrderContent> orderContents = OrderContentDataBase.selectByOrderID(order.getId());
                    for (OrderContent ord : orderContents) {
                        Product product = ProductDataBase.selectOne(ord.getProduct());
                        content.add(product.getName() + " x" + ord.getAmount() + ";");
                        totalCost += product.getPrice() * ord.getAmount();
                    }
                    orderInfos.add(new OrderInfo(order.getId(), content, totalCost));
                }
            }
            return orderInfos;

        } catch (JDBCConnectionException e) {
            throw new DAOException("Can not connect to database", e);
        }
    }
}
