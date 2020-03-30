package servlets;

import entities.main.*;
import model.AdminMarketModel;
import model.UserMarketModel;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    @EJB(beanName = "DBAdminMarketModel")
    private AdminMarketModel model;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tab = request.getParameter("tab");
        if(tab == null || tab.equals("products")) {
            List<Product> products = model.getProducts();
            request.setAttribute("products", products);
            getServletContext().getRequestDispatcher("/admin-products.jsp").forward(request, response);
        }
        else if (tab.equals("categories")) {
            List<Category> categories = model.getCategories();
            request.setAttribute("categories", categories);
            getServletContext().getRequestDispatcher("/admin-categories.jsp").forward(request, response);
        }
        else if (tab.equals("import")) {
            getServletContext().getRequestDispatcher("/import.jsp").forward(request, response);
        }
        else if (tab.equals("export")) {
            getServletContext().getRequestDispatcher("/export.jsp").forward(request, response);
        }
        else if (tab.equals("change")) {
            getServletContext().getRequestDispatcher("/chooseAction.jsp").forward(request, response);
        }
        else {
            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
    }
}