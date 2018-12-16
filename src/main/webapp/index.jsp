<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%
    //String path = request.getContextPath();
    //String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>search index</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!-- 引入bootstrap样式 -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <!-- 引入bootstrap-table样式 -->
    <link href="https://cdn.bootcss.com/bootstrap-table/1.11.1/bootstrap-table.min.css" rel="stylesheet">

    <!-- jquery -->
    <script src="https://cdn.bootcss.com/jquery/2.2.3/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

    <!-- bootstrap-table.min.js -->
    <script src="https://cdn.bootcss.com/bootstrap-table/1.11.1/bootstrap-table.min.js"></script>
    <!-- 引入中文语言包 -->
    <script src="https://cdn.bootcss.com/bootstrap-table/1.11.1/locale/bootstrap-table-zh-CN.min.js"></script>

    <script type="text/javascript">
        function search() {
            $("#commentTable").bootstrapTable('removeAll');
            $("#commentTable").bootstrapTable({
                method: "get",  //使用get请求到服务器获取数据
                url: "universe-app/service/search/search", //获取数据的Servlet地址
                pagination: true, //启动分页
                pageSize: 10,  //每页显示的记录数
                pageNumber: 1, //当前第几页
                pageList: [5, 10, 15, 20, 25],  //记录数可选列表
                smartDisplay: false, //配置为false后，当记录条数不满足上一行最小值5条时仍然可以显示 记录数可选列表和页数导航！
                search: false,  //是否启用查询
                showColumns: true,  //显示下拉框勾选要显示的列
                showRefresh: true,  //显示刷新按钮
                //sidePagination: "client", //客户端分页
                sidePagination: "server", //服务器端分页
                height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "rowkey",                 //每一行的唯一标识，一般为主键列
                showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
                cardView: false,                    //是否显示详细视图
                detailView: false,                  //是否显示父子表
                minimumCountColumns: 2,             //最少允许的列数
                clickToSelect: true,                //是否启用点击选中行
                //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
                //设置为limit可以获取limit, offset, search, sort, order
                queryParamsType: "undefined",
                queryParams: function queryParams(params) {   //设置查询参数
                    var param = {
                        keyWord: $("#keyWord").val(),
                    };
                    return param;
                },
                columns: [{
                    field: 'rowkey',
                    title: '序号',
                }, {
                    field: 'title',
                    title: '电影',
                }, {
                    field: 'star',
                    title: '星级'
                }, {
                    field: 'comment',
                    title: '评论'
                }]

            });
            $("#commentTable").bootstrapTable('refresh');
        }


        function resetTable() {
            console.log("sdfs");

            $("#table").bootstrapTable('destroy');
            $("#table").bootstrapTable('refresh');
            $('#table').bootstrapTable({});
        }

        $(document).ready(function () {
            //调用函数，初始化表格
            search();
        })
    </script>

    <style>
        .col-center-block {
            float: none;
            display: block;
            margin-left: auto;
            margin-right: auto;
        }
    </style>

</head>

<body>

<br/>
<br/>
<br/>
<br/>
<br/>
<br/>

<div class="container">
    <div class="row myCenter">
        <div class="col-xs-6 col-md-4 col-center-block">
            <form class="form-signin">
                <h2 class="form-signin-heading">ElasticSearch+HBase搜索</h2>
                <label for="keyWord" class="sr-only">关键词</label>
                <input type="text" id="keyWord" class="form-control" placeholder="关键词" required autofocus>
                <%--<label for="inputPassword" class="sr-only">密码</label>--%>
                <%--<input type="password" id="inputPassword" class="form-control" placeholder="密码" required>--%>
                <br/>
                <button class="btn btn-lg btn-primary btn-block" type="button" onclick="search()">mylovin一下</button>
            </form>
        </div>
    </div>
</div>


<%--<div id="form-div">--%>
    <%--<form id="form1" onsubmit="return false" action="##" method="post">--%>
        <%--<p>关键词：<input name="keyWord" type="text" id="keyWord" tabindex="1" size="15" value=""/></p>--%>
        <%--<p><input type="button" value="百度一下" onclick="search()">&nbsp;<input type="button" onclick="resetTable()"--%>
                                                                             <%--value="重置"></p>--%>
    <%--</form>--%>
<%--</div>--%>

<table class="table table-striped table-bordered table-condensed" id="commentTable">
</table>
</body>
</html>
