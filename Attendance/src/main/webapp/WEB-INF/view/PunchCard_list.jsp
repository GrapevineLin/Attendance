
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="x-admin-sm">
    <head>
        <meta charset="UTF-8">
        <title>班次</title>
        <meta name="renderer" content="webkit">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
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
            <a href="">打卡单</a>
            <a>
              <cite>清单</cite></a>
          </span>
          <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" onclick="location.reload()" title="刷新">
            <i class="layui-icon layui-icon-refresh" style="line-height:30px"></i></a>
        </div>
        <div class="layui-fluid">
            <div class="layui-row layui-col-space15">
                <div class="layui-col-md12">
                    <div class="layui-card">
                        <div class="layui-card-body ">
                            <form class="layui-form layui-col-space5" action="PunchCard">
                                <input type="hidden" name="oper" value="listDeal" />
<%--                                <div class="layui-inline layui-show-xs-block">--%>
<%--                                    <input class="layui-input"  autocomplete="off" placeholder="开始日" name="start" id="start">--%>
<%--                                </div>--%>
<%--                                <div class="layui-inline layui-show-xs-block">--%>
<%--                                    <input class="layui-input"  autocomplete="off" placeholder="截止日" name="end" id="end">--%>
<%--                                </div>--%>
                                <div class="layui-inline layui-show-xs-block">
                                    <input type="text" name="searchName" id="searchName" value="${searchName}" placeholder="请输入编码或者名称" autocomplete="off" class="layui-input">
                                </div>
                                <div class="layui-inline layui-show-xs-block">
                                    <button class="layui-btn"  lay-submit="" lay-filter="sreach"><i class="layui-icon">&#xe615;</i></button>
                                </div>
                            </form>
                        </div>
                        <div class="layui-card-header">
<%--                            <button class="layui-btn layui-btn-danger" onclick="delAll()"><i class="layui-icon"></i>批量删除</button>--%>
                            <button class="layui-btn" onclick="xadmin.open('添加用户','PunchCard?oper=insert',600,500)"><i class="layui-icon"></i>打卡</button>
                        </div>
                        <div class="layui-card-body layui-table-body layui-table-main">
                            <table id="datalist" class="layui-table layui-form">
                                <thead>
                                  <tr>
<%--                                    <th>--%>
<%--                                      <input type="checkbox" lay-filter="checkall" name="" lay-skin="primary">--%>
<%--                                    </th>--%>
                                    <th>ID</th>
                                    <th>打卡人编码</th>
                                    <th>打卡人姓名</th>
                                    <th>打卡日期</th>
                                    <th>备注</th>
                                  </tr>
                                </thead>
                                <tbody>

                                    <c:forEach var="item" items="${DataList}">
                                        <tr>
<%--                                            <td>--%>
<%--                                                <input type="checkbox" value="${item.punchId}" name="" lay-skin="primary"/>--%>
<%--                                            </td>--%>
                                            <td>${item.punchId}</td>
                                            <td>${item.empCode}</td>
                                            <td>${item.empName}</td>
                                            <td id="datetime">${item.date}</td>
<%--                                            <td>${item.pm}</td>--%>
                                            <td>${item.remark}</td>
<%--                                            <td class="td-manage">--%>
<%--                                                <a title="编辑"  onclick="xadmin.open('编辑[id=${item.classId}]','Classes?oper=update&id=${item.classId}',600,500)" href="javascript:;">--%>
<%--                                                    <i class="layui-icon">&#xe642;</i>--%>
<%--                                                </a>--%>
<%--                                                <a title="删除" onclick="class_del(this, ${item.classId})" href="javascript:;">--%>
<%--                                                    <i class="layui-icon">&#xe640;</i>--%>
<%--                                                </a>--%>
<%--                                            </td>--%>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
<%--                         <%-- 将_pager.jsp页面包含进来--%>
                            <jsp:include page="__pager.jsp" flush="true" />
                        </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script>
      layui.use(['laydate','form'], function(){
        var laydate = layui.laydate;
        var  form = layui.form;


        // 监听全选
        form.on('checkbox(checkall)', function(data){

          if(data.elem.checked){
            $('tbody input').prop('checked',true);
          }else{
            $('tbody input').prop('checked',false);
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
      function member_stop(obj,id){
          layer.confirm('确认要停用吗？',function(index){

              if($(obj).attr('title')=='启用'){

                //发异步把用户状态进行更改
                $(obj).attr('title','停用')
                $(obj).find('i').html('&#xe62f;');

                $(obj).parents("tr").find(".td-status").find('span').addClass('layui-btn-disabled').html('已停用');
                layer.msg('已停用!',{icon: 5,time:1000});

              }else{
                $(obj).attr('title','启用')
                $(obj).find('i').html('&#xe601;');

                $(obj).parents("tr").find(".td-status").find('span').removeClass('layui-btn-disabled').html('已启用');
                layer.msg('已启用!',{icon: 5,time:1000});
              }

          });
      }

      /*用户-删除*/
      function class_del(obj,id){
          layer.confirm('确认要删除吗？',function(index){
              $.ajax({
                  type: 'POST',
                  url: 'Classes?oper=deleteDeal&id=' + id,
                  // dataType: 'json',
                  success: function(data){
                      // console.log("666666");
                      $(obj).parents("tr").remove();
                      layer.msg('已删除!',{icon:1,time:1000});
                  },
                  error:function(data) {
                      console.log(data.msg);
                  },
              });
          });
      }



      function delAll (argument) {

          layer.confirm("确定要删除这些数据吗？", function (index) {
              var num = 0; //删除成功行数
              var total = 0; //要删除的总行数
              var obj = null; //当前对象
              var id = 0; //当前要删除的主键值
              $("#datalist input[type=checkbox]:checked").each(function () {
                  obj = this;
                  id = $(this).val();

                  if (id != null && id != "" && id != "0") {
                      total++;
                      $.ajax({
                          type: "post",
                          url: "Classes",
                          async: false,
                          data: {"oper": "deleteDeal", "id": id},
                          success: function (data) {
                              if (data == "ok") {
                                  $(obj).parents("tr").remove();
                                  num++;
                                  // layer.msg("已删除", {icon : 1, time: 1000});
                              } else {
                                  // layer.msg("删除失败", {icon : 1, time: 1000});

                              }
                          },
                          error: function (data) {
                              console.log("批量删除失败")
                          }
                      });
                      layer.msg("要删除" + total + "行记录，成功删除" + num + "行记录", {icon: 1, time: 1000});

                  }
              })
          })
      }

      var datetime = document.getElementById("datetime");
      var result = datetime.replaceAll(".0", "");
      document.getElementById("datetime").innerHTML = result;
    </script>
</html>