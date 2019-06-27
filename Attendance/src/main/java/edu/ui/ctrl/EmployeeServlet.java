package edu.ui.ctrl;

import com.liuvei.common.PagerItem;
import com.liuvei.common.SysFun;
import edu.bean.Employee;
import edu.bean.Station;
import edu.service.impl.EmployeeService;
import edu.service.impl.StationService;
import edu.service.impl.impl.EmployeeServiceImpl;
import edu.service.impl.impl.StationServiceImpl;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(UIConst.AREAPATH + "/Employee")
public class EmployeeServlet extends HttpServlet {
    EmployeeService employeeService =new EmployeeServiceImpl();

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
        String toURL = checkLogin(request, response);
        if (toURL != null) {
            response.sendRedirect(toURL);
            return;
        }
        //取得操作类型
        String oper = request.getParameter("oper");
        if (oper == null) {
            oper = "";
        } else {
            oper = oper.trim().toLowerCase();
        }
        //根据不同的操作类型, 调用不同的处理方法
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


    protected void deleteDeal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 从response对象里获取out对象——response.getWriter()之前，要先设置页面的编码
        java.io.PrintWriter out = response.getWriter();

        // 取得主键，再根据主键，获取记录
        String vId = request.getParameter("empId");
        if (!SysFun.isNullOrEmpty(vId)) {
            Long iId = SysFun.parseLong(vId);


            Long result = 0L;
            result = employeeService.delete(iId);
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
        String empName = request.getParameter("empName");
        String sex = request.getParameter("sex");
        String age = request.getParameter("age");
        String nation = request.getParameter("nation");
        String IDC = request.getParameter("IDC");
        String money = request.getParameter("money");
        String tel = request.getParameter("tel");
        String ecp =request.getParameter("ecp");
        String jobId = request.getParameter("jobId");
        String des = request.getParameter("des");

        //为了在输入页面回显原来的旧值，将旧值放到作用域，页面中进行获取
        request.setAttribute("empCode",empCode);
        request.setAttribute("empName", empName);
        request.setAttribute("sex", sex);
        request.setAttribute("age", age);
        request.setAttribute("nation", nation);
        request.setAttribute("IDC", IDC);
        request.setAttribute("money", money);
        request.setAttribute("tel", tel);
        request.setAttribute("ecp",ecp);
        request.setAttribute("jobId", jobId);
        request.setAttribute("des", des);

        //服务端验证
        String vMsg = "";
        if (SysFun.isNullOrEmpty(empCode)) {
            vMsg += "员工编码不能为空";
        }
        // 如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }
        if (SysFun.isNullOrEmpty(empName)) {
            vMsg += "员工姓名不能为空";
        }
        // 如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }
        if (SysFun.isNullOrEmpty(age)) {
            vMsg += "员工年龄不能为空";
        }
        // 如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }
        if (SysFun.isNullOrEmpty(nation)) {
            vMsg += "员工民族不能为空";
        }
        // 如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }
        if (SysFun.isNullOrEmpty(IDC)) {
            vMsg += "身份证不能为空";
        }
        // 如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }
        if (SysFun.isNullOrEmpty(tel)) {
            vMsg += "联系电话不能为空";
        }
        // 如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }
        if (SysFun.isNullOrEmpty(ecp)) {
            vMsg += "紧急联系人不能为空";
        }
        // 如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }


        Employee bean = new Employee();
        bean.setEmpCode(empCode);
        bean.setEmpName(empName);

        bean.setSex(sex);
        bean.setAge(SysFun.parseLong(age));

        bean.setNation(nation);
        bean.setIDC(IDC);

        bean.setMoney(SysFun.parseLong(money));
        bean.setTel(tel);

        bean.setEcp(ecp);
        bean.setJobId(SysFun.parseLong(jobId));

        bean.setDes(des);


        Long result = 0L;
        try {
            result = employeeService.insert(bean);
        } catch (Exception e) {
            vMsg = "添加失败." + e.getMessage();
            // TODO: handle exception
        }

        if (result > 0) {
            System.out.println("添加成功");
            out.println("<script>");
            out.println("parent.window.location.reload();");
            out.println("</script>");
        } else {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
        }

    }

    protected void insertView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StationService stationService = new StationServiceImpl();
        List<Station> sList = stationService.list();
        request.setAttribute("sList",sList);

