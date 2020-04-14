package servlets;

import entities.main.Category;
import entities.main.Product;
import model.AdminMarketModel;
import model.database.ProductAndCategoryManager;
import parse.Parser;

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

    @EJB
    private ProductAndCategoryManager model;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] ID = request.getParameterValues("checkedId");
        String price = request.getParameter("price");
        String category = request.getParameter("category");
        if(ID == null) {
            response.sendRedirect(request.getContextPath() + "/admin");
            return;
        }
        List<Integer> ids = new ArrayList<>();
        for (String id : ID) {
            int idProduct = Integer.parseInt(id);
            if (idProduct < 0)
                redirectToErrorPage("Illegal ID detected", request, response);
            ids.add(idProduct);
        }
        List<Product> checkedProducts = null;
        try {
            checkedProducts = model.getProducts(ids);
        } catch (DAOException e) {
            redirectToErrorPage(e.getMessage(), request, response);
            return;
        }
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
                request.setAttribute("price", price);
                request.setAttribute("category", category);
                request.setAttribute("mode", "change");
                getServletContext().getRequestDispatcher("/change-parameters-preview.jsp").forward(request, response);
                break;
            case "back":
                List<Product> allProducts = null;
                List<Category> categories = null;
                try {
                    allProducts = model.getProducts();
                    categories = model.getCategories();
                } catch (DAOException e) {
                    redirectToErrorPage(e.getMessage(), request, response);
                    return;
                }
                //checkedProducts = model.getProducts(ids);
                request.setAttribute("checkedProducts", checkedProducts);
                request.setAttribute("products", allProducts);
                request.setAttribute("categories", categories);
                request.setAttribute("price", price);
                request.setAttribute("category", category);
                request.setAttribute("mode", "preview");
                getServletContext().getRequestDispatcher("/change-parameters.jsp").forward(request, response);
                break;
            case "change":
                //checkedProducts = model.getProducts(ids);
                try {
                    if (!price.equals("")) {
                        int priceVal = Parser.parseID(price);
                        if (priceVal < 0)
                            redirectToErrorPage("Incorrect price value", request, response);
                        if (category.equals("-"))
                            model.editProducts(checkedProducts, priceVal);
                        else
                            model.editProducts(checkedProducts, priceVal, category);
                    }
                    else if (!category.equals("-")) {
                        model.editProducts(checkedProducts, category);
                    }
                    response.sendRedirect(request.getContextPath() + "/admin");
                } catch (DAOException e) {
                    redirectToErrorPage(e.getMessage(), request, response);
                }
                break;
            default:
                redirectToErrorPage("unknown mode", request, response);
                break;
        }
    }

    protected void redirectToErrorPage(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("message", message);
        getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
    }
}

