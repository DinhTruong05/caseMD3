<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center text-success">Xin chào: ${currentUser.name}</h2>

    <div class="text-center mt-4">
        <a href="/library/books" class="btn btn-outline-success mb-2">Xem sách trong thư viện</a><br>
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
                               onclick="return confirm('Bạn có chắc chắn muốn xóa trả này?')">Trả
                                <i class="bi bi-trash3-fill"></i>
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
</body>
</html>