        //转发到页面
        String toPage = UIConst.VIEWPATH + "/Employee_insert.jsp";
        request.getRequestDispatcher(toPage).forward(request, response);


    }
    protected void listDeal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取请求参数
        String searchName = request.getParameter("searchName");
        // 回显请求数据
        request.setAttribute("searchName", searchName);
        List<Employee> vDataList = null;
        // ------------------------------------------------------------------------
        // 分页步骤1. 创建PagerIter对象, 处理url传过来的pagesize和pageindex
        PagerItem pagerItem = new PagerItem();

        pagerItem.parsePageSize(request.getParameter(pagerItem.getParamPageSize())); pagerItem.parsePageNum(request.getParameter(pagerItem.getParamPageNum()));
        // 分页步骤2.1. 定义记录数变量
        Long rowCount = 0L;
        if (SysFun.isNullOrEmpty(searchName)) {
            // 分页步骤2.2. 根据条件，查找符合条件的所有记录数 ***** count()要根据实际换成其它方法
            rowCount = employeeService.count();
            // 分页步骤2.3. 将记录数赋给pagerItem，以便进行分页的各类计算
            pagerItem.changeRowCount(rowCount);
            // 分页步骤2.4. 从数据库取指定分页的数据 ***** pager()要根据实际换成其它方法
            vDataList = employeeService.pager(pagerItem.getPageNum(), pagerItem.getPageSize());
        }
        else {
            // 分页步骤2.2. 根据条件，查找符合条件的所有记录数 ***** count()要根据实际换成其它方法
            rowCount = employeeService.countByName(searchName);
            // 分页步骤2.3. 将记录数赋给pagerItem，以便进行分页的各类计算
            pagerItem.changeRowCount(rowCount);
            // 分页步骤2.4. 从数据库取指定分页的数据 ***** pager()要根据实际换成其它方法
            vDataList = employeeService.pagerByName(searchName, pagerItem.getPageNum(), pagerItem.getPageSize());
        }
        // 分页步骤2.5. 设置页面的跳转url
        pagerItem.changeUrl(SysFun.generalUrl(request.getRequestURI(), request.getQueryString()));
        // 分页步骤3. 将分页对象和数据列表,放到作用域,以便页面可以访问
        request.setAttribute("pagerItem", pagerItem);
        request.setAttribute("DataList", vDataList);
        // ------------------------------------------------------------------------
        // 转发到页面
        String toPage = UIConst.VIEWPATH + "/Employee_list.jsp";
        request.getRequestDispatcher(toPage).forward(request, response);


    }

    protected void listView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Employee> vDataList = null;
// ------------------------------------------------------------------------
// 分页步骤1. 创建PagerIter对象, 处理url传过来的pagesize和pageindex
        PagerItem pagerItem = new PagerItem();
        pagerItem.parsePageSize(request.getParameter(pagerItem.getParamPageSize()));
        pagerItem.parsePageNum(request.getParameter(pagerItem.getParamPageNum()));
// 分页步骤2.1. 定义记录数变量
        Long rowCount = 0L;
// 分页步骤2.2. 根据条件，查找符合条件的所有记录数 ***** count()要根据实际换成其它方法
        rowCount = employeeService.count();// 分页步骤2.3. 将记录数赋给pagerItem，以便进行分页的各类计算
        pagerItem.changeRowCount(rowCount);
// 分页步骤2.4. 从数据库取指定分页的数据 ***** pager()要根据实际换成其它方法
        vDataList = employeeService.pager(pagerItem.getPageNum(),
                pagerItem.getPageSize());
// 分页步骤2.5. 设置页面的跳转url
        pagerItem.changeUrl(SysFun.generalUrl(request.getRequestURI(),
                request.getQueryString()));
// 分页步骤3. 将分页对象和数据列表,放到作用域,以便页面可以访问
        request.setAttribute("pagerItem", pagerItem);
        request.setAttribute("DataList", vDataList);
