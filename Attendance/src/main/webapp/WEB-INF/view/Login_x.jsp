<%--
  Created by IntelliJ IDEA.
  User: 31660
  Date: 2019/6/24 0024
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html  class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>考勤系统登录</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/X-admin/css/font.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/X-admin/css/login.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/X-admin/css/xadmin.css">
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/X-admin/lib/layui/layui.js" charset="utf-8"></script>
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="login-bg">

<div class="login layui-anim layui-anim-up">
    <div class="message">考勤系统管理登录</div>
    <div id="darkbannerwrap"></div>

    <form method="post" class="layui-form" action="Login" >
        <input type="hidden" name="oper" value="loginDeal"/>
        <div>${msg}</div>
        <input id="userName" name="userName" type="text" value="${userName}" placeholder="用户名"
               lay-verify="required" class="layui-input">
        <hr class="hr15">
        <input id="passWord" name="passWord" type="password" placeholder="密码"
               lay-verify="required" class="layui-input">
        <hr class="hr15">
        <input value="登录" lay-submit lay-filter="login" style="width:100%;" type="submit">
        <hr class="hr20" >
    </form>

</div>
<script type="text/javascript">
    if (window != top) {
        top.location.href = location.href;
    }
</script>
<%--<script>--%>
<%--    $(function  () {--%>
<%--        layui.use('form', function(){--%>
<%--            var form = layui.form;--%>
<%--            // layer.msg('玩命卖萌中', function(){--%>
<%--            //   //关闭后的操作--%>
<%--            //   });--%>
<%--            //监听提交--%>
<%--            form.on('submit(login)', function(data){--%>
<%--                // alert(888)--%>
<%--                layer.msg(JSON.stringify(data.field),function(){--%>
<%--                    location.href='index.html'--%>
<%--                });--%>
<%--                return false;--%>
<%--            });--%>
<%--        });--%>
<%--    })--%>
<%--</script>--%>
<!-- 底部结束 -->
</body>
</html>