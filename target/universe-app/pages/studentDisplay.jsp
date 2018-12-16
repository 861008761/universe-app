<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bootstrap 101 Template</title>

    <%--<!-- Bootstrap -->--%>
    <%--<link rel="stylesheet" href="<%=request.getContextPath()%>/style/bootstrap/css/bootstrap.css">--%>
    <%--<link rel="stylesheet" href="<%=request.getContextPath()%>/style/bootstrap-table/dist/bootstrap-table.css">--%>

    <%--<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->--%>
    <%--<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>--%>
    <%--<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->--%>
    <%--<script src="/universe-app/style/bootstrap/js/bootstrap.js"></script>--%>

    <%--<script src="/universe-app/style/bootstrap-table/dist/bootstrap-table.js"></script>--%>

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

</head>
<body>
<script type="text/javascript">
    function initTable() {
        //初始化表格,动态从服务器加载数据
        $("#cusTable").bootstrapTable({
            method: "get",  //使用get请求到服务器获取数据
            url: "/universe-app/service/config/getPaginationConfigs", //获取数据的Servlet地址
            toolbar: '#toolbar', //工具按钮用哪个容器
            pagination: true, //启动分页
            pageSize: 10,  //每页显示的记录数
            pageNumber: 1, //当前第几页
            pageList: [5, 10, 15, 20, 25],  //记录数可选列表
            smartDisplay: false, //配置为false后，当记录条数不满足上一行最小值5条时仍然可以显示 记录数可选列表和页数导航！
            search: false,  //是否启用查询
            showColumns: true,  //显示下拉框勾选要显示的列
            showRefresh: true,  //显示刷新按钮
            sidePagination: "server", //表示服务端请求
            rowStyle:function(row,index){
                if (index%2==0){
                    return {css:{"background-color":"white"}}
                }else{
                    return {css:{"background-color":"yellow"}}
                }
            },
            height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "dataSourceName",         //每一行的唯一标识，一般为主键列
            showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                  //是否显示父子表
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType: "undefined",
            queryParams: function queryParams(params) {   //设置查询参数
                var param = {
                    pageNumber: params.pageNumber,
                    pageSize: params.pageSize,
                    dataSourceName: $("#txt_search_dataSourceName").val(),
                    netType: $("#txt_search_netType").val()
                };
                return param;
            },
            columns: [{
                checkbox: true
            }, {
                field: 'rowID',
                title: '序号',
                visible: false
            }, {
                field: 'dataSourceName',
                title: 'dataSource名称',

            }, {
                field: 'netType',
                title: '网系'
            }, {
                field: 'dynamicType',
                title: '动态数据类型'
            }, {
                field: 'dbType',
                title: '数据库类型'
            }, {
                field: 'dbIP',
                title: '数据库IP地址'
            }, {
                field: 'dbPort',
                title: '数据库端口'
            }, {
                field: 'dbName',
                title: '数据库名称或Sid'
            }, {
                field: 'url',
                title: '数据库连接字符串'
            }, {
                field: 'username',
                title: '用户名'
            }, {
                field: 'password',
                title: '密码'
            },]

        });
    }

    $(document).ready(function () {
        //调用函数，初始化表格
        initTable();

        //当点击查询按钮的时候执行
        $("#btn_query").bind("click", initTable);

        //删除
        $('#btn_delete').on("click", function () {
            var row = $.map($("#cusTable").bootstrapTable('getSelections'), function (row) {
                return row;
            });
            var ids = [];
            for (var i = 0; i < row.length; i++) {
                //获取自定义table 的中的checkbox值
                var id = row[i].pid;   //OTRECORDID这个是你要在列表中取的单个id
                ids.push(id); //然后把单个id循环放到ids的数组中
            }
            var str = JSON.stringify(ids);
            //批量删除：
            $.ajax({
                type: "POST",
                url: "/delPerson.action",
                data: "str=" + str,
                success: function (msg) {
                    // alert("返回的是："+msg);
                    if (msg == 1) {
                        // alert("删除成功！");
                        $('#cusTable').bootstrapTable('refresh');
                    } else {
                        alert("删除失败！");
                    }
                }
            });
        });

        //添加：
        $('#btn_add').on("click", function () {
            $('#myModal').modal();
        });

        //修改
        $('#btn_edit').on("click", function () {
            var row = $.map($("#cusTable").bootstrapTable('getSelections'), function (row) {
                return row;
            });
            if (row.length == 1) {
                for (var i = 0; i < row.length; i++) {
                    //alert(row[i].pid);
                    //打开弹出框：
                    $('#updatemyModal').modal();
                    //赋值
                    $('#updaterowID').val(parseInt(row[i].rowID));
                    $('#updatedataSourceName').val(row[i].dataSourceName);
                    $('#updatenetType').val(row[i].netType);
                    $('#updatedynamicType').val(row[i].dynamicType);
                    $('#updatedbType').val(row[i].dbType);
                    $('#updatedbIP').val(row[i].dbIP);
                    $('#updatedbPort').val(row[i].dbPort);
                    $('#updatedbName').val(row[i].dbName);
                    $('#updateurl').val(row[i].url);
                    $('#updateusername').val(row[i].username);
                    $('#updatepassword').val(row[i].password);
                }
            }
        });


    })

    //提交添加：
    function mya() {
        var adddataSourceName = $('#adddataSourceName').val();
        var addnetType = $('#addnetType').val();
        var adddynamicType = $('#adddynamicType').val();
        var adddbType = $('#adddbType').val();
        var adddbIP = $('#adddbIP').val();
        var adddbPort = $('#adddbPort').val();
        var adddbName = $('#adddbName').val();
        var addurl = $('#addurl').val();
        var addusername = $('#addusername').val();
        var addpassword = $('#addpassword').val();
        //实例化一个对象：
        var config =
            {
                "rowID": 0,//这个参数只为了满足后端解析，修改的时候会用到！
                "dataSourceName": adddataSourceName,
                "netType": addnetType,
                "dynamicType": adddynamicType,
                "dbType": adddbType,
                "dbIP": adddbIP,
                "dbPort": adddbPort,
                "dbName": adddbName,
                "url": addurl,
                "username": addusername,
                "password": addpassword
            };
        //将对象转换成字符串
        var str = JSON.stringify(config);
        //alert(str);
        $.ajax({
            type: "POST",
            url: "/universe-app/service/config/addConfig",
            data: "cfg=" + str,
            success: function (msg) {
                if (msg == 1) {
                    alert('添加成功！');
                    $('#cusTable').bootstrapTable('refresh');
                    $('#ff_p').form('clear');
                } else {
                    alert('添加失败！');
                }

            }
        });
    }

    //修改提交：
    function myb() {
        var updaterowID = $('#updaterowID').val();
        var updatedataSourceName = $('#updatedataSourceName').val();
        var updatenetType = $('#updatenetType').val();
        var updatedynamicType = $('#updatedynamicType').val();
        var updatedbType = $('#updatedbType').val();
        var updatedbIP = $('#updatedbIP').val();
        var updatedbPort = $('#updatedbPort').val();
        var updatedbName = $('#updatedbName').val();
        var updateurl = $('#updateurl').val();
        var updateusername = $('#updateusername').val();
        var updatepassword = $('#updatepassword').val();
        //实例化一个对象：
        var config =
            {
                "rowID": updaterowID,
                "dataSourceName": updatedataSourceName,
                "netType": updatenetType,
                "dynamicType": updatedynamicType,
                "dbType": updatedbType,
                "dbIP": updatedbIP,
                "dbPort": updatedbPort,
                "dbName": updatedbName,
                "url": updateurl,
                "username": updateusername,
                "password": updatepassword
            };
        //将对象转换成字符串
        var str = JSON.stringify(config);
        $.ajax({
            type: "POST",
            url: "/universe-app/service/config/editConfig",
            data: "cfg=" + str,
            success: function (msg) {
                if (msg == 1) {
                    alert('修改成功！');
                    $('#cusTable').bootstrapTable('refresh');
                } else {
                    alert('修改失败！');
                }

            }
        });
    }

