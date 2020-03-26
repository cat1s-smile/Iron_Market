package model.database;

import entities.main.Category;
import entities.main.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class CategoryDataBase {

    static List<Category> select() {
        EntityManager manager = DBFactory.getEntityManager();
        List result = manager.createQuery("FROM Category").getResultList();
        manager.close();
        return (List<Category>) result;
    }

    static Category searchByName(String categoryName) {
        EntityManager manager = DBFactory.getEntityManager();
        Query query = manager.createQuery("FROM Category WHERE name = :name");
        query.setParameter("name", categoryName);
        List result = query.getResultList();
        manager.close();
        return result.isEmpty() ?
                null
                : (Category) result.get(0);
    }

    static Category selectOne(int id) {
        EntityManager manager = DBFactory.getEntityManager();
        Category result = manager.find(Category.class, id);
        manager.close();
        return result;
    }

    static void insert(Category category) {
        EntityManager manager = DBFactory.getEntityManager();
        manager.getTransaction().begin();
        manager.persist(category);
        manager.getTransaction().commit();
        manager.close();
        }

    static void update(Category category) {
        EntityManager manager = DBFactory.getEntityManager();
        manager.getTransaction().begin();
        manager.merge(category);
        manager.getTransaction().commit();
        manager.close();
        }

    static void delete(int id) {
        EntityManager manager = DBFactory.getEntityManager();
        manager.getTransaction().begin();
        manager.remove(manager.find(Category.class, id));
        manager.getTransaction().commit();
        manager.close();
        }
}
