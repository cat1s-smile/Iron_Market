import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/confirm_order")
public class ConfirmOrderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            boolean statusOrder = true;
            //String userID = request.getSession().getId();
            String userID = User.getDefaultID();
            Order order = OrderDataBase.selectActive(userID);
            if(order!=null) {
                ArrayList<OrderContent> orderContents = OrderContentDataBase.selectByOrderID(order.getIdOrder());
                if (orderContents.isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/cart");
                    return;
                }
                for(OrderContent content : orderContents) {
                    Product product = ProductDataBase.selectOne(content.getIdProduct());
                    if (product.getAmount() < content.getNumber()) {
                        //lackProductIndex.add(product.getIdProduct());
                        statusOrder = false;
                        //response.sendRedirect(request.getContextPath() + "/cart");
                        // return;
                    }
                }
                if(statusOrder) {
                    for (OrderContent content : orderContents) {
                        Product product = ProductDataBase.selectOne(content.getIdProduct());
                        product.setAmount(product.getAmount() - content.getNumber());
                        ProductDataBase.update(product);
                    }
                    order.setStatus("1");
                    OrderDataBase.update(order);
                    response.sendRedirect(request.getContextPath() + "/order_successful.jsp");
                }
                else {
                    //request.setAttribute("indexes", lackProductIndex);
                    getServletContext().getRequestDispatcher("/cart").forward(request, response);
                }
            }
            else {
                response.sendRedirect(request.getContextPath() + "/cart");
            }
        }
        catch(Exception ex) {

            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
        }
    }
}