package com.team2.reservation;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    //index page
    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        UserVo user = (UserVo) session.getAttribute("loggedInUser"); 
        model.addAttribute("user", user); // 모델에 사용자 정보 추가
        restService.list(model);
        return "index";
    }

    
    //회원가입
    @PostMapping("/")
    public String add(@ModelAttribute UserVo bean) {
        userService.add(bean);
        return "redirect:/";
    }
    
    //로그인
    @PostMapping("/login")
    public String login(@RequestParam String userEmail, @RequestParam String userPw, HttpSession session, Model model) {
        UserVo user = userService.login(userEmail, userPw);
        if (user != null) {
            System.out.println("로그인 성공: " + user);
            session.setAttribute("loggedInUser", user);
            return "redirect:/"; // 로그인 성공 시 index로 리다이렉트
        } else {
            model.addAttribute("errorMessage", "잘못된 이메일 혹은 비밀번호입니다.");
            return "index"; // 로그인 실패 시 로그인 페이지로 이동
        }
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/";
    }
    
    //intro
    @GetMapping("/restaurant/{restNo}")
	@ResponseBody
	public RestaurantVo detail(@PathVariable int restNo) {
		return restService.detail(restNo);
	}
}