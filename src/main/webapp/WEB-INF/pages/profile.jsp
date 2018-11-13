<%@ include file="layout/header.jsp" %>
<br><br>
<h3 class="text-center">Edit your profile</h3>
<br><br>

<c:if test="${not empty firstname_error}">
    <div class="alert alert-danger" role="alert">
        <strong>Oh!</strong> ${firstname_error}
    </div>
</c:if>
<c:if test="${not empty lastname_error}">
    <div class="alert alert-danger" role="alert">
        <strong>Oh!</strong> ${lastname_error}
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

<form action="${pageContext.servletContext.contextPath}/profile" method="post">
    <div class="form-group row">
        <label for="user_email" class="col-sm-2 col-form-label">Email</label>
        <div class="col-sm-10">
            <input type="email" class="form-control" id="user_email" name="user_email" placeholder="Email" value="${user.email}" disabled>
        </div>
    </div>
    <input type="hidden" id="email" name="email" placeholder="Email" value="${user.email}">
    <div class="form-group row">
        <label for="firstname" class="col-sm-2 col-form-label">Firstname</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="inputFirstname" name="firstname" placeholder="Firstname" value="${user.firstname}">
        </div>
    </div>
    <div class="form-group row">
        <label for="lastname" class="col-sm-2 col-form-label">Lastname</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="inputLastname" name="lastname" placeholder="Lastname" value="${user.lastname}">
        </div>
    </div>
    <div class="form-group">
        <div class="text-center">
            <button id="buttonUpdate" type="submit" class="btn btn-primary">Update</button>
        </div>
    </div>
</form>


<%@ include file="layout/footer.jsp" %>
