package servlets;

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

@WebServlet("/edit")
public class EditServlet extends HttpServlet {

    @EJB(beanName = "DBAdminMarketModel")
    private AdminMarketModel model;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Parser.parseID(request.getParameter("id"));
            Product product = model.getProduct(id);
            if(product!=null) {
                product.setCategoryName(model.getCategory(product.getCategory()).getName());
                request.setAttribute("product", product);
                getServletContext().getRequestDispatcher("/edit.jsp").forward(request, response);
            }
            else {
                getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
            }
        }
        catch(Exception ex) {
            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            int price = Integer.parseInt(request.getParameter("price"));
            int amount = Integer.parseInt(request.getParameter("amount"));
            String description = request.getParameter("description");
            String categoryName = request.getParameter("idCategory");
            Product product = new Product(id, -1, name, price, amount, description);
            product.setCategoryName(categoryName);
            model.setExistingCategoryID(product);
            model.editProduct(product);
            response.sendRedirect(request.getContextPath() + "/admin");
        }
        catch(Exception ex) {

            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
    }
}