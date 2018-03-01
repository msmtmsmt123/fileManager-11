package FileServlets;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@WebServlet(urlPatterns = "/DownloadZipServlet")
public class DownloadZipServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        try {
            String path = UploadServlet.getPath()+req.getSession().getAttribute("username");

            File directory = new File(path);
            String[] files = req.getParameterValues("selectedFiles");


            if (files != null && files.length > 0) {
                byte[] zip = zipFiles(directory, files);

                ServletOutputStream sos = resp.getOutputStream();
                resp.setContentType("application/zip");
                resp.setHeader("Content-Disposition", "attachment; filename=\"DATA.ZIP\"");

                sos.write(zip);
                sos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] zipFiles(File directory, String[] files) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        byte bytes[] = new byte[2048];

        for (String fileName : files) {
            FileInputStream fis = new FileInputStream(directory.getPath() +
                    File.separator + fileName);
            BufferedInputStream bis = new BufferedInputStream(fis);

            zos.putNextEntry(new ZipEntry(fileName));

            int bytesRead;
            while ((bytesRead = bis.read(bytes)) != -1) {
                zos.write(bytes, 0, bytesRead);
            }
            zos.closeEntry();
            bis.close();
            fis.close();
        }
        zos.flush();
        baos.flush();
        zos.close();
        baos.close();

        return baos.toByteArray();
    }
}
