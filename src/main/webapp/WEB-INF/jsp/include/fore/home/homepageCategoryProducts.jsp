<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE>
<html>

<body>
<c:if test="${!empty param.categoryCount}">
    <c:set var="categoryCount" value="param.categoryCount" scope="page"/>
</c:if>
<c:if test="${empty param.categoryCount}">
    <c:set var="categoryCount" value="100" scope="page"/>
</c:if>

<div class="homepageCategoryProducts">
    <c:forEach items="${cs}" var="c" varStatus="st">
        <c:if test="${st.count <= categoryCount}">
            <div class="eachHomepageCategoryProducts">
                <div class="left-mark"></div>
                <span class="categoryTitle">${c.name}</span>
                <br/>
                <c:forEach items="${c.products}" var="p" varStatus="pst">
                    <c:if test="${pst.count <= 5}">
                        <div class="productItem">
                            <a href="foreProduct?pid=${p.id}">
                                <img src="img/productSingle_middle/${p.firstProductImage.id}.jpg"
                                     width="100px"/>
                            </a>
                            <a class="productItemDescLink" href="foreProduct?pid=${p.id}">
                                <span class="productItemDesc">
                                    [热销] ${fn:substring(p.name, 0, 20)}
                                </span>
                            </a>
                            <span class="productPrice">
                                <fmt:formatNumber type="number" value="${p.promotePrice}" minFractionDigits="2" />
                            </span>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </c:if>
    </c:forEach>
</div>
</body>
</html>