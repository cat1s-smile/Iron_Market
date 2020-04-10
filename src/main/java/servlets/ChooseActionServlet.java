package servlets;

import entities.jaxbready.ShopContent;
import entities.main.Category;
import entities.main.Product;
import model.AdminMarketModel;
import org.xml.sax.SAXException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@WebServlet("/chooseAction")
public class ChooseActionServlet extends HttpServlet {
    @EJB(beanName = "DBAdminMarketModel")
    private AdminMarketModel model;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = null;
        List<Category> categories = null;
        try {
            products = model.getProducts();
            categories = model.getCategories();
        } catch (DAOException e) {
            request.setAttribute("message", e.getMessage());
            getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
            return;
        }
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        request.setAttribute("mode", "preview");
        request.setAttribute("checkedProducts", null);
        String option = request.getParameter("option");
        if(option == null) {
            request.setAttribute("message", "Incorrect parameter");
            getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
            return;
        }
        switch (option) {
            case "a1":
                getServletContext().getRequestDispatcher("/change-status.jsp").forward(request, response);
                break;
            case "a2":
                getServletContext().getRequestDispatcher("/change-parameters.jsp").forward(request, response);
                break;
            default:
                request.setAttribute("message", "wrong action");
                getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
        }
    }
}
