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
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private final ReserveDao reserveDao;

    @Autowired
    public ReserveService(ReserveDao restDao) {
        this.reserveDao = restDao;
    }

    // 사용자 예약 목록 조회
    public void listByUser(int userNo, Model model) {
        model.addAttribute("list", reserveDao.pullListByUser(userNo));
    }
    
    // 사용자 예약 기능 (예약 중복 체크)
    public void addReservation(int restNo, int headCount, String reserveDate, int userNo) {
        ReserveVo reserve = createReserveVo(restNo, headCount, userNo);
        LocalDateTime localDateTime = parseReserveDate(reserveDate);
        
//        chkDuplReservation(userNo, restNo, localDateTime.toLocalDate());
        reserve.setReserveTime(Timestamp.valueOf(localDateTime));
        reserveDao.addList(reserve);
    }
    
    // 예약 수정
    public void updateReservation(int reserveNo, int restNo, int headCount, String reserveDate, int userNo) {
        ReserveVo existingReservation = existingReservation(reserveNo);
        LocalDateTime localDateTime = parseReserveDate(reserveDate);
        
        updateReservation(existingReservation, restNo, headCount, userNo, localDateTime);
        reserveDao.setList(existingReservation);
    }
    
    // 예약 삭제
    public void deleteReservation(int reserveNo) {
        reserveDao.rmList(reserveNo);
    }  

    
    
    // 예약 객체 생성
    private ReserveVo createReserveVo(int restNo, int headCount, int userNo) {
        ReserveVo reserve = new ReserveVo();
        reserve.setRestNo(restNo);
        reserve.setHeadCount(headCount);
        reserve.setUserNo(userNo);
        return reserve;
    }

    // 날짜 문자열 파싱
    private LocalDateTime parseReserveDate(String reserveDate) {
        try {
            return LocalDateTime.parse(reserveDate, DATETIME_FORMATTER).withSecond(0);
        } catch (Exception e) {
            throw new IllegalStateException("잘못된 요청입니다.");
        }
    }

    // 중복 예약 확인
    private void chkDuplReservation(int userNo, int restNo, LocalDate reservationDate) {
        LocalDate today = LocalDate.now();
        if (reservationDate.isEqual(today)) {
            List<ReserveVo> existingReservations = reserveDao.findReservationsByUserAndRestaurant(userNo, restNo, today);
            if (!existingReservations.isEmpty()) {
                throw new IllegalStateException("당일에 이미 예약된 레스토랑입니다.");
            }
        }
    }

    // 기존 예약 조회
    private ReserveVo existingReservation(int reserveNo) {
        ReserveVo existingReservation = reserveDao.getList(reserveNo);
        if (existingReservation == null) {
            throw new IllegalStateException("존재하지 않는 예약입니다.");
        }
        return existingReservation;
    }

    //예약 정보 업데이트
    private void updateReservation(ReserveVo reservation, int restNo, int headCount, int userNo, LocalDateTime localDateTime) {
        reservation.setRestNo(restNo);
        reservation.setHeadCount(headCount);
        reservation.setUserNo(userNo);
        reservation.setReserveTime(Timestamp.valueOf(localDateTime));
    }
}