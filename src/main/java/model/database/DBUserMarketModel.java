package model.database;

import entities.main.*;
import entities.supporting.CartItem;
import entities.supporting.OrderInfo;
import model.UserMarketModel;
import servlets.DAOException;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class DBUserMarketModel implements UserMarketModel {
    @Override
    public List<Category> getCategories() {
        return CategoryDataBase.select();
    }

    @Override
    public List<Product> getProducts() {
        return ProductDataBase.selectOnSale();
    }

    @Override
    public Product getProduct(int productID) {
        return ProductDataBase.selectOne(productID);
    }

    @Override
    public List<Product> getProductsByCategory(int categoryID) {
        return ProductDataBase.selectByCategoryOnSale(categoryID);
    }

    @Override
    public List<Product> getProductsBySearch(String searchRequest) {
        return ProductDataBase.selectBySearch(searchRequest);
    }

    @Override
    public List<Product> getProductsBySearch(String searchRequest, int categoryID) {
        return ProductDataBase.selectBySearch(searchRequest, categoryID);
    }

    @Override
    public void buyProduct(int productID, String userID) {
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
    }

    @Override
    public int getProductNumber(String userID) {
        Order order = OrderDataBase.selectActive(userID);
        int productNumber = 0;
        if (order != null) {
            List<OrderContent> cart = OrderContentDataBase.selectByOrderID(order.getId());
            for (OrderContent ord : cart) {
                productNumber += ord.getAmount();
            }
        }
        return productNumber;
    }

    @Override
    public List<CartItem> getCart(String userID) {
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
        Order order = OrderDataBase.selectActive(userID);
        if (order == null || order.getStatus().equals("1"))
            throw new DAOException("order can not be modified");
        OrderContentDataBase.delete(order.getId(), productID);
    }

    @Override
    public void incrementItemNumber(String userID, int productID) throws DAOException {
        Order order = OrderDataBase.selectActive(userID);
        if (order == null || order.getStatus().equals("1"))
            throw new DAOException("order can not be modified");
        OrderContent orderContent = OrderContentDataBase.selectOne(order.getId(), productID);
        if (orderContent == null)
            throw new DAOException("order can not be modified");
        orderContent.setAmount(orderContent.getAmount() + 1);
        OrderContentDataBase.update(orderContent);
    }

    @Override
    public void decrementItemNumber(String userID, int productID) throws DAOException {
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
    }

    @Override
    public boolean confirmOrder(String userID) {
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
                ProductDataBase.update(product);
            }
            order.setStatus("1");
            OrderDataBase.update(order);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<OrderInfo> getOrdersHistory(String userID) {
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
    }
}
