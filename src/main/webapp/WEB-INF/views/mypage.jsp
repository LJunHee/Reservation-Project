<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> <!-- JSTL fmt 태그 선언 -->
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>예약 목록</title>
    <%@include file="template/head.jspf" %>
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
            <h2 class="text-center mb-4">나의 예약 목록</h2>

            <!-- 예약 목록 테이블 -->
            <table class="table table-striped table-bordered">
                <thead class="table-dark">
                    <tr>
                        <th scope="col">식당명</th>
                        <th scope="col">예약 시간</th>
                        <th scope="col">예약 인원 수</th>
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
					        <td><a href="#">${reservation.restName}</a></td>
					        <!-- 예약 시간 클릭 시 edit.jspf 모달 열기 -->
					        <td>
					            <a href="#" 
					               onclick="setEditModal('${reservation.reserveNo}', '${reservation.reserveTime}', '${reservation.headCount}')" 
					               data-toggle="modal" data-target="#editModal">
                                   <!-- 시간 포맷 적용: 초를 제외한 yyyy-MM-dd HH:mm 형식으로 출력 -->
					               <fmt:formatDate value="${reservation.reserveTime}" pattern="yyyy-MM-dd HH:mm"/>
					            </a>
					        </td>
					        <!-- 예약 인원 수 클릭 시 edit.jspf 모달 열기 -->
					        <td>
					            <a href="#" 
					               onclick="setEditModal('${reservation.reserveNo}', '${reservation.reserveTime}', '${reservation.headCount}')" 
					               data-toggle="modal" data-target="#editModal">
					               ${reservation.headCount}
					            </a>
					        </td>
					    </tr>
					</c:forEach>
                </tbody>
            </table>

            <div class="text-right">
                <c:if test="${not empty list}">
                    <a href="#" class="btn btn-primary" role="button" data-toggle="modal" data-target="#reviewModal">리뷰 작성</a>
                </c:if>
            </div>
        </div>
    </div>
</nav>
</body>
<%@ include file="reserve/review.jspf" %>
<%@ include file="reserve/edit.jspf" %>
<script>
    function setEditModal(reserveNo, reserveTime, headCount) {
        document.getElementById('editModalReserveNo').value = reserveNo;
        document.getElementById('editModalReserveTime').value = reserveTime;
        document.getElementById('editModalHeadCount').value = headCount;
    }
</script>


</html>

