package servlets;

import entities.main.Category;
import entities.main.Product;
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

@WebServlet("/create")
public class CreateServlet extends HttpServlet {

    @EJB(beanName = "DBAdminMarketModel")
    private AdminMarketModel model;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Category> categories = model.getCategories();
        request.setAttribute("categories", categories);
        getServletContext().getRequestDispatcher("/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String name = request.getParameter("name");
            int price = Integer.parseInt(request.getParameter("price"));
            int amount = Integer.parseInt(request.getParameter("amount"));
            String description = request.getParameter("description");
            String categoryName = request.getParameter("category");
            //int idCategory = Integer.parseInt(c);
            Product product = new Product(-1, name, price, amount, description);
            product.setCategoryName(categoryName);
            model.setExistingCategoryID(product);
            model.createProduct(product);
            response.sendRedirect(request.getContextPath()+"/admin");
        }
        catch(Exception ex) {

            getServletContext().getRequestDispatcher("/create.jsp").forward(request, response);
        }
    }
}



