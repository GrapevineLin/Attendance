package edu.ui.ctrl;

import com.liuvei.common.PagerItem;
import com.liuvei.common.SysFun;
import edu.bean.Employee;
import edu.bean.PunchCard;
import edu.bean.User;
import edu.service.impl.EmployeeService;
import edu.service.impl.PunchCardService;
import edu.service.impl.impl.EmployeeServiceImpl;
import edu.service.impl.impl.PunchCardServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet(UIConst.AREAPATH + "/PunchCard")
public class PunchCardServlet extends HttpServlet {

    private static PunchCardService punchCardService = new PunchCardServiceImpl();

    private static EmployeeService employeeService = new EmployeeServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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


        String oper = request.getParameter("oper");
        if(oper == null){
            oper = "";
        }else{
            oper = oper.trim();
        }

        //将打卡人的信息放到session中
        User user = (User) session.getAttribute(UIConst.BG_LOGINUSER_KEY);
        String userName = user.getUserName();
        Employee employee = employeeService.loadByName(userName);
        String empCode = employee.getEmpCode();
        session.setAttribute("userName", userName);
        session.setAttribute("empCode", empCode);


        //根据不同的操作类型，调用不同的处理方法
        switch (oper){
            case "list":
                listView(request, response);
                break;
            case "listDeal":
                listDeal(request, response);
                break;
            case "insert":
                insertView(request, response);
                break;
            case "insertDeal":
                insertDeal(request, response);
                break;
            default:
                //列表操作：默认
                System.out.println("oper不存在");
                break;
        }

    }

    protected void listView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<PunchCard> vDataList = null;

        //分页步骤：创建PagerItem对象，处理url传来的pagesize和pageindex
        PagerItem pagerItem = new PagerItem();
        pagerItem.parsePageSize(request.getParameter(pagerItem.getParamPageSize()));
        pagerItem.parsePageNum(request.getParameter(pagerItem.getParamPageNum()));
        //定义记录数变量
        Long rowCount = 0L;
        //根据条件，查找符合条件的所有记录数
        rowCount =punchCardService.count();
        //将记录赋值给pagerItem, 以便进行分页的各类计算
        pagerItem.changeRowCount(rowCount);
        //从数据库取指定分页的数据
        vDataList = punchCardService.pager(pagerItem.getPageNum(), pagerItem.getPageSize());
        //设置页面跳转的url
        pagerItem.changeUrl(SysFun.generalUrl(request.getRequestURI(), request.getQueryString()));
        //将分页对象和数据列表，放到作用域，以便页面可以访问
        request.setAttribute("pagerItem", pagerItem);
        for(int i = 0; i < vDataList.size(); i++){

        }
        request.setAttribute("DataList", vDataList);

        //-------------------------------------------------




        //转发到页面
        String toPage = UIConst.VIEWPATH + "/PunchCard_list.jsp";
        request.getRequestDispatcher(toPage).forward(request, response);
    }

    protected void listDeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求参数
        String searchName = request.getParameter("searchName");
        //回显请求数据
        request.setAttribute("searchName", searchName);

        List<PunchCard> vDataList = null;
        //----------------------------------------------------------
        //分页步骤：创建PagerItem对象，处理url传来的pagesize和pageindex
        PagerItem pagerItem = new PagerItem();
        pagerItem.parsePageSize(request.getParameter(pagerItem.getParamPageSize()));
        pagerItem.parsePageNum(request.getParameter(pagerItem.getParamPageNum()));
        //定义记录数变量
        Long rowCount = 0L;
        if(SysFun.isNullOrEmpty(searchName)){
            //根据条件，查找符合条件的所有记录数
//            System.out.println("dfdfdf");
            rowCount = punchCardService.count();
            //将记录赋值给pagerItem, 以便进行分页的各类计算
            pagerItem.changeRowCount(rowCount);
            //从数据库取指定分页的数据
            vDataList = punchCardService.pager(pagerItem.getPageNum(), pagerItem.getPageSize());
        }else{
            //根据条件，查找符合条件的所有记录数
//            System.out.println("dfdfdfdfdfdf");
            rowCount = punchCardService.countByCode(searchName);
            //将记录赋值给pagerItem, 以便进行分页的各类计算
            pagerItem.changeRowCount(rowCount);
            //根据名字查找(这里是根据empCode进行查找)
            vDataList = punchCardService.pagerByCode(searchName, pagerItem.getPageNum(), pagerItem.getPageSize());
        }

        //设置页面跳转的url
        pagerItem.changeUrl(SysFun.generalUrl(request.getRequestURI(), request.getQueryString()));
        //将分页对象和数据列表，放到作用域，以便页面可以访问
        request.setAttribute("pagerItem", pagerItem);
        request.setAttribute("DataList", vDataList);

        //转发到页面
        String toPage = UIConst.VIEWPATH + "/PunchCard_list.jsp";
        request.getRequestDispatcher(toPage).forward(request, response);
    }

    protected void insertView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String toPage = UIConst.VIEWPATH + "/PunchCard_insert.jsp";
        request.getRequestDispatcher(toPage).forward(request, response);
    }

    protected void insertDeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置请求对象的编码方式    
        request.setCharacterEncoding("utf-8");
        //  设置响应对象的编码方式
        response.setCharacterEncoding("utf-8");
        // 设置响应的内容类型为text/html
        response.setContentType("text/html;charset=utf-8");
        // 从request里获取session对象和application对象
        HttpSession session = request.getSession();
        ServletContext application = request.getServletContext();
        // 调用继承的方法来获取config对象
        ServletConfig config = getServletConfig();
        // 从response对象里获取out对象——response.getWriter()之前，要先设置页面的编码
        PrintWriter out = response.getWriter();

        //获得数据请求
        String empCode = request.getParameter("empCode");
        String empName = request.getParameter("empName");
        String date = request.getParameter("date");
        String remark = request.getParameter("remark");


        //回显到页面
        request.setAttribute("empCode", empCode);
        request.setAttribute("empName", empName);
        request.setAttribute("date", date);
        request.setAttribute("remark", remark);
        //服务器端验证

        String vMsg = "";
        if(SysFun.isNullOrEmpty(empCode)){
            vMsg += "班次编码不能为空";
        }
        //验证失败，则将失败内容转发到页面
        if(!SysFun.isNullOrEmpty(vMsg)){
            request.setAttribute("msg", vMsg);
            insertView(request, response);
            return;
        }
        if(SysFun.isNullOrEmpty(empName)){
            vMsg += "班次名称不能为空";
        }
        if(!SysFun.isNullOrEmpty(vMsg)){
            request.setAttribute("msg", vMsg);
            insertView(request, response);
            return;
        }
        if(SysFun.isNullOrEmpty(date)){
            vMsg += "早上上班时间不能为空";
        }
        if(!SysFun.isNullOrEmpty(vMsg)){
            request.setAttribute("msg", vMsg);
            insertView(request, response);
            return;
        }
//        if(!userPass.equals(userPass2)){
//            vMsg += "两次密码不相同";
//        }
        if(!SysFun.isNullOrEmpty(vMsg)){
            request.setAttribute("msg", vMsg);
            insertView(request, response);
            return;
        }


        //真正处理
        PunchCard bean = new PunchCard();
        bean.setEmpCode(empCode);
        bean.setEmpName(empName);
        String date2 =  date.replace('T', ' ');
        try {
            System.out.println(date);
            bean.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        bean.setRemark(remark);

        Long result = 0L;
        try{
            result = punchCardService.insert(bean);
        }catch (Exception e){
            vMsg = "添加失败" + e.getMessage();
        }

        if(result > 0){

            //添加成功，则父窗口的地址栏重新加载
            out.println("<script>");
            out.println("alert('添加成功')");
            out.println("parent.window.location.reload();");
            out.println("</script>");
        }else{
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
        }

    }
    protected String checkLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        javax.servlet.http.HttpSession session = request.getSession();
        String toURL = null;
        Object obj = session.getAttribute(UIConst.BG_LOGINUSER_KEY);
        if (obj == null) {
            toURL = request.getContextPath() + UIConst.AREAPATH + "/Login";
        }
        return toURL;
    }
}
