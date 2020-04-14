package servlets;

import entities.jaxbready.ShopContent;
import model.AdminMarketModel;
import model.database.ProductAndCategoryManager;
import org.xml.sax.SAXException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;

@WebServlet("/import")
public class ImportServlet extends HttpServlet {
    @EJB
    private ProductAndCategoryManager model;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ShopContent shopContent = null;
        String xmlPath = request.getParameter("file");
        try {
            shopContent = model.createShopContent(xmlPath, new StreamSource(getClass().getClassLoader().getResourceAsStream("shopContent.xsd")));
            String option = request.getParameter("option");
            if(option == null) {
                request.setAttribute("message", "Incorrect parameter");
                getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
                return;
            }
            switch (option)  {
                case "a1":
                    model.insertUpdateIgnoreDuplicates(shopContent);
                    break;
                case "a2":
                    model.insertUpdateOverwriteDuplicates(shopContent);
                    break;
                case "a3":
                    model.insertUpdateWithDuplicates(shopContent);
                    break;
                default:
                    request.setAttribute("message", "Incorrect parameter");
                    getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
            }
            response.sendRedirect(request.getContextPath() + "/admin");
        } catch (DAOException e) {
            request.setAttribute("message", e.getMessage());
            getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
        }
    }
}
