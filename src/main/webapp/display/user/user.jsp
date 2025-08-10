<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page import="org.example.library.Entites.User" %>
<%
    User user = (User) session.getAttribute("currentUser");
    if (user == null || !"user".equals(user.getRole())) {
        response.sendRedirect("/library/");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Trang Người Dùng</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
    <style>
        body {
            background: url('https://images.unsplash.com/photo-1512820790803-83ca734da794') no-repeat center center fixed;
            background-size: cover;
            color: #fff;
        }
        .overlay {
            background: rgba(0, 0, 0, 0.55);
            min-height: 100vh;
            padding-top: 50px;
        }
        .card-custom {
            background: rgba(255, 255, 255, 0.9);
            border-radius: 15px;
            padding: 20px;
            color: #333;
            box-shadow: 0 4px 20px rgba(0,0,0,0.3);
        }
        table th, table td {
            vertical-align: middle;
        }
        .btn-outline-success, .btn-outline-danger {
            border-radius: 20px;
        }
    </style>
</head>
<body>
<div class="overlay">
    <div class="container">
        <div class="card-custom">
            <h2 class="text-center text-success mb-4">
                <i class="bi bi-person-circle"></i> Xin chào: ${currentUser.name}
            </h2>

            <div class="text-center mb-4">
                <a href="/library/books" class="btn btn-outline-success px-4">
                    <i class="bi bi-book"></i> Xem sách trong thư viện
                </a>
            </div>

            <div class="table-responsive">
                <table class="table table-hover table-bordered align-middle text-center">
                    <thead class="table-success">
                    <tr>
                        <th>STT</th>
                        <th>Tên sách</th>
                        <th>Thể loại</th>
                        <th>Giá</th>
                        <th>Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${rentedBooks}" var="book" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${book.name}</td>
                            <td>${book.genre}</td>
                            <td><fmt:formatNumber value="${book.price}" type="number" groupingUsed="true"/> VNĐ</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/library/return?id=${book.id}"
                                   class="btn btn-sm btn-outline-danger"
                                   onclick="return confirm('Bạn có chắc chắn muốn trả sách này?')">
                                    <i class="bi bi-arrow-counterclockwise"></i> Trả
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="text-end mt-4">
                <a href="${pageContext.request.contextPath}/library/logout" class="btn btn-outline-secondary px-4">
                    <i class="bi bi-box-arrow-right"></i> Đăng xuất
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
