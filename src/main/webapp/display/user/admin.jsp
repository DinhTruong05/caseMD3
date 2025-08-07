<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="org.example.library.Entites.User" %>
<%
    User user = (User) session.getAttribute("currentUser");
    if (user == null || !"admin".equals(user.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">
    <div class="card shadow-lg">
        <div class="card-header bg-primary text-white text-center">
            <h2>Xin chào, Quản trị viên: <%= user.getName() %></h2>
        </div>
        <div class="card-body">
            <div class="row mb-4">
                <div class="col-md-6">
                    <p><i class="bi bi-person-fill"></i> <strong>Username:</strong> <%= user.getUsername() %></p>
                    <p><i class="bi bi-envelope-fill"></i> <strong>Email:</strong> <%= user.getEmail() %></p>
                </div>
                <div class="col-md-6">
                    <p><i class="bi bi-telephone-fill"></i> <strong>Phone:</strong> <%= user.getPhone() %></p>
                    <p><i class="bi bi-geo-alt-fill"></i> <strong>Address:</strong> <%= user.getAddress() %></p>
                </div>
            </div>

            <div class="mb-4 d-flex justify-content-between align-items-center">
                <h4 class="text-success mb-0"><i class="bi bi-book-half"></i> Danh sách sách</h4>
                <div>
                    <a href="${pageContext.request.contextPath}/library/listuser" class="btn btn-outline-dark me-2">
                        <i class="bi bi-people-fill"></i> Danh sách User
                    </a>
                    <a href="${pageContext.request.contextPath}/library/addbook" class="btn btn-outline-info">
                        <i class="bi bi-plus-circle-fill"></i> Thêm sách
                    </a>
                </div>
            </div>

            <div class="table-responsive">
                <table class="table table-hover table-bordered align-middle">
                    <thead class="table-primary text-center">
                    <tr>
                        <th>STT</th>
                        <th>Tên sách</th>
                        <th>Thể loại</th>
                        <th>Giá</th>
                        <th>Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${listbooks}" var="book" varStatus="status">
                        <tr>
                            <td class="text-center">${status.index + 1}</td>
                            <td>${book.name}</td>
                            <td>${book.genre}</td>
                            <td>${book.price}</td>
                            <td class="text-center">
                                <a href="${pageContext.request.contextPath}/library/deleteB?id=${book.id}"
                                   class="btn btn-sm btn-outline-danger"
                                   onclick="return confirm('Bạn có chắc chắn muốn xóa sách này?')">
                                    <i class="bi bi-trash3-fill"></i> Xóa
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="text-end mt-4">
                <a href="${pageContext.request.contextPath}/library/logout" class="btn btn-outline-secondary">
                    <i class="bi bi-box-arrow-right"></i> Đăng xuất
                </a>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
