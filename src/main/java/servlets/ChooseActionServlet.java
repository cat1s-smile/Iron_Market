package servlets;

import entities.jaxbready.ShopContent;
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
        List<Product> products = model.getProducts();
        request.setAttribute("products", products);
        request.setAttribute("mode", "preview");
        request.setAttribute("checkedProducts", null);
        switch (request.getParameter("option")){
            case "a1":
                getServletContext().getRequestDispatcher("/changeStatus.jsp").forward(request, response);
                break;
            case "a2":
                getServletContext().getRequestDispatcher("/changeParameters.jsp").forward(request, response);
                break;
        }
    }
}
