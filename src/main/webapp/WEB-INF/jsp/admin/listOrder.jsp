<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>


<title>订单管理</title>
<script>
    $(function () {
        $("button.orderPageCheckOrderItems").click(function () {
            let oid = $(this).attr("oid");
            $("tr.orderPageOrderItemTR[oid=" + oid + "]").toggle(); //隐藏与显示
        });
    });
</script>

<div class="workingArea">
    <h1 class="label label-info">订单管理</h1>
    <br>
    <br>

    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover  table-condensed">
            <thead>
            <tr class="success">
                <th>ID</th>
                <th>状态</th>
                <th>金额</th>
                <th>商品数量</th>
                <th>买家名称</th>
                <th>创建时间</th>
                <th>支付时间</th>
                <th>发货时间</th>
                <th>确认收货时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${orders}" var="order">
                <tr>
                    <td>${order.id}</td>
                    <td>${order.status}</td>
                    <td>$
                        <fmt:formatNumber type="number" value="${order.total}" minFractionDigits="2"/>
                    </td>
                    <td align="center">${order.totalNum}</td>
                    <td align="center">${order.user.name}</td>
                    <td>
                        <fmt:formatDate value="${order.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td>
                        <fmt:formatDate value="${order.payDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td>
                        <fmt:formatDate value="${order.deliveryDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td>
                        <fmt:formatDate value="${order.confirmDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td>
                        <button class="orderPageCheckOrderItems btn btn-primary btn-xs" oid="${order.id}">
                            查看详情
                            <c:if test="${order.status == 'waitDelivery'}">
                                <a href="admin_order_delivery?id=${order.id}">
                                    <button class="btn btn-primary btn-xs">发货</button>
                                </a>
                            </c:if>
                        </button>
                    </td>
                </tr>
                <tr class="orderPageOrderItemTR" oid="${order.id}">
                    <td colspan="10px" align="center">
                        <div class="orderPageOrderItem">
                            <table class="orderPageOrderItemTable" align="center" width="800px">
                                <c:forEach items="${order.orderItems}" var="item">
                                    <tr>
                                        <td align="left">
                                            <img src="img/productSingle/${item.product.firstProductImage.id}.jpg"
                                                 width="40px" height="40px">
                                        </td>
                                        <td>
                                            <a href="foreProduct?pid=${item.product.id}">
                                                <span>${item.product.name}</span>
                                            </a>
                                        </td>
                                        <td align="right">
                                            <span class="text-muted">${item.number}个</span>
                                        </td>
                                        <td align="right">
                                            <span class="text-muted">单价:$ ${item.product.promotePrice}</span>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="pageDiv">
        <%@include file="../include/admin/adminPage.jsp" %>
    </div>

</div>

<%@include file="../include/admin/adminFooter.jsp" %>