<%@ include file="layout/header.jsp" %>

<br>
<div class="text-center">
    <h3>${not empty application ? "Edit application" : "Create a new application"}</h3>
</div>
<br>

<c:if test="${not empty name_error}">
    <div class="alert alert-danger" role="alert">
        <strong>Oh!</strong> ${name_error}
    </div>
</c:if>
<c:if test="${not empty description_error}">
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
<form action="/applications" method="post">
    <input type="hidden" name="action" id="action" value="${not empty application ? "EDIT" : "CREATE"}">
    <c:if test="${not empty application}">
        <input type="hidden" id="app_id" name="app_id" value="${application.id}">
    </c:if>
    <div class="form-group row">
        <label for="name" class="col-sm-2 col-form-label">Name</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="name" name="name" value="${application.name}" placeholder="Application name .. ">
        </div>
    </div>
    <div class="form-group row">
        <label for="description" class="col-sm-2 col-form-label">Description</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="description" name="description" value="${application.description}" placeholder="Application description .. ">
        </div>
    </div>
    <div class="form-group">
        <div class="text-center">
            <button type="submit" class="btn btn-primary">${not empty application ? "Update" : "Create"}</button>
        </div>
    </div>
</form>
<hr>
<br>

<div class="text-center">
    <h3>List of applications</h3>
</div>

<br><br>
<div class="table-responsive">
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Name</th>
            <th width="30%">Description</th>
            <th width="20%">App key</th>
            <th width="20%">App token</th>
            <th class="text-center">Action</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${applications}" var="app">
            <tr>
                <th>${app.name}</th>
                <td width="30%">${app.description}</td>
                <td width="20%">${app.appKey}</td>
                <td width="20%">${app.appToken}</td>
                <td class="text-center">
                    <div class="dropdown">
                        <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                            Action
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                            <a href="/applications?action=edit&app_id=${app.id}" class="dropdown-item">Edit</a>
                            <a href="/applications?action=delete&app_id=${app.id}" class="dropdown-item">Delete</a>
                            <a href="/applications?action=regenerate&app_id=${app.id}" class="dropdown-item">Regenerate token</a>
                        </div>
                    </div>
                </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="layout/footer.jsp" %>
