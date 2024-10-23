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
        reserve.setRestNo(restNo);
        reserve.setHeadCount(headCount);
        reserve.setUserNo(userNo);

        LocalDateTime localDateTime;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            localDateTime = LocalDateTime.parse(reserveDate, formatter).withSecond(0);
        } catch (Exception e) {
            // 예외 처리 코드 제거
            throw new IllegalStateException("잘못된 요청입니다."); // 필요 시 다른 예외 메시지로 수정 가능
        }

        LocalDate today = LocalDate.now();
        if (localDateTime.toLocalDate().isEqual(today)) {
            List<ReserveVo> existingReservations = reserveDao.findReservationsByUserAndRestaurant(userNo, restNo, today);
            if (!existingReservations.isEmpty()) {
                throw new IllegalStateException("당일에 이미 예약된 레스토랑입니다."); // 중복 예약 시 예외 던짐
            }
        }

        reserve.setReserveTime(Timestamp.valueOf(localDateTime));
        reserveDao.addList(reserve);
    }

}
