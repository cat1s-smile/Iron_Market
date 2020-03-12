import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Product> products = ProductDataBase.select();
        ArrayList<Category> categories = CategoryDataBase.select();
        request.setAttribute("catID", -1);
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        /*User user = UserDataBase.selectOne(request.getSession().getId());
        if(user == null)
            UserDataBase.insert(new User(request.getSession().getId(), "", "", ""));
         */
        int productNumber = 0;
        Order order = OrderDataBase.selectActive(User.getDefaultID());
        if(order!=null) {
            ArrayList<OrderContent> cart = OrderContentDataBase.selectByOrderID(order.getIdOrder());
            for(OrderContent ord : cart) {
                productNumber += ord.getNumber();
            }
        }
        request.setAttribute("cart", productNumber);
        getServletContext().getRequestDispatcher("/user.jsp").forward(request, response);
    }
}