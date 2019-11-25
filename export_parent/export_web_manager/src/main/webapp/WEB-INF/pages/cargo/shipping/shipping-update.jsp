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
            <small>委托单列表</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">货运管理</a></li>
            <li class="active">委托单编辑</li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!--订单信息-->
        <div class="panel panel-default">
            <div class="panel-heading">编辑委托单</div>
            <%--
                enctype="multipart/form-data"
            --%>
            <form id="editForm" action="${ctx}/cargo/shipping/update.do" method="post">
                <div class="row data-type" style="margin: 0px">
                    <input type="hidden" name="shippingOrderId" value="${shipping.shippingOrderId}"/>
                    <div class="col-md-2 title">运输方式</div>
                    <div class="col-md-4 data">
                        <div class="form-group form-inline">
                            <div class="radio"><label><input type="radio" ${shipping.shippingType=="SEA海运"?'checked':''} name="shippingType" value="SEA海运">SEA海运</label></div>
                            <div class="radio"><label><input type="radio" ${shipping.shippingType=="AIR空运"?'checked':''} name="shippingType" value="AIR空运">AIR空运</label></div>
                        </div>
                    </div>

                    <div class="col-md-2 title">货主</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="货主" name="shipper" value="${shipping.shipper}">
                    </div>

                    <div class="col-md-2 title">提单抬头</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="提单抬头" name="blTitle" value="${shipping.blTitle}" >
                    </div>

                    <div class="col-md-2 title">正本通知人</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="正本通知人" name="originalNotifiedBy" value="${shipping.originalNotifiedBy}">
                    </div>


                    <div class="col-md-2 title">信用证</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="信用证" name="creditLetter" value="${shipping.creditLetter}">
                    </div>

                    <div class="col-md-2 title">装运港</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="装运港" name="loadingPort" value="${shipping.loadingPort}">
                    </div>

                    <div class="col-md-2 title">转船港</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="转船港" name="transhipmentPort" value="${shipping.transhipmentPort}">
                    </div>
                    <div class="col-md-2 title">卸货港</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="卸货港" name="dischargePort" value="${shipping.dischargePort}">
                    </div>

                    <div class="col-md-2 title">装期</div>
                    <div class="col-md-4 data">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" placeholder="装期"  name="loadingTime" class="form-control pull-right"
                                   value="<fmt:formatDate value="${shipping.loadingTime}" pattern="yyyy-MM-dd"/>" id="loadingDate">
                        </div>
                    </div>

                    <div class="col-md-2 title">效期</div>
                    <div class="col-md-4 data">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" placeholder="效期"  name="effectiveTime" class="form-control pull-right"
                                   value="<fmt:formatDate value="${shipping.effectiveTime}" pattern="yyyy-MM-dd"/>" id="limitDate">
                        </div>
                    </div>

                    <div class="col-md-2 title">是否分批</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="是否分批" name="ifBatches" value="${shipping.ifBatches}">
                    </div>

                    <div class="col-md-2 title">是否转船</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="是否转船" name="ifTransshipment" value="${shipping.ifTransshipment}">
                    </div>


                    <div class="col-md-2 title">份数</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="份数" name="copiesNo" value="${shipping.copiesNo}">
                    </div>


                    <div class="col-md-2 title">扼要说明</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="扼要说明" name="briefDescription" value="${shipping.briefDescription}">
                    </div>

                    <div class="col-md-2 title rowHeight2x">运输要求</div>
                    <div class="col-md-4 data  rowHeight2x">
                        <textarea class="form-control" rows="3" placeholder="运输要求" name="requirements">${shipping.requirements}</textarea>
                    </div>

                    <div class="col-md-2 title">运费说明</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="运费说明" name="freightDescription" value="${shipping.freightDescription}">
                    </div>

                    <div class="col-md-2 title">复核人</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="复核人" name="reviewer" value="${shipping.reviewer}">
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
                <h3 class="box-title">装箱单列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">
                    <!--数据列表-->
                    <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                        <thead>
                        <tr>
                            <td><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
                            <th class="sorting">装箱号</th>
                            <th class="sorting">卖方</th>
                            <th class="sorting">买方</th>
                            <th class="sorting">发票号</th>
                            <th class="sorting">发票日期</th>
                            <th class="sorting">唛头</th>
                            <th class="sorting">状态</th>
                            <th class="text-center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="o" varStatus="status">
                            <tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'">
                                <td><input type="checkbox" name="id" value="${o.packingListId}"/></td>
                                <td>${o.packingListId}</td>
                                <td>${o.seller}</td>
                                <td>${o.buyer}</td>
                                <td>${o.invoiceNo}</td>
                                <td>${o.invoiceDate}</td>
                                <td>${o.marks}</td>
                                <td>
                                    <c:if test="${o.state==0}">草稿</c:if>
                                    <c:if test="${o.state==1}"><font color="green">已上报</font></c:if>
                                    <c:if test="${o.state==2}"><font color="red">已委托</font></c:if>
                                </td>
                                <td>
                                    <a href="${ctx }/cargo/packing/toUpdate.do?id=${o.packingListId}">[修改]</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- /.box-body -->

            <!-- .box-footer-->
            <div class="box-footer">
                <jsp:include page="../../common/page.jsp">
                    <jsp:param value="/cargo/packing/list.do" name="pageUrl"/>
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
    $('#loadingDate').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });

    $('#limitDate').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
</script>
</html>