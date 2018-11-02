<%@ include file="layout/header.jsp" %>

<br>

<br>

<c:if test="${not empty name_error}">
    <div class="alert alert-danger" role="alert">
        <strong>Oh!</strong> ${name_error}
    </div>
</c:if>
<c:if test="${not empty description_error}">
    <div class="alert alert-danger" role="alert">
        <strong>Oh!</strong> ${description_error}
    </div>
</c:if>
<c:if test="${not empty error}">
    <div class="alert alert-danger" role="alert">
        <strong>Oh!</strong> ${error}
    </div>
</c:if>
<c:if test="${not empty success}">
    <div class="alert alert-success" role="alert">
        <strong>Yeah!</strong> ${success}
    </div>
</c:if>





<br>
<div class="text-center">
    <h3>List of users</h3>
</div>

<br><br>
<div class="table-responsive">
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Id</th>
            <th>Email</th>
            <th>Firstname</th>
            <th>Lastname</th>
            <th>Admin</th>
            <th>Enabled</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <th>${user.id}</th>
                <td width="30%">${user.email}</td>
                <td width="15%">${user.firstname}</td>
                <td width="20%">${user.lastname}</td>
                <td width="20%">${user.isAdmin}</td>
                <td width="20%">${user.isEnable}</td>
                <td width="20%" class="text-center">
                    <div class="dropdown">
                        <button type="button" class="btn btn-dark dropdown-toggle" data-toggle="dropdown">
                            Action
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                            <a href="${pageContext.servletContext.contextPath}/users?action=delete&user_id=${user.id}" class="dropdown-item">Reset password</a>
                        </div>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="layout/footer.jsp" %>
