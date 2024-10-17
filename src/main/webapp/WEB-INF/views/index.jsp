<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>레스토랑 예약 플랫폼</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/css/bootstrap-theme.min.css" integrity="sha384-6pzBo3FDv/PJ8r2KRkGHifhEocL+1X2rVCTTkUfGk7/0pbek5mMa1upzvWbrUbOZ" crossorigin="anonymous">
	<style>
        .jumbotron {
            background-image: url('https://via.placeholder.com/1200x400');
            background-size: cover;
            color: white;
            text-shadow: 2px 2px 4px #000000;
        }
    </style>
</head>
<body>
	<%@ include file = "template/menu.jspf" %>

    <div class="container">
        <div class="jumbotron">
            <h1>맛있는 식사를 예약하세요</h1>
            <p>다양한 레스토랑을 쉽고 빠르게 예약할 수 있습니다.</p>
            <p><a class="btn btn-primary btn-lg" href="#" role="button">지금 예약하기</a></p> 
        </div>

        <div class="row">
            <div class="col-md-4">
                <h2>인기 레스토랑</h2>
                <p>가장 인기 있는 레스토랑들을 소개합니다.</p>
                <p><a class="btn btn-default" href="#" role="button">더보기 »</a></p>
            </div>
            <div class="col-md-4">
                <h2>오늘의 추천</h2>
                <p>오늘 방문하기 좋은 레스토랑을 추천해드립니다.</p>
                <p><a class="btn btn-default" href="#" role="button">추천 보기 »</a></p>
            </div>
            <div class="col-md-4">
                <h2>특별 이벤트</h2>
                <p>특별한 날을 위한 레스토랑 이벤트를 확인하세요.</p>
                <p><a class="btn btn-default" href="#" role="button">이벤트 보기 »</a></p>
            </div>
        </div>

        <hr>

        <div class="row">
            <div class="col-md-12">
                <h3>최근 등록된 레스토랑</h3>
                <div class="row">
                    <c:forEach begin="1" end="4" varStatus="loop">
                        <div class="col-md-3">
                            <div class="thumbnail">
                                <img src="https://via.placeholder.com/200x150" alt="레스토랑 이미지">
                                <div class="caption">
                                    <h4>레스토랑 ${loop.index}</h4>
                                    <p>간단한 레스토랑 설명이 들어갑니다.</p>
                                    <p><a href="#" class="btn btn-primary" role="button">자세히 보기</a></p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>

    <footer class="footer">
        <div class="container">
            <p class="text-muted">&copy; 2024 레스토랑 예약 플랫폼. All rights reserved.</p>
        </div>
    </footer>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/js/bootstrap.min.js"></script>
</body>
</html>
