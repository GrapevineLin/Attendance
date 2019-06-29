
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html class="x-admin-sm">
    <head>
        <meta charset="UTF-8">
        <title>欢迎进入考勤系统</title>
        <meta name="renderer" content="webkit">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/X-admin/css/font.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/X-admin/css/xadmin.css">
        <script src="${pageContext.request.contextPath}/static/X-admin/lib/layui/layui.js" charset="utf-8"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/static/X-admin/js/xadmin.js"></script>
        <script src="${pageContext.request.contextPath}/static/X-admin/js/jquery.min.js"></script>
        <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
        <!--[if lt IE 9]>
          <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
          <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

    </head>
    <body>
        <div class="layui-fluid">
            <div class="layui-row layui-col-space15">
                <div class="layui-col-md12">
                    <div class="layui-card">
                        <div class="layui-card-body ">
                            <blockquote class="layui-elem-quote" style="text-align: center">欢迎:
                                <span class="x-red">${loginUser}</span>！登陆时间:<span id="currentTime"></span>
                            </blockquote>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </div>
        <script>

            //转化时间以显示
            var time = new Date();
            var year = time.getFullYear();
            var month = time.getMonth() + 1 < 10 ? '0' + (time.getMonth() + 1) : (time.getMonth() + 1);
            var day = time.getDate();
            var hour = time.getHours() < 10 ? '0' + time.getHours() : time.getHours();
            var min = time.getMinutes() < 10 ? '0' + time.getMinutes() : time.getMinutes();
            var sec = time.getSeconds();
            var format = year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec;
            document.getElementById("currentTime").innerHTML = format;

        </script>
    </body>
</html>