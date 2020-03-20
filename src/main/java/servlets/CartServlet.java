package servlets;

import entities.main.*;
import entities.supporting.*;
import model.AdminMarketModel;
import model.UserMarketModel;
import model.database.*;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @EJB(beanName = "DBUserMarketModel")
    private UserMarketModel model;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //String userID = request.getSession().getId();
        String userID = User.getDefaultID();
        List<CartItem> cart = model.validateOrder(model.getCart(userID));
        request.setAttribute("cart", cart);
        if(!model.isOrderValid(cart))
            request.setAttribute("correct", 0);
        request.setAttribute("cost", model.getOrderCost(cart));
        getServletContext().getRequestDispatcher("/cart.jsp").forward(request, response);
    }
}