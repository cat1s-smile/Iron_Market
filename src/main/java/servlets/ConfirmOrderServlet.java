package servlets;

import entities.main.*;
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

@WebServlet("/confirm_order")
public class ConfirmOrderServlet extends HttpServlet {

    @EJB(beanName = "DBUserMarketModel")
    private UserMarketModel model;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            boolean isSuccessful = model.confirmOrder(User.getDefaultID());
            if(isSuccessful)
                getServletContext().getRequestDispatcher("/order-successful.jsp").forward(request, response);
            else
                response.sendRedirect(request.getContextPath() + "/cart");
        }
        catch(DAOException ex) {
            request.setAttribute("message", ex.getMessage());
            getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
        }
    }
}