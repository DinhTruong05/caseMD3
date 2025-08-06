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

        <a href="/library/return" class="btn btn-outline-secondary mb-2">Trả sách</a><br>
        <a href="/library/" class="btn btn-danger mt-3">Đăng xuất</a>
    </div>
</div>
</body>
</html>
