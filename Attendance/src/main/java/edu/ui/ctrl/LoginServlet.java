package edu.ui.ctrl;

import edu.bean.User;
import edu.service.impl.UserService;
import edu.service.impl.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(UIConst.AREAPATH + "/Login")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /* **************************************************************** */
        /* ********** Servlet的doXXX方法中的编码方式和6个标准对象 ********** */
        /* **************************************************************** */
        // ***** Servlet的doXXX方法中的编码方式
        // 设置请求对象的编码方式
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


        String oper = request.getParameter("oper");
        if (oper != null && oper.equalsIgnoreCase("loginDeal")) {
            loginDeal(request, response);
        } else {
            loginView(request, response);
        }
    }

    protected void loginView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String toPage = UIConst.VIEWPATH + "/Login_x.jsp";
        request.getRequestDispatcher(toPage).forward(request, response);
    }

    protected void loginDeal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

        //获取客户端数据
        String userName = request.getParameter("userName");
        String userPass = request.getParameter("passWord");
//        String validateCode = request.getParameter("validateCode");

        request.setAttribute("userName", userName);
        String toPage = UIConst.VIEWPATH + "/Login_x.jsp"; //登录失败，转发页面
        String msg = "";
        request.setAttribute("msg", msg);
        if (userName == null || userName.isEmpty()) {
            msg = "请填写用户名！!";
            request.setAttribute("msg", msg);
            request.getRequestDispatcher(toPage).forward(request, response);
            return;
        }
        if (userPass == null || userPass.isEmpty()) {
            msg = "请填写密码";
            request.setAttribute("msg", msg);
            request.getRequestDispatcher(toPage).forward(request, response);
            return;
        }
//        if (validateCode == null || validateCode.isEmpty()) {
//            msg = "请填写验证码";
//            request.setAttribute("msg", msg);
//            request.getRequestDispatcher(toPage).forward(request, response);
//            return;
//        }
        //取出会话中的验证码
//        String strCode = (String) session.getAttribute(UIConst.BG_VALLDATE_CODE_KEY);
        //验证码验证
//        if (!validateCode.equals(strCode)) {
//            msg = "验证码不正确";
//            request.setAttribute("msg", msg);
//            request.getRequestDispatcher(toPage).forward(request, response);
//            return;
//        }

        //数据库验证
        User bean = userService.LoadByName(userName);
        if (bean == null) {
            msg = "用户名不存在";
            request.setAttribute("msg", msg);
            request.getRequestDispatcher(toPage).forward(request, response);
            return;
        }
        if (!bean.getPassWord().equals(userPass)) {
            msg = "密码错误";
            request.setAttribute("msg", msg);
            request.getRequestDispatcher(toPage).forward(request, response);
            return;
        }

        //业务处理
        //将用户信息放入会话中，一边进行登录检测时使用
        session.setAttribute(UIConst.BG_LOGINUSER_KEY, bean);

        //登录成功，重定向
        String toURL = application.getContextPath() + UIConst.AREAPATH + "/Main";
        response.sendRedirect(toURL);

    }

    protected void logoutDeal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();   //会话失败
        String toURL = request.getContextPath() + UIConst.AREAPATH + "/Login";
        response.sendRedirect(toURL);
    }
}
