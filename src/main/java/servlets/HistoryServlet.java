package servlets;

import entities.main.*;
import entities.supporting.OrderInfo;
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

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {


    @EJB(beanName = "DBUserMarketModel")
    private UserMarketModel model;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userID = User.getDefaultID();
        request.setAttribute("orders", model.getOrdersHistory(userID));
        getServletContext().getRequestDispatcher("/history.jsp").forward(request, response);
    }
}
