package edu.ui.ctrl;

import com.alibaba.fastjson.JSON;
import com.liuvei.common.PagerItem;
import com.liuvei.common.SysFun;
import edu.bean.Employee;
import edu.bean.Leave;
import edu.service.impl.EmployeeService;
import edu.service.impl.LeaveService;
import edu.service.impl.impl.EmployeeServiceImpl;
import edu.service.impl.impl.LeaveServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            case "listdeal":
                listDeal(request, response); // 列表处理
                break;
            case "insert":
                insertView(request, response); // 添加页面
                break;
            case "insertdeal":
                insertDeal(request, response); // 添加处理
                break;
            case "update":
                updateView(request, response); // 修改页面
                break;
            case "updatedeal":
                updateDeal(request, response); // 修改处理
                break;
            case "deletedeal":
                deleteDeal(request, response); // 删除处理
                break;
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
    protected void listDeal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取请求参数
        String searchName = request.getParameter("searchName");
        // 回显请求数据
        request.setAttribute("searchName", searchName);
        List<Leave> vDataList = null;
        // ------------------------------------------------------------------------
        // 分页步骤1. 创建PagerIter对象, 处理url传过来的pagesize和pageindex
        PagerItem pagerItem = new PagerItem();

        pagerItem.parsePageSize(request.getParameter(pagerItem.getParamPageSize())); pagerItem.parsePageNum(request.getParameter(pagerItem.getParamPageNum()));
        // 分页步骤2.1. 定义记录数变量
        Long rowCount = 0L;
        if (SysFun.isNullOrEmpty(searchName)) {
            // 分页步骤2.2. 根据条件，查找符合条件的所有记录数 ***** count()要根据实际换成其它方法
            rowCount = leaveService.count();
            // 分页步骤2.3. 将记录数赋给pagerItem，以便进行分页的各类计算
            pagerItem.changeRowCount(rowCount);
            // 分页步骤2.4. 从数据库取指定分页的数据 ***** pager()要根据实际换成其它方法
            vDataList = leaveService.pager(pagerItem.getPageNum(), pagerItem.getPageSize());
        }
        else {
            // 分页步骤2.2. 根据条件，查找符合条件的所有记录数 ***** count()要根据实际换成其它方法
            rowCount = leaveService.countByName(searchName);
            // 分页步骤2.3. 将记录数赋给pagerItem，以便进行分页的各类计算
            pagerItem.changeRowCount(rowCount);
            // 分页步骤2.4. 从数据库取指定分页的数据 ***** pager()要根据实际换成其它方法
            vDataList = leaveService.pagerByName(searchName, pagerItem.getPageNum(), pagerItem.getPageSize());
        }
        // 分页步骤2.5. 设置页面的跳转url
        pagerItem.changeUrl(SysFun.generalUrl(request.getRequestURI(), request.getQueryString()));
        // 分页步骤3. 将分页对象和数据列表,放到作用域,以便页面可以访问
        request.setAttribute("pagerItem", pagerItem);
        request.setAttribute("leaveList", vDataList);
        // ------------------------------------------------------------------------
        // 转发到页面
        String toPage = UIConst.VIEWPATH + "/Leave_list.jsp";
        request.getRequestDispatcher(toPage).forward(request, response);


    }
    protected void updateDeal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 从response对象里获取out对象——response.getWriter()之前，要先设置页面的编码
        java.io.PrintWriter out = response.getWriter();

        // (0) 获取请求数据
        String vId = request.getParameter("leaveId");
        String empCode = request.getParameter("empCode");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String reason = request.getParameter("reason");

        // 为了在输入页面回显原来的旧值,需要将旧值放到作用域,页面中进行获取
        request.setAttribute("leaveId",vId);
        request.setAttribute("empCode",empCode);
        request.setAttribute("beginDate", beginDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("reason", reason);



        Long iId = SysFun.parseLong(vId);
        Leave bean = leaveService.load(iId);


        // (3) 真正处理
        bean.setLeaveId(iId);
        bean.setEmpCode(empCode);
        bean.setBeginDate(Timestamp.valueOf(beginDate));
        bean.setEndDate(Timestamp.valueOf(endDate));
        bean.setReason(reason);
        Long result = 0L;

        Map<String,Object> map = new HashMap<>();

        result = leaveService.update(bean);
        if (result > 0) {
            map.put("code",200);
            map.put("msg","success");
            response.getWriter().print(JSON.toJSONString(map));
        } else {
            map.put("code", 500);
            map.put("msg", "fail");
            response.getWriter().print(JSON.toJSONString(map));
        }

    }
    protected void updateView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EmployeeService employeeService = new EmployeeServiceImpl();
        List<Employee>empList = employeeService.list();
        request.setAttribute("empList",empList);


        // 从response对象里获取out对象——response.getWriter()之前，要先设置页面的编码
        java.io.PrintWriter out = response.getWriter();

        // 取得主键，再根据主键，获取记录
        String vId = request.getParameter("leaveId");
        if (!SysFun.isNullOrEmpty(vId)) {
            Long iId = SysFun.parseLong(vId);
            Leave bean = leaveService.load(iId);

            if (bean != null) {
                // 使用对象来回显
                // request.setAttribute("bean", bean);
                // 为了在输入页面回显原来的旧值,需要将旧值放到作用域,页面中进行获取
                request.setAttribute("leaveId",bean.getLeaveId());
                request.setAttribute("empCode",bean.getEmpCode());
                request.setAttribute("beginDate", bean.getBeginDate());
                request.setAttribute("endDate", bean.getEndDate());
                request.setAttribute("reason", bean.getReason());
                String toPage = UIConst.VIEWPATH +
                        "/Leave_update.jsp";
                request.getRequestDispatcher(toPage).forward(request, response);
                return;
            }
        }
        out.println("<script>");
        out.println("alert('数据不存在123');");
        out.println("parent.window.location.reload();");  	 	out.println("</script>");


    }
    protected void deleteDeal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 从response对象里获取out对象——response.getWriter()之前，要先设置页面的编码
        java.io.PrintWriter out = response.getWriter();

        // 取得主键，再根据主键，获取记录
        String vId = request.getParameter("leaveId");
        if (!SysFun.isNullOrEmpty(vId)) {
            Long iId = SysFun.parseLong(vId);


            Long result = 0L;
            result = leaveService.delete(iId);
            if (result > 0) {
                out.print("ok"); // 不要使用println()
                return;
            }
        }
        out.println("nook");
    }
    protected void insertDeal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        java.io.PrintWriter out = response.getWriter();
        // 获取请求数据
        String empCode = request.getParameter("empCode");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String reason = request.getParameter("reason");

        //为了在输入页面回显原来的旧值，将旧值放到作用域，页面中进行获取
        request.setAttribute("empCode",empCode);
        request.setAttribute("beginDate", beginDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("reason", reason);



        Leave bean = new Leave();
        bean.setEmpCode(empCode);
        bean.setBeginDate(Timestamp.valueOf(beginDate));
        bean.setEndDate(Timestamp.valueOf(endDate));
        bean.setReason(reason);

        Map<String,Object> map = new HashMap<>();
        Long result = 0L;
        result = leaveService.insert(bean);
        if (result > 0) {
            map.put("code",200);
            map.put("msg","success");
            response.getWriter().print(JSON.toJSONString(map));
        } else {
            map.put("code", 500);
            map.put("msg", "fail");
            response.getWriter().print(JSON.toJSONString(map));
        }

    }
    protected void insertView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EmployeeService employeeService = new EmployeeServiceImpl();
        List<Employee>empList = employeeService.list();
        request.setAttribute("empList",empList);

        //转发到页面
        String toPage = UIConst.VIEWPATH + "/Leave_insert.jsp";
        request.getRequestDispatcher(toPage).forward(request, response);


    }




}
