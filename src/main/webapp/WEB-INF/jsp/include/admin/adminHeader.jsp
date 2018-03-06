<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/back/style.css">
    <%--<script src="js/jquery/jquery-3.3.1.min.js"></script>--%>
    <%--<link rel="stylesheet" href="css/bootstrap/bootstrap.min.css">--%>
    <%--<script src="js/bootstrap/bootstrap.min.js"></script>--%>
    <script  src="http://code.jquery.com/jquery-latest.js"></script>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <script>
        function  checkEmpty(id, name) {
            let value = $("#" + id).val();
            if (value.length === 0) {
                alert(name + "不能为空");
                $("#" + id)[0].focus();
                return false;
            }
            return true;
        }

        function checkNumber(id, name) {
            let value = $("#" + id).val();
            if (value.length === 0) {
                alert(name + "不能为空");
                $("#" + id)[0].focus();
                return false;
            }
            if (isNaN(value)) {
                alert(name + "必须是数字");
                $("#" + id)[0].focus();
                return false;
            }

            return true;
        }

        function checkInt(id, name) {
            let value = $("#" + id).val();
            if (value.length === 0) {
                alert(name + "不能为空");
                $("#" + id)[0].focus();
                return false;
            }
            if (!Number.isInteger(Number(value))) {
                alert(name + "必须是整数");
                $("#" + id)[0].focus();
                return false;
            }
            return true;
        }

        $(function () {
            $("a").click(function () {
                let deleteLink = $(this).attr("deleteLink");
                console.log(deleteLink);
                if ("true" === deleteLink) {
                    return confirm("确认要删除");
                }
            });
        })
    </script>
</head>
</html>
