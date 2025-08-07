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
<html>
<head>
  <title>Danh sách người dùng</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h2 class="text-success">📋 Danh sách người dùng</h2>
    <a href="${pageContext.request.contextPath}/display/user/admin.jsp" class="btn btn-outline-primary">
      <i class="bi bi-arrow-left-circle"></i> Quay lại trang Admin
    </a>
  </div>

  <div class="table-responsive shadow-sm rounded">
    <table class="table table-hover table-bordered align-middle bg-white">
      <thead class="table-success text-center">
      <tr>
        <th scope="col">#</th>
        <th scope="col">Tên đăng nhập</th>
        <th scope="col">Họ và tên</th>
        <th scope="col">Email</th>
        <th scope="col">Số điện thoại</th>
        <th scope="col">Địa chỉ</th>
        <th scope="col">📋Sách đang mượn</th>
        <th scope="col">Hành động</th>

      </tr>
      </thead>
      <tbody>
      <c:forEach items="${listuser}" var="u" varStatus="status">
        <tr>
          <td class="text-center">${status.index + 1}</td>
          <td>${u.username}</td>
          <td>${u.name}</td>
          <td>${u.email}</td>
          <td>${u.phone}</td>
          <td>${u.address}</td>
          <td> </td>
          <td class="text-center">
            <a href="${pageContext.request.contextPath}/library/delete?id=${u.id}"
               class="btn btn-sm btn-outline-danger"
               onclick="return confirm('Bạn có chắc muốn xóa người dùng này?');">
              <i class="bi bi-trash"></i> Xóa
            </a>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
