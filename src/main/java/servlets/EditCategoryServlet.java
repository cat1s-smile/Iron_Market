package servlets;

import entities.main.Category;
import model.AdminMarketModel;
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

    @EJB(beanName = "DBAdminMarketModel")
    private AdminMarketModel model;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Parser.parseID(request.getParameter("id"));
            Category category = model.getCategory(id);
            if (category != null) {
                request.setAttribute("category", category);
                getServletContext().getRequestDispatcher("/editCategory.jsp").forward(request, response);
            } else {
                getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Parser.parseID(request.getParameter("id"));
            String name = request.getParameter("name");
            Category duplicate = model.getCategory(name);
            Category category = new Category(id, name);
            if (name.equalsIgnoreCase(duplicate.getName())) {
                if (id != duplicate.getId())
                    getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
            }
            else
                model.editCategory(category);
                response.sendRedirect(request.getContextPath() + "/admin?tab=categories");
        } catch (Exception ex) {

            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
    }
}