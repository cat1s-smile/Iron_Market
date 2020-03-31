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
        String category = request.getParameter("category");
        ArrayList<Integer> ids = new ArrayList<>();
        for (String id : ID) {
            int idProduct = Integer.parseInt(id);
            ids.add(idProduct);
        }
        List<Product> checkedProducts;
        switch (request.getParameter("mode")) {
            case "preview":
                checkedProducts = model.getProducts(ids);
                request.setAttribute("products", checkedProducts);
                request.setAttribute("price", price);
                request.setAttribute("category", category);
                request.setAttribute("mode", "change");
                getServletContext().getRequestDispatcher("/changeParametersPreview.jsp").forward(request, response);
                break;
            case "back":
                List<Product> allProducts = model.getProducts();
                checkedProducts = model.getProducts(ids);
                request.setAttribute("checkedProducts", checkedProducts);
                request.setAttribute("products", allProducts);
                request.setAttribute("price", price);
                request.setAttribute("category", category);
                request.setAttribute("mode", "preview");
                getServletContext().getRequestDispatcher("/changeParameters.jsp").forward(request, response);
                break;
            case "change":
                checkedProducts = model.getProducts(ids);
                if (!price.equals("") && !category.equals("")) {
                    try {
                        model.editProducts(checkedProducts, Integer.parseInt(price), category);
                    } catch (DAOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    if (!price.equals("")) model.editProducts(checkedProducts, Integer.parseInt(price));
                    else {
                        if (!category.equals("")) {
                            try {
                                model.editProducts(checkedProducts, category);
                            } catch (DAOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                request.setAttribute("mode", "preview");
                response.sendRedirect(request.getContextPath() + "/admin");
                break;
            }
        }
    }

