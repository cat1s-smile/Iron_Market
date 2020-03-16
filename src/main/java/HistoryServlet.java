import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //String userID = request.getSession().getId();
        String userID = User.getDefaultID();
        ArrayList<Order> orders = OrderDataBase.selectByID(userID);
        ArrayList<OrderInfo> orderInfos = new ArrayList<>();
        if (!orders.isEmpty()) {
            for (Order order : orders) {
                ArrayList<String> content = new ArrayList<>();
                double totalCost = 0.0;
                ArrayList<OrderContent> orderContents = OrderContentDataBase.selectByOrderID(order.getIdOrder());
                for (OrderContent ord : orderContents) {
                    Product product = ProductDataBase.selectOne(ord.getIdProduct());
                    content.add(product.getName() + " x" + ord.getNumber() + ";");
                    totalCost += product.getPrice() * ord.getNumber();
                }
                orderInfos.add(new OrderInfo(order.getIdOrder(), content, totalCost));
            }
        }
        request.setAttribute("orders", orderInfos);
        getServletContext().getRequestDispatcher("/history.jsp").forward(request, response);
    }
}
