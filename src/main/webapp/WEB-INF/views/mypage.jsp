<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>예약 목록</title>
    <%@include file = "template/head.jspf" %>
</head>
<body>
<nav class="navbar navbar-default">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${root}/">레스토랑 예약</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li><a href="${root}/">HOME</a></li>
				<li><a href="${root}/restaurant">예약하기</a></li>
				<c:if test="${not empty sessionScope.loggedInUser}">
					<li class="active"><a href="${root}/mypage/">마이페이지</a></li>
				</c:if>
			</ul>
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
                        <td>${reservation.restTime}</td>
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
