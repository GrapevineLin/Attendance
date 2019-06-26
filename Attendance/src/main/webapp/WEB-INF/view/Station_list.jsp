<%--
  Created by IntelliJ IDEA.
  User: 31660
  Date: 2019/6/26 0026
  Time: 10:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="x-admin-sm">
<head>
    <meta charset="UTF-8">
    <title>岗位页面-X-admin2.2</title>
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
            <a href="">岗位</a>
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
                    <form class="layui-form layui-col-space5" action="Station" method="get">
                        <input type="hidden" name="oper" value="listDeal"/>
                        <div class="layui-inline layui-show-xs-block">
                            <input type="text" name="searchName" placeholder="请输入编码或者名称" autocomplete="off"
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
                            onclick="xadmin.open('添加用户','Station?oper=insert',800,600,false)"><i
                            class="layui-icon"></i>添加
                    </button>
                </div>
                <div class="layui-card-body layui-table-body layui-table-main">
                    <table class="layui-table layui-form" id="datalist">
                        <thead>
                        <tr>
                            <th>
                                <input type="checkbox" lay-filter="checkall" name="" lay-skin="primary">
                            </th>
                            <th>ID</th>
                            <th>岗位编码</th>
                            <th>岗位名称</th>
                            <th>所在部门</th>
                            <th>直接上级</th>
                            <th>岗位类别</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${DataList }">
                            <tr>
                                <td>
                                    <input type="checkbox" name="" value="${item.jobId}" lay-skin="primary">
                                </td>
                                <td>${item.jobId}</td>
                                <td>${item.jobCode}</td>
                                <td>${item.jobName}</td>
                                <td>${item.dep}</td>
                                <td>${item.dirSup}</td>
                                <td>${item.jobCat}</td>
                                    <%--<td class="td-status">
                                      <span class="layui-btn layui-btn-normal layui-btn-mini">已启用</span></td>
                                    <td class="td-manage">
                                      <a onclick="station_stop(this,'10001')" href="javascript:;"  title="启用">
                                        <i class="layui-icon">&#xe601;</i>
                                    </a>--%>
                                <td class="td-manage">
                                    <a title="编辑"
                                       onclick="xadmin.open('编辑[id=${item.jobId}]','Station?oper=update&id=${item.jobId}','800','500',false)"
                                       href="javascript:;">
                                        <i class="layui-icon">&#xe642;</i>
                                    </a>
                                    <a title="删除" onclick="station_del(this,${item.jobId})" href="javascript:;">
                                        <i class="layui-icon">&#xe640;</i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <jsp:include page="__pager.jsp" flush="true"/>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>

    /*项目-增加*/
    function item_add(title, url, w, h) {
        layer_show(title, url, w, h)
    }

    layui.use(['laydate', 'form'], function () {
        var laydate = layui.laydate;
        var form = layui.form;


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


    });

    /*用户-停用*/
    function station_stop(obj, id) {
        layer.confirm('确认要停用吗？', function (index) {

            if ($(obj).attr('title') == '启用') {

                //发异步把用户状态进行更改
                $(obj).attr('title', '停用')
                $(obj).find('i').html('&#xe62f;');

                $(obj).parents("tr").find(".td-status").find('span').addClass('layui-btn-disabled').html('已停用');
                layer.msg('已停用!', {icon: 5, time: 1000});

            } else {
                $(obj).attr('title', '启用')
                $(obj).find('i').html('&#xe601;');

                $(obj).parents("tr").find(".td-status").find('span').removeClass('layui-btn-disabled').html('已启用');
                layer.msg('已启用!', {icon: 5, time: 1000});
            }

        });
    }

    /*原本-删除*/
    // function station_del(obj, id) {
    //     layer.confirm('确认要删除吗？', function (index) {
    //         //发异步删除数据
    //         $(obj).parents("tr").remove();
    //         layer.msg('已删除!', {icon: 1, time: 1000});
    //     });
    // }

    /*删除*/
    function station_del(obj, id) {
        layer.confirm('确认要删除吗？', function (index) {
            $.ajax({
                type: 'POST',
                url: 'Station?oper=deleteDeal&jobId=' + id,
                //dataType: 'json',
                success: function (data) {
                    if (data == "ok") {
                        $(obj).parents("tr").remove();
                        layer.msg('已删除!', {icon: 1, time: 1000});
                    } else {
                        layer.msg('删除失败', {icon: 1, time: 100});
                    }
                },
                error: function (data) {
                    console.log(data.msg);
                },
            });
        });
    }

    /*项目-查看*/
    function item_detail(title, url, id, w, h) {
        layer_show(title, url, w, h);
    }

    // function delAll(argument) {
    //     var ids = [];
    //
    //     // 获取选中的id
    //     $('tbody input').each(function (index, el) {
    //         if ($(this).prop('checked')) {
    //             ids.push($(this).val())
    //         }
    //     });
    //
    //     layer.confirm('确认要删除吗？' + ids.toString(), function (index) {
    //         //捉到所有被选中的，发异步进行删除
    //         layer.msg('删除成功', {icon: 1});
    //         $(".layui-form-checked").not('.header').parents('tr').remove();
    //     });
    // }

    /*批量-删除*/
    function delAll() {
        layer.confirm("确认要删除选中的数据", function (index) {
            var num = 0;      //删除成功的行数
            var total = 0;    //要删除的总行数
            var obj = null;   //当前对象
            var id = 0;       //当前要删除的主键值
            $('#datalist input[type=checkbox]:checked').each(function () {
                obj = this;
                id = $(this).val();     //取得，当前复选框代表的主键值
                //排除全选或反选的复选框
                if (id != null && id != "" && id != 0) {
                    total++;    //总行数
                    //Start : ajax方式，一行一行删除
                    $.ajax({
                        type: 'POST',
                        url: 'Station',
                        async: false, //是否异步
                        data: {"oper": "deleteDeal", "jobId": id},
                        success: function (data) {
                            if (data = "ok") {
                                $(obj).parents("tr").remove();
                                num++;  //删除成功的数+1
                            } else {

                            }
                        },
                        error: function (data) {
                        }
                    });
                    //End ： ajax方式，一行一行删除
                }
            });
            layer.msg("要删除" + total + "行记录，成功删除" + num + "行记录。", {
                icon: 1,
                time: 1000
            });
        });
    }


</script>
</html>