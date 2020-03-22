package servlets;

import entities.jaxbready.ShopContent;
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

@WebServlet("/import")
public class ImportServlet extends HttpServlet {
    @EJB(beanName = "DBAdminMarketModel")
    private AdminMarketModel model;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShopContent shopContent = null;
        String xmlPath = request.getParameter("file");
        try {
           shopContent =  model.createShopContent(xmlPath, "C:\\Users\\aaa\\IdeaProjects\\IronMarket\\src\\main\\resources\\shopContent.xsd");
        } catch (JAXBException | SAXException e) {
            e.printStackTrace();
        }
        switch (request.getParameter("option")){
            case "a1":
                model.insertUpdateIgnoreDuplicates(shopContent);
                break;
            case "a2":
                model.insertUpdateOverwriteDuplicates(shopContent);
                break;
            case "a3":
                model.insertUpdateWithDuplicates(shopContent);
                break;
        }
        response.sendRedirect(request.getContextPath()+"/admin");
    }
}
