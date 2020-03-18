package servlets;

import entities.main.Category;
import model.database.CategoryDataBase;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editCategory")
public class EditCategoryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Category category = CategoryDataBase.selectOne(id);
            if(category!=null) {
                request.setAttribute("category", category);
                getServletContext().getRequestDispatcher("/editCategory.jsp").forward(request, response);
            }
            else {
                getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
            }
        }
        catch(Exception ex) {
            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idCategory = Integer.parseInt(request.getParameter("idCategory"));
            String name = request.getParameter("name");
            Category category = new Category(idCategory, name);
            CategoryDataBase.update(category);
            response.sendRedirect(request.getContextPath() + "/admin-categories");
        }
        catch(Exception ex) {

            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
    }
}