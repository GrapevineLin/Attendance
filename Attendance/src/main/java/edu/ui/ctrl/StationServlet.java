package edu.ui.ctrl;

import com.liuvei.common.PagerItem;
import com.liuvei.common.SysFun;
import edu.bean.Department;
import edu.bean.Station;
import edu.service.impl.DepartmentService;
import edu.service.impl.StationService;
import edu.service.impl.impl.DepartmentServiceImpl;
import edu.service.impl.impl.StationServiceImpl;
import edu.ui.ctrl.UIConst;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(UIConst.AREAPATH + "/Station")
public class StationServlet extends HttpServlet {

    private StationService stationService = new StationServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            case "detail":
                detailView(request, response); // 查看页面
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

    protected void listView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Station> vDataList = null;

        // ------------------------------------------------------------------------
        // 分页步骤1. 创建PagerIter对象, 处理url传过来的pagesize和pageindex
        PagerItem pagerItem = new PagerItem();
        pagerItem.parsePageSize(request.getParameter(pagerItem.getParamPageSize()));
        pagerItem.parsePageNum(request.getParameter(pagerItem.getParamPageNum()));
        // 分页步骤2.1. 定义记录数变量
        Long rowCount = 0L;
        // 分页步骤2.2. 根据条件，查找符合条件的所有记录数 ***** count()要根据实际换成其它方法
        rowCount = stationService.count();// 分页步骤2.3. 将记录数赋给pagerItem，以便进行分页的各类计算
        pagerItem.changeRowCount(rowCount);
        // 分页步骤2.4. 从数据库取指定分页的数据 ***** pager()要根据实际换成其它方法
        vDataList = stationService.pager(pagerItem.getPageNum(),
                pagerItem.getPageSize());
        // 分页步骤2.5. 设置页面的跳转url
        pagerItem.changeUrl(SysFun.generalUrl(request.getRequestURI(),
                request.getQueryString()));
        // 分页步骤3. 将分页对象和数据列表,放到作用域,以便页面可以访问
        request.setAttribute("pagerItem", pagerItem);
        request.setAttribute("DataList", vDataList);
        // ------------------------------------------------------------------------
        // 转发到页面
        String toPage = UIConst.VIEWPATH + "/Station_list.jsp";
        request.getRequestDispatcher(toPage).forward(request, response);
    }

    protected void listDeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求参数
        String searchName = request.getParameter("searchName");
        //回显请求数据
        request.setAttribute("searchName", searchName);

        List<Station> vDataList = null;
        // ------------------------------------------------------------------------
        // 分页步骤1. 创建PagerIter对象, 处理url传过来的pagesize和pageindex
        PagerItem pagerItem = new PagerItem();
        pagerItem.parsePageSize(request.getParameter(pagerItem.getParamPageSize()));
        pagerItem.parsePageNum(request.getParameter(pagerItem.getParamPageNum()));
        // 分页步骤2.1. 定义记录数变量
        Long rowCount = 0L;
        if (SysFun.isNullOrEmpty(searchName)) {
            // 分页步骤2.2. 根据条件，查找符合条件的所有记录数 ***** count()要根据实际换成其它方法
            rowCount = stationService.count();
            // 分页步骤2.3. 将记录数赋给pagerItem，以便进行分页的各类计算
            pagerItem.changeRowCount(rowCount);
            // 分页步骤2.4. 从数据库取指定分页的数据 ***** pager()要根据实际换成其它方法
            vDataList = stationService.pager(pagerItem.getPageNum(),
                    pagerItem.getPageSize());
        } else {
            // 分页步骤2.2. 根据条件，查找符合条件的所有记录数 ***** count()要根据实际换成其它方法
            rowCount = stationService.countByName(searchName);
            // 分页步骤2.3. 将记录数赋给pagerItem，以便进行分页的各类计算
            pagerItem.changeRowCount(rowCount);
            // 分页步骤2.4. 从数据库取指定分页的数据 ***** pager()要根据实际换成其它方法
            vDataList = stationService.pagerByName(searchName,
                    pagerItem.getPageNum(), pagerItem.getPageSize());
        } //分页步骤2 .5.设置页面的跳转url
        pagerItem.changeUrl(SysFun.generalUrl(request.getRequestURI(),
                request.getQueryString()));
        // 分页步骤3. 将分页对象和数据列表,放到作用域,以便页面可以访问
        request.setAttribute("pagerItem", pagerItem);
        request.setAttribute("DataList", vDataList);
        // ------------------------------------------------------------------------// 转发到页面
        String toPage = UIConst.VIEWPATH + "/Station_list.jsp";
        request.getRequestDispatcher(toPage).forward(request, response);
    }

    protected void insertView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DepartmentService departmentService = new DepartmentServiceImpl();
        List<Department> departmentList = departmentService.list();
        request.setAttribute("departmentList", departmentList);

        List<Station> stationList = stationService.list();
        request.setAttribute("stationList", stationList);

        //转发页面
        String toPage = UIConst.VIEWPATH + "/Station_insert.jsp";
        request.getRequestDispatcher(toPage).forward(request, response);
    }

    protected void insertDeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从response对象里获取out对象——response.getWriter()之前，要先设置页面的编码
        java.io.PrintWriter out = response.getWriter();
        // 获取请求数据
