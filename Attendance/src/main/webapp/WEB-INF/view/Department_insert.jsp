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
<div class="layui-fluid">
    <div class="layui-row">
        <form action="Department" class="layui-form" method="post">
            <input type="hidden" name="oper" value="insertDeal">
            <div class="layui-form-item">
                <label for="depCode" class="layui-form-label">
                    <span class="x-red">*</span>部门编码</label>
                <div class="layui-input-inline">
                    <input type="text" id="depCode" name="depCode" value="${depCode}" required=""
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="depName" class="layui-form-label">
                    <span class="x-red">*</span>部门名称</label>
                <div class="layui-input-inline">
                    <input type="text" id="depName" name="depName" value="${depName}" required=""
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="depHead" class="layui-form-label">
                    <span class="x-red">*</span>部门负责人</label>
                <div class="layui-input-inline">
                    <select id="depHead" name="depHead" class="layui-select">
                        <option value="">请选择</option>
                        <c:forEach var="item" items="${empList}">
                            <option value="${item.empName}">${item.empCode}-${item.empName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label for="depResp" class="layui-form-label">
                    <span class="x-red">*</span>部门描述</label>
                <div class="layui-input-inline">
                    <input type="text" id="depResp" name="depResp" value="${depResp}" required=""
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="supDepId" class="layui-form-label">
                    <span class="x-red">*</span>上级部门</label>
                <div class="layui-input-inline">
                    <input type="text" id="supDepId" name="supDepId" value="${supDepId}" required=""
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"></label>
                <button class="layui-btn" lay-submit>增加</button>
            </div>
        </form>
    </div>
</div>
<script>layui.use(['form', 'layer', 'jquery'],
    function () {
        $ = layui.jquery;
        var form = layui.form,
            layer = layui.layer;

        //自定义验证规则
        form.verify({
            nikename: function (value) {
                if (value.length < 5) {
                    return '昵称至少得5个字符啊';
                }
            },
            pass: [/(.+){6,12}$/, '密码必须6到12位'],
            repass: function (value) {
                if ($('#L_pass').val() != $('#L_repass').val()) {
                    return '两次密码不一致';
                }
            }
        });

        //监听提交
        form.on('submit(add)',
            function (data) {
                console.log(data);
                //发异步，把数据提交给php
                layer.alert("增加成功", {
                        icon: 6
                    },
                    function () {
                        //关闭当前frame
                        xadmin.close();

                        // 可以对父窗口进行刷新 
                        xadmin.father_reload();
                    });
                return false;
            });

    });</script>
<script>var _hmt = _hmt || [];
(function () {
    var hm = document.createElement("script");
    hm.src = "https://hm.baidu.com/hm.js?b393d153aeb26b46e9431fabaf0f6190";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
})();</script>
</body>

</html>