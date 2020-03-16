import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //String userID = request.getSession().getId();
        String userID = User.getDefaultID();
        Order order = OrderDataBase.selectActive(userID);
        if (order!=null) {
            ArrayList<OrderContent> cart = OrderContentDataBase.selectByOrderID(order.getIdOrder());
            ArrayList<CartItem> cartItems = new ArrayList<>();
            int cost = 0;
            for (OrderContent ord : cart) {
                Product product = ProductDataBase.selectOne(ord.getIdProduct());
                if(product.getAmount() < ord.getNumber() || product.getStatus() == 0) {
                    cartItems.add(new CartItem(product, ord.getNumber(), 0));
                    request.setAttribute("correct", 0);
                }
                else {
                    cartItems.add(new CartItem(product, ord.getNumber(), 1));
                }
                cost += product.getPrice() * ord.getNumber();
            }
            request.setAttribute("cart", cartItems);
            request.setAttribute("cost", cost);
        }
        else {
            request.setAttribute("cost", 0);
        }
        getServletContext().getRequestDispatcher("/cart.jsp").forward(request, response);
    }
}