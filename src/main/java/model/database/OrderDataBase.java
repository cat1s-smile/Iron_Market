package model.database;

import entities.main.Order;
import entities.main.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class OrderDataBase {
    private static String username = "root";
    private static String password = "кщще";
    private static String url = "jdbc:mysql://localhost:3306/marketdb?useUnicode=true&serverTimezone=UTC";

    static List<Order> select() {
        EntityManager manager = DBFactory.getEntityManager();
        List result = manager.createQuery("FROM Order").getResultList();
        manager.close();
        return (List<Order>) result;
    }

    static List<Order> selectByID(String userID) {
        EntityManager manager = DBFactory.getEntityManager();
        Query query = manager.createQuery("FROM Order WHERE user = :user");
        query.setParameter("user", userID);
        List result = query.getResultList();
        manager.close();
        return (List<Order>) result;
    }

    static void update(Order order) {
        EntityManager manager = DBFactory.getEntityManager();
        manager.getTransaction().begin();
        manager.merge(order);
        manager.getTransaction().commit();
        manager.close();
        }


    static Order selectActive(String userID) {
        EntityManager manager = DBFactory.getEntityManager();
        Query query = manager.createQuery("FROM Order WHERE user like :user and status like :status");
        query.setParameter("user", userID);
        query.setParameter("status", "0");
        List result = query.getResultList();
        manager.close();
        return result == null?
                null
                : (Order) result.get(0);
    }

    static void insert(Order order) {
        EntityManager manager = DBFactory.getEntityManager();
        manager.getTransaction().begin();
        manager.persist(order);
        manager.getTransaction().commit();
        manager.close();
        }
}
