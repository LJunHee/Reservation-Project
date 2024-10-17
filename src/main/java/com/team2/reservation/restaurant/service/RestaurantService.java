package com.team2.reservation.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.team2.reservation.restaurant.model.RestaurantDao;
import com.team2.reservation.restaurant.model.RestaurantVo;



@Service
public class RestaurantService {
    private final RestaurantDao restaurantDao;

    @Autowired
    public RestaurantService(RestaurantDao restaurantDao) {
        this.restaurantDao = restaurantDao;
    }
    
	public void list(Model model) {
		model.addAttribute("list", restaurantDao.pullList());
	}
    
    public RestaurantVo detail(int restNo) {
        return restaurantDao.getList(restNo);
    }
}
