<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>레스토랑 예약</title>
    <%@ include file="template/head.jspf"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"> <!-- Bootstrap CSS -->
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
                <li class="active"><a href="${root}/">HOME</a></li>
                <li><a href="${root}/restaurant">예약하기</a></li>
                <c:if test="${not empty sessionScope.loggedInUser}">
                    <li><a href="${root}/mypage/">마이페이지</a></li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="jumbotron">
        <h1>레스토랑 예약</h1>
        <p>원하시는 레스토랑을 예약하세요.</p>
    </div>
    
    <div class="row">
        <div class="col-md-12">
            <c:choose>
                <c:when test="${empty list}">
                    <h3 class="text-warning">현재 등록된 레스토랑이 없습니다.</h3>
                </c:when>
                <c:otherwise>
                    <div class="row">
                        <!-- 레스토랑 목록을 동적으로 생성 -->
                        <c:forEach var="item" items="${list}">
                            <div class="col-md-3">
                                <div class="thumbnail">
                                    <img src="${item.restImage}" alt="사진 ${item.restName}"
                                        onerror="this.src='${root}/resources/img/default.jpg'"
                                        class="img-responsive">
                                    <div class="caption">
                                        <h4>${item.restName != null ? item.restName : '이름 없음'}</h4>
                                        <p>주소 : ${item.restAddress != null ? item.restAddress : '주소가 없습니다.'}</p>
                                        <p>전화번호 : ${item.restPhone != null ? item.restPhone : '전화번호가 없습니다.'}</p>
                                        <p>
                                            <a href="#" class="btn btn-primary" role="button"
                                                data-toggle="modal" data-target="#restInfoModal"
                                                data-name="${item.restName}" 
                                                data-address="${item.restAddress}"
                                                data-info="${item.restInfo}" 
                                                data-menu="${item.restMenu}"
                                                data-phone="${item.restPhone}" 
                                                data-open="${item.openTimeStr}"
                                                data-close="${item.closeTimeStr}" 
                                                data-restno="${item.restNo}"
                                                onclick="setRestaurantDetails(this)">자세히 보기</a>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    
                    <!-- 페이지네이션 -->
                    <c:if test="${totalPages > 1}"> <!-- 총 페이지 수가 1보다 큰 경우에만 페이지네이션을 표시 -->
                        <nav aria-label="Page navigation">
                            <ul class="pagination"> <!-- bootstrap에서 가져온 페이지네이션 스타일 -->
                                <!-- 이전 페이지 그룹으로 이동하는 버튼 -->
                                <c:if test="${startPage > 1}"> <!-- 현재 페이지 그룹의 사작 페이지가 1보다 큰 경우에만 이전 페이지 버튼 표시 -->
                                    <!-- 이전 페이지로 이동할 수 있는 링크 제작 -->
                                    <li>
                                        <a href="?page=${startPage - 1}" aria-label="Previous"> <!-- 이전 페이지 번호로의  URL 생성 -->
                                            <span aria-hidden="true">&laquo;</span> <!-- 왼쪽 방향 화살표를 표시 -->
                                        </a>
                                    </li>
                                </c:if>
								
                                <!-- 페이지 번호 목록 -->
                                <c:forEach var="i" begin="${startPage}" end="${endPage}"> <!-- startPage 부터 endPage까지의 숫자를 반복하여 페이지 번호 생성 -->
                                    <c:choose>
                                        <c:when test="${i == currentPage}"> 
                                            <li class="active"><span>${i}</span></li> <!-- 현재 페이지를 클릭할 수 없게 하기 -->
                                        </c:when>
                                        <c:otherwise>
                                            <li><a href="?page=${i}">${i}</a></li> <!-- 현재 페이지 외에 다른 페이지 클릭 가능하게 하기 -->
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>

                                <!-- 다음 페이지 그룹으로 이동하는 버튼 -->
                                <c:if test="${endPage < totalPages}"> <!-- 현재 페이지 그룹의 마지막 페이지(endPage)가 전체 페이지 수(totalPages)보다 작은 경우에만 다음 페이지 버튼 표시 -->
                                    
                                    <li>
                                        <a href="?page=${endPage + 1}" aria-label="Next"> <!-- 다음 페이지 번호로의 URL 생성 -->
                                            <span aria-hidden="true">&raquo;</span> <!-- 오른쪽 방향 화살표 표시 -->
                                        </a>
                                    </li>
                                </c:if>
                            </ul>
                        </nav>
                    </c:if>
                    <!-- // 페이지네이션 끝 -->
                    
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<%@ include file="restaurant/restInfo.jspf" %>
<%@ include file="template/footer.jspf" %>

<!-- 모달의 JavaScript 함수 -->
<script>
function setRestaurantDetails(button) {
    var name = button.getAttribute('data-name');
    var address = button.getAttribute('data-address');
    var info = button.getAttribute('data-info');
    var menu = button.getAttribute('data-menu');
    var phone = button.getAttribute('data-phone');
    var openTime = button.getAttribute('data-open');
    var closeTime = button.getAttribute('data-close');
    var restNo = button.getAttribute('data-restno');

    document.querySelector('#restInfoModal #restName').textContent = name;
    document.querySelector('#restInfoModal #restAddress').textContent = address;
    document.querySelector('#restInfoModal #restInfo').textContent = info;
    document.querySelector('#restInfoModal #restMenu').textContent = menu;
    document.querySelector('#restInfoModal #restPhone').textContent = phone;
    document.querySelector('#restInfoModal #restTime').textContent = openTime + " - " + closeTime;

    var restNoInput = document.querySelector('#restInfoModal input[name="restNo"]');
    var reservationRestNoInput = document.querySelector('#reservationModal input[name="restNo"]');
    restNoInput.value = restNo;
    reservationRestNoInput.value = restNo;
}
</script>

</body>
</html>