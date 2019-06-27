
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="x-admin-sm">
    
    <head>
        <meta charset="UTF-8">
        <title>添加班次</title>
        <meta name="renderer" content="webkit">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/X-admin/css/font.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/X-admin/css/xadmin.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/X-admin/lib/layui/layui.js" charset="utf-8"></script>
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
                <form class="layui-form" action="PunchCard">
                    <input type="hidden" name="oper" value="insertDeal" />
                    <div class="layui-form-item">
                        <label for="empCode" class="layui-form-label">
                            <span class="x-red">*</span>打卡人编码</label>
                        <div class="layui-input-inline">
                            <input type="text" id="empCode" name="empCode" value="${sessionScope.empCode}" required="" readonly  autocomplete="off" class="layui-input"></div>
                        </div>
                    <div class="layui-form-item">
                        <label for="empName" class="layui-form-label">
                            <span class="x-red">*</span>打卡人姓名</label>
                        <div class="layui-input-inline">
                            <input type="text" id="empName" name="empName" value="${sessionScope.userName}" required="" readonly  autocomplete="off" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label for="date" class="layui-form-label">
                            <span class="x-red">*</span>打卡时间</label>
                        <div class="layui-input-inline">
                            <input type="datetime-local" id="date" name="date" readonly required="" value=""  autocomplete="off" class="layui-input"></div>
                        <div class="layui-form-mid layui-word-aux"></div></div>
                    <div class="layui-form-item">
                        <label for="remark" class="layui-form-label">备注</label>
                        <div style="margin-left: 110px;">
                            <textarea id="remark" name="remark" value="${remark}" rows="5" cols="4" class="layui-textarea"></textarea></div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"></label>
                        <button class="layui-btn" lay-submit="">点我打卡</button></div>
                </form>
            </div>
        </div>
        <script>layui.use(['form', 'layer','jquery'],
            function() {
                $ = layui.jquery;
                var form = layui.form,
                layer = layui.layer;

                // //自定义验证规则
                // form.verify({
                //     classCode: function(value) {
                //         if (value.length < 1 || value.isEmpty()) {
                //             return '班次编码不能为空';
                //         }
                //     },
                //     className: function(value) {
                //         if (value.length < 1 || value.isEmpty()) {
                //             return '班次名称不能为空';
                //         }
                //     },
                //     am: function(value) {
                //         var hour = parseInt(value.substring(0, 2));
                //         if (hour > 12) {
                //             return '上班时间不能超过12点am';
                //         }else if (hour < 6) {
                //             return '上班时间不能早于6点am';
                //         }
                //     },
                //     pm: function (value) {
                //         var hour = parseInt(value.substring(0, 2));
                //         if(hour < 14){
                //             return '下班时间不能早于14点pm';
                //         }else if(hour > 21){
                //             return '下班时间不能晚于21点pm';
                //         }
                //     }
                // });

                //监听提交
                form.on('submit(add)');

            });
        //转化时间以显示
        var time = new Date();
        var year = time.getFullYear();
        var month = time.getMonth() + 1 < 10 ? '0' + (time.getMonth() + 1) : (time.getMonth() + 1);
        var day = time.getDate();
        var hour = time.getHours() < 10 ? '0' + time.getHours() : time.getHours();
        var min = time.getMinutes() < 10 ? '0' + time.getMinutes() : time.getMinutes();
        // var sec = time.getSeconds();
        var format = year + "-" + month + "-" + day + "T" + hour + ":" +min;
        document.getElementById("date").setAttribute("value", format);
        </script>

    </body>

</html>