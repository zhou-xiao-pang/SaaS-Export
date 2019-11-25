<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>数据 - AdminLTE2定制版</title>
    <meta name="description" content="AdminLTE2定制版">
    <meta name="keywords" content="AdminLTE2定制版">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <!-- 页面meta /-->
</head>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            财务管理
            <small>发票管理</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">发票管理</a></li>
            <li class="active">发票管理</li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!--订单信息-->
        <div class="panel panel-default">
            <div class="panel-heading">财务报运单</div>
            <form id="editForm" action="${ctx}/cargo/finance/edit.do" method="post">
                <div class="panel-heading">对【${id}】财务报运</div>
                <input type="hidden" name="invoiceIds" value="${id}">
                <input type="hidden" name="financeId" value="${finance.financeId}">
                <div class="row data-type" style="margin: 0px">
                    <div class="col-md-2 title">制单人</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="制单人" name="createBy" value="${finance.createBy}">
                    </div>


                    <div class="col-md-2 title">制单日期</div>
                    <div class="col-md-4 data">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" placeholder="制单日期"  name="createTime" class="form-control pull-right"
                                   value="<fmt:formatDate value="${finance.creatTime}" pattern="yyyy-MM-dd"/>" id="signingDate">
                        </div>
                    </div>

                    <div class="col-md-2 title">创建部门</div>
                    <div class="col-md-4 data">
                        <select class="form-control" onchange="document.getElementById('deptName').value=this.options[this.selectedIndex].text" name="createDept">
                            <option value="">请选择</option>
                            <c:forEach items="${deptList}" var="item">
                                <option ${finance.createDept == item.id ?'selected':''} value="${item.id}">${item.deptName}</option>
                            </c:forEach>
                        </select>
                    </div>


                    <div class="col-md-2 title">状态</div>
                    <div class="col-md-4 data">
                        草稿
                        <input type="hidden" class="form-control" placeholder="状态" name="state" value="0">
                    </div>

                </div>
            </form>
        </div>
        <!--订单信息/-->

        <!--工具栏-->
        <div class="box-tools text-center">
            <button type="button" onclick='document.getElementById("editForm").submit()' class="btn bg-maroon">保存</button>
            <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
        </div>
        <!--工具栏/-->

    </section>
    <!-- 正文区域 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">审核通过的发票列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">

                    <!--工具栏-->
                    <div class="pull-left">
                        <div class="form-group form-inline">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default" title="查看" onclick='view()'><i class="fa  fa-eye-slash"></i> 查看</button>
                                <button type="button" class="btn btn-default" title="刷新" onclick="window.location.reload();"><i class="fa fa-refresh"></i> 刷新</button>
                                <button type="button" class="btn btn-default" title="财务报运" onclick="document.getElementById('exportForm').submit()"><i class="fa fa-refresh"></i> 财务报运</button>
                            </div>
                        </div>
                    </div>
                    <div class="box-tools pull-right">
                        <div class="has-feedback">
                            <input type="text" class="form-control input-sm" placeholder="搜索">
                            <span class="glyphicon glyphicon-search form-control-feedback"></span>
                        </div>
                    </div>
                    <!--工具栏/-->

                    <!--数据列表-->
                    <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                        <thead>
                        <tr>
                            <th class="" style="padding-right:0px;">

                            </th>
                            <th class="sorting">发票号</th>
                            <th class="sorting">订单号</th>
                            <th class="sorting">提单号</th>
                            <th class="sorting">创建人</th>
                            <th class="sorting">创建部门</th>
                            <th class="sorting">总金额</th>
                            <th class="sorting">状态</th>
                            <th class="text-center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <form id="exportForm" action="/cargo/finance/toExport.do" method="post">
                            <c:forEach items="${page.list}" var="o" varStatus="status">
                                <tr>
                                    <td><input type="checkbox" name="id" value="${o.invoiceId}"/></td>
                                    <td><a href="${ctx}/cargo/contract/toView.do?id=${o.id}">${o.scNo}</a></td>
                                    <td>${o.blNo}</td>
                                    <td>${o.creatBy}</td>
                                    <td>${o.creatDate}</td>
                                    <td>${o.amount}</td>
                                    <td><c:if test="${o.state==0}">草稿</c:if>
                                        <c:if test="${o.state==1}"><font color="green">已上报</font></c:if>
                                        <c:if test="${o.state==2}"><font color="red">已报运</font></c:if>
                                    </td>
                                    <td>
                                        <a href="${ctx }/cargo/invoice/toView.do?id=${o.invoiceId}">[查看详情]</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </form>
                        </tbody>
                        <!--
                    <tfoot>
                    <tr>
                    <th>Rendering engine</th>
                    <th>Browser</th>
                    <th>Platform(s)</th>
                    <th>Engine version</th>
                    <th>CSS grade</th>
                    </tr>
                    </tfoot>-->
                    </table>
                    <!--数据列表/-->

                    <!--工具栏/-->

                </div>
                <!-- 数据表格 /-->


            </div>
            <!-- /.box-body -->

            <!-- .box-footer-->
            <div class="box-footer">
                <jsp:include page="../../common/page.jsp">
                    <jsp:param value="/cargo/invoice/list.do" name="pageUrl"/>
                </jsp:include>
            </div>
            <!-- /.box-footer-->


        </div>

    </section>

</div>
<!-- 内容区域 /-->
</body>
<script src="../../plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="../../plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<link rel="stylesheet" href="../../css/style.css">
<script>
    $('#signingDate').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
    $('#deliveryPeriod').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
</script>
</html>