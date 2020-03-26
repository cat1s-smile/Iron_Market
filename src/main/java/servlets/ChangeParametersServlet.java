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
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/changeParametersPreview")
public class ChangeParametersServlet extends HttpServlet {

    @EJB(beanName = "DBAdminMarketModel")
    private AdminMarketModel model;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] ID = request.getParameterValues("checkedId");
        String price = request.getParameter("price");
        String idCategory = request.getParameter("idCategory");
        ArrayList<Product> checkedProducts = new ArrayList<>();
        switch (request.getParameter("mode")) {
            case "preview":
                for (String id : ID) {
                    int idProduct = Integer.parseInt(id);
                    Product product = model.getProduct(idProduct);
                    checkedProducts.add(product);
                }
                request.setAttribute("products", checkedProducts);
                request.setAttribute("price", price);
                request.setAttribute("idCategory", idCategory);
                request.setAttribute("mode", "change");
                getServletContext().getRequestDispatcher("/changeParametersPreview.jsp").forward(request, response);
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
                getServletContext().getRequestDispatcher("/changeParameters.jsp").forward(request, response);
                break;
            case "change":
                for (String id : ID) {
                    int idProduct = Integer.parseInt(id);
                    Product product = model.getProduct(idProduct);
                    if(!price.equals("")) product.setPrice(Integer.parseInt(price));
                    if(!idCategory.equals("")) product.setIdCategory(Integer.parseInt(idCategory));
                    model.editProduct(product);
                }
                request.setAttribute("mode", "preview");
                response.sendRedirect(request.getContextPath() + "/admin");
                break;
            }
        }
    }

