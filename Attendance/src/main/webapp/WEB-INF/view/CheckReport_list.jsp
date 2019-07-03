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
<div class="x-nav">
          <span class="layui-breadcrumb">
            <a href="">首页</a>
            <a href="">考勤</a>
            <a>
              <cite>清单</cite></a>
          </span>
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right"
       onclick="location.reload()" title="刷新">
        <i class="layui-icon layui-icon-refresh" style="line-height:30px"></i></a>
</div>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body ">
                    <form class="layui-form layui-col-space5" action="CheckReport" method="get">
                        <input type="hidden" name="oper" value="listDeal"/>
                        <div class="layui-inline layui-show-xs-block" >
                            <input type="text" name="searchName" placeholder="请输入名称" autocomplete="off"
                                   class="layui-input" value="${searchName}">
                        </div>
                        <div class="layui-inline layui-show-xs-block">
                            <button class="layui-btn" lay-submit="" lay-filter="sreach"><i
                                    class="layui-icon">&#xe615;</i>
                            </button>
                        </div>
                    </form>
                </div>
                <div class="layui-card-header">
                </div>
                <div class="layui-card-body layui-table-body layui-table-main">
                    <table id="datalist"  class="layui-table layui-form">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>员工编码</th>
                            <th>姓名</th>
                            <th>早上上班时间</th>
                            <th>下午下班时间</th>
                            <th>出勤情况</th>
                        </thead>
                        <tbody id="lay_table">
                        </tbody>
                    </table>
                        <div id="lay_pager"></div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>

    layui.use(['laydate', 'form', 'laypage'], function () {
        var laydate = layui.laydate;
        var form = layui.form;
        var laypage = layui.laypage;
        
        var total = 0;
        var $ = layui.$;
        $.ajax({
           type: 'get',
           url: 'CheckReport?oper=getlist',
           async: false,
           dataType: 'json',
           success: function (data) {
               total = data.length;
               console.log(data);
               var json = data;
               loadData(json);
           }
        });
            //时间戳转换
        function add0(m){return m<10?'0'+m:m }

        function formatDate(timestamp)
        {
            //timestamp是整数，否则要parseInt转换
            var time = new Date(timestamp);
            var y = time.getFullYear();
            var m = time.getMonth()+1;
            var d = time.getDate();
            var h = time.getHours();
            var mm = time.getMinutes();
            var s = time.getSeconds();
            return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
        }
        function loadData(data) {
            var nums = 10; //每页数量
            // new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var render = function (data, curr) {
                var arr = [], thisData = data.concat().splice(curr * nums - nums, nums);
                for(var i = 0; i < thisData.length; i++){

                    var str = "<tr>" +
                        "<td>" + (i + 1) + "</td>" +
                        "<td>" + thisData[i].empCode + "</td>" +
                        "<td>" + thisData[i].empName + "</td>" +
                        "<td>" + (thisData[i].am === undefined ? " " : formatDate(thisData[i].am) ) + "</td>" +
                        "<td>" + (thisData[i].pm === undefined ? " " : formatDate(thisData[i].pm) ) + "</td>" +
                        "<td>" + thisData[i].attendance + "</td>" +
                        "</tr>";
                    arr.push(str);
                }
                return arr.join('');
            };
            laypage.render({
                elem: 'lay_pager',
                // pages: Math.ceil(data.length / nums), //得到总页数
                // curr: 1,
                count: total,
                limit: 10,
                group: 10,
                layout: ['count', 'prev', 'page', 'next', 'refresh', 'skip'],
                jump: function (obj) {
                    document.getElementById('lay_table').innerHTML = render(data, obj.curr);
                    console.log(total);
                }
            })
        }

    });





</script>
</html>