// ------------------------------------------------------------------------
// 转发到页面
        String toPage = UIConst.VIEWPATH + "/Employee_list.jsp";
        request.getRequestDispatcher(toPage).forward(request, response);


    }
    protected void updateDeal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 从response对象里获取out对象——response.getWriter()之前，要先设置页面的编码
        java.io.PrintWriter out = response.getWriter();

        // (0) 获取请求数据
        String vId = request.getParameter("empId");
        String empCode = request.getParameter("empCode");
        String empName = request.getParameter("empName");
        String sex = request.getParameter("sex");
        String age = request.getParameter("age");
        String nation = request.getParameter("nation");
        String IDC = request.getParameter("IDC");
        String money = request.getParameter("money");
        String tel = request.getParameter("tel");
        String ecp =request.getParameter("ecp");
        String jobId = request.getParameter("jobId");
        String des = request.getParameter("des");

        // 为了在输入页面回显原来的旧值,需要将旧值放到作用域,页面中进行获取
        request.setAttribute("empId",vId);
        request.setAttribute("empCode",empCode);
        request.setAttribute("empName", empName);
        request.setAttribute("sex", sex);
        request.setAttribute("age", age);
        request.setAttribute("nation", nation);
        request.setAttribute("IDC", IDC);
        request.setAttribute("money", money);
        request.setAttribute("tel", tel);
        request.setAttribute("ecp",ecp);
        request.setAttribute("jobId", jobId);
        request.setAttribute("des", des);

        // (1) 服务端验证
        String vMsg = "";
        if (SysFun.isNullOrEmpty(vId)) {
            vMsg += "Id不能为空";
        }
        // 如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            updateView(request, response);
        }

        if (SysFun.isNullOrEmpty(empCode)) {
            vMsg += "员工编码不能为空";
        }
        // 如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }
        if (SysFun.isNullOrEmpty(empName)) {
            vMsg += "员工姓名不能为空";
        }
        // 如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }
        if (SysFun.isNullOrEmpty(age)) {
            vMsg += "员工年龄不能为空";
        }
        // 如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }
        if (SysFun.isNullOrEmpty(nation)) {
            vMsg += "员工民族不能为空";
        }
        // 如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }
        if (SysFun.isNullOrEmpty(IDC)) {
            vMsg += "身份证不能为空";
        }
        // 如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }
        if (SysFun.isNullOrEmpty(tel)) {
            vMsg += "联系电话不能为空";
        }
        // 如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }
        if (SysFun.isNullOrEmpty(ecp)) {
            vMsg += "紧急联系人不能为空";
        }
        // 如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }

        Long iId = SysFun.parseLong(vId);
        Employee bean = employeeService.load(iId);

        if (bean == null) {
            vMsg = "数据不存在";
        }
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            updateView(request, response);
            return;
        }

        // (3) 真正处理
        //bean.setEmpId(iId);
        bean.setEmpCode(empCode);
        bean.setEmpName(empName);
        bean.setSex(sex);
        bean.setAge(SysFun.parseLong(age));
        bean.setNation(nation);
        bean.setIDC(IDC);
        bean.setMoney(SysFun.parseLong(money));
        bean.setTel(tel);
        bean.setEcp(ecp);
        bean.setJobId(SysFun.parseLong(jobId));
        bean.setDes(des);

        Long result = 0L;
        try {
            result = employeeService.update(bean);
        } catch (Exception e) {
            vMsg = "修改失败." + e.getMessage();
            // TODO: handle exception
        }
        if (result > 0) {
             System.out.println("修改成功");             // listView(request, response);
            // 如果修改成功，则父窗口页面的地址栏重新加载
            out.println("<script>");
            out.println("parent.window.location.reload();");
            out.println("</script>");
        } else {
            request.setAttribute("msg", vMsg);
            System.out.println("修改失败");
            updateView(request, response);
        }

    }

    protected void updateView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StationService stationService = new StationServiceImpl();
        List<Station> sList = stationService.list();
        request.setAttribute("sList",sList);

        // 从response对象里获取out对象——response.getWriter()之前，要先设置页面的编码
        java.io.PrintWriter out = response.getWriter();

        // 取得主键，再根据主键，获取记录
        String vId = request.getParameter("empId");
        if (!SysFun.isNullOrEmpty(vId)) {
            Long iId = SysFun.parseLong(vId);
            Employee bean = employeeService.load(iId);

            if (bean != null) {
                // 使用对象来回显
                // request.setAttribute("bean", bean);
                // 为了在输入页面回显原来的旧值,需要将旧值放到作用域,页面中进行获取
                request.setAttribute("empId",bean.getEmpId());
                request.setAttribute("empCode",bean.getEmpCode());
                request.setAttribute("empName", bean.getEmpName());
                request.setAttribute("sex", bean.getSex());
                request.setAttribute("age", bean.getAge());
                request.setAttribute("nation", bean.getNation());
                request.setAttribute("IDC", bean.getIDC());
                request.setAttribute("money", bean.getMoney());
                request.setAttribute("tel", bean.getTel());
                request.setAttribute("ecp",bean.getEcp());
                request.setAttribute("jobId", bean.getJobId());
                request.setAttribute("des", bean.getDes());

                String toPage = UIConst.VIEWPATH +
                        "/Employee_update.jsp";
                request.getRequestDispatcher(toPage).forward(request, response);
                return;
            }
        }
        out.println("<script>");
        out.println("alert('数据不存在123');");
        out.println("parent.window.location.reload();");  	 	out.println("</script>");


    }

    protected String checkLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String toURL = null;
        Object obj = session.getAttribute(UIConst.BG_LOGINUSER_KEY);
        if (obj == null) {
            toURL = request.getContextPath() + UIConst.AREAPATH + "/Login";
        }
        return toURL;
    }
}
