<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE>
<html>

<body>
<a href="${contextPath}">
    <img src="img/site/logo.gif" id="logo" class="logo">
</a>
<form action="foreSearch" method="post">
    <div class="searchDiv">
        <input type="text" name="keyword" placeholder="太阳眼镜" value="${param.keyword}">
        <button type="submit" class="searchButton">搜索</button>
        <div class="searchBelow">
            <c:forEach items="${cs}" var="c" varStatus="st">
                <c:if test="${st.count >= 5 && st.count <= 8}">
                    <span>
                        <a href="foreCategory?cid=${c.id}">${c.name}</a>
                        <c:if test="${st.count != 8}">
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