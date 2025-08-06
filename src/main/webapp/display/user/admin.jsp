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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h1 class="text-center text-primary">Chào quản trị viên: ${currentUser.name}</h1>
    <p><strong>Username:</strong> ${currentUser.username}</p>
    <p><strong>Email:</strong> ${currentUser.email}</p>
    <p><strong>Phone:</strong> ${currentUser.phone}</p>
    <p><strong>Address:</strong> ${currentUser.address}</p>


</div>
<div class=" mt-4">
    <h4 class="text-success">Danh sách sách</h4>
    <a href="/library/addbook" class="btn btn-outline-info mb-2">+ Thêm sách</a><br>
    <table>
        <tr>
            <th>STT</th>
            <th>Name</th>
            <th>Genre</th>
            <th>Price</th>
            <th>Actions</th>
        </tr>
        <c:forEach items="${requestScope.listbooks}" var="book" >
            <tr>
                <td>${status.index + 1}</td>
                <td>${book.name}</td>
                <td>${book.genre}</td>
                <td>${book.price}</td>
                <a href="/library/delete?id=${book.id}" class="btn btn-outline-secondary mb-2">Xóa sách</a>
            </tr>
        </c:forEach>
    </table>
    <a href="/library/" class="btn btn-danger mt-3">Đăng xuất</a>
</div>
</body>
</html>
