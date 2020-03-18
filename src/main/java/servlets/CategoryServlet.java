package servlets;

import entities.main.*;
import model.database.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/rawCategory")
public class CategoryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ArrayList<Product> products = ProductDataBase.selectByCategory(id);
        ArrayList<Category> categories = CategoryDataBase.select();
        int productNumber = 0;
        Order order = OrderDataBase.selectActive(User.getDefaultID());
        if(order!=null) {
            ArrayList<OrderContent> cart = OrderContentDataBase.selectByOrderID(order.getIdOrder());
            for(OrderContent ord : cart) {
                productNumber += ord.getNumber();
            }
        }
        request.setAttribute("cart", productNumber);
        request.setAttribute("catID", id);
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        getServletContext().getRequestDispatcher("/user.jsp").forward(request, response);
    }
}