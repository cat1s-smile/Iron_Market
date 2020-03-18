package servlets;

import entities.main.Category;
import model.database.CategoryDataBase;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/createCategory")
public class CreateCategoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/createCategory.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String name = request.getParameter("name");
            Category category = new Category(name);
            CategoryDataBase.insert(category);
            response.sendRedirect(request.getContextPath()+"/admin-categories");
        }
        catch(Exception ex) {

            getServletContext().getRequestDispatcher("/createCategory.jsp").forward(request, response);
        }
    }
}



