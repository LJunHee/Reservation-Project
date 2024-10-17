<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>레스토랑 리뷰 - 레스토랑 예약 플랫폼</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/css/bootstrap-theme.min.css">
    <style>
        .review-form {
            margin-top: 20px;
            margin-bottom: 40px;
        }
        .review-item {
            margin-bottom: 20px;
            border-bottom: 1px solid #eee;
            padding-bottom: 20px;
        }
        .star-rating {
            color: #FFD700;
        }
    </style>
</head>
<body>
<%@ include file = "template/menu.jspf" %>

    <div class="container">
        <h1>레스토랑 리뷰</h1>

        <!-- 리뷰 작성 폼 -->
        <div class="review-form">
            <h3>새 리뷰 작성</h3>
            <form>
                <div class="form-group">
                    <label for="restaurantSelect">레스토랑 선택</label>
                    <select class="form-control" id="restaurantSelect">
                        <option>레스토랑 A</option>
                        <option>레스토랑 B</option>
                        <option>레스토랑 C</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="rating">평점</label>
                    <select class="form-control" id="rating">
                        <option>5 - 매우 만족</option>
                        <option>4 - 만족</option>
                        <option>3 - 보통</option>
                        <option>2 - 불만</option>
                        <option>1 - 매우 불만</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="reviewText">리뷰 내용</label>
                    <textarea class="form-control" id="reviewText" rows="3"></textarea>
                </div>
                <button type="submit" class="btn btn-primary">리뷰 등록</button>
            </form>
        </div>

        <!-- 리뷰 목록 -->
        <h3>최근 리뷰</h3>
        <div class="review-list">
            <c:forEach begin="1" end="5" varStatus="loop">
                <div class="review-item">
                    <h4>레스토랑 ${loop.index}</h4>
                    <p class="star-rating">
                        <c:forEach begin="1" end="${6 - loop.index}">★</c:forEach>
                    </p>
                    <p><strong>작성자:</strong> 사용자${loop.index}</p>
                    <p>이 레스토랑은 정말 훌륭합니다. 음식의 맛과 서비스 모두 만족스러웠습니다. 특히 스테이크가 일품이었어요. 다음에 또 방문하고 싶습니다.</p>
                    <small class="text-muted">작성일: 2024-03-${loop.index + 10}</small>
                </div>
            </c:forEach>
        </div>

        <!-- 페이지네이션 -->
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <li>
                    <a href="#" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <li class="active"><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li>
                    <a href="#" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
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