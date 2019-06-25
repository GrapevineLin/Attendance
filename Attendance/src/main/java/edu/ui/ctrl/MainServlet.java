package edu.ui.ctrl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(UIConst.AREAPATH  + "/Main")
public class MainServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        // 设置响应对象的编码方式response.setCharacterEncoding("utf-8");
        // 设置响应的内容类型为text/html
        response.setContentType("text/html; charset=utf-8");
        // ***** Servlet的doxxx方法中的6个标准对象（含request和response）
        // 从request里获取session对象和application对象
        HttpSession session = request.getSession();
        javax.servlet.ServletContext application = request.getServletContext();
        // 调用继承的方法来获取config对象
        javax.servlet.ServletConfig config = getServletConfig();
        // 从response对象里获取out对象——response.getWriter()之前，要先设置页面的编码
        java.io.PrintWriter out = response.getWriter();
        /* ----------------------------------------------------------------- */


        //检测是否登陆
        String toURL = checkLogin(request, response);
        if(toURL != null){
            response.sendRedirect(toURL);
            return;
        }

        //取得操作类型
        String oper = request.getParameter("oper");
        if(oper != null && oper.equalsIgnoreCase("welcome")){
            //桌面显示welcome.jsp
            welcomeView(request, response);
        }else{
            //转发到主页
            mainView(request, response);

        }

    }

    protected void mainView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //转发到Main.jsp
        String toPage = UIConst.VIEWPATH + "/Main.jsp";
        request.getRequestDispatcher(toPage).forward(request, response);
    }
    protected void welcomeView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //转发页面到Welcome.jsp
        String toPage = UIConst.VIEWPATH + "/Welcome.jsp";
        request.getRequestDispatcher(toPage).forward(request, response);
    }

    protected String checkLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String toURL = null;
        Object obj = session.getAttribute(UIConst.BG_LOGINUSER_KEY);
        if(obj == null){
            toURL = request.getContextPath() + UIConst.AREAPATH + "/Login";
        }
        return toURL;
    }
}
