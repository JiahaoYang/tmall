<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>

<script>
    $(function () {
        /**
         * Ajax异步调用
         * 注册键盘松开事件
         */
        $("input.pvValue").keyup(function () {
            let value = $(this).val();
            let page = "admin_propertyValue_update";
            let pvid = $(this).attr("pvid");
            let parentSpan = $(this).parent("span");
            parentSpan.css("border", "1px solid yellow");
            $.post(
                page,
                {"value": value, "id": pvid},
                function (result) {
                    if ("success" === result)
                        parentSpan.css("border", "1px solid green");
                    else
                        parentSpan.css("border", "1px solid red");
                }
            );
        });
    });
</script>

<title>编辑产品属性值</title>
<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="admin_category_list">所有分类</a></li>
        <li><a href="admin_property_list?cid=${product.cid}">${product.category.name}</a></li>
        <li class="active">${product.name}</li>
        <li class="active">编辑产品属性值</li>
    </ol>

    <div class="editPVDiv">
        <c:forEach items="${ptValues}" var="p">
            <div class="eachPV">
                <span class="pvName">${p.property.name}</span>
                <span class="pvValue">
                    <input class="pvValue" pvid="${p.id}" type="text" value="${p.value}">
                </span>
            </div>
        </c:forEach>
        <div style="clear:both"></div>
    </div>
</div>
