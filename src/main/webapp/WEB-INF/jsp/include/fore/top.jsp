<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="top">
    <a href="foreHome">
        <span style="color:#C40000;margin:0" class=" glyphicon glyphicon-home redColor"></span>
        天猫首页
    </a>
    <span>喵,欢迎来天猫</span>
    <c:if test="${!empty user}">
        <a href="loginPage">${user.name}</a>
        <a href="foreLogout">退出</a>
    </c:if>
    <c:if test="${empty user}">
        <a href="loginPage">请登录</a>
        <a href="registerPage">免费注册</a>
    </c:if>
    <span class="pull-right">
        <a href="foreBought">我的订单</a>
        <a href="foreCart">
            <span style="color:#C40000;margin:0" class=" glyphicon glyphicon-shopping-cart redColor"></span>
            购物车
        </a>
    </span>
</nav>