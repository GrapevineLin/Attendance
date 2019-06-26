package edu.ui.ctrl;

import com.liuvei.common.PagerItem;
import com.liuvei.common.SysFun;
import edu.bean.Classes;
import edu.service.impl.ClassesService;
import edu.service.impl.impl.ClassesServiceImpl;

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
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(UIConst.AREAPATH + "/Classes")
public class ClassesServlet extends HttpServlet {

    private static ClassesService classesService = new ClassesServiceImpl();

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

        String oper = request.getParameter("oper");
        if(oper == null){
            oper = "";
        }else{
            oper = oper.trim();
        }

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
            case "update":
                updateView(request, response);
                break;
            case "updateDeal":
                updateDeal(request, response);
                break;
            case "detail":
                detailView(request, response);
                break;
            case "deleteDeal":
                deleteDeal(request, response);
                break;
            default:
                //列表操作：默认
                System.out.println("oper不存在");
                break;
        }
    }

    protected void listView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Classes> vDataList = null;

        //分页步骤：创建PagerItem对象，处理url传来的pagesize和pageindex
        PagerItem pagerItem = new PagerItem();
        pagerItem.parsePageSize(request.getParameter(pagerItem.getParamPageSize()));
        pagerItem.parsePageNum(request.getParameter(pagerItem.getParamPageNum()));
        //定义记录数变量
        Long rowCount = 0L;
        //根据条件，查找符合条件的所有记录数
        rowCount =classesService.count();
        //将记录赋值给pagerItem, 以便进行分页的各类计算
        pagerItem.changeRowCount(rowCount);
        //从数据库取指定分页的数据
        vDataList = classesService.pager(pagerItem.getPageNum(), pagerItem.getPageSize());
        //设置页面跳转的url
        pagerItem.changeUrl(SysFun.generalUrl(request.getRequestURI(), request.getQueryString()));
        //将分页对象和数据列表，放到作用域，以便页面可以访问
        request.setAttribute("pagerItem", pagerItem);
        for(int i = 0; i < vDataList.size(); i++){

        }
        request.setAttribute("DataList", vDataList);

        //-------------------------------------------------

        //转发到页面
        String toPage = UIConst.VIEWPATH + "/Classes_list.jsp";
        request.getRequestDispatcher(toPage).forward(request, response);
    }

    protected void listDeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求参数
        String searchName = request.getParameter("searchName");
        //回显请求数据
        request.setAttribute("searchName", searchName);

        List<Classes> vDataList = null;
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
            rowCount = classesService.count();
            //将记录赋值给pagerItem, 以便进行分页的各类计算
            pagerItem.changeRowCount(rowCount);
            //从数据库取指定分页的数据
            vDataList = classesService.pager(pagerItem.getPageNum(), pagerItem.getPageSize());
        }else{
            //根据条件，查找符合条件的所有记录数
//            System.out.println("dfdfdfdfdfdf");
            rowCount = classesService.countByName(searchName);
            //将记录赋值给pagerItem, 以便进行分页的各类计算
            pagerItem.changeRowCount(rowCount);
            //根据名字查找
            vDataList = classesService.pagerByName(searchName, pagerItem.getPageNum(), pagerItem.getPageSize());
        }

        //设置页面跳转的url
        pagerItem.changeUrl(SysFun.generalUrl(request.getRequestURI(), request.getQueryString()));
        //将分页对象和数据列表，放到作用域，以便页面可以访问
        request.setAttribute("pagerItem", pagerItem);
        request.setAttribute("DataList", vDataList);

        //转发到页面
        String toPage = UIConst.VIEWPATH + "/Classes_list.jsp";
        request.getRequestDispatcher(toPage).forward(request, response);
    }

    protected void insertView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String toPage = UIConst.VIEWPATH + "/Classes_insert.jsp";
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
        String classCode = request.getParameter("classCode");
        String className = request.getParameter("className");
        String am = request.getParameter("am");
        String pm = request.getParameter("pm");
        String remark = request.getParameter("remark");


        //回显到页面
        request.setAttribute("classCode", classCode);
        request.setAttribute("className", className);
        request.setAttribute("am", am);
        request.setAttribute("pm", pm);
        request.setAttribute("remark", remark);
        //服务器端验证

        String vMsg = "";
        if(SysFun.isNullOrEmpty(classCode)){
            vMsg += "班次编码不能为空";
        }
        //验证失败，则将失败内容转发到页面
        if(!SysFun.isNullOrEmpty(vMsg)){
            request.setAttribute("msg", vMsg);
            insertView(request, response);
            return;
        }
        if(SysFun.isNullOrEmpty(className)){
            vMsg += "班次名称不能为空";
        }
        if(!SysFun.isNullOrEmpty(vMsg)){
            request.setAttribute("msg", vMsg);
            insertView(request, response);
            return;
        }
        if(SysFun.isNullOrEmpty(am)){
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
        if(SysFun.isNullOrEmpty(pm)){
            vMsg += "下午下班时间不能为空";
        }
        if(!SysFun.isNullOrEmpty(vMsg)){
            request.setAttribute("msg", vMsg);
            insertView(request, response);
            return;
        }

        //真正处理
        Classes bean = new Classes();
        bean.setClassCode(classCode);
        bean.setClassName(className);
        try {
            bean.setAm(new Time(new SimpleDateFormat("HH:mm").parse(am).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            bean.setPm(new Time(new SimpleDateFormat("HH:mm").parse(pm).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        bean.setRemark(remark);

        Long result = 0L;
        try{
            result = classesService.insert(bean);
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

    protected void updateView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String vId = request.getParameter("id");
        if(!SysFun.isNullOrEmpty(vId)){
            Long iId = SysFun.parseLong(vId);
            Classes bean = classesService.load(iId);

            if(bean != null){
                //使用对象来回显
                request.setAttribute("classId",bean.getClassId());
                request.setAttribute("classCode", bean.getClassCode());
                request.setAttribute("className",bean.getClassName());
                request.setAttribute("am",bean.getAm());
                request.setAttribute("pm",bean.getPm());
                request.setAttribute("remark",bean.getRemark());

                String toPage = UIConst.VIEWPATH + "/Classes_update.jsp";
                request.getRequestDispatcher(toPage).forward(request, response);
                return;
            }
            out.println("<script>");
            out.println("alert('数据不存在');");
            out.println("parent.window.location.reload();");
            out.println("</script>");

        }
    }

    protected void updateDeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String classId = request.getParameter("classId");
        String classCode = request.getParameter("classCode");
        String className = request.getParameter("className");
        String am = request.getParameter("am");
        String pm = request.getParameter("pm");
        String remark = request.getParameter("remark");

        //回显到页面
        request.setAttribute("classId",classId);
        request.setAttribute("classCode", classCode);
        request.setAttribute("className",className);
        request.setAttribute("am",am);
        request.setAttribute("pm",pm);
        request.setAttribute("remark", remark);

        System.out.println("灌灌灌灌灌");

        //前端验证
        String vMsg = "";
        if(SysFun.isNullOrEmpty(classId)){
            vMsg += "班次ID不能为空";
        }
        if(!SysFun.isNullOrEmpty(vMsg)){
            request.setAttribute("msg", vMsg);
            updateView(request, response);
            return;
        }
        if(SysFun.isNullOrEmpty(classCode)){
            vMsg += "班次编码不为空";
        }
        //验证失败，则将失败内容转发到页面
        if(!SysFun.isNullOrEmpty(vMsg)){
            request.setAttribute("msg", vMsg);
            updateView(request, response);
            return;
        }
        if(SysFun.isNullOrEmpty(className)){
            vMsg += "班次名称不能为空";
        }
        if(!SysFun.isNullOrEmpty(vMsg)){
            request.setAttribute("msg", vMsg);
            updateView(request, response);
            return;
        }
        if(SysFun.isNullOrEmpty(am)){
            vMsg += "早上上班时间不能为空";
        }
        if(!SysFun.isNullOrEmpty(vMsg)){
            request.setAttribute("msg", vMsg);
            updateView(request, response);
            return;
        }
        if(SysFun.isNullOrEmpty(pm)){
            vMsg += "下午下班时间不能为空";
        }
        if(!SysFun.isNullOrEmpty(vMsg)){
            request.setAttribute("msg", vMsg);
            updateView(request, response);
            return;
        }
        //数据库验证
        Long iId = SysFun.parseLong(classId);
        Classes bean = classesService.load(iId);
        if(bean == null){
            vMsg = "数据不存在";
        }
        if(!SysFun.isNullOrEmpty(vMsg)){
            request.setAttribute("msg", vMsg);
            updateView(request, response);
            return;
        }
        //真正处理
        bean.setClassId(iId);
        bean.setClassCode(classCode);
        bean.setClassName(className);
        System.out.println("djfdjfdjfj");
        try {

            bean.setAm(new Time(new SimpleDateFormat("HH:mm").parse(am).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            bean.setPm(new Time(new SimpleDateFormat("HH:mm").parse(pm).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        bean.setRemark(remark);

        Long result = 0L;
        try{
            result = classesService.update(bean);
        }catch (Exception e){
            vMsg = "修改失败" + e.getMessage();
        }
        if(result > 0){
            out.println("<script>");
            out.println("alert('修改成功');");
            out.println("parent.window.location.reload();");
            out.println("</script>");
        }else {
            request.setAttribute("msg", vMsg);
            updateView(request, response);
        }
    }


    protected void detailView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String vId = request.getParameter("id");
        if(!SysFun.isNullOrEmpty(vId)){
            Long iPk = SysFun.parseLong(vId);
            Classes bean = classesService.load(iPk);
            if(bean != null){

                request.setAttribute("bean", bean);

                String toPage = UIConst.VIEWPATH + "/Classes_detail.jsp";
                request.getRequestDispatcher(toPage).forward(request, response);
            }
        }
    }

    protected void deleteDeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        //取得主键，再根据主键获得记录
        String vid = request.getParameter("id");
        if(!SysFun.isNullOrEmpty(vid)){
            Long iId = SysFun.parseLong(vid);

            Long result = 0L;
            result = classesService.delete(iId);

            if(result > 0){
                out.print("ok");
                return;
            }
        }
        out.print("nook");

    }
}
