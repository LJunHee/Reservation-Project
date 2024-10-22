package com.team2.reservation.reserve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.team2.reservation.reserve.model.ReserveDao;
import com.team2.reservation.reserve.model.ReserveVo;

@Service
public class ReserveService {
    private final ReserveDao reserveDao;

    @Autowired
    public ReserveService(ReserveDao restDao) {
        this.reserveDao = restDao;
    }

    // 사용자의 예약 목록을 조회하는 메서드
    public void listByUser(int userNo, Model model) {
        model.addAttribute("list", reserveDao.pullListByUser(userNo));
    }
    
    public ReserveVo detail(int restNo) {
        return reserveDao.getList(restNo);
    }

    public void add(ReserveVo bean) {
        System.out.println(reserveDao.addList(bean));
    }

    public void edit(ReserveVo bean) {
        System.out.println(reserveDao.setList(bean));
    }

    public void delete(int restNo) {
        System.out.println(reserveDao.rmList(restNo));
    }
}