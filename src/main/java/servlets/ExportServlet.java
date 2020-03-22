package servlets;

import entities.jaxbready.ShopContent;
import model.AdminMarketModel;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;

@WebServlet("/export")
public class ExportServlet extends HttpServlet {
    @EJB(beanName = "DBAdminMarketModel")
    private AdminMarketModel model;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShopContent shopContent = null;
        switch (request.getParameter("option")){
            case "a1":
                shopContent = model.getAllProducts();
                break;
            case "a2":
                shopContent = model.getProductsOnSale();
                break;
            case "a3":
                shopContent = model.getArchivedProducts();
                break;
        }
        try {
            model.toXmlFile(shopContent, "C:\\Users\\alex1\\IdeaProjects\\Iron_Market\\src\\main\\resources\\result.xml");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath()+"/admin");
    }
}
