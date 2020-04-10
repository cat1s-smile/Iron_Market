package servlets;

import entities.main.Category;
import entities.main.Product;
import model.AdminMarketModel;
import model.UserMarketModel;
import parse.Parser;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/create")
public class CreateServlet extends HttpServlet {

    @EJB(beanName = "DBAdminMarketModel")
    private AdminMarketModel model;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Category> categories = model.getCategories();
            request.setAttribute("categories", categories);
            getServletContext().getRequestDispatcher("/create.jsp").forward(request, response);
        } catch (DAOException e) {
            request.setAttribute("message", e.getMessage());
            getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String name = request.getParameter("name");
            int price = Parser.parseID(request.getParameter("price"));
            int amount = Parser.parseID(request.getParameter("amount"));
            String description = request.getParameter("description");
            String categoryName = request.getParameter("category");
            //int idCategory = Integer.parseInt(c);
            Product product = new Product(-1, name, price, amount, description);
            product.setCategoryName(categoryName);
            model.setExistingCategoryID(product);
            model.createProduct(product);
            response.sendRedirect(request.getContextPath()+"/admin");
        }
        catch(DAOException ex) {
            request.setAttribute("message", ex.getMessage());
            getServletContext().getRequestDispatcher("/create-category.jsp").forward(request, response);
        }
    }
}



