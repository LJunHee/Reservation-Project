package com.team2.reservation.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.team2.reservation.restaurant.model.RestaurantDao;
import com.team2.reservation.restaurant.model.RestaurantVo;

import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantDao restaurantDao;
    private static final int PAGE_SIZE = 8; // 페이지당 레스토랑 개수
    private static final int PAGE_DISPLAY_LIMIT = 10; // 표시할 페이지 번호의 최대 개수

    @Autowired
    public RestaurantService(RestaurantDao restaurantDao) {
        this.restaurantDao = restaurantDao;
    }

    public void list(int page, Model model) {
        int offset = (page - 1) * PAGE_SIZE; // 시작 인덱스 계산
        List<RestaurantVo> restaurantList = restaurantDao.pullList(offset, PAGE_SIZE);
        // restaruantDao.pullList() 메서드 이용, 위에 계산된 offset과 PAGE_SIZE를 이용하여 DB에서 해당 페이지의 레스토랑 목록을 가져옴
        int totalRestaurants = restaurantDao.getTotalCount(); // restaurantDao.getTotalCount() 메서드 이용, 전체 레스토랑의 개수를 조회하여 저장(페이지 수 계산을 위해서 사용)
        int totalPages = (int) Math.ceil((double) totalRestaurants / PAGE_SIZE); // 총 레스토랑 개수를 PAGE_SIZE로 나누어 전체 페이지 수를 계산함(Math.ceil은 나누기 하여 남은 소수점을 올림하기 위함)

        // 페이지네이션 계산
        int startPage = ((page - 1) / PAGE_DISPLAY_LIMIT) * PAGE_DISPLAY_LIMIT + 1;//현재 페이지가 속한 페이지 그룹의 시작 페이지 번호 계산
        int endPage = Math.min(startPage + PAGE_DISPLAY_LIMIT - 1, totalPages); // PAGE_DISPLAY_LIMIT를 이용하여 마지막 페이지 번호 계산

        model.addAttribute("list", restaurantList); // 현재 페이지의 레스토랑 목록을 모델에 추가
        model.addAttribute("currentPage", page); // 현재 페이지 번호를 모델에 추가
        model.addAttribute("totalPages", totalPages); // 전체 페이지 수를 모델에 추가
        model.addAttribute("startPage", startPage); // 표시할 시작 페이지 번호를 모델에 추가
        model.addAttribute("endPage", endPage); // 표시할 마지막 페이지 번호를 모델에 추가
    }

    public RestaurantVo detail(int restNo) {
        return restaurantDao.getList(restNo);
    }
}