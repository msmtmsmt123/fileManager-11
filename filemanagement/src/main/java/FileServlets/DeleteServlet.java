package FileServlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet(urlPatterns = "/DeleteServlet")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fileName=req.getParameter("fname");
        String filePath=UploadServlet.getPath()+req.getSession().getAttribute("username")+fileName;
        File file=new File(filePath);
        file.delete();
        resp.sendRedirect("HomePage.jsp");

    }
}
