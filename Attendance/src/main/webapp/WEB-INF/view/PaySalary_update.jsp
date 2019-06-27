<%--
  Created by IntelliJ IDEA.
  User: 31660
  Date: 2019/6/26 0026
  Time: 11:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>更新页面-X-admin2.2</title>
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
    <![endif]--></head>

<body>
<div class="layui-fluid">
    <div class="layui-row">
        <form class="layui-form" id="objForm" action="PaySalary"
              method="post">
            <input type="hidden" name="oper" value="updateDeal"/>
            <input type="hidden" name="payId" value="${payId}"/>
            <div class="layui-form-item">
                <label for="empId" class="layui-form-label">
                    <span class="x-red">*</span>领薪人名称</label>
                <div class="layui-input-inline">
                    <select id="empId" name="empId" class="layui-select">
                        <option value="">请选择</option>
                        <c:forEach var="item" items="${empList}">
                            <option value="${item.empId}">${item.empCode}-${item.empName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" for="beginDate">领薪区间</label>
                <div class="layui-input-inline">
                    <input type="datetime-local" name="beginDate" id="beginDate" value="${beginDate}"
                           placeholder="yyyy-MM-dd HH:mm:ss" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" for="beginDate"></label>
                <div class="layui-input-inline">
                    <input type="datetime-local" name="endDate" id="endDate" value="${endDate}"
                           placeholder="yyyy-MM-dd HH:mm:ss" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="salary" class="layui-form-label">
                    <span class="x-red">*</span>薪水</label>
                <div class="layui-input-inline">
                    <input type="text" id="salary" name="salary" value="${salary}" required=""
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"></label>
                <button class="layui-btn" lay-submit>提交</button>
            </div>

        </form>
    </div>
</div>
<script>layui.use(['form', 'layer'],
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
                        // 获得frame索引
                        var index = parent.layer.getFrameIndex(window.name);
                        //关闭当前frame
                        parent.layer.close(index);
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
<script>
/*    $(function () {
        //选中当前负责人
        $("depHead").val("${empName}")
    })*/
</script>
</body>

</html>