<%--
  Created by IntelliJ IDEA.
  User: 31660
  Date: 2019/6/28 0028
  Time: 14:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>更新</title>
    <%--想要使用 xadmin 要导入这么些 --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/X-admin/css/font.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/X-admin/css/xadmin.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/X-admin/lib/layui/layui.js"
            charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/X-admin/js/xadmin.js"></script>
</head>
<body>
<form class="layui-form">
    <input type="hidden" name="repairId" value="${repairId}"/>
    <div class="layui-form-item">
        <label class="layui-form-label">补卡人</label>
        <div class="layui-input-inline">
            <select class="layui-select" name="empCode" id="empCode">
                <option value="无">请选择</option>
                <c:forEach var="item" items="${employeeList}">
                    <option value="${item.empCode}">${item.empName}</option>
                </c:forEach>
            </select>

        </div>

    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">补卡时间</label>
        <div class="layui-input-block">
            <input type="date" name="date" required lay-verify="required" placeholder="请输入补卡时间" autocomplete="off"
                   class="layui-input" value="${date}">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">补卡原因</label>
        <div style="margin-left:110px;top:-5px;padding:0">
                    <textarea id="reason" name="reason" rows="4" cols="4" class="layui-textarea"
                              value="${reason}">${reason}</textarea>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script>
    //Demo
    /*表单监听*/
    layui.use('form', function () {
        var form = layui.form;
        //监听提交
        form.on('submit(formDemo)', function (data) {
            /*提交的时候要发送一个ajax请求*/
            console.log(data);
            $.ajax({
                type: 'POST',
                url: 'RepairCard',
                data: {
                    'oper': 'updateDeal',
                    'repairId': data.field.repairId,
                    'empCode': data.field.empCode,
                    'date': data.field.date,
                    'reason': data.field.reason
                },
                dataType: 'JSON',
                success: function (info) {
                    if (info.code == 200) {
                        //如果修改成功了
                        //要关闭当前页面 并且刷新父页面
                        xadmin.close();
                        xadmin.father_reload();
                    } else {
                        layer.msg("对不起 更新失败了！");
                    }
                }
            })

            return false;
        });
    });
</script>
</body>
</html>