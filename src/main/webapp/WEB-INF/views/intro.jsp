<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <!-- fn 태그 라이브러리 추가 -->
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>레스토랑 예약</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/css/bootstrap.min.css">
    <style>
        .jumbotron {
            background-image: url('https://via.placeholder.com/1200x400');
            background-size: cover;
            color: white;
            text-shadow: 2px 2px 4px #000000;
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/js/bootstrap.min.js"></script>
</head>
<body>
    <%@ include file="template/menu.jspf" %>  <!-- 메뉴 파일 포함 -->
    
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

    <script>
        // 레스토랑 상세 정보 알림 표시 함수
        function showRestaurantDetails(details) {
            alert("레스토랑: " + details.name + "\n설명: " + details.description);
        }
    </script>
</body>
</html>