import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/admin-orders")
public class IndexOrderServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Order> orders = OrderDataBase.select();
        request.setAttribute("orders", orders);

        getServletContext().getRequestDispatcher("/admin-orders.jsp").forward(request, response);
    }
}