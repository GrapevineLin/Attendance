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
    <script src="${pageContext.request.contextPath}/static/X-admin/laydate/laydate.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]--></head>

<body>
<div class="layui-fluid">
    <div class="layui-row">
        <form class="layui-form" id="objForm">
            <input type="hidden" name="leaveId" value="${leaveId}"/>

            <div class="layui-form-item">
                <label for="empCode" class="layui-form-label">
                    <span class="x-red">*</span>员工编码</label>
                <div class="layui-input-inline">
                    <select id="empCode" name="empCode" class="layui-select" required="">
                        <option value="">请选择</option>
                        <c:forEach var="item" items="${empList}">
                            <option value="${item.empCode}">${item.empCode}-${item.empName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label" for="beginDate">开始时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="beginDate" id="beginDate" value="${beginDate}"
                           placeholder="-" class="layui-input" autocomplete="off">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" for="beginDate">结束时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="endDate" id="endDate" value="${endDate}"
                           placeholder="-" class="layui-input" autocomplete="off">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="reason" class="layui-form-label">原因</label>
                <div class="layui-input-inline">
                    <input type="text" id="reason" name="reason" value="${reason}"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"></label>
                <button class="layui-btn" lay-submit lay-filter="add">提交</button>
            </div>

        </form>
    </div>
</div>
<script>layui.use(['form', 'layer'],
        function () {
            $ = layui.jquery;
            var form = layui.form,
                layer = layui.layer,
                laydate = layui.laydate;


            //执行一个laydate实例
            laydate.render({
                elem: '#beginDate', //指定元素
                type: 'datetime'
            });

            //执行一个laydate实例
            laydate.render({
                elem: '#endDate', //指定元素
                type: 'datetime'
            });


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
                    console.log('成功进入监听');
                    var date1 = document.getElementById('beginDate').value;
                    var date2 = document.getElementById('endDate').value;
                    var i = GetDateDiff(date1, date2, "second");

                    if (i<0)
                    {
                        alert("开始时间必须在结束时间前");
                        return false;
                    } else if(i%1800!=0){
                        alert("请假时间必须是三十分钟的倍数");
                        return false;
                    } else {
                        //在这里面向后台发送添加的ajax请求
                        console.log("AJAX READY!")
                        $.ajax({
                            type:'POST',
                            url:'Leave',
                            data:{
                                "oper": "updateDeal",
                                "leaveId":'${leaveId}',
                                "empCode":$('#empCode').val(),
                                "beginDate":$('#beginDate').val(),
                                "endDate":$('#endDate').val(),
                                "reason":$('#reason').val()
                            },
                            dataType:'JSON',
                            success:function (data) {

                                if (data.code == 200) {
                                    //如果修改成功了
                                    //要关闭当前页面
                                    layer.msg("nmsl！");
                                    xadmin.close();
                                    xadmin.father_reload();
                                } else {
                                    layer.msg("对不起 添加失败了！");
                                }
                            },
                        })
                    }

                    return false;
                });

    });
function GetDateDiff(startTime, endTime, diffType) {
    //将xxxx-xx-xx的时间格式，转换为 xxxx/xx/xx的格式
    startTime = startTime.replace(/\-/g, "/");
    endTime = endTime.replace(/\-/g, "/");

    //将计算间隔类性字符转换为小写
    diffType = diffType.toLowerCase();

    var sTime = new Date(startTime);    //开始时间
    var eTime = new Date(endTime);  //结束时间

    //作为除数的数字
    var divNum = 1;
    switch (diffType) {
        case "second":
            divNum = 1000;
            break;
        case "minute":
            divNum = 1000 * 60;
            break;
        case "hour":
            divNum = 1000 * 3600;
            break;
        case "day":
            divNum = 1000 * 3600 * 24;
            break;
        default:
            break;
    }
    return parseInt((eTime.getTime() - sTime.getTime()) / parseInt(divNum));
}
</script>
<script>var _hmt = _hmt || [];
(function () {
    var hm = document.createElement("script");
    hm.src = "https://hm.baidu.com/hm.js?b393d153aeb26b46e9431fabaf0f6190";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
})();</script>

</body>

</html>