package model.database;

import entities.jaxbready.RawCategory;
import entities.jaxbready.RawProduct;
import entities.jaxbready.ShopContent;
import entities.main.*;
import entities.supporting.CartItem;
import entities.supporting.OrderInfo;
import model.UserMarketModel;

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
            int orderID = order.getIdOrder();
            OrderContentDataBase.insert(new OrderContent(orderID, productID, 1));
        } else {
            OrderContent orderContent = null;
            orderContent = OrderContentDataBase.selectOne(order.getIdOrder(), productID);
            if (orderContent == null)
                OrderContentDataBase.insert(new OrderContent(order.getIdOrder(), productID, 1));
            else {
                orderContent.setNumber(orderContent.getNumber() + 1);
                OrderContentDataBase.update(orderContent);
            }
        }
    }

    @Override
    public int getProductNumber(String userID) {
        Order order = OrderDataBase.selectActive(userID);
        int productNumber = 0;
        if (order != null) {
            List<OrderContent> cart = OrderContentDataBase.selectByOrderID(order.getIdOrder());
            for (OrderContent ord : cart) {
                productNumber += ord.getNumber();
            }
        }
        return productNumber;
    }

    @Override
    public List<CartItem> getCart(String userID) {
        Order order = OrderDataBase.selectActive(userID);
        List<CartItem> cart = new ArrayList<>();
        if (order != null) {
            List<OrderContent> cartItems = OrderContentDataBase.selectByOrderID(order.getIdOrder());
            for (OrderContent ord : cartItems) {
                Product product = ProductDataBase.selectOne(ord.getIdProduct());
                cart.add(new CartItem(product, ord.getNumber(), 1));
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
    public void removeItemFromOrder(String userID, int productID) throws IllegalAccessException {
        Order order = OrderDataBase.selectActive(userID);
        if (order == null || order.getStatus().equals("1"))
            throw new IllegalAccessException();
        OrderContentDataBase.delete(order.getIdOrder(), productID);
    }

    @Override
    public void incrementItemNumber(String userID, int productID) throws IllegalAccessException {
        Order order = OrderDataBase.selectActive(userID);
        if (order == null || order.getStatus().equals("1"))
            throw new IllegalAccessException();
        OrderContent orderContent = OrderContentDataBase.selectOne(order.getIdOrder(), productID);
        if (orderContent == null)
            throw new IllegalAccessException();
        orderContent.setNumber(orderContent.getNumber() + 1);
        OrderContentDataBase.update(orderContent);
    }

    @Override
    public void decrementItemNumber(String userID, int productID) throws IllegalAccessException {
        Order order = OrderDataBase.selectActive(userID);
        if (order == null || order.getStatus().equals("1"))
            throw new IllegalAccessException();
        OrderContent orderContent = OrderContentDataBase.selectOne(order.getIdOrder(), productID);
        if (orderContent == null)
            throw new IllegalAccessException();
        if (orderContent.getNumber() == 1) {
            OrderContentDataBase.delete(order.getIdOrder(), productID);
        } else {
            orderContent.setNumber(orderContent.getNumber() - 1);
            OrderContentDataBase.update(orderContent);
        }
    }

    @Override
    public boolean confirmOrder(String userID) {
        if (!isOrderValid(validateOrder(getCart(userID))))
            return false;
        Order order = OrderDataBase.selectActive(userID);
        if (order != null) {
            List<OrderContent> orderContents = OrderContentDataBase.selectByOrderID(order.getIdOrder());
            if (orderContents.isEmpty()) {
                return false;
            }
            for (OrderContent content : orderContents) {
                Product product = getProduct(content.getIdProduct());
                if (product.getAmount() < content.getNumber() || product.getStatus() == 0) {
                    return false;
                }
            }
            for (OrderContent content : orderContents) {
                Product product = getProduct(content.getIdProduct());
                product.setAmount(product.getAmount() - content.getNumber());
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
                List<OrderContent> orderContents = OrderContentDataBase.selectByOrderID(order.getIdOrder());
                for (OrderContent ord : orderContents) {
                    Product product = ProductDataBase.selectOne(ord.getIdProduct());
                    content.add(product.getName() + " x" + ord.getNumber() + ";");
                    totalCost += product.getPrice() * ord.getNumber();
                }
                orderInfos.add(new OrderInfo(order.getIdOrder(), content, totalCost));
            }
        }
        return orderInfos;
    }
}
