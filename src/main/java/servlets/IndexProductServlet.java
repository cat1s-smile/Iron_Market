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

@WebServlet("/admin-products")
public class IndexProductServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Product> products = ProductDataBase.select();
        request.setAttribute("products", products);
        getServletContext().getRequestDispatcher("/admin-products.jsp").forward(request, response);
    }
}