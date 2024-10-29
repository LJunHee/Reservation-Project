<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>레스토랑 예약</title>
<%@ include file="template/head.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
                <li class="active"><a href="${root}/restaurant">예약하기</a></li>
                <c:if test="${not empty sessionScope.loggedInUser}">
                    <li><a href="${root}/mypage/">마이페이지</a></li>
                </c:if>
            </ul>
            <%@ include file="template/menu.jspf" %>
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
										<img
											src="${root}/resources/img/${item.restName}.jpg"
											alt="사진 ${item.restName}"
											onerror="this.src='${root}/resources/img/default.jpg'"
											class="img-responsive">
										<div class="caption">
											<h4>${item.restName != null ? item.restName : '이름 없음'}</h4>
											<p>${item.restAddress != null ? item.restAddress : '주소가 없습니다.'}</p>
											<p>${item.restInfo != null ? item.restInfo : '설명이 없습니다.'}</p>
											<p>
												<a href="#" class="btn btn-primary" role="button"
													data-toggle="modal" data-target="#restInfoModal"
													data-name="${fn:escapeXml(item.restName)}"
													data-address="${fn:escapeXml(item.restAddress)}"
													data-info="${fn:escapeXml(item.restInfo)}"
													data-phone="${fn:escapeXml(item.formattedPhone)}"
													data-open="${fn:escapeXml(item.openTimeStr)}"
													data-close="${fn:escapeXml(item.closeTimeStr)}"
													data-restno="${fn:escapeXml(item.restNo)}"

													onclick="setRestaurantDetails(this)">자세히 보기</a>
											</p>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>


<%@ include file="restaurant/restInfo.jspf" %>
<%@ include file="restaurant/reservation.jspf" %>
<%@ include file="template/footer.jspf" %>

<!-- 모달의 JavaScript 함수 -->
<script>
function setRestaurantDetails(button) {
    var name = button.getAttribute('data-name');
    var address = button.getAttribute('data-address');
    var info = button.getAttribute('data-info');
    var phone = button.getAttribute('data-phone');
    var openTime = button.getAttribute('data-open');
    var closeTime = button.getAttribute('data-close');
    var restNo = button.getAttribute('data-restno');


    document.querySelector('#restInfoModal #restName').textContent = name;
    document.querySelector('#restInfoModal #restAddress').textContent = address;
    document.querySelector('#restInfoModal #restInfo').textContent = info;
    document.querySelector('#restInfoModal #restPhone').textContent = phone;
    document.querySelector('#restInfoModal #restTime').textContent = openTime + " - " + closeTime;

    // 숨은 입력 필드에 restNo 설정
    var restNoInput = document.querySelector('#restInfoModal input[name="restNo"]');
    var reservationRestNoInput = document.querySelector('#reservationModal input[name="restNo"]');
        restNoInput.value = restNo; // 값 설정
        reservationRestNoInput.value = restNo; // 예약 모달의 restNo에 값 설정
}

</script>

</body>
</html>