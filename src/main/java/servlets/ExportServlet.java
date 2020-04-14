package servlets;

import entities.jaxbready.ShopContent;
import model.AdminMarketModel;
import model.database.ProductAndCategoryManager;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.server.ExportException;

@WebServlet("/export")
public class ExportServlet extends HttpServlet {

    @EJB
    private ProductAndCategoryManager model;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filePath;
        ShopContent shopContent;
        try {
            String option = request.getParameter("option");
            if(option == null) {
                request.setAttribute("message", "Incorrect parameter");
                getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
                return;
            }
            switch (option) {
                case "a1":
                    shopContent = model.getAllProducts();
                    filePath = "all_products.xml";
                    break;
                case "a2":
                    shopContent = model.getProductsOnSale();
                    filePath = "products_on_sale.xml";
                    break;
                case "a3":
                    shopContent = model.getArchivedProducts();
                    filePath = "archived_products.xml";
                    break;
                default:
                    request.setAttribute("message", "Incorrect parameter");
                    getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
                    return;
            }
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("text/xml;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filePath + "\"");
            model.toXmlFile(shopContent, outputStream);
            outputStream.close();
            response.sendRedirect(request.getContextPath() + "/admin");
        } catch (DAOException e) {
            request.setAttribute("message", e.getMessage());
            getServletContext().getRequestDispatcher("/not-found.jsp").forward(request, response);
        }
    }
}
