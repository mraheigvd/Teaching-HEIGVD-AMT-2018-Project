<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Gamification-WP1 - Login</title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/resources/css/signin.css" rel="stylesheet">
</head>

<body class="text-center">
<form class="form-signin" method="post" action="${pageContext.servletContext.contextPath}/login">
    <img class="mb-4" src="" alt="" width="72" height="72">
    <h1>Gamification W1</h1>
    <h3 class="h3 mb-3 font-weight-normal">Please sign in</h3>
    <c:if test="${email != null}">
        <div class="alert alert-danger" role="alert">
            <strong>Oh!</strong> ${email}
        </div>
    </c:if>
    <c:if test="${password != null}">
        <div class="alert alert-danger" role="alert">
            <strong>Oh!</strong> ${password}
        </div>
    </c:if>
    <c:if test="${login != null}">
        <div class="alert alert-danger" role="alert">
            <strong>Oh!</strong> ${login}
        </div>
    </c:if>
    <c:if test="${passwordChanged != null}">
        <div class="alert alert-success" role="alert">
            ${passwordChanged}
        </div>
    </c:if>

    <label for="inputEmail" class="sr-only">Email address</label>
    <input type="email" id="inputEmail" name="email" class="form-control" placeholder="Email address" required autofocus>
    <label for="inputPassword" class="sr-only">Password</label>
    <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password" required>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    <a class="btn btn-lg btn-secondary btn-block" href="${pageContext.servletContext.contextPath}/register">Register</a>
    <p class="mt-5 mb-3 text-muted">&copy; Gamification-WP1 2018</p>
</form>
</body>
</html>
