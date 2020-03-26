package model.database;

import entities.main.OrderContent;
import entities.main.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class OrderContentDataBase {
    static List<OrderContent> select() {
        EntityManager manager = DBFactory.getEntityManager();
        List result = manager.createQuery("FROM OrderContent ").getResultList();
        manager.close();
        return (List<OrderContent>) result;
    }

    static List<OrderContent> selectByOrderID(int orderID) {
        EntityManager manager = DBFactory.getEntityManager();
        Query query = manager.createQuery("FROM OrderContent WHERE order = :order");
        query.setParameter("order", orderID);
        List result = query.getResultList();
        manager.close();
        return (List<OrderContent>) result;
    }

    static boolean doOrdersContain(int productID) {
        EntityManager manager = DBFactory.getEntityManager();
        Query query = manager.createQuery("FROM OrderContent WHERE product = :product");
        query.setParameter("product", productID);
        List result = query.getResultList();
        manager.close();
        return !result.isEmpty();
    }

    static OrderContent selectOne(int orderID, int productID) {
        EntityManager manager = DBFactory.getEntityManager();
        Query query = manager.createQuery("FROM OrderContent WHERE order = :order and product = :product");
        query.setParameter("order", orderID);
        query.setParameter("product", productID);
        List result = query.getResultList();
        manager.close();
        return result.isEmpty() ?
                null
                : (OrderContent) result.get(0);
    }

    static void insert(OrderContent orderContent) {
        EntityManager manager = DBFactory.getEntityManager();
        manager.getTransaction().begin();
        manager.persist(orderContent);
        manager.getTransaction().commit();
        manager.close();
        }

    static void update(OrderContent content) {
        EntityManager manager = DBFactory.getEntityManager();
        manager.getTransaction().begin();
        manager.merge(content);
        manager.getTransaction().commit();
        manager.close();
        }

    static void delete(int orderID, int productID) {
        EntityManager manager = DBFactory.getEntityManager();
        Query query = manager.createQuery("FROM OrderContent WHERE order = :order and product = :product");
        query.setParameter("order", orderID);
        query.setParameter("product", productID);
        List result = query.getResultList();
        if (!result.isEmpty()) {
            manager.remove(result.get(0));
            manager.getTransaction().begin();
            manager.getTransaction().commit();
        }
        manager.close();
        }
}
