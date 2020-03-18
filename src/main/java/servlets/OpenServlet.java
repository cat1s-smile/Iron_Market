package servlets;

import entities.main.*;
import model.database.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/open")
public class OpenServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Product product = ProductDataBase.selectOne(id);
            if(product!=null) {
                request.setAttribute("product", product);
                getServletContext().getRequestDispatcher("/open.jsp").forward(request, response);
            }
            else {
                getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
            }
        }
        catch(Exception ex) {
            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
    }

    /*protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            RawProduct rawProduct = model.database.ProductDataBase.selectOne(id);
            String name = rawProduct.getName();
            int price = rawProduct.getPrice();
            int amount = rawProduct.getAmount() - 1;
            String description = rawProduct.getDescription();
            int idCategory = rawProduct.getIdCategory();
            RawProduct productToBuy = new RawProduct(id, idCategory, name, price, amount, description);
            model.database.ProductDataBase.update(productToBuy);
            response.sendRedirect(request.getContextPath() + "/user");
        }
        catch(Exception ex) {

            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
    }*/
}