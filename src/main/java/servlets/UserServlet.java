package servlets;

import entities.main.*;
import model.UserMarketModel;
import model.database.*;
import parse.Parser;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    @EJB(beanName = "DBUserMarketModel")
    private UserMarketModel model;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Category> categories = model.getCategories();
            List<Product> products = null;
            int catID = Parser.parseID(request.getParameter("catID"));
            String searchRequest = request.getParameter("search");
            if (searchRequest == null || searchRequest.equals("")) {
                if (catID == Parser.NAN || catID < 0)
                    products = model.getProducts();
                else
                    products = model.getProductsByCategory(catID);
            } else {
                if (catID == Parser.NAN || catID < 0)
                    products = model.getProductsBySearch(searchRequest);
                else
                    products = model.getProductsBySearch(searchRequest, catID);
                request.setAttribute("search", searchRequest);
            }
            request.setAttribute("catID", catID);
            request.setAttribute("products", products);
            request.setAttribute("categories", categories);
            request.setAttribute("cart", model.getProductNumber(User.getDefaultID()));

            if (request.getParameter("id") != null) {
                int id = Integer.parseInt(request.getParameter("id"));
                Product product = model.getProduct(id);
                if (product != null) {
                    String categoryName = null;
                    for (Category category : categories) {
                        if (product.getCategory() == category.getId())
                            categoryName = category.getName();
                    }
                    product.setCategoryName(categoryName);
                    request.setAttribute("product", product);
                    request.setAttribute("overlay", 1);
                } else {
                    getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
                }
            }
            getServletContext().getRequestDispatcher("/user.jsp").forward(request, response);
        } catch (DAOException e) {
            request.setAttribute("message", e.getMessage());
            getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}