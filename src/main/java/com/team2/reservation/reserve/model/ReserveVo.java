package com.team2.reservation.reserve.model;

import java.sql.Time;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReserveVo {
    private int reserveNo;     // ���� ��ȣ
    private int restNo;        // �Ĵ� ��ȣ
    private int userNo;        // ����� ��ȣ
    private int headCount; // �ο� ��
    private String restName;   // �Ĵ� �̸�
    private String restReview; // ����
    private Timestamp reserveTime;  // ���� �ð�
    private Time openTime,closeTime;
    
    public String getReserveTimeStr() {
        return reserveTime != null ? reserveTime.toString().substring(0, 16) : null;
    }
}