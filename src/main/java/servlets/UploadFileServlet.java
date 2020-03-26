package servlets;

import parse.Parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Gets absolute path to root directory of web app.
            String appPath = request.getServletContext().getRealPath("");
            appPath = appPath.replace('\\', '/');

           // The directory to save uploaded file
            String fullPath = null;
            if (appPath.endsWith("/")) {
                fullPath = appPath;
            } else {
                fullPath = appPath + "/";
            }

            // Part list (multi files).

            String filePath = null;
            for (Part part : request.getParts()) {
                String fileName = extractFileName(part);
                if (fileName != null && fileName.length() > 0) {
                    filePath = fullPath + fileName;
                    System.out.println("Write attachment to file: " + filePath);
                    // Write to file
                    part.write(filePath);
                }
            }

            // Upload successfully!.

            int id = Parser.parseID(request.getParameter("impOrExp"));
            if (id==0) {
                // Transform to HTML
                TransformerFactory tFactory = TransformerFactory.newInstance();
                String xslPath = "C:\\Users\\alex1\\IdeaProjects\\Iron_Market\\src\\main\\resources\\shop.xsl";
                Source xslDoc = new StreamSource(xslPath);
                Source xmlDoc = new StreamSource(filePath);
                String outputFileName = "C:\\Users\\alex1\\IdeaProjects\\Iron_Market\\target\\IronMarket-1.0-SNAPSHOT\\preview.html";
                OutputStream htmlFile = new FileOutputStream(outputFileName);
                Transformer trasform = tFactory.newTransformer(xslDoc);
                trasform.transform(xmlDoc, new StreamResult(htmlFile));

                request.setAttribute("xsl", xslPath);
                request.setAttribute("file", filePath);

                getServletContext().getRequestDispatcher("/importResults.jsp").forward(request, response);
            }
            else {
                request.setAttribute("file", filePath);
                getServletContext().getRequestDispatcher("/exportResults.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/import.jsp");
            dispatcher.forward(request, response);
        }
    }

    private String extractFileName(Part part) {
        // form-data; name="file"; filename="C:\file1.zip"
        // form-data; name="file"; filename="C:\Note\file2.zip"
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                // C:\file1.zip
                // C:\Note\file2.zip
                String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                clientFileName = clientFileName.replace("\\", "/");
                int i = clientFileName.lastIndexOf('/');
                // file1.zip
                // file2.zip
                return clientFileName.substring(i + 1);
            }
        }
        return null;
    }

}