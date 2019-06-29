package edu.ui.ctrl;

import com.alibaba.fastjson.JSON;
import com.liuvei.common.PagerItem;
import com.liuvei.common.SysFun;
import edu.bean.PaySalary;
import edu.service.impl.PaySalaryService;
import edu.service.impl.impl.PaySalaryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(UIConst.AREAPATH + "/Pay")
public class PayServlet extends HttpServlet {
    private PaySalaryService paysalaryService = new PaySalaryServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        javax.servlet.http.HttpSession session = request.getSession();
        javax.servlet.ServletContext application = request.getServletContext();
        javax.servlet.ServletConfig config = getServletConfig();
        java.io.PrintWriter out = response.getWriter();
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
            case "listdeal":
                listDeal(request, response);
                break;
            case "delete":
                delete(request, response);
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
                System.out.println("oper不存在" + oper);
                break;
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String payId = request.getParameter("payId");
        //从数据库获取封装好的模型
        Long result = paysalaryService.delete(Long.parseLong(payId));
        //返回结果给前台或者请求转发
        //进行判断 如果结果大于0 表示删除成功 否则就是删除失败
        Map<String, Object> rs = new HashMap<>();
        if (result > 0) {
            rs.put("code", 200);
            rs.put("msg", "success");
            response.getWriter().print(JSON.toJSONString(rs));
        } else {
            rs.put("code", 500);
            rs.put("msg", "success");
            response.getWriter().print(JSON.toJSONString(rs));
        }
    }

    private void listDeal(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        //获取请求参数
        String searchName = request.getParameter("searchName");
        //回显请求数据
        request.setAttribute("searchName", searchName);

        List<PaySalary> vDataList = null;
        // ------------------------------------------------------------------------
        // 分页步骤1. 创建PagerIter对象, 处理url传过来的pagesize和pageindex
        PagerItem pagerItem = new PagerItem();
        pagerItem.parsePageSize(request.getParameter(pagerItem.getParamPageSize()));
        pagerItem.parsePageNum(request.getParameter(pagerItem.getParamPageNum()));
        // 分页步骤2.1. 定义记录数变量
        Long rowCount = 0L;
        if (SysFun.isNullOrEmpty(searchName)) {
            // 分页步骤2.2. 根据条件，查找符合条件的所有记录数 ***** count()要根据实际换成其它方法
            rowCount = paysalaryService.count();
            // 分页步骤2.3. 将记录数赋给pagerItem，以便进行分页的各类计算
            pagerItem.changeRowCount(rowCount);
            // 分页步骤2.4. 从数据库取指定分页的数据 ***** pager()要根据实际换成其它方法
            vDataList = paysalaryService.pager(pagerItem.getPageNum(),
                    pagerItem.getPageSize());
        } else {
            // 分页步骤2.2. 根据条件，查找符合条件的所有记录数 ***** count()要根据实际换成其它方法
            rowCount = paysalaryService.countByName(searchName);
            // 分页步骤2.3. 将记录数赋给pagerItem，以便进行分页的各类计算
            pagerItem.changeRowCount(rowCount);
            // 分页步骤2.4. 从数据库取指定分页的数据 ***** pager()要根据实际换成其它方法
            vDataList = paysalaryService.pagerByName(searchName,
                    pagerItem.getPageNum(), pagerItem.getPageSize());
        } //分页步骤2 .5.设置页面的跳转url
        pagerItem.changeUrl(SysFun.generalUrl(request.getRequestURI(),
                request.getQueryString()));
        Map<String,Object> rs = new HashMap<>();
        rs.put("code", 0);
        rs.put("count", pagerItem.getPageSize());//返回的数据有多少条  --> 分页
        rs.put("msg", "success");
        rs.put("data", vDataList);
        //按照要求完成返回数据之后  就可以转换成JSON字符串
        response.getWriter().print(JSON.toJSONString(rs));
    }

    protected void listView(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        //// ------------------------------------------------------------------------
        //// 转发到页面
        String toPage = UIConst.VIEWPATH + "/PaySalary_list_new.jsp";
        request.getRequestDispatcher(toPage).forward(request, response);
    }

    protected String checkLogin(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        javax.servlet.http.HttpSession session = request.getSession();
        String toURL = null;
        Object obj = session.getAttribute(UIConst.BG_LOGINUSER_KEY);
        if (obj == null) {
            toURL = request.getContextPath() + UIConst.AREAPATH + "/Login";
        }
        return toURL;
    }
}