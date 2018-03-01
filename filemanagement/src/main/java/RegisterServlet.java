import FileServlets.UploadServlet;
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
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("nameRegPage");
        String email = req.getParameter("emailRegPage");
        String username = req.getParameter("usernameRegPage");
        String pass = req.getParameter("passRegPage");

        Boolean userExist=false;

        File file = new File("C:\\Users\\user\\IdeaProjects\\nairis\\filemanagerparent\\filemanagement\\src\\main\\resources\\users.json");

        String jsonString = FileUtils.readFileToString(file, "UTF-8");

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = null;

        try {
            jsonArray = (JSONArray) parser.parse(jsonString);
//            JSONArray jsonArray = new JSONArray();
        } catch (ParseException e) {
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("RegisterPage.jsp");
        PrintWriter out = resp.getWriter();

        for (Object obj : jsonArray) {
            obj = (JSONObject) obj;
            if (username.equals(((JSONObject) obj).get("username")) ||
                    email.equals(((JSONObject) obj).get("email"))) {
                userExist=true;
                out.println("<font color=red> Username and/or Email already exist </font>");
                dispatcher.include(req, resp);
                break;
            }
        }

        if (!userExist){
            String path = UploadServlet.getPath();
            Boolean createFolder = new File(path + username).mkdir();

            JSONObject newUser = new JSONObject();
            newUser.put("name", name);
            newUser.put("email", email);
            newUser.put("username", username);
            newUser.put("password_hash", DigestUtils.sha256Hex(pass));

            jsonArray.add(newUser);
            FileUtils.writeStringToFile(file, jsonArray.toJSONString(), "UTF-8");
            out.println("<p> Dear " + name + " Registration is done successfully" +
                    "<br> new folder <b>"+username+"</b> is created</p>");
            dispatcher.include(req, resp);
        }



    }
}
