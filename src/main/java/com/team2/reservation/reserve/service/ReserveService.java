package com.team2.reservation.reserve.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.team2.reservation.reserve.model.ReserveDao;
import com.team2.reservation.reserve.model.ReserveVo;

@Service
public class ReserveService {
    private final ReserveDao reserveDao;

    @Autowired
    public ReserveService(ReserveDao reserveDao) {
        this.reserveDao = reserveDao;
    }

    // 사용자의 예약 목록을 조회하는 메서드
    public void listByUser(int userNo, Model model) {
        model.addAttribute("list", reserveDao.pullListByUser(userNo));
    }
    
    // 예약 상세 정보 조회
    public ReserveVo detail(int reserveNo) {
        return reserveDao.getList(reserveNo);
    }

    // 예약 추가
    public void add(ReserveVo bean) {
        System.out.println(reserveDao.addList(bean));
    }

    // 예약 수정
    public void edit(ReserveVo bean) {
        reserveDao.setList(bean);
    }

    // 예약 삭제
    public void delete(int reserveNo) {
        reserveDao.rmList(reserveNo);
    }
    
    // 예약 추가 기능 (예약 중복 체크 포함)
    public void addReservation(int restNo, int headCount, String reserveDate, int userNo) {
        ReserveVo reserve = new ReserveVo();
        reserve.setRestNo(restNo);
        reserve.setHeadCount(headCount);
        reserve.setUserNo(userNo);

        LocalDateTime localDateTime;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            localDateTime = LocalDateTime.parse(reserveDate, formatter).withSecond(0);
        } catch (Exception e) {
            throw new IllegalStateException("잘못된 요청입니다."); 
        }

        LocalDate today = LocalDate.now();
        if (localDateTime.toLocalDate().isEqual(today)) {
            List<ReserveVo> existingReservations = reserveDao.findReservationsByUserAndRestaurant(userNo, restNo, today);
            if (!existingReservations.isEmpty()) {
                throw new IllegalStateException("당일에 이미 예약된 레스토랑입니다.");
            }
        }

        reserve.setReserveTime(Timestamp.valueOf(localDateTime));
        reserveDao.addList(reserve);
    }
}
