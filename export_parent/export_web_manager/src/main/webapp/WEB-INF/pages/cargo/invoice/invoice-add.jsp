<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
            货运管理
            <small>发票新增</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">货运管理</a></li>
            <li class="active">发票新增</li>
        </ol>
    </section>
    <!-- 内容头部 /-->
<script>
    function getOrderFormSubmit() {
        var shippingOrderId = document.getElementById("shippingOrderId").value;
        document.getElementById("invoiceId").value = shippingOrderId;
        document.getElementById("editForm").submit();
    }
</script>
    <!-- 正文区域 -->
    <section class="content">

        <!--订单信息-->
        <div class="panel panel-default">
            <div class="panel-heading">新增发票</div>
            <form id="editForm" action="${ctx}/cargo/invoice/save.do" method="post">
                <input type="hidden" id="invoiceId" name="invoiceId">
                <div class="row data-type" style="margin: 0px">
                    <div class="col-md-2 title">订单号</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="订单号" name="scNo">
                    </div>

                    <div class="col-md-2 title">提单号</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="提单号" name="blNo">
                    </div>

                    <div class="col-md-2 title rowHeight2x">条款</div>
                    <div class="col-md-4 data  rowHeight2x">
                        <textarea class="form-control" rows="7" placeholder="条款" name="clause" cols="50"></textarea>
                    </div>

                </div>
            </form>
        </div>
        <!--订单信息/-->

        <!--工具栏-->
        <div class="box-tools text-center">
            <button type="button" onclick="getOrderFormSubmit()" class="btn bg-maroon">保存</button>
            <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
        </div>
        <!--工具栏/-->
    </section>
    <!-- 正文区域 /-->

    <section class="content">

        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">委托单列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">
                    <!--数据列表-->
                    <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                        <thead>
                        <tr>
                            <td class="tableHeader"></td>
                            <td class="tableHeader">海运/空运</td>
                            <td class="tableHeader">货主</td>
                            <td class="tableHeader">提单抬头</td>
                            <td class="tableHeader">正本通知人</td>
                            <td class="tableHeader">信用证</td>
                            <td class="tableHeader">装运港</td>
                            <td class="tableHeader">转船港</td>
                            <td class="tableHeader">卸货港</td>
                            <td class="tableHeader">装期</td>
                            <td class="tableHeader">效期</td>
                            <td class="tableHeader">运费说明</td>
                        </tr>
                        </thead>
                        <tbody class="tableBody" >
                        ${links }
                        <c:forEach items="${page.list}" var="o" varStatus="status">
                            <tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
                                <td><input type="radio" id="shippingOrderId" name="id" value="${o.shippingOrderId}"/></td>
                                <td>${status.index+1}</td>
                                <td>${o.shippingType}</td>
                                <td>${o.shipper}</td>
                                <td>${o.blTitle}</td>
                                <td>${o.originalNotifiedBy}</td>
                                <td>${o.creditLetter}</td>
                                <td>${o.loadingPort}</td>
                                <td>${o.transhipmentPort}</td>
                                <td>${o.dischargePort}</td>
                                <td><fmt:formatDate value="${o.loadingTime}" pattern="yyyy-MM-dd"/></td>
                                <td><fmt:formatDate value="${o.effectiveTime}" pattern="yyyy-MM-dd"/></td>
                                <td>${o.freightDescription}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
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
                    <jsp:param value="/cargo/orderForm/list.do" name="pageUrl"/>
                </jsp:include>
            </div>
            <!-- /.box-footer-->
        </div>

    </section>

</div>
<!-- 内容区域 /-->
</body>

</html>