package FileServlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(urlPatterns = "/DownloadServlet/*")
public class DownloadServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String fileName=req.getParameter("fname");
        String filePath=UploadServlet.getPath()+req.getSession().getAttribute("username")+fileName;
        File file=new File(filePath);
        FileInputStream inputStream=new FileInputStream(file);

        ServletContext context=getServletContext();

        String mimeType=context.getMimeType(filePath);
        if (mimeType==null){
            mimeType="application/octet-stream";
        }

        resp.setContentType(mimeType);
        resp.setContentLength((int)file.length());

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
        resp.setHeader(headerKey, headerValue);

        OutputStream outputStream=resp.getOutputStream();

        int count=0;
        byte[] buffer=new byte[4096];

        while ((count=inputStream.read(buffer))>0){
            outputStream.write(buffer,0,count);
            outputStream.flush();
        }
        inputStream.close();
        outputStream.close();

    }
}
