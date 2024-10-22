package com.team2.reservation;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team2.reservation.reserve.service.ReserveService;
import com.team2.reservation.restaurant.service.RestaurantService;
import com.team2.reservation.user.model.UserDao;
import com.team2.reservation.user.model.UserVo;
import com.team2.reservation.user.service.UserService;

@Controller
public class HomeController {
    private final UserService userService;
    private final RestaurantService restService;
    private final ReserveService reserveService;
	private final UserDao userDao;

    @Autowired
    public HomeController(RestaurantService restService, ReserveService reserveService, UserService userService, UserDao userDao) {
        this.restService = restService;
        this.userService = userService;
        this.reserveService = reserveService; 
        this.userDao = userDao;
        
    }
    
    //index page
    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        UserVo user = (UserVo) session.getAttribute("loggedInUser"); 
        model.addAttribute("user", user); // 모델에 사용자 정보 추가
        restService.list(model);
        return "index";
    }

    
    //register
    @PostMapping("/")
    public String add(@ModelAttribute UserVo bean) {
        userService.add(bean);
        return "redirect:/";
    }
    
    //check-email
    @PostMapping("/check-email")
    public ResponseEntity<String> checkEmail(@RequestParam String userEmail) {
        System.out.println("recieve msg : " + userEmail);  
        boolean isAvailable = userService.isEmailAvailable(userEmail);
        return isAvailable ? ResponseEntity.ok("available") : ResponseEntity.ok("exists");
    }


    
    //login
    @PostMapping("/login")
    public String login(@RequestParam String userEmail, @RequestParam String userPw, HttpSession session, Model model) {
        UserVo user = userService.login(userEmail, userPw);
        if (user != null) {
            System.out.println("Login Success : " + user);
            session.setAttribute("loggedInUser", user);
            return "redirect:/"; // 로그인 성공시 index로 리다이렉트
        } else {
            model.addAttribute("errorMessage", "Wrong email or Password");
            return "index"; // 로그인 실패 시 로그인 페이지로 이동
        }
    }

    //logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/";
    }
    
    // 마이페이지- 사용자의 예약 목록을 보여주는 기능 추가
    @GetMapping("/mypage")
    public String myPage(Model model, HttpSession session) {
        UserVo user = (UserVo) session.getAttribute("loggedInUser");  // 로그인한 사용자 가져오기

        // 사용자의 예약 목록 조회 (userNo 사용)
        reserveService.listByUser(user.getUserNo(), model);  // 예약 목록을 model에 추가
        return "mypage";  // mypage.jsp로 이동
    }
    

    
    //restaurant
    @GetMapping("/restaurant")
    public String showRestaurants(Model model) {
    	restService.list(model);
        return "restaurant"; // 
    }


}