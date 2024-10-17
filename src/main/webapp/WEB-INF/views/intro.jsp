<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

    <script>
        var currentRestaurant = null; // 현재 가게 정보를 저장할 변수

        // 자바스크립트로 버튼 클릭 시 가게 정보를 모달로 보여주는 기능
        function showRestaurantDetails(restaurant) {
            // 현재 가게 정보를 저장합니다
            currentRestaurant = restaurant;
            
            // 가게 정보를 모달에 표시
            $('#restaurantNameDetail').text(restaurant.name);
            $('#contactDetail').text(restaurant.contact);
            $('#operatingHoursDetail').text(restaurant.operatingHours);
            $('#reviewDetail').text(restaurant.review);

            // 예약 폼을 숨깁니다 (초기 상태에서)
            $('#reservationForm').hide();
            $('#restaurantDetails').show();
            $('#restaurantDetailModal').modal('show');
        }

        // 예약 버튼 클릭 시 예약 폼을 표시하고 기존 정보를 숨깁니다.
        function showReservationForm() {
            if (currentRestaurant) {
                // 기존의 가게 정보를 숨깁니다
                $('#restaurantDetails').hide();
                
                // 예약 폼을 표시합니다
                $('#reservationForm').show();
            }
        }

        // 예약 폼을 취소하는 함수
        function cancelReservation() {
            // 예약 폼을 숨깁니다
            $('#reservationForm').hide();
            
            // 기존의 가게 정보를 다시 보여줍니다
            $('#restaurantDetails').show();
        }
    </script>
</head>
<body>
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
                                <img src="${pageContext.request.contextPath}/images/${item.image}" alt="사진 ${item.id}">
                                <div class="caption">
                                    <h4>${item.title}</h4>
                                    <p>${item.description}</p>
                                    <!-- "자세히 보기" 버튼: 클릭 시 해당 레스토랑 정보를 모달에 표시 -->
                                    <p>
                                        <button class="btn btn-primary" 
                                                onclick="showRestaurantDetails({
                                                    name: '${item.title}', 
                                                    contact: '${item.contact}', 
                                                    operatingHours: '${item.operatingHours}', 
                                                    review: '${item.review}' 
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

    <!-- 가게 정보 모달 -->
    <div class="modal fade" id="restaurantDetailModal" tabindex="-1" role="dialog" aria-labelledby="restaurantDetailModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="restaurantDetailModalLabel">가게 상세 정보</h4>
                </div>
                <div class="modal-body">
                    <!-- 기존 가게 정보 표시 영역 -->
                    <div id="restaurantDetails">
                        <p><strong>레스토랑 이름:</strong> <span id="restaurantNameDetail"></span></p>
                        <p><strong>연락처:</strong> <span id="contactDetail"></span></p>
                        <p><strong>운영 시간:</strong> <span id="operatingHoursDetail"></span></p>
                        <p><strong>리뷰:</strong> <span id="reviewDetail"></span></p>

                        <!-- 예약 버튼 -->
                        <button class="btn btn-success" onclick="showReservationForm()">예약하기</button>
                    </div>

                    <!-- 예약 폼 영역 -->
                    <div id="reservationForm" style="display:none;">
                        <form action="${pageContext.request.contextPath}/submitReservation" method="POST">
                            <div class="form-group">
                                <label for="people">인원</label>
                                <select class="form-control" id="people" name="people" required>
                                    <option value="1">1명</option>
                                    <option value="2">2명</option>
                                    <option value="3">3명</option>
                                    <option value="4">4명</option>
                                    <option value="5">5명</option>
                                    <option value="6">6명</option>
                                    <option value="7">7명</option>
                                    <option value="8">8명</option>
                                    <option value="9">9명</option>
                                    <option value="10">10명</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="reservationDate">예약 날짜</label>
                                <input type="date" class="form-control" id="reservationDate" name="reservationDate" required>
                            </div>
                            <button type="submit" class="btn btn-primary">예약하기</button>
                            <button type="button" class="btn btn-default" onclick="cancelReservation()">취소</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <footer class="footer">
        <div class="container">
            <p class="text-muted">&copy; 2024 레스토랑 예약 플랫폼. All rights reserved.</p>
        </div>
    </footer>
</body>
</html>