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

@WebServlet("/buy")
public class BuyServlet extends HttpServlet {

    @EJB(beanName = "DBUserMarketModel")
    private UserMarketModel model;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Parser.parseID(request.getParameter("buyID"));
            if (id == Parser.NAN) {
                request.setAttribute("message", "Parameter \"id\" is incorrect");
                getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
            }
            else {
                String userID = User.getDefaultID();
                model.buyProduct(id, userID);
                int catID = Parser.parseID(request.getParameter("catID"));
                if(catID != Parser.NAN)
                    request.setAttribute("catID", catID);
                String searchRequest = request.getParameter("search");
                if(searchRequest != null)
                    request.setAttribute("search", searchRequest);
                request.setAttribute("cart", request.getParameter("cart"));
                getServletContext().getRequestDispatcher("/user").forward(request, response);
                //response.sendRedirect(request.getContextPath() + "/user");
            }

        } catch (DAOException ex) {
            request.setAttribute("message", ex.getMessage());
            getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
        }
    }
}