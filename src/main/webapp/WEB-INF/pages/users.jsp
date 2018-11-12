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
<!-- Button trigger modal -->

<br><br>
<div class="table-responsive">
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Firstname</th>
            <th width="30%">Lastname</th>
            <th width="20%">Email</th>
            <th class="text-center">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td class="align-middle">${user.firstname}</td>
                <td width="30%" class="align-middle">${user.lastname}</td>
                <td width="15%" class="align-middle">${user.email}</td>
                <td width="20%" class="text-center">
                    <button type="button" class="btn btn-dark dropdown-toggle" data-toggle="dropdown">
                        Action
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                        <a href="${pageContext.servletContext.contextPath}/users?action=disable&user_id=${user.id}" class="dropdown-item <c:if test="${user.isEnable}">text-danger</c:if> <c:if test="${!user.isEnable}">text-primary</c:if>">
                            <c:if test="${user.isEnable}">
                                DISABLE
                            </c:if>
                            <c:if test="${!user.isEnable}">
                                ENABLE
                            </c:if>
                        </a>
                        <a href="${pageContext.servletContext.contextPath}/users?action=reset&user_id=${user.id}" class="dropdown-item">Reset password</a>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- Modal -->
<div class="modal fade" id="appModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.servletContext.contextPath}/applications" method="post">
                    <input type="hidden" name="action" id="action" value="${not empty application ? "EDIT" : "CREATE"}">
                    <c:if test="${not empty application}">
                        <input type="hidden" id="app_id" name="app_id" value="${application.id}">
                    </c:if>
                    <div class="form-group">
                        <label class="control-label">Name:</label>
                        <div>
                            <input type="text" class="form-control" id="name" name="name" value="${application.name}" placeholder="Application name .. ">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label">Description:</label>
                        <div>
                            <input type="text" class="form-control" id="description" name="description" value="${application.description}" placeholder="Application description .. ">
                        </div>
                    </div>
                    <a class="btn btn-secondary" href="${pageContext.servletContext.contextPath}/applications">Close</a>
                    <button type="submit" class="btn btn-primary">${not empty application ? "Update" : "Create"}</button>
                </form>
                <br>
            </div>
        </div>
    </div>
</div>

<script>
    <c:if test="${not empty application}">
    $('#appModal').modal();
    </c:if>
</script>
<%@ include file="layout/footer.jsp" %>