package model.database;

import entities.main.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class ProductDataBase {

    static List<Product> select() {
        EntityManager manager = DBFactory.getEntityManager();
        List result = manager.createQuery("FROM Product").getResultList();
        manager.close();
        return (List<Product>) result;
    }

    static List<Product> selectOnSale() {
        return getByStatus(1);
    }

    static List<Product> selectArchivedProducts() {
        return getByStatus(0);
    }

    private static List<Product> getByStatus(int status) {
        EntityManager manager = DBFactory.getEntityManager();
        Query query = manager.createQuery("FROM Product WHERE status = :status");
        query.setParameter("status", status);
        List result = query.getResultList();
        manager.close();
        return (List<Product>) result;
    }

    static List<Product> selectByCategory(int categoryID) {
        EntityManager manager = DBFactory.getEntityManager();
        Query query = manager.createQuery("FROM Product WHERE category = :category");
        query.setParameter("category", categoryID);
        List result = query.getResultList();
        manager.close();
        return (List<Product>) result;
    }

    static List<Product> selectByCategoryOnSale(int categoryID) {
        EntityManager manager = DBFactory.getEntityManager();
        Query query = manager.createQuery("FROM Product WHERE category = :category and status = :status");
        query.setParameter("category", categoryID);
        query.setParameter("status", 1);
        List result = query.getResultList();
        manager.close();
        return (List<Product>) result;
    }

    static List<Product> selectBySearch(String search, int categoryID) {
        EntityManager manager = DBFactory.getEntityManager();
        Query query = manager.createQuery("FROM Product " +
                "WHERE category = :category and (name like :search or description like :search)");
        query.setParameter("category", categoryID);
        query.setParameter("search", '%' + search + '%');
        List result = query.getResultList();
        manager.close();
        return (List<Product>) result;
    }

    static List<Product> selectBySearch(String search) {
        EntityManager manager = DBFactory.getEntityManager();
        Query query = manager.createQuery("FROM Product " +
                "WHERE name like :search or description like :search");
        query.setParameter("search", '%' + search + '%');
        List result = query.getResultList();
        manager.close();
        return (List<Product>) result;
    }

    static Product selectOne(int id) {
        EntityManager manager = DBFactory.getEntityManager();
        Product result = manager.find(Product.class, id);
        manager.close();
        return result;
    }

    static boolean isContain(Product product) {
        EntityManager manager = DBFactory.getEntityManager();
        Query query = manager.createQuery("FROM Product " +
                "WHERE name like :name and description like :desc and category = :cat");
        query.setParameter("name", product.getName());
        query.setParameter("desc", product.getDescription());
        query.setParameter("cat", product.getCategory());
        boolean result = !query.getResultList().isEmpty();
        manager.close();
        return result;
    }

    static void insert(Product product) {
        EntityManager manager = DBFactory.getEntityManager();
        manager.getTransaction().begin();
        manager.persist(product);
        manager.getTransaction().commit();
        manager.close();
        }

    static void update(Product product) {
        EntityManager manager = DBFactory.getEntityManager();
        manager.getTransaction().begin();
        manager.merge(product);
        manager.getTransaction().commit();
        manager.close();
        }

    static void delete(int id) {
        EntityManager manager = DBFactory.getEntityManager();
        manager.getTransaction().begin();
        manager.remove(manager.find(Product.class, id));
        manager.getTransaction().commit();
        manager.close();
        }

    static void archive(Product product) {
        product.setStatus(0);
        EntityManager manager = DBFactory.getEntityManager();
        manager.getTransaction().begin();
        manager.merge(product);
        manager.getTransaction().commit();
        manager.close();
        }

    static void deArchive(Product product) {
        product.setStatus(1);
        EntityManager manager = DBFactory.getEntityManager();
        manager.getTransaction().begin();
        manager.merge(product);
        manager.getTransaction().commit();
        manager.close();
        }
}
