<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>欢迎页面-X-admin2.2</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/X-admin/css/font.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/X-admin/css/xadmin.css">
    <script src="${pageContext.request.contextPath}/static/X-admin/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/X-admin/js/xadmin.js"></script>
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="x-nav">
          <span class="layui-breadcrumb">
            <a href="">首页</a>
            <a href="">考勤</a>
            <a>
              <cite>清单</cite></a>
          </span>
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right"
       onclick="location.reload()" title="刷新">
        <i class="layui-icon layui-icon-refresh" style="line-height:30px"></i></a>
</div>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body ">
                    <form class="layui-form layui-col-space5" action="CheckReport" method="get">
                        <input type="hidden" name="oper" value="listDeal"/>
                        <div class="layui-inline layui-show-xs-block" >
                            <input type="text" name="searchName" placeholder="请输入名称" autocomplete="off"
                                   class="layui-input" value="${searchName}">
                        </div>
                        <div class="layui-inline layui-show-xs-block">
                            <button class="layui-btn" lay-submit="" lay-filter="sreach"><i
                                    class="layui-icon">&#xe615;</i>
                            </button>
                        </div>
                    </form>
                </div>
                <div class="layui-card-header">
                </div>
                <div class="layui-card-body layui-table-body layui-table-main">
                    <table id="datalist"  class="layui-table layui-form">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>员工编码</th>
                            <th>姓名</th>
                            <th>早上上班时间</th>
                            <th>下午下班时间</th>
                            <th>出勤情况</th>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${DataList }" varStatus="v">
                            <tr>

                                <td>${v.count}</td>
                                <td>${item.empCode}</td>
                                <td>${item.empName}</td>
                                <td>${item.am}</td>
                                <td>${item.pm}</td>
                                <td>${item.attendance}</td>

                                    <%--<td class="td-status">
                                      <span class="layui-btn layui-btn-normal layui-btn-mini">已启用</span></td>
                                    <td class="td-manage">
                                      <a onclick="member_stop(this,'10001')" href="javascript:;"  title="启用">
                                        <i class="layui-icon">&#xe601;</i>
                                    </a>--%>
                        </c:forEach>

                        </tbody>
                    </table>
                    <jsp:include page="__pager.jsp" flush="true"/>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>

    layui.use(['laydate', 'form'], function () {
        var laydate = layui.laydate;
        var form = layui.form;


        // 监听全选
        form.on('checkbox(checkall)', function (data) {

            if (data.elem.checked) {
                $('tbody input').prop('checked', true);
            } else {
                $('tbody input').prop('checked', false);
            }
            form.render('checkbox');
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#start' //指定元素
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#end' //指定元素
        });


    });




</script>
</html>