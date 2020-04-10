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
        List<Integer> ids = new ArrayList<>();
        for (String id : ID) {
            int idProduct = Integer.parseInt(id);
            if (idProduct < 0)
                redirectToErrorPage("Illegal ID detected", request, response);
            ids.add(idProduct);
        }
        try {
            List<Product> checkedProducts = model.getProducts(ids);
            String mode = request.getParameter("mode");
            if(mode == null) {
                request.setAttribute("message", "Incorrect parameter");
                getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
                return;
            }
            switch (mode) {
                case "preview":
                    //checkedProducts = model.getProducts(ids);
                    request.setAttribute("products", checkedProducts);
                    request.setAttribute("mode", "change");
                    getServletContext().getRequestDispatcher("/change-status-preview.jsp").forward(request, response);
                    break;
                case "back":
                    List<Product> allProducts = model.getProducts();
                    //checkedProducts = model.getProducts(ids);
                    request.setAttribute("checkedProducts", checkedProducts);
                    request.setAttribute("products", allProducts);
                    request.setAttribute("mode", "preview");
                    getServletContext().getRequestDispatcher("/change-status.jsp").forward(request, response);
                    break;
                case "change":
                    //checkedProducts = model.getProducts(ids);
                    for (Product product : checkedProducts) {
                        model.changeProductStatus(product.getId());
                    }
                    request.setAttribute("mode", "preview");
                    response.sendRedirect(request.getContextPath() + "/admin");
                    break;
            }
        } catch (DAOException e) {
            redirectToErrorPage(e.getMessage(), request, response);
        }
    }

    protected void redirectToErrorPage(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("message", message);
        getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
    }

}

