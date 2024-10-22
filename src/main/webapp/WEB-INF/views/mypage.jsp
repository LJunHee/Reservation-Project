<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마이 페이지</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
%>
    <%@ include file="template/menu.jspf" %>
    <div class="container mt-5">
        <h2 class="text-center mb-4">예약 목록</h2>

        <!-- 예약 목록 테이블 -->
        <table class="table table-striped table-bordered">
            <thead class="table-dark">
                <tr>
                    <th scope="col">식당명</th>
                    <th scope="col">예약 날짜</th>
                    <th scope="col">리뷰 작성</th>
                </tr>
            </thead>
            <tbody>
                <!-- JSTL을 사용하여 예약 목록을 반복 출력 -->
                <c:if test="${empty list}">
                    <!-- 예약이 없을 때 안내 문구 -->
                    <tr>
                        <td colspan="3" class="text-center">예약이 없습니다.</td>
                    </tr>
                </c:if>
                
                <c:forEach var="reservation" items="${list}">
                    <tr>
                        <td>${reservation.restName}</td>
<<<<<<< Updated upstream
                        <td>${reservation.restTime}</td>
=======
                        <td>${reservation.reserveTime}</td>
>>>>>>> Stashed changes
                        <td>
                            <!-- 리뷰 작성 버튼 -->
                            <form action="${root}/review" method="get" class="d-inline">
							    <input type="hidden" name="reservationId" value="${reservation.reserveNo}">
							    <button type="submit" class="btn btn-primary">리뷰 작성</button>
							</form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Bootstrap JS (Optional for interactive components like modals or tooltips) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
