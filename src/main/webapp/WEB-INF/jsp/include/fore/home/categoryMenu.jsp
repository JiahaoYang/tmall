<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="categoryMenu">
    <c:forEach items="${cs}" var="c">
        <div class="eachCategory" cid="${c.id}">
            <span class="glyphicon glyphicon-link"></span>
            <a href="foreCategory?cid=${c.id}">${c.name}</a>
        </div>
    </c:forEach>
</div>