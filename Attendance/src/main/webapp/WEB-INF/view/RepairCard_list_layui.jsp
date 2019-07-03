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
                <%--                <div class="layui-card-body ">--%>
                <%--                    <form class="layui-form layui-col-space5" action="RepairCard" method="get">--%>
                <%--                        &lt;%&ndash;                        <input type="hidden" name="oper" value="listDeal"/>&ndash;%&gt;--%>
                <%--                        <div class="layui-inline layui-show-xs-block">--%>
                <%--                            <input type="text" name="searchName" placeholder="请输入人员编码" autocomplete="off"--%>
                <%--                                   class="layui-input" value="${searchName}">--%>
                <%--                        </div>--%>
                <%--                        <div class="layui-inline layui-show-xs-block">--%>
                <%--                            <button class="layui-btn" lay-submit="" lay-filter="sreach"><i--%>
                <%--                                    class="layui-icon">&#xe615;</i>--%>
                <%--                            </button>--%>
                <%--                        </div>--%>
                <%--                    </form>--%>
                <%--                </div>--%>
                <div class="demoTable">
                    <br>
                    &nbsp;&nbsp;&nbsp;搜索ID：
                    <div class="layui-inline">
                        <input class="layui-input" name="id" id="demoReload" autocomplete="off">
                    </div>
                    <button class="layui-btn" data-type="reload">搜索</button>
                </div>
                <br>
                &nbsp;
                <div class="layui-btn-group demoTable">
                    <button class="layui-btn" data-type="getCheckData">批量删除</button>
                    <button class="layui-btn"
                            onclick="xadmin.open('添加','RepairCard?oper=add',500,400,false)">添加
                    </button>
                </div>
                <div class="layui-card-body layui-table-body layui-table-main">
                    <table class="layui-table layui-form" id="showlist" lay-filter="oper"></table>
                    <script type="text/html" id="toolbarDemo">
                        </div>
                        </div>
                        </div>
                        </div>
                        </div>

                        <%--操作工具栏--%>
                        <script type="text/html" id="bar">
                            <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
                            <%-- lay-event="del" 识别事件 lay-event --%>
                            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
                    </script>

                    <script>
                        layui.use('table', function () {
                            var table = layui.table,
                                excel = layui.excel;

                            table.render({
                                elem: '#showlist'
                                , height: 500
                                , url: 'RepairCard?oper=getList' //数据接口
                                , page: true //开启分页
                                , toolbar: '#toolbarDemo' //操作1:启用自定义模板表格头部工具栏
                                , cols: [[ //表头
                                    {type: 'checkbox', width: "5%", fixed: 'left'}
                                    , {field: 'repairId', title: 'ID', width: "5%", sort: true, fixed: 'left'}
                                    , {field: 'empCode', title: '补卡人编码', width: "10%"}
                                    , {field: 'empName', title: '补卡人姓名', width: "10%"}
                                    , {field: 'date', title: '补卡日期', width: "20%", sort: true}
                                    , {field: 'reason', title: '原因', width: "35%"}
                                    , {fixed: 'right', title: '操作', toolbar: '#bar', width: "15%"}
                                ]]
                            });
                            /*仍然利用table组件 对操作栏进行监听*/
                            //监听行工具事件
                            table.on('tool(oper)', function (obj) {
                                //obj obj代表选中的整行表格
                                var data = obj.data; //obj.data 代表是这行表格的数据
                                if (obj.event === 'del') {
                                    layer.confirm('真的删除这行么', function (index) {
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
                                    xadmin.open('补卡单编辑', 'RepairCard?oper=update&repairId=' + data.repairId, 600, 600, false);
                                }

                            });
                            table.on('toolbar(oper)', function (obj) {
                                var checkStatus = table.checkStatus(obj.config.id);
                                switch (obj.event) {
                                    case 'add':
                                        layer.msg('添加');
                                        break;
                                    case 'delete':
                                        layer.msg('删除');
                                        table.on('checkbox(oper)', function (obj) {
                                            console.log(obj.checked); //当前是否选中状态
                                            console.log(obj.data); //选中行的相关数据
                                            console.log(obj.type); //如果触发的是全选，则为：all，如果触发的是单选，则为：one
                                        });
                                        break;
                                    case 'update':
                                        layer.msg('编辑');
                                        break;
                                }
                            });
                            //批量删除
                            var $ = layui.$, active = {
                                getCheckData: function () { //获取选中数据
                                    var checkStatus = table.checkStatus('showlist')
                                        , data = checkStatus.data;
                                    var repairList = checkStatus.data;
                                    var repairIdList = [];
                                    checkStatus.data.forEach(function (item, index, arr) {
                                        repairIdList.push(arr[index].repairId);
                                    })
                                    console.log("repairIdList" + repairIdList);
                                    layer.confirm("确实删除" + checkStatus.data.length + "条数据吗?", function () {
                                        repairIdList.forEach(function (item, index, arr) {
                                            $.ajax({
                                                type: 'GET',
                                                url: 'RepairCard',
                                                data: {
                                                    "oper": "delete",
                                                    "repairIdList": arr[index]
                                                },
                                                dataType: 'JSON',
                                                success: function (data) {
                                                    if (data.code = "200") {
                                                    } else {
                                                        layer.msg("这回删除失败了");
                                                    }
                                                },
                                                error: function (data) {
                                                }
                                            });
                                            table.reload('showlist');
                                            layer.msg("删除成功");
                                        })
                                    })
                                },
                                reload: function () {
                                    var demoReload = $('#demoReload');
                                    console.log("demoReload.val()" + demoReload.val());
                                    //执行重载
                                    tableIns.reload({
                                        page: {
                                            curr: 1 //重新从第 1 页开始
                                        }
                                        , where: {
                                            key: {
                                                repairId: demoReload.val()
                                            }
                                        }
                                    });
                                }
                            };
                            $('.demoTable .layui-btn').on('click', function () {
                                var type = $(this).data('type');
                                active[type] ? active[type].call(this) : '';
                            });

                            //监听头工具栏事件
                            table.on('toolbar(oper)', function (obj) {
                                var checkStatus = table.checkStatus(obj.config.id),
                                    data = checkStatus.data; //获取选中的数据
                                switch (obj.event) {
                                    case 'table_export':
                                        exportFile('showlist')
                                        break;
                                }
                                ;
                            });

                            /**
                             * by yutons
                             * Array.from() 非常方便的将一个类数组的集合 ==》 数组，直接使用数组身上的方法。例如：常用的map，foreach…
                             * 但是，问题来了，IE不识别Array.from这个方法。所以写了它兼容IE的写法。
                             */
                            if (!Array.from) {
                                Array.from = function (el) {
                                    return Array.apply(this, el);
                                }
                            }

                            //表格导出
                            function exportFile(id) {
                                //根据传入tableID获取表头
                                var headers = $("div[lay-id=" + id + "] .layui-table-box table").get(0);
                                var htrs = Array.from(headers.querySelectorAll('tr'));
                                var titles = {};
                                for (var j = 0; j < htrs.length; j++) {
                                    var hths = Array.from(htrs[j].querySelectorAll("th"));
                                    for (var i = 0; i < hths.length; i++) {
                                        var clazz = hths[i].getAttributeNode('class').value;
                                        if (clazz != ' layui-table-col-special' && clazz != 'layui-hide') {
                                            //排除居左、具有、隐藏字段
                                            //修改:默认字段data-field+i,兼容部分数据表格中不存在data-field值的问题
                                            titles['data-field' + i] = hths[i].innerText;
                                        }
                                    }
                                }
                                //根据传入tableID获取table内容
                                var bodys = $("div[lay-id=" + id + "] .layui-table-box table").get(1);
                                var btrs = Array.from(bodys.querySelectorAll("tr"))
                                var bodysArr = new Array();
                                for (var j = 0; j < btrs.length; j++) {
                                    var contents = {};
                                    var btds = Array.from(btrs[j].querySelectorAll("td"));
                                    for (var i = 0; i < btds.length; i++) {
                                        for (var key in titles) {
                                            //修改:默认字段data-field+i,兼容部分数据表格中不存在data-field值的问题
                                            var field = 'data-field' + i;
                                            if (field === key) {
                                                //根据表头字段获取table内容字段
                                                contents[field] = btds[i].innerText;
                                            }
                                        }
                                    }
                                    bodysArr.push(contents)
                                }
                                //将标题行置顶添加到数组
                                bodysArr.unshift(titles);
                                //导出excel
                                excel.exportExcel({
                                    sheet1: bodysArr
                                }, '补卡单' + new Date().toLocaleString() + '.xlsx', 'xlsx');
                            }


                        });

                    </script>
</body>
</html>