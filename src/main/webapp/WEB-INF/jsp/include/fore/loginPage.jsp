<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
    $(function () {
        <c:if test="${!empty msg}">
        $("span.errorMessage").html("${msg}");
        $("div.loginErrorMessageDiv").show();
        </c:if>

        $("form.loginForm").submit(function () {
            if (0 === $("#name").val().length || 0 === $("#password").val().length) {
                $("span.errorMessage").html("请输入账号密码");
                $("div.loginErrorMessageDiv").show();
                return false;
            }
            return true;
        });

        $("form.loginForm input").keyup(function () {
            $("div.loginErrorMessageDiv").hide();
        });

        let left = window.innerWidth/2+162;
        $("div.loginSmallDiv").css("left",left);
    })
</script>

<div class="loginDiv" style="position: relative">
    <div class="simpleLogo">
        <a href="#"><img src="img/site/simpleLogo.png"></a>
    </div>
    <img id="loginBackgroundImg" class="loginBackgroundImg" src="img/site/loginBackground.png">
    <form method="post" action="foreLogin" class="loginForm">
        <div class="loginSmallDiv" id="loginSmallDiv">
            <div class="loginErrorMessageDiv">
                <div class="alert alert-danger">
                    <span class="errorMessage"></span>
                </div>
            </div>
            <div class="login_acount_text">账户登录</div>
            <div class="loginInput">
                <span class="loginInputIcon">
                    <span class=" glyphicon glyphicon-user"></span>
                </span>
                <input id="name" name="name" type="text" placeholder="手机/会员名">
            </div>
            <div class="loginInput">
                <span class="loginInputIcon">
                    <span class=" glyphicon glyphicon-lock"></span>
                </span>
                <input id="password" name="password" type="password" placeholder="密码">
            </div>
            <div>
                <a class="notImplementLink" href="#nowhere">忘记登录密码</a>
                <a href="registerPage" class="pull-right">免费注册</a>
            </div>
            <div style="margin-top:20px">
                <button class="btn btn-block redButton" type="submit">登录</button>
            </div>
        </div>
    </form>
</div>