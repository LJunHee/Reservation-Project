package com.team2.reservation.restaurant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.team2.reservation.restaurant.model.RestaurantDao;
import com.team2.reservation.restaurant.model.RestaurantVo;

import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantDao restaurantDao;
    private static final int PAGE_SIZE = 8; // �������� ������� ����
    private static final int PAGE_DISPLAY_LIMIT = 10; // ǥ���� ������ ��ȣ�� �ִ� ����

    @Autowired
    public RestaurantService(RestaurantDao restaurantDao) {
        this.restaurantDao = restaurantDao;
    }

    public void list(int page, Model model) {
        int offset = (page - 1) * PAGE_SIZE; // ���� �ε��� ���
        List<RestaurantVo> restaurantList = restaurantDao.pullList(offset, PAGE_SIZE);
        // restaruantDao.pullList() �޼��� �̿�, ���� ���� offset�� PAGE_SIZE�� �̿��Ͽ� DB���� �ش� �������� ������� ����� ������
        int totalRestaurants = restaurantDao.getTotalCount(); // restaurantDao.getTotalCount() �޼��� �̿�, ��ü ��������� ������ ��ȸ�Ͽ� ����(������ �� ����� ���ؼ� ���)
        int totalPages = (int) Math.ceil((double) totalRestaurants / PAGE_SIZE); // �� ������� ������ PAGE_SIZE�� ������ ��ü ������ ���� �����(Math.ceil�� ������ �Ͽ� ���� �Ҽ����� �ø��ϱ� ����)

        // ���������̼� ���
        int startPage = ((page - 1) / PAGE_DISPLAY_LIMIT) * PAGE_DISPLAY_LIMIT + 1;//���� �������� ���� ������ �׷��� ���� ������ ��ȣ ���
        int endPage = Math.min(startPage + PAGE_DISPLAY_LIMIT - 1, totalPages); // PAGE_DISPLAY_LIMIT�� �̿��Ͽ� ������ ������ ��ȣ ���

        model.addAttribute("list", restaurantList); // ���� �������� ������� ����� �𵨿� �߰�
        model.addAttribute("currentPage", page); // ���� ������ ��ȣ�� �𵨿� �߰�
        model.addAttribute("totalPages", totalPages); // ��ü ������ ���� �𵨿� �߰�
        model.addAttribute("startPage", startPage); // ǥ���� ���� ������ ��ȣ�� �𵨿� �߰�
        model.addAttribute("endPage", endPage); // ǥ���� ������ ������ ��ȣ�� �𵨿� �߰�
    }

    public RestaurantVo detail(int restNo) {
        return restaurantDao.getList(restNo);
    }
}