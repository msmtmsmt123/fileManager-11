import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter
public class SecurityFilter implements Filter {
    private ServletContext context;
    public void init(FilterConfig filterConfig){
        this.context=filterConfig.getServletContext();
        this.context.log("RequestLoggingFilter initialized");
    }

    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)req;
        HttpSession session=request.getSession();
        if ("true".equals(session.getAttribute("isLoggedIn"))){
            filterChain.doFilter(req,resp);
        }
        else {
            RequestDispatcher dispatcher=req.getRequestDispatcher("LoginPage.jsp");
            PrintWriter out=resp.getWriter();
            out.println("<font color=red> You must Login to continue </font>");
            dispatcher.include(request,resp);
        }
    }

    public void destroy() {

    }
}
