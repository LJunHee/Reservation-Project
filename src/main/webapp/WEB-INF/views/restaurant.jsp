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
                <li class="active"><a href="${root}/restaurant">예약하기</a></li>
                <c:if test="${not empty sessionScope.loggedInUser}">
                    <li><a href="${root}/review/">마이페이지</a></li>
                </c:if>
            </ul>
<%@include file = "template/menu.jspf" %>

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
                                    <img src="${pageContext.request.contextPath}/images/${item.restNo}.jpg" alt="사진 ${item.restName}" class="img-responsive">
                                    <div class="caption">
                                        <h4>${item.restName != null ? item.restName : '이름 없음'}</h4>
                                        <p>${item.restInfo != null ? item.restInfo : '설명이 없습니다.'}</p>
                                        <p>
                                            <button class="btn btn-primary" 
                                                    onclick="showRestaurantDetails({
                                                        name: '${fn:escapeXml(item.restName)}', 
                                                        description: '${fn:escapeXml(item.restInfo)}',
                                                        phone: '${fn:escapeXml(item.formattedPhone)}',
                                                        openTime: '${fn:escapeXml(item.openTimeStr)}',
                                                        closeTime: '${fn:escapeXml(item.closeTimeStr)}'
                                                    })" 
                                                    role="button">자세히 보기</button>
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

<%@ include file = "template/footer.jspf" %>
<script>
    // 레스토랑 상세 정보 알림 표시 함수
    function showRestaurantDetails(details) {
        alert("레스토랑: " + details.name + 
              "\n설명: " + details.description + 
              "\n전화번호: " + details.phone + 
              "\n영업시간: " + details.openTime + " - " + details.closeTime);
    }
</script>
</body>
</html>
