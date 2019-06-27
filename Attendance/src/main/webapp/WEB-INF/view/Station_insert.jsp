<%--
  Created by IntelliJ IDEA.
  User: 31660
  Date: 2019/6/26 0026
  Time: 11:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="x-admin-sm">

<head>
    <meta charset="UTF-8">
    <title>插入页面-X-admin2.2</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/X-admin/css/font.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/X-admin/css/xadmin.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/X-admin/lib/layui/layui.js"
            charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/X-admin/js/xadmin.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="layui-fluid">
    <div class="layui-row">
        <form class="layui-form" action="Station" method="post">
            <input type="hidden" name="oper" value="insertDeal"/>
            <div class="layui-form-item">
                <label for="jobCode" class="layui-form-label">
                    <span class="x-red">*</span>岗位编码</label>
                <div class="layui-input-inline">
                    <input type="text" id="jobCode" name="jobCode" required="" lay-verify="jobCode" autocomplete="off"
                           class="layui-input" value="${jobCode}">
                </div>
                <div class="layui-form-mid layui-word-aux">
                    岗位编码必须输入
                </div>
            </div>
            <div class="layui-form-item">
                <label for="jobName" class="layui-form-label">
                    <span class="x-red">*</span>岗位名称</label>
                <div class="layui-input-inline">
                    <input type="text" id="jobName" name="jobName" required="" lay-verify="jobName" autocomplete="off"
                           class="layui-input" value="${jobName}">
                </div>
                <div class="layui-form-mid layui-word-aux">
                    岗位名称必须输入
                </div>
            </div>
            <div class="layui-form-item">
                <label for="dep" class="layui-form-label">
                    所在部门</label>
                <div class="layui-input-inline">
                    <select class="layui-select" name="dep" id="dep">
                        <option value="">请选择</option>
                        <c:forEach var="item" items="${departmentList}">
                            <option value="${item.depName}">${item.depCode}-${item.depName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label for="dirSup" class="layui-form-label">
                    直接上级</label>
                <div class="layui-input-inline">
                    <select class="layui-select" name="dirSup" id="dirSup">
                        <option value="">请选择</option>
                        <option value="">无</option>
                        <c:forEach var="item" items="${stationList}">
                            <option value="${item.jobCode}">${item.jobCode}-${item.jobName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label for="jobCat" class="layui-form-label">
                    岗位类别</label>
                <div class="layui-input-inline">
                    <input type="text" id="jobCat" name="jobCat" required="" lay-verify="jobCat"
                           autocomplete="off" class="layui-input" value="${jobCat}"></div>
            </div>
            <div class="layui-form-item">
                <label for="jobDes" class="layui-form-label">
                    岗位描述</label>
                <div style="margin-left:110px;top:-5px;padding:0">
                    <textarea id="jobDes" name="jobDes" rows="5" cols="5" class="layui-textarea"
                              value="${jobDes}"></textarea>
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
            jobCode: function (value) {
                if (value.length <= 0) {
                    return '岗位编码必须输入';
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