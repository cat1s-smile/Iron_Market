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

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String search = request.getParameter("search");
        int catID = Integer.parseInt(request.getParameter("catID"));
        ArrayList<Product> products;
        if (catID == -1)
            products = ProductDataBase.selectBySearch(search);
        else
            products = ProductDataBase.selectBySearch(search, catID);
        ArrayList<Category> categories = CategoryDataBase.select();
        request.setAttribute("cart", request.getParameter("cart"));
        request.setAttribute("catID", catID);
        request.setAttribute("req", search);
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);

        getServletContext().getRequestDispatcher("/user.jsp").forward(request, response);
    }
}