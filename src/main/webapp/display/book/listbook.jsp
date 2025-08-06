<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách sách</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-5">
    <h3 class="text-success mb-4">📚 Danh sách sách</h3>

    <a href="/library/addbook" class="btn btn-outline-info mb-3">+ Thêm sách</a>

    <table class="table table-bordered table-striped">
        <thead class="table-light">
        <tr>
            <th>STT</th>
            <th>Tên sách</th>
            <th>Thể loại</th>
            <th>Giá</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty requestScope.listbooks}">
                <c:forEach items="${requestScope.listbooks}" var="book" varStatus="status">
                    <tr>
                        <td>${status.index + 1}</td>
                        <td>${book.name}</td>
                        <td>${book.genre}</td>
                        <td><fmt:formatNumber value="${book.price}" type="number" groupingUsed="true"/> VNĐ</td>
                        <td>
                            <a href="/library/delete?id=${book.id}"
                               class="btn btn-sm btn-outline-danger"
                               onclick="return confirm('Bạn có chắc muốn xóa sách này?')">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="5" class="text-center text-danger">Không có sách nào trong danh sách.</td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>

    <a href="/library/" class="btn btn-danger mt-3">Quay lại</a>
</div>

</body>
</html>
