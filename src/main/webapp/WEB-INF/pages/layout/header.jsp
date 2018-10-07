<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!doctype html>
<html lang="en">
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Gamification-WP1</title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">
</head>

<body>

<nav class="navbar navbar-fixed-top navbar-expand-lg navbar-dark bg-dark navbar-inner">
    <a class="navbar-brand" href="#">Gamification WP1</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarColor01">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item <c:if test="${fn:contains(pageContext.request.requestURI, 'profile.jsp')}">active</c:if>" >
                <a class="nav-link" href="/profile">Profile</a>
            </li>
            <li class="nav-item <c:if test="${fn:contains(pageContext.request.requestURI, 'applications.jsp')}">active</c:if>">
                <a class="nav-link" href="/applications">Applications</a>
            </li>
            <c:if test="${user.getIsAdmin()}">
                <li class="nav-item <c:if test="${fn:contains(pageContext.request.requestURI, 'users.jsp')}">active</c:if>">
                    <a class="nav-link" href="/users">Users</a>
                </li>
            </c:if>
        </ul>
        <form class="form-inline">
            <a href="/logout" class="btn btn-outline-info my-2 my-sm-0" role="button" aria-pressed="true">Logout</a>
        </form>
    </div>
</nav>

<div class="container">


</div>

</body>
</html>