//        String vId = request.getParameter("jobId");
        String jobCode = request.getParameter("jobCode");
        String depName = request.getParameter("jobName");
        String dep = request.getParameter("dep");
        String dirSup = request.getParameter("dirSup");
        String jobCat = request.getParameter("jobCat");
        // 为了在输入页面回显原来的旧值,需要将旧值放到作用域,页面中进行获取
//        request.setAttribute("jobId", vId);
        request.setAttribute("jobCode", jobCode);
        request.setAttribute("jobName", depName);
        request.setAttribute("dep", dep);
        request.setAttribute("dirSup", dirSup);
        request.setAttribute("jobCat", jobCat);
        // (1) 服务端验证
        String vMsg = "";
        if (SysFun.isNullOrEmpty(jobCode)) {
            vMsg += "部门编码不能为空";
        } //如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }
        if (SysFun.isNullOrEmpty(depName)) {
            vMsg += "岗位名称不能为空";
        } //如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }
        if (SysFun.isNullOrEmpty(dep)) {
            vMsg += "所在部门不能为空";
        } //如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }
        if (SysFun.isNullOrEmpty(jobCat)) {
            vMsg += "岗位类别不能为空";
        } //如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }

        Long sId = SysFun.parseLong(jobCode);

        Station bean = new Station();
