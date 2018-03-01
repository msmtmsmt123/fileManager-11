import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username=req.getParameter("usernameForm");
        String pass=req.getParameter("passForm");
        String passHash = DigestUtils.sha256Hex(pass);

        Boolean wrongUsername=true;


        File file = new File("C:\\Users\\user\\IdeaProjects\\nairis\\filemanagerparent\\filemanagement\\src\\main\\resources\\users.json");
        String jsonArrayString= FileUtils.readFileToString(file,"UTF-8");
        JSONParser parser=new JSONParser();
        JSONArray jsonArray=null;
        try {
            jsonArray=(JSONArray)parser.parse(jsonArrayString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < jsonArray.size(); i++) {
            if (username.equals( ((JSONObject)jsonArray.get(i)).get("username") ) &&
                    passHash.equals( ((JSONObject)jsonArray.get(i)).get("password_hash") ) ){
                wrongUsername=false;
                HttpSession session=req.getSession();
                session.setAttribute("isLoggedIn","true");
                session.setAttribute("user'sName",((JSONObject)jsonArray.get(i)).get("name"));
                session.setAttribute("username",username+File.separator);
                resp.sendRedirect("HomePage.jsp");
            }
        }

        if (wrongUsername){
            RequestDispatcher dispatcher=req.getRequestDispatcher("LoginPage.jsp");

            PrintWriter out=resp.getWriter();
            out.println("<font color=red> Wrong username and/or password </font>");

            dispatcher.include(req,resp);
        }


    }
}
