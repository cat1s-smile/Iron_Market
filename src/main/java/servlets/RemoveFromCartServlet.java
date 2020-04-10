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

@WebServlet("/remove_from_cart")
public class RemoveFromCartServlet extends HttpServlet {
    @EJB(beanName = "DBUserMarketModel")
    private UserMarketModel model;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String userID = User.getDefaultID();
            int productID = Parser.parseID(request.getParameter("id"));
            String toDo = request.getParameter("todo");
            switch (toDo) {
                case "-":
                    model.decrementItemNumber(userID, productID);
                    break;
                case "+":
                    model.incrementItemNumber(userID, productID);
                    break;
                case "remove":
                    model.removeItemFromOrder(userID, productID);
                    break;
                default:
                    request.setAttribute("message", "Incorrect parameter");
                    getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
                    return;
            }
            response.sendRedirect(request.getContextPath() + "/cart");
        } catch (DAOException e) {
            request.setAttribute("message", e.getMessage());
            getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
        }
    }
}