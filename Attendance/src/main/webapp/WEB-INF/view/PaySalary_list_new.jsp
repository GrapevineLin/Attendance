<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="x-admin-sm">
<head>
    <meta charset="utf-8">
    <title>派薪单</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/X-admin/css/font.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/X-admin/css/xadmin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/X-admin/lib/layui/css/layui.css" media="all">
    <script src="${pageContext.request.contextPath}/static/X-admin/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/X-admin/js/xadmin.js"></script>
</head>
<body>
<div class="x-nav">
          <span class="layui-breadcrumb">
            <a href="">首页</a>
            <a href="">部门</a>
            <a>
              <cite>清单</cite></a>
          </span>
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right"
       onclick="location.reload()" title="刷新">
        <i class="layui-icon layui-icon-refresh" style="line-height:30px"></i></a>
</div>
<%--查询和添加模块--%>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-body ">
                    <form class="layui-form layui-col-space5" action="PaySalary" method="get">
                        <input type="hidden" name="oper" value="listDeal"/>
                        <div class="layui-inline layui-show-xs-block">
                            <input type="text" name="searchName" placeholder="请输入人员编码或者名称" autocomplete="off"
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
                    <button class="layui-btn layui-btn-danger" onclick="delAll()"><i class="layui-icon"></i>批量删除
                    </button>
                    <button href="javascript:;" class="layui-btn"
                            onclick="xadmin.open('添加','PaySalary?oper=insert',600,600,false)"><i
                            class="layui-icon"></i>添加
                    </button>
                </div>
                <div class="layui-card-body layui-table-body layui-table-main">
                    <table class="layui-table layui-form" id="PaySalary" lay-filter="oper"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<%-- 表格内容 --%>
<table id="demo" lay-filter="test"></table>

<%--操作工具栏--%>
<script type="text/html" id="tool">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <%-- lay-event="del" 识别事件 lay-event --%>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script>

    layui.use(['laydate', 'form', 'table'], function () {
        var laydate = layui.laydate;
        var form = layui.form;
        var table = layui.table;


        // 监听全选
        form.on('checkbox(checkall)', function (data) {

            if (data.elem.checked) {
                $('tbody input').prop('checked', true);
            } else {
                $('tbody input').prop('checked', false);
            }
            form.render('checkbox');
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#start' //指定元素
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#end' //指定元素
        });

        table.render({
            elem: '#PaySalary'
            , height: 312
            , url: 'Pay?oper=listdeal' //数据接口
            , page: true //开启分页
            , cols: [[ //表头
                {checkbox: true, fixed: true}
                ,{field: 'payId', title: 'ID',  sort: true, fixed: 'left'}
                , {field: 'empCode', title: '领薪人编码' }
                , {field: 'empName', title: '领薪人姓名', sort: true}
                , {field: 'salary', title: '薪水' }
                , {field: 'beginDate', title: '计算开始日期' }
                , {field: 'endDate', title: '计算结束日期',  sort: true}
                , {fixed: 'right', title: '操作', toolbar: '#tool' }
            ]]
        });

        table.on('tool(oper)', function (obj) {
            //obj obj代表选中的整行表格
            var data = obj.data; //obj.data 代表是这行表格的数据
            //console.log(obj)
            if (obj.event === 'del') {
                layer.confirm('真的删除行么', function (index) {
                    /* 弹出确认框,如果你点击确定 就会执行这下面的内容*/
                    $.ajax({
                        type: 'GET',
                        url: 'Pay?oper=delete',
                        data: {
                            "payId": data.payId
                        },
                        dataType: 'JSON',
                        success: function (info) {
                            if (info.code == 200) {
                                //如果后台删除成功了，我前台才删除
                                obj.del();
                            } else {
                                layer.msg("这回删除失败了");
                            }
                        }

                    });
                    layer.close(index);
                });
            } else if (obj.event === 'edit') {
                //编辑先忽略
                layer.open({
                    //layer提供了5种层类型。可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                    type: 1,
                    area: [500 + 'px', 600 + 'px'],
                    fix: false, //不固定
                    maxmin: true,
                    shadeClose: true,
                    shade: 0.4,
                    title: "编辑补卡单",
                    // content: 'RepairCard?oper=update&repairId=' + data.repairId
                    content: $("#Update")
                });
                // setFormValue(obj, data);
                // layer.prompt({
                //     formType: 2
                //     , value: data.reason
                // }, function (value, index) {
                //     obj.update({
                //         email: value
                //     });
                //     layer.close(index);
                // });
            }
        });

    });


</script>
</body>
</html>