<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>레스토랑 예약</title>
    <%@ include file = "template/head.jspf" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    
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
				<li class="active"><a href="${root}/intro">예약하기</a></li>
				<c:if test="${not empty sessionScope.loggedInUser}">
					<li><a href="${root}/review/">마이페이지</a></li>
				</c:if>
			</ul>
<%@ include file="template/menu.jspf" %>

    <div class="container">
        <div class="jumbotron">
            <h1>레스토랑 예약</h1>
            <p>원하시는 레스토랑을 예약하세요.</p>
        </div>

        <div class="row">
            <div class="col-md-12">
                <div class="row">
                    <!-- 레스토랑 목록을 동적으로 생성 -->
                    <c:forEach var="item" items="${IntroItems}">
                        <div class="col-md-3">
                            <div class="thumbnail">
                                <!-- 레스토랑 이미지 출력 -->
                                <img src="${pageContext.request.contextPath}/images/${item.image}" alt="사진 ${item.name}" class="img-responsive">
                                <div class="caption">
                                    <!-- 레스토랑 이름 출력 -->
                                    <h4>${item.name != null ? item.name : '이름 없음'}</h4>
                                    <!-- 레스토랑 설명 출력 -->
                                    <p>${item.info != null ? item.info : '설명이 없습니다.'}</p>
                                    <!-- 자세히 보기 버튼 -->
                                    <p>
                                        <button class="btn btn-primary" 
                                                onclick="showRestaurantDetails({
                                                    name: '${fn:escapeXml(item.name)}', 
                                                    description: '${fn:escapeXml(item.info)}'
                                                })" 
                                                role="button">자세히 보기</button>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
	<%@ include file = "template/footer.jspf" %>
    <script>
        // 레스토랑 상세 정보 알림 표시 함수
        function showRestaurantDetails(details) {
            alert("레스토랑: " + details.name + "\n설명: " + details.description);
        }
    </script>
</body>
</html>
