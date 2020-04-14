package servlets;

import model.AdminMarketModel;
import model.UserMarketModel;
import model.database.ProductAndCategoryManager;
import parse.Parser;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/archive")
public class ArchiveServlet extends HttpServlet {

    @EJB
    private ProductAndCategoryManager model;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Parser.parseID(request.getParameter("id"));
            if (id == Parser.NAN) {
                request.setAttribute("message", "Parameter \"id\" is incorrect");
                getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
            }
            else {
                model.changeProductStatus(id);
                response.sendRedirect(request.getContextPath() + "/admin");
            }
        } catch (DAOException ex) {
            request.setAttribute("message", ex.getMessage());
            getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
        }
    }
}