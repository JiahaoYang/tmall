<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE>
<html>

<body>
<form action="foreSearch" method="post">
    <div class="simpleSearchDiv">
        <input type="text" name="keyword" placeholder="平衡车" value="${param.keyword}">
        <button type="submit" class="searchButton">搜天猫</button>
        <div class="searchBelow">
            <c:forEach items="${cs}" var="c" varStatus="st">
                <c:if test="${st.count >= 8 && st.count <= 11}">
                    <span>
                        <a href="foreCategory?cid=${c.id}">${c.name}</a>
                        <c:if test="${st.count != 11}">
                            <span>|</span>
                        </c:if>
                    </span>
                </c:if>
            </c:forEach>
        </div>
    </div>
</form>
</body>
</html>