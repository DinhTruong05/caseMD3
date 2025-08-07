<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thêm sách mới</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow">
                <div class="card-header bg-success text-white">
                    <h4 class="mb-0"><i class="bi bi-book-plus"></i> Thêm sách mới</h4>
                </div>
                <div class="card-body">

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger" role="alert">
                            <i class="bi bi-exclamation-triangle-fill"></i> ${error}
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/library/addbook" method="post">
                        <div class="mb-3">
                            <label for="name" class="form-label">Tên sách:</label>
                            <input type="text" id="name" name="name" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label for="genre" class="form-label">Thể loại:</label>
                            <input type="text" id="genre" name="genre" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label for="price" class="form-label">Giá:</label>
                            <input type="number" id="price" name="price" class="form-control" required min="0">
                        </div>

                        <div class="d-flex justify-content-between">
                            <a href="${pageContext.request.contextPath}/display/user/admin.jsp" class="btn btn-outline-secondary">
                                <i class="bi bi-arrow-left"></i> Quay lại danh sách
                            </a>
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-plus-circle-fill"></i> Thêm sách
                            </button>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
