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
                ,{fixed: 'right', title:'操作', toolbar: '#bar', width:"20%"}
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
                layer.prompt({
                    formType: 2
                    , value: data.email
                }, function (value, index) {
                    obj.update({
                        email: value
                    });
                    layer.close(index);
                });
            }
        });
    });

</script>
</body>
</html>