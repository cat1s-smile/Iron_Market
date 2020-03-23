package servlets;

import entities.jaxbready.ShopContent;
import model.AdminMarketModel;

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
    @EJB(beanName = "DBAdminMarketModel")
    private AdminMarketModel model;

   /* @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("file");
        File file = new File(fileName);
        ServletOutputStream outputStream = null;
        BufferedInputStream inputStream = null;
        try {
            outputStream = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setContentLength((int) file.length());
            FileInputStream fileInputStream = new FileInputStream(file);
            inputStream = new BufferedInputStream(fileInputStream);
            int readBytes = 0;
            while ((readBytes = inputStream.read()) != -1)
                outputStream.write(readBytes);
        }catch (ExportException e){
            e.printStackTrace();
        }finally {
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
    }*/

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String filePath = request.getParameter("file");
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
            model.toXmlFile(shopContent, filePath);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        File file = new File(filePath);
        ServletOutputStream outputStream = null;
        BufferedInputStream inputStream = null;
        try {
            outputStream = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filePath + "\"");
            response.setContentLength((int) file.length());
            FileInputStream fileInputStream = new FileInputStream(file);
            inputStream = new BufferedInputStream(fileInputStream);
            int readBytes = 0;
            while ((readBytes = inputStream.read()) != -1)
                outputStream.write(readBytes);
        }catch (ExportException e){
            e.printStackTrace();
        }finally {
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
        response.sendRedirect(request.getContextPath()+"/admin");
    }
}
