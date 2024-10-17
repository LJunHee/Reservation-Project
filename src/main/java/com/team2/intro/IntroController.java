package com.team2.intro;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet({"/intro", "/reservation", "/submitReservation"})  // 3개의 URL 패턴을 처리
public class IntroController extends HttpServlet {

    // 갤러리 페이지와 예약 폼을 처리하는 메소드
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI();

        if (path.endsWith("/intro")) {
            showGallery(request, response);  // 갤러리 페이지 요청
        } else if (path.endsWith("/reservation")) {
            showReservationForm(request, response);  // 예약 폼 요청
        }
    }

    // 예약 처리하는 메소드
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI();

        if (path.endsWith("/submitReservation")) {
            processReservation(request, response);  // 예약 처리
        }
    }

    // 갤러리 페이지 표시
    private void showGallery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Map<String, String>> galleryItems = new ArrayList<>();

        // 레스토랑 1
        Map<String, String> item1 = new HashMap<>();
        item1.put("id", "1");
        item1.put("image", "photo1.jpg");
        item1.put("title", "레스토랑 1");
        item1.put("description", "이 레스토랑에 대한 간단한 설명입니다.");
        galleryItems.add(item1);

        // 레스토랑 2
        Map<String, String> item2 = new HashMap<>();
        item2.put("id", "2");
        item2.put("image", "photo2.jpg");
        item2.put("title", "레스토랑 2");
        item2.put("description", "이 레스토랑에 대한 간단한 설명입니다.");
        galleryItems.add(item2);

        // 레스토랑 3
        Map<String, String> item3 = new HashMap<>();
        item3.put("id", "3");
        item3.put("image", "photo3.jpg");
        item3.put("title", "레스토랑 3");
        item3.put("description", "이 레스토랑에 대한 간단한 설명입니다.");
        galleryItems.add(item3);

        // 레스토랑 4
        Map<String, String> item4 = new HashMap<>();
        item4.put("id", "4");
        item4.put("image", "photo4.jpg");
        item4.put("title", "레스토랑 4");
        item4.put("description", "이 레스토랑에 대한 간단한 설명입니다.");
        galleryItems.add(item4);

        request.setAttribute("IntroItems", galleryItems);  // 데이터를 JSP로 전달
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/intro.jsp");
        dispatcher.forward(request, response);  // 갤러리 페이지로 포워딩
    }

    // 예약 폼 표시
    private void showReservationForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/reservationForm.jsp");
        dispatcher.forward(request, response);  // 예약 폼 페이지로 포워딩
    }

    // 예약 처리
    private void processReservation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String people = request.getParameter("people");  // 예약 인원
        String reservationDate = request.getParameter("reservationDate");  // 예약 날짜

        // 예약 처리 (예시로 간단히 출력)
        System.out.println("예약된 인원: " + people);
        System.out.println("예약 날짜: " + reservationDate);

        // 예약 완료 후 갤러리로 리다이렉트
        response.sendRedirect(request.getContextPath() + "/intro");
    }
}