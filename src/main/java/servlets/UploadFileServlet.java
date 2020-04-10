package servlets;

import model.AdminMarketModel;
import parse.Parser;

import java.io.*;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

@WebServlet("/uploadFile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UploadFileServlet extends HttpServlet {

    @EJB(beanName = "DBAdminMarketModel")
    private AdminMarketModel model;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Part filePart = request.getPart("file");
            InputStream src = filePart.getInputStream();
            Source xslDoc = new StreamSource(getClass().getClassLoader().getResourceAsStream("shop.xsl"));
            String tempXml = "product.xml";
            request.setAttribute("file", tempXml);
            request.setAttribute("preview", model.xslTransform(src, xslDoc, tempXml));
            getServletContext().getRequestDispatcher("/import-results.jsp").forward(request, response);
        } catch (DAOException e) {
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/import.jsp");
            dispatcher.forward(request, response);
        }
    }
}