package servlets;

import entities.main.Product;
import model.AdminMarketModel;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/changesPreview")
public class ChangeStatusServlet extends HttpServlet {

    @EJB(beanName = "DBAdminMarketModel")
    private AdminMarketModel model;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] ID = request.getParameterValues("checkedId");
        ArrayList<Product> checkedProducts = new ArrayList<>();
        switch (request.getParameter("mode")) {
            case "preview":
                for (String id : ID) {
                    int idProduct = Integer.parseInt(id);
                    Product product = model.getProduct(idProduct);
                    checkedProducts.add(product);
                }
                request.setAttribute("products", checkedProducts);
                request.setAttribute("mode", "change");
                getServletContext().getRequestDispatcher("/changeStatusPreview.jsp").forward(request, response);
                break;
            case "back":
                List<Product> allProducts = model.getProducts();
                for (String id : ID) {
                    int idProduct = Integer.parseInt(id);
                    Product product = model.getProduct(idProduct);
                    checkedProducts.add(product);
                }
                request.setAttribute("checkedProducts", checkedProducts);
                request.setAttribute("products", allProducts);
                request.setAttribute("mode", "preview");
                getServletContext().getRequestDispatcher("/changeStatus.jsp").forward(request, response);
                break;
            case "change":
                for (String id : ID) {
                    int idProduct = Integer.parseInt(id);
                    model.changeProductStatus(idProduct);
                }
                request.setAttribute("mode", "preview");
                response.sendRedirect(request.getContextPath() + "/admin");
                break;
            }
        }
    }

