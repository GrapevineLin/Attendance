package edu.ui.ctrl;

import com.alibaba.fastjson.JSON;
import com.liuvei.common.SysFun;
import edu.bean.Employee;
import edu.bean.RepairCard;
import edu.service.impl.EmployeeService;
import edu.service.impl.RepairCardService;
import edu.service.impl.impl.EmployeeServiceImpl;
import edu.service.impl.impl.RepairCardServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(UIConst.AREAPATH + "/RepairCard")
public class RepairServlet extends HttpServlet {

    private RepairCardService repairCardService = new RepairCardServiceImpl();

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
            case "getlist":
                getList(request, response);
                break;
            case "delete":
                delete(request, response);
                break;
            case "add":
                addView(request, response);
                break;
            case "adddeal":
                addDeal(request, response);
                break;
            //case "insert":
            //    insertView(request, response); // 添加页面
            //    break;
            //case "insertdeal":
            //    insertDeal(request, response); // 添加处理
            //    break;
            case "update":
                updateView(request, response); // 修改页面
                break;
            case "updatedeal":
                updateDeal(request, response); // 修改处理
                break;
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

    private void addDeal(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String empCode = request.getParameter("empCode");
        String date = request.getParameter("date");
        String reason = request.getParameter("reason");
        System.out.println("date:" + date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");//设置时间格式
        Date ts = null;//创建Date对象
        try {
            ts = sdf.parse(date);//转换为Date类型 这里要抛一个异常
        } catch (ParseException e) {
            e.printStackTrace();
        }

        RepairCard repairCard = new RepairCard();
        repairCard.setEmpCode(empCode);
        assert ts != null;
        repairCard.setDate(new java.sql.Date(ts.getTime()));
        repairCard.setReason(reason);
        Map<String, Object> map = new HashMap<>();
        Long result = 0L;
        result = repairCardService.insert(repairCard);
        if (result > 0L) {
            map.put("code", 200);
            map.put("msg", "success");
            response.getWriter().print(JSON.toJSONString(map));
        } else {
            map.put("code", 500);
            map.put("msg", "fail");
            response.getWriter().print(JSON.toJSONString(map));
        }


    }

    private void addView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmployeeService employeeService = new EmployeeServiceImpl();
        List<Employee> employeeList = employeeService.list();
        request.setAttribute("employeeList", employeeList);

        String toPage = UIConst.VIEWPATH + "/RepairCard_add.jsp";
        request.getRequestDispatcher(toPage).forward(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String repairId = request.getParameter("repairIdList");
        System.out.println("repairId:" + request.getParameter("repairIdList"));
        //从数据库获取封装好的模型
        Long result = repairCardService.delete(Long.parseLong(repairId));
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

    private void getList(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        Long page = Long.parseLong(request.getParameter("page"));
        Long limit = Long.parseLong(request.getParameter("limit"));
        //List<RepairCard> repairCards = repairCardService.list();
        List<RepairCard> repairCards = repairCardService.pager(page, limit);
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", repairCardService.count());
        map.put("data", repairCards);
        //将结果转成JSON字符串 返回给前台
        System.out.println("Json" + JSON.toJSONString(map));
        response.getWriter().print(JSON.toJSONString(map));
    }

    protected void listView(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        //List<RepairCard> vDataList = null;
        //
        //// ------------------------------------------------------------------------
        //// 分页步骤1. 创建PagerIter对象, 处理url传过来的pagesize和pageindex
        //PagerItem pagerItem = new PagerItem();
        //pagerItem.parsePageSize(request.getParameter(pagerItem.getParamPageSize()));
        //pagerItem.parsePageNum(request.getParameter(pagerItem.getParamPageNum()));
        //// 分页步骤2.1. 定义记录数变量
        //Long rowCount = 0L;
        //// 分页步骤2.2. 根据条件，查找符合条件的所有记录数 ***** count()要根据实际换成其它方法
        //rowCount = repairCardService.count();// 分页步骤2.3. 将记录数赋给pagerItem，以便进行分页的各类计算
        //pagerItem.changeRowCount(rowCount);
        //// 分页步骤2.4. 从数据库取指定分页的数据 ***** pager()要根据实际换成其它方法
        //vDataList = repairCardService.pager(pagerItem.getPageNum(),
        //        pagerItem.getPageSize());
        //// 分页步骤2.5. 设置页面的跳转url
        //pagerItem.changeUrl(SysFun.generalUrl(request.getRequestURI(),
        //        request.getQueryString()));
        //// 分页步骤3. 将分页对象和数据列表,放到作用域,以便页面可以访问
        //request.setAttribute("pagerItem", pagerItem);
        //request.setAttribute("DataList", vDataList);
        //// ------------------------------------------------------------------------
        //// 转发到页面
        String toPage = UIConst.VIEWPATH + "/RepairCard_list_layui.jsp";
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

    protected void updateView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<RepairCard> repairCards = repairCardService.list();
        request.setAttribute("repairCards", repairCards);

        EmployeeService employeeService = new EmployeeServiceImpl();
        List<Employee> employeeList = employeeService.list();
        request.setAttribute("employeeList", employeeList);

        // 从response对象里获取out对象——response.getWriter()之前，要先设置页面的编码
        java.io.PrintWriter out = response.getWriter();
        // 取得主键，再根据主键，获取记录
        String vId = request.getParameter("repairId");
        System.out.println("repairId:" + vId);
        if (!SysFun.isNullOrEmpty(vId)) {
            Long iId = SysFun.parseLong(vId);
            RepairCard bean = repairCardService.load(iId);
            if (bean != null) {
                // 使用对象来回显
                // request.setAttribute("bean", bean);
                // 为了在输入页面回显原来的旧值,需要将旧值放到作用域,页面中进行获取
                request.setAttribute("repairId", bean.getRepairId());
                request.setAttribute("empCode", bean.getEmpCode());
                request.setAttribute("date", bean.getDate());
                request.setAttribute("reason", bean.getReason());
                String toPage = UIConst.VIEWPATH +
                        "/RepairCard_edit.jsp";
                request.getRequestDispatcher(toPage).forward(request,
                        response);
                return;
            }
        }
        out.println("<script>");
        out.println("alert('数据不存在');");
        out.println("parent.window.location.reload();");
        out.println("</script>");
    }

    protected void updateDeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        java.io.PrintWriter out = response.getWriter();
        // (0) 获取请求数据
        String repairId = request.getParameter("repairId");
        String empCode = request.getParameter("empCode");
        String date = request.getParameter("date");
        String reason = request.getParameter("reason");
        // (1) 服务端验证
        //String vMsg = "";
        //if (SysFun.isNullOrEmpty(repairId)) {
        //    vMsg += "Id不能为空";
        //} //如果验证失败, 则将失败内容放到作用域变量, 并转发到页面
        //if (!SysFun.isNullOrEmpty(vMsg)) {
        //    request.setAttribute("msg", vMsg);
        //    System.out.println(vMsg);
        //    insertView(request, response);
        //    return;
        //}
        //if (SysFun.isNullOrEmpty(jobCode)) {
        //    vMsg += "部门编码不能为空";
        //} //如果验证失败,则将失败内容放到作用域变量,并转发到页面
        //if (!SysFun.isNullOrEmpty(vMsg)) {
        //    request.setAttribute("msg", vMsg);
        //    System.out.println(vMsg);
        //    insertView(request, response);
        //    return;
        //}
        //if (SysFun.isNullOrEmpty(depName)) {
        //    vMsg += "岗位名称不能为空";
        //} //如果验证失败,则将失败内容放到作用域变量,并转发到页面
        //if (!SysFun.isNullOrEmpty(vMsg)) {
        //    request.setAttribute("msg", vMsg);
        //    System.out.println(vMsg);
        //    insertView(request, response);
        //    return;
        //}
        // (2) 数据库验证
        Long repairId_L = SysFun.parseLong(repairId);
        RepairCard bean = repairCardService.load(repairId_L);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");//设置时间格式
        Date ts = null;//创建Date对象
        try {
            ts = sdf.parse(date);//转换为Date类型 这里要抛一个异常
        } catch (ParseException e) {
            e.printStackTrace();
        }

        bean.setRepairId(repairId_L);
        bean.setEmpCode(empCode);
        bean.setDate(new java.sql.Date(ts.getTime()));
        bean.setReason(reason);

        Long result = 0L;
        result = repairCardService.update(bean);
        System.out.println("result:" + result);
        Map<String, Object> rsMap = new HashMap<>();
        if (result > 0) {
            rsMap.put("code", 200);
            rsMap.put("msg", "success");
            response.getWriter().print(JSON.toJSONString(rsMap));
        } else {
            rsMap.put("code", 500);
            rsMap.put("msg", "fail");
            response.getWriter().print(JSON.toJSONString(rsMap));
        }

    }


}
