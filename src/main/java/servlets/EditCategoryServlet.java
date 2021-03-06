package servlets;

import entities.main.Category;
import model.AdminMarketModel;
import model.database.ProductAndCategoryManager;
import parse.Parser;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editCategory")
public class EditCategoryServlet extends HttpServlet {

    @EJB
    private ProductAndCategoryManager model;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Parser.parseID(request.getParameter("id"));
            Category category = model.getCategory(id);
            if (category != null) {
                request.setAttribute("category", category);
                getServletContext().getRequestDispatcher("/edit-category.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Incorrect id detected");
                getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
            }
        } catch (DAOException ex) {
            getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Parser.parseID(request.getParameter("id"));
            String name = request.getParameter("name");
            Category category = new Category(id, name);
            model.editCategory(category);
            response.sendRedirect(request.getContextPath() + "/admin?tab=categories");
        } catch(DAOException ex) {
            request.setAttribute("message", ex.getMessage());
            doGet(request, response);
            //getServletContext().getRequestDispatcher("/create-category.jsp").forward(request, response);
        }
    }
}