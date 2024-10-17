package com.team2.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team2.reservation.restaurant.model.RestaurantVo;
import com.team2.reservation.restaurant.service.RestaurantService;
import com.team2.reservation.user.model.UserVo;
import com.team2.reservation.user.service.UserService;

@Controller
public class HomeController {
    private final UserService userService;
    private final RestaurantService restService;

    @Autowired
    public HomeController(RestaurantService restService, UserService userService) {
        this.restService = restService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {
    	restService.list(model);
        System.out.println(model.getAttribute("list")); // 확인용 로그
        return "index";
    }
    
    @PostMapping("/")
    public String add(@ModelAttribute UserVo bean) {
        userService.add(bean);
        return "redirect:/";
    }
    
    @GetMapping("/restaurant/{restNo}")
	@ResponseBody
	public RestaurantVo detail(@PathVariable int restNo) {
		return restService.detail(restNo);
	}
}