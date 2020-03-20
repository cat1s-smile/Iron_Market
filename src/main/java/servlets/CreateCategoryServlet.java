package servlets;

import entities.main.Category;
import model.AdminMarketModel;
import model.UserMarketModel;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/createCategory")
public class CreateCategoryServlet extends HttpServlet {

    @EJB(beanName = "DBAdminMarketModel")
    private AdminMarketModel model;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/createCategory.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String name = request.getParameter("name");
            Category category = new Category(name);
            model.createCategory(category);
            request.setAttribute("tab", "categories");
            response.sendRedirect(request.getContextPath()+"/admin");
        }
        catch(Exception ex) {

            getServletContext().getRequestDispatcher("/createCategory.jsp").forward(request, response);
        }
    }
}