//        bean.setJobId(sId); //主键不用自己添加
        bean.setDep(dep);
        bean.setDirSup(dirSup);
        bean.setJobCode(jobCode);
        bean.setJobName(depName);
        bean.setJobCat(jobCat);
        Long result = 0L;
        try {
            result = stationService.insert(bean);
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

    protected void updateView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从response对象里获取out对象——response.getWriter()之前，要先设置页面的编码
        java.io.PrintWriter out = response.getWriter();
        // 取得主键，再根据主键，获取记录
        String vId = request.getParameter("id");
        if (!SysFun.isNullOrEmpty(vId)) {
            Long iId = SysFun.parseLong(vId);
            Station bean = stationService.load(iId);
            if (bean != null) {
                // 使用对象来回显
                // request.setAttribute("bean", bean);
                // 为了在输入页面回显原来的旧值,需要将旧值放到作用域,页面中进行获取
                request.setAttribute("jobId", bean.getJobId());
                request.setAttribute("jobCode", bean.getJobCode());
                request.setAttribute("jobName", bean.getJobName());
                request.setAttribute("dep", bean.getDep());
                request.setAttribute("dirSup", bean.getDirSup());
                request.setAttribute("jobCat", bean.getJobCat());
                String toPage = UIConst.VIEWPATH +
                        "/Station_update.jsp";
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
        String vId = request.getParameter("jobId");
        String jobCode = request.getParameter("jobCode");
        String depName = request.getParameter("jobName");
        String dep = request.getParameter("dep");
        String dirSup = request.getParameter("dirSup");
        String jobCat = request.getParameter("jobCat");
        // 为了在输入页面回显原来的旧值,需要将旧值放到作用域,页面中进行获取
        request.setAttribute("jobId", vId);
        request.setAttribute("jobCode", jobCode);
        request.setAttribute("jobName", depName);
        request.setAttribute("dep", dep);
        request.setAttribute("dirSup", dirSup);
        request.setAttribute("jobCat", jobCat);
        // (1) 服务端验证
        String vMsg = "";
        if (SysFun.isNullOrEmpty(vId)) {
            vMsg += "Id不能为空";
        } //如果验证失败, 则将失败内容放到作用域变量, 并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }
        if (SysFun.isNullOrEmpty(jobCode)) {
            vMsg += "部门编码不能为空";
        } //如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }
        if (SysFun.isNullOrEmpty(depName)) {
            vMsg += "岗位名称不能为空";
        } //如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }
        if (SysFun.isNullOrEmpty(dep)) {
            vMsg += "所在部门不能为空";
        } //如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }
        if (SysFun.isNullOrEmpty(dirSup)) {
            vMsg += "直接上级不能为空";
        } //如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }
        if (SysFun.isNullOrEmpty(jobCat)) {
            vMsg += "岗位类别不能为空";
        } //如果验证失败,则将失败内容放到作用域变量,并转发到页面
        if (!SysFun.isNullOrEmpty(vMsg)) {
            request.setAttribute("msg", vMsg);
            System.out.println(vMsg);
            insertView(request, response);
            return;
        }
        // (2) 数据库验证
        Long iId = SysFun.parseLong(vId);
        Station bean = stationService.load(iId);
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
        bean.setJobId(iId);
        bean.setDep(dep);
        bean.setDirSup(dirSup);
        bean.setJobCode(jobCode);
        bean.setJobName(depName);
        bean.setJobCat(jobCat);
        Long result = 0L;
        try {
            result = stationService.update(bean);
        } catch (Exception e) {
            vMsg = "修改失败." + e.getMessage();
            // TODO: handle exception
        }
        if (result > 0) {
            // System.out.println("修改成功");
            // listView(request, response);
            // 如果修改成功，则父窗口页面的地址栏重新加载
            out.println("<script>");
            out.println("parent.window.location.reload();");
            out.println("</script>");
        } else {
            request.setAttribute("msg", vMsg);
            //System.out.println("修改失败");
            updateView(request, response);
        }
    }

    protected void deleteDeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从response对象里获取out对象——response.getWriter()之前，要先设置页面的编码
        java.io.PrintWriter out = response.getWriter();
        //取得逐渐，再根据主键获取记录
        String vId = request.getParameter("jobId");
        if (!SysFun.isNullOrEmpty(vId)) {
            Long iId = SysFun.parseLong(vId);
            Long result = 0L;
            result = stationService.delete(iId);
            if (result > 0) {
                out.print("ok");// 不要使用println()
                return;
            }
        }
        out.println("nook");
    }

    protected void detailView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从response对象里获取out对象——response.getWriter()之前，要先设置页面的编码
        java.io.PrintWriter out = response.getWriter();
        // 取得主键，再根据主键，获取记录
        String vId = request.getParameter("jobId");
        if (!SysFun.isNullOrEmpty(vId)) {
            Long iPK = SysFun.parseLong(vId);
            Station bean = stationService.load(iPK);
            if (bean != null) {
                // 使用对象来回显
                request.setAttribute("bean", bean);
                String toPage = UIConst.VIEWPATH + "/Station_detail.jsp";
                request.getRequestDispatcher(toPage).forward(request, response);
                return;
            }
        }
        out.println("<script>");
        out.println("alert('数据不存在.');");
        out.println("parent.window.location.reload();");
        out.println("</script>");
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
