import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/remove_from_cart")
public class RemoveFromCartServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            //String userID = request.getSession().getId();
            String userID = User.getDefaultID();
            int id = Integer.parseInt(request.getParameter("id"));
            Order order = OrderDataBase.selectActive(userID);
            String toDo = request.getParameter("todo");
            switch (toDo) {
                case "-":
                    OrderContent orderContent = OrderContentDataBase.selectOne(order.getIdOrder(), id);
                    if(orderContent.getNumber() ==1) {
                        OrderContentDataBase.delete(order.getIdOrder(), id);
                    }
                    else {
                        orderContent.setNumber(orderContent.getNumber() - 1);
                        OrderContentDataBase.update(orderContent);
                    }
                    break;
                case "+":
                    orderContent = OrderContentDataBase.selectOne(order.getIdOrder(), id);
                    orderContent.setNumber(orderContent.getNumber() + 1);
                    OrderContentDataBase.update(orderContent);
                    break;
                case "remove":
                    OrderContentDataBase.delete(order.getIdOrder(), id);
                    break;
                default:
            }
            response.sendRedirect(request.getContextPath() + "/cart");
        }
        catch(Exception ex) {

            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
    }
}