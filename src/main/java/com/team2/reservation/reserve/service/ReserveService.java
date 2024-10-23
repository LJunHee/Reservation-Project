package com.team2.reservation.reserve.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    
    // restNo를 추가 파라미터로 받도록 수정
    public void addReservation(int restNo, int headCount, String reserveDate, int userNo) {
        ReserveVo reserve = new ReserveVo();
        reserve.setRestNo(restNo); // restNo를 설정
        reserve.setHeadCount(headCount);
        reserve.setUserNo(userNo);
        
        try {
            // 'T'를 포함한 형식 확인 및 LocalDateTime 변환
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(reserveDate, formatter);
            
            // 초를 0으로 설정
            localDateTime = localDateTime.withSecond(0);
            
            // LocalDateTime을 Timestamp로 변환
            reserve.setReserveTime(Timestamp.valueOf(localDateTime));
        } catch (Exception e) {
            // 형식이 잘못되었을 때 오류 처리
            System.out.println("잘못된 시간 형식입니다: " + e.getMessage());
            return; // 메서드 종료
        }
        
        reserveDao.addList(reserve); // DB에 예약 추가
    }
}
