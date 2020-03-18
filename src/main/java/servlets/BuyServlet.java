package servlets;

import entities.main.*;
import model.database.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/buy")
public class BuyServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            /*String userID = request.getSession().getId();
            int id = Integer.parseInt(request.getParameter("id"));
            entities.main.Order order = null;
            order = model.database.OrderDataBase.selectActive(userID);
            entities.main.OrderContent orderContent = null;
            if(order == null) {
                model.database.OrderDataBase.insert(new entities.main.Order(userID, "0"));
                order = model.database.OrderDataBase.selectActive(userID);
                int orderID = order.getIdOrder();
                model.database.OrderContentDataBase.insert(new entities.main.OrderContent(orderID, id, 1));
            }
            else {
                order = model.database.OrderDataBase.selectActive(userID);
                orderContent = model.database.OrderContentDataBase.selectOne(order.getIdOrder(), id);
                if(orderContent == null)
                    model.database.OrderContentDataBase.insert(new entities.main.OrderContent(order.getIdOrder(), id, 1));
                else {
                    orderContent.setNumber(orderContent.getNumber() + 1);
                    model.database.OrderContentDataBase.update(orderContent);
                }
            }*/
            String userID = User.getDefaultID();
            int id = Integer.parseInt(request.getParameter("id"));
            Order order = null;
            order = OrderDataBase.selectActive(userID);
            if (order == null) {
                OrderDataBase.insert(new Order(userID, "0"));
                order = OrderDataBase.selectActive(userID);
                int orderID = order.getIdOrder();
                OrderContentDataBase.insert(new OrderContent(orderID, id, 1));
            } else {
                OrderContent orderContent = null;
                orderContent = OrderContentDataBase.selectOne(order.getIdOrder(), id);
                if (orderContent == null)
                    OrderContentDataBase.insert(new OrderContent(order.getIdOrder(), id, 1));
                else {
                    orderContent.setNumber(orderContent.getNumber() + 1);
                    OrderContentDataBase.update(orderContent);
                }
            }
            response.sendRedirect(request.getContextPath() + "/user");
        } catch (Exception ex) {

            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
    }
}