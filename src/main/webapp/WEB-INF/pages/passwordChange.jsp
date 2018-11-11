<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Gamification-WP1 - Password change</title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/resources/css/signin.css" rel="stylesheet">
</head>

<body class="text-center">
<form class="form-signin" method="post" action="${pageContext.servletContext.contextPath}/login">
    <img class="mb-4" src="" alt="" width="72" height="72">
    <h1>Gamification W1</h1>
    <h3 class="h3 mb-3 font-weight-normal">Please change your password.</h3>
    <c:if test="${newPassword != null}">
        <div class="alert alert-danger" role="alert">
            <strong>Oh!</strong> ${newPassword}
        </div>
    </c:if>
    <c:if test="${confirmPassword != null}">
        <div class="alert alert-danger" role="alert">
            <strong>Oh!</strong> ${confirmPassword}
        </div>
    </c:if>
    <label for="inputNewPassword" class="sr-only">New password</label>
    <input type="password" id="inputNewPassword" name="newPassword" class="form-control" placeholder="New password" required autofocus>
    <label for="inputConfirmPassword" class="sr-only">Confirm new password</label>
    <input type="password" id="inputConfirmPassword" name="confirmPassword" class="form-control" placeholder="Confirm new password" required autofocus>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Change password</button>
    <p class="mt-5 mb-3 text-muted">&copy; Gamification-WP1 2018</p>
</form>
</body>
</html>
