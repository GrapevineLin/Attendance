
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
                <form class="layui-form" action="Classes">
                    <input type="hidden" name="oper" value="insertDeal" />
                    <div class="layui-form-item">
                        <label for="classCode" class="layui-form-label">
                            <span class="x-red">*</span>班次编码</label>
                        <div class="layui-input-inline">
                            <input type="text" id="classCode" name="classCode" value="${classCode}" required="" lay-verify="classCode" autocomplete="off" class="layui-input"></div>
                        </div>
                    <div class="layui-form-item">
                        <label for="className" class="layui-form-label">
                            <span class="x-red">*</span>班次名称</label>
                        <div class="layui-input-inline">
                            <input type="text" id="className" name="className" value="${className}" required="" lay-verify="className" autocomplete="off" class="layui-input"></div>
                    </div>
                    <div class="layui-form-item">
                        <label for="am" class="layui-form-label">
                            <span class="x-red">*</span>早上上班时间</label>
                        <div class="layui-input-inline">
                            <input type="time" id="am" name="am" required="" value="${am}" lay-verify="am" autocomplete="off" class="layui-input"></div>
                        <div class="layui-form-mid layui-word-aux"></div></div>
                    <div class="layui-form-item">
                        <label for="pm" class="layui-form-label">
                            <span class="x-red">*</span>下午下班时间</label>
                        <div class="layui-input-inline">
                            <input type="time" id="pm" name="pm" required="" value="${pm}" lay-verify="pm" autocomplete="off" class="layui-input"></div>
                        <div class="layui-form-mid layui-word-aux"></div></div>
                    <div class="layui-form-item">
                        <label for="remark" class="layui-form-label">备注</label>
                        <div class="layui-input-inline">
                            <textarea id="remark" name="remark" value="${remark}" autocomplete="off" class="layui-textarea"></textarea></div>
                    </div>
                    <div class="layui-form-item">
<%--                        <label for="L_repass" class="layui-form-label"></label>--%>
                        <button class="layui-btn" lay-filter="add" lay-submit="">添加班次</button></div>
                </form>
            </div>
        </div>
        <script>layui.use(['form', 'layer','jquery'],
            function() {
                $ = layui.jquery;
                var form = layui.form,
                layer = layui.layer;

                //自定义验证规则
                form.verify({
                    classCode: function(value) {
                        if (value.length < 1 || value.isEmpty()) {
                            return '班次编码不能为空';
                        }
                    },
                    className: function(value) {
                        if (value.length < 1 || value.isEmpty()) {
                            return '班次名称不能为空';
                        }
                    },
                    am: function(value) {
                        var hour = parseInt(value.substring(0, 2));
                        if (hour > 12) {
                            return '上班时间不能超过12点am';
                        }else if (hour < 6) {
                            return '上班时间不能早于6点am';
                        }
                    },
                    pm: function (value) {
                        var hour = parseInt(value.substring(0, 2));
                        if(hour < 14){
                            return '下班时间不能早于14点pm';
                        }else if(hour > 21){
                            return '下班时间不能晚于21点pm';
                        }
                    }
                });

                //监听提交
                form.on('submit(add)',
                function(data) {
                    console.log(data);
                    //发异步，把数据提交给php
                    layer.alert("增加成功", {
                        icon: 6
                    },
                    function() {
                        //关闭当前frame
                        xadmin.close();

                        // 可以对父窗口进行刷新 
                        xadmin.father_reload();
                    });
                    return false;
                });

            });</script>
<%--        <script>var _hmt = _hmt || []; (function() {--%>
<%--                var hm = document.createElement("script");--%>
<%--                hm.src = "https://hm.baidu.com/hm.js?b393d153aeb26b46e9431fabaf0f6190";--%>
<%--                var s = document.getElementsByTagName("script")[0];--%>
<%--                s.parentNode.insertBefore(hm, s);--%>
<%--            })();</script>--%>
    </body>

</html>