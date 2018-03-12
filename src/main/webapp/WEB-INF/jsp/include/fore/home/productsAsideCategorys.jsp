<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE>
<html>

<body>
<c:forEach items="${cs}" var="c">
    <div class="productsAsideCategorys" cid="${c.id}">
        <c:forEach items="${c.productsByRow}" var="ps">
            <div class="row show1">
                <c:forEach items="${ps}" var="p">
                    <c:if test="${!empty p.subTitle}">
                        <a href="foreProduct?pid=${p.id}">
                            <c:forEach items="${fn:split(p.subTitle, ' ')}" var="title" varStatus="st">
                                <c:if test="${st.index == 0}">
                                    ${title}
                                </c:if>
                            </c:forEach>
                            <div class="seperator"></div>
                        </a>
                    </c:if>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
</c:forEach>
</body>
</html>