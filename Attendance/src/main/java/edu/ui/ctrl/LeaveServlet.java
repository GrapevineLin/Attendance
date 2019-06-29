package edu.ui.ctrl;

import com.liuvei.common.PagerItem;
import com.liuvei.common.SysFun;
import edu.bean.Leave;
import edu.service.impl.LeaveService;
import edu.service.impl.impl.LeaveServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(UIConst.AREAPATH + "/Leave")
public class LeaveServlet extends HttpServlet {
    private LeaveService leaveService = new LeaveServiceImpl();

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
        javax.servlet.http.HttpSession session = request.getSession();
        javax.servlet.ServletContext application = request.getServletContext();
        // 调用继承的方法来获取config对象
        javax.servlet.ServletConfig config = getServletConfig();
        // 从response对象里获取out对象——response.getWriter()之前，要先设置页面的编码
        java.io.PrintWriter out = response.getWriter();
        /* ----------------------------------------------------------------- */

        // 检测是否登录
        String toURL = checkLogin(request, response);
        if (toURL != null) {
            response.sendRedirect(toURL);
            return;
        }

        //获取操作参数
        String oper = request.getParameter("oper");
        System.out.println("oper=" + oper);
        if (oper == null) {
            oper = "";
        } else {
            oper = oper.trim().toLowerCase();
        }

        // 根据不同的操作参数，调用不同的方法
        switch (oper) {
            case "list":
                listView(request, response); // 列表页面
                break;
            //case "listdeal":
            //    listDeal(request, response); // 列表处理
            //    break;
            //case "insert":
            //    insertView(request, response); // 添加页面
            //    break;
            //case "insertdeal":
            //    insertDeal(request, response); // 添加处理
            //    break;
            //case "update":
            //    updateView(request, response); // 修改页面
            //    break;
            //case "updatedeal":
            //    updateDeal(request, response); // 修改处理
            //    break;
            //case "detail":
            //    detailView(request, response); // 查看页面
            //    break;
            //case "deletedeal":
            //    deleteDeal(request, response); // 删除处理
            //    break;
            default:
                // listView(request, response); // 列表页面 : 默认
                System.out.println("oper不存在。");
                break;
        }
    }

    protected String checkLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        javax.servlet.http.HttpSession session = request.getSession();
        String toURL = null;
        Object obj = session.getAttribute(UIConst.BG_LOGINUSER_KEY);
        if (obj == null) {
            toURL = request.getContextPath() + UIConst.AREAPATH + "/Login";
        }
        return toURL;
    }

    protected void listView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Leave> leaveList = null;

        // ------------------------------------------------------------------------
        // 分页步骤1. 创建PagerIter对象, 处理url传过来的pagesize和pageindex
        PagerItem pagerItem = new PagerItem();
        pagerItem.parsePageSize(request.getParameter(pagerItem.getParamPageSize()));
        pagerItem.parsePageNum(request.getParameter(pagerItem.getParamPageNum()));
        // 分页步骤2.1. 定义记录数变量
        Long rowCount = 0L;
        // 分页步骤2.2. 根据条件，查找符合条件的所有记录数 ***** count()要根据实际换成其它方法
        rowCount = leaveService.count();// 分页步骤2.3. 将记录数赋给pagerItem，以便进行分页的各类计算
        pagerItem.changeRowCount(rowCount);
        // 分页步骤2.4. 从数据库取指定分页的数据 ***** pager()要根据实际换成其它方法
        leaveList = leaveService.pager(pagerItem.getPageNum(),
                pagerItem.getPageSize());
        // 分页步骤2.5. 设置页面的跳转url
        pagerItem.changeUrl(SysFun.generalUrl(request.getRequestURI(),
                request.getQueryString()));
        // 分页步骤3. 将分页对象和数据列表,放到作用域,以便页面可以访问
        request.setAttribute("pagerItem", pagerItem);
        request.setAttribute("leaveList", leaveList);
        // ------------------------------------------------------------------------
        // 转发到页面
        String toPage = UIConst.VIEWPATH + "/Leave_list.jsp";
        request.getRequestDispatcher(toPage).forward(request, response);
    }

}
