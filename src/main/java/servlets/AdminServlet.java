package servlets;

import entities.main.*;
import model.AdminMarketModel;
import model.database.ProductAndCategoryManager;

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

    @EJB
    private ProductAndCategoryManager model;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tab = request.getParameter("tab");
        if (tab == null)
            tab = "products";
        switch (tab) {
            case "products":
                List<Product> products = null;
                try {
                    products = model.getProducts();
                } catch (DAOException e) {
                    request.setAttribute("message", e.getMessage());
                    getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
                }
                request.setAttribute("products", products);
                getServletContext().getRequestDispatcher("/admin-products.jsp").forward(request, response);
                break;
            case "categories":
                List<Category> categories = null;
                try {
                    categories = model.getCategories();
                } catch (DAOException e) {
                    request.setAttribute("message", e.getMessage());
                    getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
                }
                request.setAttribute("categories", categories);
                getServletContext().getRequestDispatcher("/admin-categories.jsp").forward(request, response);
                break;
            case "import":
                getServletContext().getRequestDispatcher("/import.jsp").forward(request, response);
                break;
            case "export":
                getServletContext().getRequestDispatcher("/export.jsp").forward(request, response);
                break;
            case "change":
                getServletContext().getRequestDispatcher("/choose-action.jsp").forward(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin");
                break;
        }
    }
}