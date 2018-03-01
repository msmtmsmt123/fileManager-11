package FileServlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    private Logger LOGGER=Logger.getLogger(UploadServlet.class.getCanonicalName());

    public static String getPath() throws IOException {
        Properties properties=new Properties();
//        Reader reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("config.properties"));
        Reader reader=new FileReader("C:\\Users\\user\\IdeaProjects\\nairis\\filemanagerparent\\filemanagement\\src\\main\\java\\config.properties");
        properties.load(reader);
        return (String)properties.get("root.dir");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        final Part filePart = req.getPart("file");
        final String fileName = getFileName(filePart);
        final String path=getPath()+req.getSession().getAttribute("username");

        OutputStream out = null;
        InputStream filecontent = null;

        final PrintWriter writer = resp.getWriter();

        try {
            out = new FileOutputStream(new File(path + File.separator
                    + fileName));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }


            LOGGER.log(Level.INFO, "File{0}being uploaded to {1}",
                    new Object[]{fileName, path});
        } catch (FileNotFoundException fne) {
            writer.println("You either did not specify a file to upload or are "
                    + "trying to upload a file to a protected or nonexistent "
                    + "location.");
            writer.println("<br/> ERROR: " + fne.getMessage());

            LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}",
                    new Object[]{fne.getMessage()});
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
//            if (writer != null) {
//                writer.close();
//            }
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("HomePage.jsp");
        writer.println("<font color=blue> New file " + fileName + " created at " + path + "<font>");
        dispatcher.include(req,resp);
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
