package edu.ui.ctrl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import edu.bean.EmployeeCharts;
import edu.bean.RepairCharts;
import edu.dao.impl.ChartsImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(UIConst.AREAPATH + "/Charts")
public class ChartsServlet extends HttpServlet {
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
            case "getlist":
                getList(request, response);
                break;
            case "getrepaircharts":
                GetRepairCharts(request, response);
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

    private void getList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ChartsImpl charts = new ChartsImpl();
        List<EmployeeCharts> employees = charts.list();
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("data", employees);
        System.out.println("MAP:" + map + "employees:" + employees);
        //将结果转成JSON字符串 返回给前台
        System.out.println("Json" + JSON.toJSONString(map));
        response.getWriter().print(JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat));
    }

    protected void listView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String toPage = UIConst.VIEWPATH + "/Charts.jsp";
        request.getRequestDispatcher(toPage).forward(request, response);
    }

    private void GetRepairCharts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ChartsImpl charts = new ChartsImpl();
        List<RepairCharts> repairCharts = charts.GetRepairCharts();
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("data", repairCharts);
        System.out.println("MAP:" + map + "repairCharts:" + repairCharts);
        //将结果转成JSON字符串 返回给前台
        System.out.println("Json" + JSON.toJSONString(map));
        response.getWriter().print(JSON.toJSONString(map));
    }
}
