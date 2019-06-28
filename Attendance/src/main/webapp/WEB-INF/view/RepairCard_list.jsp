<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>补卡单</title>
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
            <a href="">补卡单</a>
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
                            onclick="xadmin.open('添加','Station?oper=insert',600,500,false)"><i
                            class="layui-icon"></i>添加
                    </button>
                </div>
                <div class="layui-card-body layui-table-body layui-table-main">
                    <table class="layui-table layui-form" id="showlist" lay-filter="oper"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<%--编辑页面--%>
<div class="layui-row" id="Update" style="display:none;">
    <div class="layui-col-md10">
        <form class="layui-form layui-from-pane" action="" style="margin-top:20px">
            <div class="layui-form-item">
                <label class="layui-form-label">补卡人</label>
                <div class="layui-input-block">
                    <input type="text" name="empCode" required lay-verify="required" autocomplete="off"
                           placeholder="请输入补卡人编码" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">补卡时间</label>
                <div class="layui-input-block">
                    <input type="date" name="date" required autocomplete="off" placeholder="请输入补卡时间"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">补卡原因</label>
                <div class="layui-input-block">
                    <div style="margin-left:0px;top:-5px;padding:0">
                    <textarea id="reason" name="reason" rows="4" cols="4" class="layui-textarea"
                              value=""></textarea>
                    </div>
                </div>
            </div>

            <div class="layui-form-item" style="margin-top:40px">
                <div class="layui-input-block">
                    <button class="layui-btn  layui-btn-submit " lay-submit="" lay-filter="demo11">确认修改</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</div>

<%--操作工具栏--%>
<script type="text/html" id="bar">
    <a class="layui-btn layui-btn-xs" lay-event="edit">查看</a>
    <%-- lay-event="del" 识别事件 lay-event --%>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script>
    layui.use('table', function () {
        var table = layui.table;
        table.render({
            elem: '#showlist'
            , height: 312
            , url: 'RepairCard?oper=getList' //数据接口
            , page: true //开启分页
            , cols: [[ //表头
                {field: 'repairId', title: 'ID', width: "10%", sort: true, fixed: 'left'}
                , {field: 'empCode', title: '补卡人编码', width: "20%"}
                , {field: 'date', title: '补卡日期', width: "20%", sort: true}
                , {field: 'reason', title: '原因', width: "30%"}
                , {fixed: 'right', title: '操作', toolbar: '#bar', width: "20%"}
            ]]
        });
        /*仍然利用table组件 对操作栏进行监听*/
        //监听行工具事件
        table.on('tool(oper)', function (obj) {
            //obj obj代表选中的整行表格
            var data = obj.data; //obj.data 代表是这行表格的数据
            //console.log(obj)
            if (obj.event === 'del') {
                layer.confirm('真的删除行么', function (index) {
                    /* 弹出确认框,如果你点击确定 就会执行这下面的内容*/
                    $.ajax({
                        type: 'GET',
                        url: 'RepairCard?oper=delete',
                        data: {
                            "repairId": data.repairId
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

        // function setFormValue(obj, data) {
        //     form.on('submit(demo11)', function (massage) {
        //         $.ajax({
        //             url: 'RepairCard?oper=delete',
        //             type: 'POST',
        //             data: {
        //                 repairId: data.repairId,
        //                 // empCode: data.empCode,
        //                 // date: data.date,
        //                 // reason: data.reason
        //             },
        //             success: function (msg) {
        //                 var returnCode = msg.returnCode;//取得返回数据（Sting类型的字符串）的信息进行取值判断
        //                 if (returnCode == 200) {
        //                     layer.closeAll('loading');
        //                     layer.load(2);
        //                     layer.msg("修改成功", {icon: 6});
        //                     setTimeout(function () {
        //                         obj.update({
        //                             eqptType: massage.field.neweqptType,
        //                             eqptIdCode: massage.field.neweqptIdCode,
        //                             eqptName: massage.field.neweqptName
        //                         });//修改成功修改表格数据不进行跳转
        //                         layer.closeAll();//关闭所有的弹出层
        //                     }, 1000);
        //                     加载层 - 风格
        //                 } else {
        //                     layer.msg("修改失败", {icon: 5});
        //                 }
        //             }
        //         })
        //     })
        //
        // }

    });

</script>
</body>
</html>