package com.team2.reservation.restaurant.model;

import java.sql.Time;
import java.sql.Timestamp;

import com.team2.reservation.resrve.model.ReserveVo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantVo {
	private int restNo;
	private String restName, restInfo, restPhone;
	private Time openTime, closeTime;
}