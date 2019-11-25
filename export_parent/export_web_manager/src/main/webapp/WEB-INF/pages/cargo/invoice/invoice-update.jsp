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
            发票管理
            <small>发票表单</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">发票管理</a></li>
            <li class="active">发票表单</li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!--订单信息-->
        <div class="panel panel-default">
            <div class="panel-heading">发票信息</div>
            <form id="editForm" action="${ctx}/cargo/invoice/update.do" method="post">
                <input type="hidden" name="id" value="${invoice.invoiceId}">
                <div class="row data-type" style="margin: 0px">
                    <div class="col-md-2 title">订单号</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="订单号" name="scNo" value="${invoice.scNo}">
                    </div>

                    <div class="col-md-2 title">提单号</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="提单号" name="blNo" value="${invoice.blNo}">
                    </div>

                    <div class="col-md-2 title">状态</div>
                    <div class="col-md-4 data">
                        <select class="form-control"  name="state" id="state">
                            <option value="">请选择</option>
                            <option value="0" ${invoice.state==0?'selected':''}>草稿</option>
                            <option value="1" ${invoice.state==1?'selected':''}>已提交</option>
                        </select>
                    </div>

                    <div class="col-md-2 title">创建人</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="创建人" name="creatBy" value="${invoice.creatBy}">
                    </div>

                    <div class="col-md-2 title">创建部门</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="创建部门" name="creatDept" value="${invoice.creatDept}">
                    </div>

                    <div class="col-md-2 title">创建日期</div>
                    <div class="col-md-4 data">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" placeholder="创建日期"  name="creatDate" class="form-control pull-right"
                                   value="<fmt:formatDate value="${invoice.creatDate}" pattern="yyyy-MM-dd"/>" id="deliveryPeriod">
                        </div>
                    </div>
                    <div class="col-md-2 title">总金额</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="总金额" name="amount" value="${invoice.amount}">
                    </div>

                    <div class="col-md-2 title">条款</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="条款" name="clause" value="${invoice.clause}">
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

</div>
<!-- 内容区域 /-->
</body>
<script src="../../plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="../../plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<link rel="stylesheet" href="../../css/style.css">
<script>
    $('#deliveryPeriod').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
</script>
</html>