<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách sách</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container my-5">
    <div class="card shadow">
        <!-- Tiêu đề -->
        <div class="card-header bg-success text-white text-center py-3">
            <h4 class="mb-0">
                <i class="bi bi-journal-bookmark-fill me-2"></i>Danh sách sách
            </h4>
        </div>

        <!-- Nội dung -->
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered table-hover align-middle text-center">
                    <thead class="table-success">
                    <tr>
                        <th>STT</th>
                        <th class="text-start">Tên sách</th>
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
                                    <td class="text-start">${book.name}</td>
                                    <td>${book.genre}</td>
                                    <td>
                                        <fmt:formatNumber value="${book.price}" type="number" groupingUsed="true"/> VNĐ
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/library/rent?id=${book.id}"
                                           class="btn btn-sm btn-outline-primary"
                                           onclick="return confirm('Bạn có chắc muốn thuê sách này?')">
                                            <i class="bi bi-bag-plus-fill"></i> Thuê
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="5" class="text-center text-danger">
                                    <i class="bi bi-exclamation-triangle-fill"></i> Không có sách nào trong danh sách.
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>

            <!-- Nút quay lại -->
            <div class="text-end mt-3">
                <a href="${pageContext.request.contextPath}/display/user/user.jsp" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left-circle"></i> Quay lại
                </a>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
