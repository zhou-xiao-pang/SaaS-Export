<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../../base.jsp" %>
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
    <script src="${ctx}/plugins/jQuery/jquery-2.2.3.min.js"></script>
</head>
<script>
    function deleteById() {
        var id = getCheckId();
        if (id) {
            if (confirm("你确认要删除此条记录吗？")) {
                location.href = "${ctx}/cargo/shipping/delete.do?id=" + id;
            }
        } else {
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }
    }

    function submit() {
        var id = getCheckId()
        if (id) {
            location.href = "${ctx}/cargo/shipping/submit.do?id=" + id;
        } else {
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }
    }

    function cancel() {
        var id = getCheckId();
        if (id) {
            location.href = "${ctx}/cargo/shipping/cancel.do?id=" + id;
        } else {
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }
    }

    function select() {
        var id = getCheckId();
        if (id) {
            location.href = "${ctx}/cargo/map/shippingMap.do?id=" + id;
        } else {
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }

    }


</script>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <section class="content-header">
        <h1>
            货运管理
            <small>委托管理</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">委托单列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->

                <!--工具栏-->
                <div class="pull-left">
                    <div class="form-group form-inline">
                        <div class="btn-group">
                            <button type="button" class="btn btn-default" title="新建"
                                    onclick='location.href="${ctx}/cargo/shipping/toAdd.do"'><i
                                    class="fa fa-file-o"></i> 新建
                            </button>
                            <button type="button" class="btn btn-default" title="删除" onclick='deleteById()'><i
                                    class="fa fa-trash-o"></i> 删除
                            </button>
                            <button type="button" class="btn btn-default" title="提交" onclick='submit()'><i
                                    class="fa fa-retweet"></i> 提交
                            </button>
                            <button type="button" class="btn btn-default" title="取消" onclick='cancel()'><i
                                    class="fa fa-remove"></i> 取消
                            </button>
                            <button type="button" class="btn btn-default" title="发货地查询" onclick='select()'><i>Map</i>
                                查看港口机场
                            </button>
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
                        <td><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
                        <th class="sorting">委托单id</th>
                        <th class="sorting">运输方式</th>
                        <th class="sorting">货主</th>
                        <th class="sorting">信用证</th>
                        <th class="sorting">装运港</th>
                        <th class="sorting">转船港</th>
                        <th class="sorting">卸货港</th>
                        <th class="sorting">装期</th>
                        <th class="sorting">效期</th>
                        <th class="sorting">状态</th>
                        <th class="text-center">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.list}" var="o" varStatus="status">
                        <tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'">
                            <td><input class="checkboxs" type="checkbox" name="id" value="${o.shippingOrderId}"/></td>
                            <td>${o.shippingOrderId}</td>
                            <td>${o.shippingType}</td>
                            <td>${o.shipper}</td>
                            <td>${o.creditLetter}</td>
                            <td>${o.loadingPort}</td>
                            <td>${o.transhipmentPort}</td>
                            <td>${o.dischargePort}</td>
                            <td>${o.loadingTime}</td>
                            <td>${o.effectiveTime}</td>
                            <td>
                                <c:if test="${o.state==0}">草稿</c:if>
                                <c:if test="${o.state==1}"><font color="green">已提交</font></c:if>
                            </td>
                            <td>
                                <a href="${ctx }/cargo/shipping/toUpdate.do?id=${o.shippingOrderId}">[修改]</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <!-- /.box-body -->

            <!-- .box-footer-->
            <div class="box-footer">
                <jsp:include page="../../common/page.jsp">
                    <jsp:param value="cargo/shipping/list.do" name="pageUrl"/>
                </jsp:include>
            </div>
            <!-- /.box-footer-->


        </div>

    </section>
</div>
</body>

</html>