package edu.ui.filter;

import edu.bean.User;
import edu.ui.ctrl.UIConst;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "empFilter", urlPatterns = UIConst.AREAPATH + "/*", initParams = {
        @WebInitParam(name = "excludePages", value = UIConst.AREAPATH + "/Login;" + UIConst.AREAPATH + "/Main;" + UIConst.AREAPATH + "/PunchCard;" + UIConst.AREAPATH + "/EmpDetail"),
})
public class EmpFilter implements Filter {
    private static String admin = "";
    private static String excludePages;
    private static String[] excludePagesArray;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        boolean isExcludePage = false;
        for(String i : excludePagesArray){
            if(request.getServletPath().equals(i)){
                isExcludePage = true;
                break;
            }
        }
        if(!isExcludePage){
            HttpSession session = null;
            User user = null;
            String loginUser = null;
            try{
                session = request.getSession();
                user = (User) session.getAttribute(UIConst.BG_LOGINUSER_KEY);
                loginUser = user.getUserName();
            }catch (NullPointerException e){
                e.printStackTrace();
//            response.sendRedirect(request.getContextPath() + UIConst.AREAPATH + "/Login");
            }

            if(admin.equals(loginUser)){
                chain.doFilter(req, resp);

            }else{
                request.getRequestDispatcher(UIConst.VIEWPATH + "/error.jsp").forward(request, response);
            }
        }else{
            chain.doFilter(req, resp);
        }





//        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        admin = config.getServletContext().getInitParameter("loginUser");
        excludePages = config.getInitParameter("excludePages");
        excludePagesArray = excludePages.split(";");
    }

}