</script>

<div class="panel-body" style="padding-bottom:0px;">
    <div class="panel panel-default">
        <div class="panel-heading">查询条件</div>
        <div class="panel-body">
            <form id="formSearch" class="form-horizontal">
                <div class="form-group" style="margin-top:15px">
                    <label class="control-label col-sm-1" for="txt_search_dataSourceName">数据源名称</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="txt_search_dataSourceName">
                    </div>
                    <label class="control-label col-sm-1" for="txt_search_netType">网系</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="txt_search_netType">
                    </div>
                    <div class="col-sm-4" style="text-align:left;">
                        <button type="button" onclick="mya()" id="btn_query" class="btn btn-primary">查询</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div id="toolbar" class="btn-group">
        <button id="btn_add" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
        </button>
        <button id="btn_edit" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
        </button>
        <button id="btn_delete" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
        </button>
    </div>

    <table class="table table-hover" id="cusTable">
    </table>
</div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="myModalLabel">新增</h4>
            </div>
            <div class="modal-body">
                <form id="ff_p" method="post">
                    <div class="dropdown">
                        <button class="<%--btn btn-default dropdown-toggle--%>form-control" type="button" id="menu1" data-toggle="dropdown">教程
                            <span class="caret"></span></button>
                        <ul class="dropdown" role="menu" aria-labelledby="menu1">
                            <li role="presentation"><a role="menuitem" tabindex="-1" href="#">HTML</a></li>
                            <li role="presentation"><a role="menuitem" tabindex="-1" href="#">CSS</a></li>
                            <li role="presentation"><a role="menuitem" tabindex="-1" href="#">JavaScript</a></li>
                            <li role="presentation" class="divider"></li>
                            <li role="presentation"><a role="menuitem" tabindex="-1" href="#">关于我们</a></li>
                        </ul>
                    </div>

                    <div class="form-group">
                        <label for="adddataSourceName">数据源名称</label>
                        <input type="text" name="dataSourceName" class="form-control" id="adddataSourceName"
                               placeholder="数据源名称">
                    </div>

                    <div class="form-group">
                        <label for="addnetType">网系</label>
                        <input type="text" name="netType" class="form-control" id="addnetType" placeholder="网系">
                    </div>

                    <div class="form-group">
                        <label for="adddynamicType">动态数据类型</label>
                        <input type="text" name="dynamicType" class="form-control" id="adddynamicType"
                               placeholder="动态数据类型">
                    </div>

                    <div class="form-group">
                        <label for="adddbType">数据库类型</label>
                        <input type="text" name="dbType" class="form-control" id="adddbType" placeholder="数据库类型">
                    </div>
                    <div class="form-group">
                        <label for="adddbIP">数据库IP地址</label>
                        <input type="text" name="dbIP" class="form-control" id="adddbIP" placeholder="数据库IP地址">
                    </div>
                    <div class="form-group">
                        <label for="adddbPort">数据库端口</label>
                        <input type="text" name="dbPort" class="form-control" id="adddbPort" placeholder="数据库端口">
                    </div>
                    <div class="form-group">
                        <label for="adddbName">数据库名称或Sid</label>
                        <input type="text" name="dbName" class="form-control" id="adddbName" placeholder="数据库名称或Sid">
                    </div>
                    <div class="form-group">
                        <label for="addurl">数据库连接字符串</label>
                        <input type="text" name="url" class="form-control" id="addurl" placeholder="数据库连接字符串">
                    </div>
                    <div class="form-group">
                        <label for="addusername">用户名</label>
                        <input type="text" name="username" class="form-control" id="addusername" placeholder="用户名">
                    </div>
                    <div class="form-group">
                        <label for="addpassword">密码</label>
                        <input type="text" name="password" class="form-control" id="addpassword" placeholder="密码">
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal"><span
                                class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭
                        </button>
                        <button type="button" id="btn_submit" class="btn btn-primary" data-dismiss="modal"
                                onclick="mya()"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="updatemyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="updatemyModalLabel">修改</h4>
            </div>
            <div class="modal-body">
                <form id="updateff_p" method="post">
                    <%--<div class="form-group">--%>
                    <%--<label for="updatedataSourceName">数据源名称</label>--%>
                    <%--<input type="text" name="dataSourceName" class="form-control" id="updatedataSourceName" placeholder="数据源名称">--%>
                    <%--</div>--%>
                    <%-- 序号不能修改，只能回传 --%>
                    <div class="form-group">
                        <label for="updaterowID">序号</label>
                        <input type="text" name="netType" disabled="disabled" class="form-control" id="updaterowID"
                               placeholder="序号">
                    </div>

                    <div class="form-group">
                        <label for="updatedataSourceName">数据源名称</label>
                        <input type="text" name="dataSourceName" class="form-control" id="updatedataSourceName"
                               placeholder="数据源名称">
                    </div>

                    <div class="form-group">
                        <label for="updatenetType">网系</label>
                        <input type="text" name="netType" class="form-control" id="updatenetType" placeholder="网系">
                    </div>

                    <div class="form-group">
                        <label for="updatedynamicType">动态数据类型</label>
                        <input type="text" name="dynamicType" class="form-control" id="updatedynamicType"
                               placeholder="动态数据类型">
                    </div>

                    <div class="form-group">
                        <label for="updatedbType">数据库类型</label>
                        <input type="text" name="dbType" class="form-control" id="updatedbType" placeholder="数据库类型">
                    </div>
                    <div class="form-group">
                        <label for="updatedbIP">数据库IP地址</label>
                        <input type="text" name="dbIP" class="form-control" id="updatedbIP" placeholder="数据库IP地址">
                    </div>
                    <div class="form-group">
                        <label for="updatedbPort">数据库端口</label>
                        <input type="text" name="dbPort" class="form-control" id="updatedbPort" placeholder="数据库端口">
                    </div>
                    <div class="form-group">
                        <label for="updatedbName">数据库名称或Sid</label>
                        <input type="text" name="dbName" class="form-control" id="updatedbName" placeholder="数据库名称或Sid">
                    </div>
                    <div class="form-group">
                        <label for="updateurl">数据库连接字符串</label>
                        <input type="text" name="url" class="form-control" id="updateurl" placeholder="数据库连接字符串">
                    </div>
                    <div class="form-group">
                        <label for="updateusername">用户名</label>
                        <input type="text" name="username" class="form-control" id="updateusername" placeholder="用户名">
                    </div>
                    <div class="form-group">
                        <label for="updatepassword">密码</label>
                        <input type="text" name="password" class="form-control" id="updatepassword" placeholder="密码">
                    </div>


                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal"><span
                                class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭
                        </button>
                        <button type="button" id="btn_update" class="btn btn-primary" data-dismiss="modal"
                                onclick="myb()"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>