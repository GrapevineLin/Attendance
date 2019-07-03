<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>考勤管理系统</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/X-admin/css/font.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/X-admin/css/xadmin.css">
    <!-- <link rel="stylesheet" href="./css/theme5.css"> -->
    <script src="${pageContext.request.contextPath}/static/X-admin/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/X-admin/js/xadmin.js"></script>
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script>
        // 是否开启刷新记忆tab功能
        // var is_remember = false;
    </script>
</head>
<body class="index">
<!-- 顶部开始 -->
<div class="container">
    <div class="logo">
        <a href="Main">考勤管理系统</a></div>
    <div class="left_open">
        <a><i title="展开左侧栏" class="iconfont">&#xe699;</i></a>
    </div>
    <ul class="layui-nav right" lay-filter="">
        <li class="layui-nav-item">
            <a href="javascript:;">当前登陆者：${loginUser}</a>
            <dl class="layui-nav-child">
                <!-- 二级菜单 -->
                <dd>
                    <a onclick="xadmin.open('个人信息','http://www.baidu.com')">个人信息</a></dd>
                <dd>
                    <a id="exit">退出</a></dd>
            </dl>
        </li>
    </ul>
</div>
<!-- 顶部结束 -->
<!-- 中部开始 -->
<!-- 左侧菜单开始 -->
<div class="left-nav">
    <div id="side-nav">
        <ul id="nav">
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="公司架构">&#xe6ce;</i>
                    <cite>公司架构</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('员工','Employee?oper=list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>员工</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('岗位','Station?oper=list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>岗位</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('部门','Department?oper=list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>部门</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('班次','Classes?oper=list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>班次</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:; ">
                    <i class="iconfont left-nav-li" lay-tips="考勤数据">&#xe6ce;</i>
                    <cite>考勤数据</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('图表分析','Charts?oper=list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>图表分析</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="考勤报表">&#xe6ce;</i>
                    <cite>考勤报表</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('多级分类','CheckReport?oper=list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>考勤情况</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="考勤设置">&#xe6ce;</i>
                    <cite>考勤设置</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('打卡单','PunchCard?oper=list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>打卡单</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('补卡单','RepairCard?oper=list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>补卡单</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('请假单','Leave?oper=list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>请假单</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="财务管理">&#xe6ce;</i>
                    <cite>财务管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('派薪单','PaySalary?oper=list')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>派薪单</cite></a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div>
<!-- <div class="x-slide_left"></div> -->
<!-- 左侧菜单结束 -->
<!-- 右侧主体开始 -->
<div class="page-content">
    <div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
        <ul class="layui-tab-title">
            <li class="home">
                <i class="layui-icon">&#xe68e;</i>我的桌面
            </li>
        </ul>
        <div class="layui-unselect layui-form-select layui-form-selected" id="tab_right">
            <dl>
                <dd data-type="this">关闭当前</dd>
                <dd data-type="other">关闭其它</dd>
                <dd data-type="all">关闭全部</dd>
            </dl>
        </div>
        <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <iframe src='Main?oper=welcome' frameborder="0" scrolling="yes" class="x-iframe">
                </iframe>
            </div>
        </div>
        <div id="tab_show"></div>
    </div>
</div>
<div class="page-content-bg"></div>
<style id="theme_style"></style>
<!-- 右侧主体结束 -->
<!-- 中部结束 -->
<script>
    document.getElementById("exit").onclick = function () {
        $.ajax({
            type: 'get',
            url: 'Login?oper=logoutDeal',
            success: function (data) {
                // alert("退出成功");
                location.href = "Login";
            },
            error: function (msg) {
                alert("error");
            }
        })
    }
</script>
</body>

</html>