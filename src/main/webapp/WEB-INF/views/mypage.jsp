<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>예약 목록</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<%@ include file="template/menu.jspf" %>
    <div class="container mt-5">
        <h2 class="text-center mb-4">예약 목록</h2>

        <!-- 예약 목록 테이블 -->
        <table class="table table-striped table-bordered">
            <thead class="table-dark">
                <tr>
                    <th scope="col">식당명</th>
                    <th scope="col">예약 날짜</th>
                    <th scope="col">리뷰 작성</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // 임의의 예약 데이터 생성
                    String[][] reservations = {
                        {"Restaurant A", "2024-10-20", "1"},
                        {"Restaurant B", "2024-10-21", "2"},
                        {"Restaurant C", "2024-10-22", "3"},
                        {"Restaurant D", "2024-10-23", "4"}
                    };

                    // 예약 데이터를 테이블로 출력
                    for (int i = 0; i < reservations.length; i++) {
                %>
                <tr>
                    <td><%= reservations[i][0] %></td>
                    <td><%= reservations[i][1] %></td>
                    <td>
                        <!-- 리뷰 작성 버튼 -->
                        <form action="${root}/review" method="post" class="d-inline">
					        <!-- 예약 ID를 리뷰 페이지로 넘겨주기 위해 hidden 필드 사용 -->
					        <input type="hidden" name="reservationId" value="<%= reservations[i][2] %>">
					        <button type="submit" class="btn btn-primary">리뷰 작성</button>
					    </form>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>

    <!-- Bootstrap JS (Optional for interactive components like modals or tooltips) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>