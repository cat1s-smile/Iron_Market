import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/create")
public class CreateServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String name = request.getParameter("name");
            int price = Integer.parseInt(request.getParameter("price"));
            int amount = Integer.parseInt(request.getParameter("amount"));
            String description = request.getParameter("description");
            int idCategory = Integer.parseInt(request.getParameter("idCategory"));
            Product product = new Product(idCategory, name, price, amount, description);
            ProductDataBase.insert(product);
            response.sendRedirect(request.getContextPath()+"/admin-products");
        }
        catch(Exception ex) {

            getServletContext().getRequestDispatcher("/create.jsp").forward(request, response);
        }
    }
}



