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
        var currentRestaurant = null;

        function showRestaurantDetails(restaurant) {
            currentRestaurant = restaurant;
            $('#restaurantNameDetail').text(restaurant.name);
            $('#contactDetail').text(restaurant.contact);
            $('#operatingHoursDetail').text(restaurant.operatingHours);
            $('#reviewDetail').text(restaurant.review);
            $('#reservationForm').hide();
            $('#restaurantDetails').show();
            $('#restaurantDetailModal').modal('show');
        }

        function showReservationForm() {
            if (currentRestaurant) {
                $('#restaurantDetails').hide();
                $('#reservationForm').show();
            }
        }

        function cancelReservation() {
            $('#reservationForm').hide();
            $('#restaurantDetails').show();
        }

        $(document).ready(function() {
            if ($('#restaurantList').children().length === 0) {
                alert("레스토랑 목록이 없습니다. 관리자에게 문의하세요.");
            }
        });
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
                <div class="row" id="restaurantList">
                    <c:forEach var="item" items="${list}">
                        <div class="col-md-3">
                            <div class="thumbnail">
                                <!-- <img src="${pageContext.request.contextPath}/images/${item.restNo}.jpg" alt="사진 ${item.restNo}"> -->
                                <div class="caption">
                                    <h4>${item.restName}</h4>
                                    <p>${item.restInfo}</p>
                                    <p>${item.restPhone}</p>
                                    <p>${item.openTime} ~ ${item.closeTime}</p>
                                    <p>
                                        <button class="btn btn-primary" 
                                                onclick="showRestaurantDetails({
                                                    name: '${item.restName}', 
                                                    contact: '${item.restPhone}', 
                                                    operatingHours: '${item.openTime} ~ ${item.closeTime}', 
                                                    review: '${item.restInfo}' 
                                                })" 
                                                role="button">자세히 보기</button>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                    <c:if test="${empty list}">
                        <div class="col-md-12">
                            <p>현재 등록된 레스토랑이 없습니다.</p>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

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
                    <div id="restaurantDetails">
                        <p><strong>레스토랑 이름:</strong> <span id="restaurantNameDetail"></span></p>
                        <p><strong>연락처:</strong> <span id="contactDetail"></span></p>
                        <p><strong>운영 시간:</strong> <span id="operatingHoursDetail"></span></p>
                        <p><strong>리뷰:</strong> <span id="reviewDetail"></span></p>
                        <button class="btn btn-success" onclick="showReservationForm()">예약하기</button>
                    </div>
                    <div id="reservationForm" style="display:none;">
                        <form action="${pageContext.request.contextPath}/submitReservation" method="POST">
                            <div class="form-group">
                                <label for="people">인원</label>
                                <select class="form-control" id="people" name="people" required>
                                    <c:forEach var="num" begin="1" end="10">
                                        <option value="${num}">${num}명</option>
                                    </c:forEach>
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
