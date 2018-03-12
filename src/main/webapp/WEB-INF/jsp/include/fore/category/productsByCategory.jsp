<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:if test="${empty param.categoryCount}">  <%--param相当于request.getParam 用于获得请求参数--%>
    <c:set var="categoryCount" value="100" scope="page"/>
</c:if>
<c:if test="${!empty param.categoryCount}">
    <c:set var="categoryCount" value="${categoryCount}" scope="page"/>
</c:if>

<div class="categoryProducts">
    <c:forEach items="${c.products}" var="p" varStatus="stc">
        <%--<c:if test="${stc.count <= categorycount}">--%>
            <div class="productUnit" price="${p.promotePrice}">
                <div class="productUnitFrame">
                    <a href="foreProduct?pid=${p.id}">
                        <img class="productImage" src="img/productSingle_middle/${p.firstProductImage.id}.jpg">
                    </a>
                    <span class="productPrice">¥<fmt:formatNumber type="number" value="${p.promotePrice}"
                                                                  minFractionDigits="2"/></span>
                    <a class="productLink" href="foreProduct?pid=${p.id}">
                            ${fn:substring(p.name, 0, 50)}
                    </a>

                    <a class="tmallLink" href="foreProduct?pid=${p.id}">天猫专卖</a>

                    <div class="show1 productInfo">
                        <span class="monthDeal ">月成交 <span class="productDealNumber">${p.saleCount}笔</span></span>
                        <span class="productReview">评价<span class="productReviewNumber">${p.reviewCount}</span></span>
                        <span class="wangwang">
                    <a class="wangwanglink" href="#nowhere">
                        <img src="img/site/wangwang.png">
                    </a>

                    </span>
                    </div>
                </div>
            </div>
        <%--</c:if>--%>
    </c:forEach>
    <div style="clear:both"></div>
</div>