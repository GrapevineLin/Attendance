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
    <script src="${pageContext.request.contextPath}/static/X-admin/laydate/laydate.js"></script>
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="layui-fluid">
    <div class="layui-row">
        <form class="layui-form" >
<%--            <input type="hidden" name="oper" value="insertDeal">--%>


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
                <label class="layui-form-label" for="beginDate">
                    <span class="x-red">*</span>开始时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="beginDate" id="beginDate" required=""
                           placeholder="-" class="layui-input" autocomplete="off">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label" for="beginDate">
                    <span class="x-red">*</span>结束时间</label>
                <div class="layui-input-inline">
                    <input type="text" name="endDate" id="endDate" required=""
                           placeholder="-" class="layui-input" autocomplete="off">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="reason" class="layui-form-label">原因</label>
                <div class="layui-input-inline">
                    <input type="text" id="reason" name="reason"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"></label>
                <button class="layui-btn" lay-submit lay-filter="add">增加</button>
            </div>
        </form>
    </div>
</div>

<script>layui.use(['form', 'layer', 'jquery', 'laydate'],
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
            endDate: function (value) {
                if ($('#beginDate').after($('#endDate'))) {
                    return '开始时间必须早于结束时间';
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
                            "oper": "insertDeal",
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
                        fail:function (data) {
                            layer.msg("对不起 添加失败了！");
                        }
                    })
                }

                return false;
            });

    });</script>
<script>var _hmt = _hmt || [];
(function () {
    var hm = document.createElement("script");
    hm.src = "https://hm.baidu.com/hm.js?b393d153aeb26b46e9431fabaf0f6190";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
})();


/*
 * 获得时间差,时间格式为 年-月-日 小时:分钟:秒 或者 年/月/日 小时：分钟：秒
 * 其中，年月日为全格式，例如 ： 2010-10-12 01:00:00
 * 返回精度为：秒，分，小时，天
 */
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
</body>

</html>