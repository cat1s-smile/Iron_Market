package servlets;

import entities.main.Category;
import model.database.CategoryDataBase;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/admin-categories")
public class IndexCategoryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Category> categories = CategoryDataBase.select();
        request.setAttribute("categories", categories);

        getServletContext().getRequestDispatcher("/admin-categories.jsp").forward(request, response);
    }
}