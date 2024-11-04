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
    
    // �궗�슜�옄 �삁�빟 紐⑸줉 議고쉶
    public void listByUser(int userNo, Model model) {
        model.addAttribute("list", reserveDao.pullListByUser(userNo));
    }
    
    // �궗�슜�옄 �삁�빟 湲곕뒫 (�삁�빟 以묐났 泥댄겕)
    public void addReservation(int restNo, int headCount, String reserveDate, int userNo) {
        ReserveVo reserve = createReserveVo(restNo, headCount, userNo);
        LocalDateTime localDateTime = parseReserveDate(reserveDate);
        
        chkDuplReservation(userNo, restNo, localDateTime.toLocalDate());
        reserve.setReserveTime(Timestamp.valueOf(localDateTime));
        reserveDao.addList(reserve);
    }
    
    // �삁�빟 �닔�젙
    public void updateReservation(int reserveNo, int restNo, int headCount, String reserveDate, int userNo) {
        ReserveVo existingReservation = existingReservation(reserveNo);
        LocalDateTime localDateTime = parseReserveDate(reserveDate);
        
        updateReservation(existingReservation, restNo, headCount, userNo, localDateTime);
        reserveDao.setList(existingReservation);
    }
    
    // �삁�빟 �궘�젣
    public void deleteReservation(int reserveNo) {
        reserveDao.rmList(reserveNo);
    }  

    
    
    // �삁�빟 媛앹껜 �깮�꽦
    private ReserveVo createReserveVo(int restNo, int headCount, int userNo) {
        ReserveVo reserve = new ReserveVo();
        reserve.setRestNo(restNo);
        reserve.setHeadCount(headCount);
        reserve.setUserNo(userNo);
        return reserve;
    }

    // �궇吏� 臾몄옄�뿴 �뙆�떛
    private LocalDateTime parseReserveDate(String reserveDate) {
        try {
            return LocalDateTime.parse(reserveDate, DATETIME_FORMATTER).withSecond(0);
        } catch (Exception e) {
            throw new IllegalStateException("�옒紐삳맂 �슂泥��엯�땲�떎.");
        }
    }

    // 以묐났 �삁�빟 �솗�씤
    private void chkDuplReservation(int userNo, int restNo, LocalDate reservationDate) {
        LocalDate today = LocalDate.now();
        if (reservationDate.isEqual(today)) {
            List<ReserveVo> existingReservations = reserveDao.findReservationsByUserAndRestaurant(userNo, restNo, today);
            if (!existingReservations.isEmpty()) {
                throw new IllegalStateException("�떦�씪�뿉 �씠誘� �삁�빟�맂 �젅�뒪�넗�옉�엯�땲�떎.");
            }
        }
    }

    // 湲곗〈 �삁�빟 議고쉶
    private ReserveVo existingReservation(int reserveNo) {
        ReserveVo existingReservation = reserveDao.getList(reserveNo);
        if (existingReservation == null) {
            throw new IllegalStateException("議댁옱�븯吏� �븡�뒗 �삁�빟�엯�땲�떎.");
        }
        return existingReservation;
    }

    //�삁�빟 �젙蹂� �뾽�뜲�씠�듃
    private void updateReservation(ReserveVo reservation, int restNo, int headCount, int userNo, LocalDateTime localDateTime) {
        reservation.setRestNo(restNo);
        reservation.setHeadCount(headCount);
        reservation.setUserNo(userNo);
        reservation.setReserveTime(Timestamp.valueOf(localDateTime));
    }
    
    
}