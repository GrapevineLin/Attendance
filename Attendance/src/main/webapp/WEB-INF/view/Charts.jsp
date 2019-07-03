<%--
  Created by IntelliJ IDEA.
  User: 31660
  Date: 2019/7/2 0002
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>图表分析</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/X-admin/css/font.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/X-admin/css/xadmin.css">
    <script src="${pageContext.request.contextPath}/static/X-admin/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/X-admin/js/xadmin.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/X-admin/js/xadmin.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/JS/echarts.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/X-admin/js/jquery.min.js"></script>

</head>
<body>
<div class="x-nav">
          <span class="layui-breadcrumb">
            <a href="">首页</a>
            <a href="">数据</a>
            <a>
              <cite>图表分析</cite></a>
          </span>
</div>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div id="age" style="width: 500px;height:400px;display: inline-block"></div>
                <div id="repairCard" style="width: 500px;height:400px;display: inline-block"></div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    // 员工年龄分析，初始化echarts实例
    var myChart = echarts.init(document.getElementById('age'));
    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '员工年龄分析',
            x: 'center'
        },
        tooltip: {},
        legend: {
            orient: 'vertical',
            left: 'left',
            data: []
        },
        // xAxis: {
        //     data: []
        // },
        // yAxis: {},
        // series: [{
        //     name: '年龄',
        //     type: 'bar',
        //     data: [0, 10, 20, 30, 40]
        // }]
        series: [
            {
                name: '员工年龄',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: []
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
    var ageArr = []; //数据
    var legend = []; //图例
    $.ajax({
        type: "GET",
        async: true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "Charts?oper=getList",    //请求发送到TestServlet处
        data: {},
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            //请求成功时执行该函数内容，result即为服务器返回的json对象
            if (result) {
                for (var i = 0; i < result.data.length; i++) {
                    ageArr.push({
                        value: result.data[i].count,
                        name: result.data[i].age
                    })
                    legend.push(result.data[i].age)
                }
                console.log("names:" + ageArr);
                myChart.hideLoading();    //隐藏加载动画
                myChart.setOption({        //加载数据图表
                    series: {
                        data: ageArr
                    },
                    legend: {
                        data: legend
                    }
                });

            }

        },
        error: function (errorMsg) {
            //请求失败时执行该函数
            alert("图表请求数据失败!");
            myChart.hideLoading();
        }
    });

    // 请假单分析，初始化echarts实例
    var myChart_repair = echarts.init(document.getElementById('repairCard'));
    // 指定图表的配置项和数据
    var option_repair = {
        title: {
            text: '员工请假次数分析',
            x: 'center'
        },
        tooltip: {},
        legend: {
            orient: 'vertical',
            left: 'left',
            data: []
        },
        series: [
            {
                name: '员工请假',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: []
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart_repair.setOption(option_repair);
    myChart_repair.showLoading();    //数据加载完之前先显示一段简单的loading动画
    var repairArr = []; //数据
    var legend_repair = []; //图例
    $.ajax({
        type: "GET",
        async: true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "Charts?oper=GetRepairCharts",    //请求发送到TestServlet处
        data: {},
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            //请求成功时执行该函数内容，result即为服务器返回的json对象
            if (result) {
                for (var i = 0; i < result.data.length; i++) {
                    repairArr.push({
                        value: result.data[i].count,
                        name: result.data[i].name
                    });
                    legend_repair.push(result.data[i].name)
                }
                console.log("names:" + ageArr);
                myChart_repair.hideLoading();    //隐藏加载动画
                myChart_repair.setOption({        //加载数据图表
                    series: {
                        data: repairArr
                    },
                    legend: {
                        data: legend_repair
                    }
                });

            }

        },
        error: function (errorMsg) {
            //请求失败时执行该函数
            alert("图表请求数据失败!");
            myChart.hideLoading();
        }
    })

</script>
</body>

</html>