package com.team2.reservation;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team2.reservation.reserve.model.ReserveVo;
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
        model.addAttribute("user", user); 
        restService.list(model);
        return "index";
    }

    

   
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
            return "redirect:/"; 
        } else {
            model.addAttribute("errorMessage", "Wrong email or Password");
            return "index"; 
        }
    }

    //logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/";
    }
    
    
    // 마이페이지 - 사용자의 예약 목록을 보여주는 기능 추가
    @GetMapping("/mypage")
    public String myPage(Model model, HttpSession session) {
        UserVo user = (UserVo) session.getAttribute("loggedInUser");
        reserveService.listByUser(user.getUserNo(), model);
        return "mypage";
    }
    @PostMapping("/mypage/edit")
    public String editReservation(@ModelAttribute ReserveVo reserveVo, @RequestParam("reserveTime") String reserveTimeStr) {
        // reserveTimeStr (datetime-local) -> Timestamp로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(reserveTimeStr, formatter);
        Timestamp reserveTime = Timestamp.valueOf(localDateTime);

        // 변환된 reserveTime을 ReserveVo에 설정
        reserveVo.setReserveTime(reserveTime);

        // 예약 수정 처리
        reserveService.edit(reserveVo);
        return "redirect:/mypage";
    }

    @PostMapping("/mypage/delete")
    public String deleteReservation(@RequestParam("reserveNo") int reserveNo) {
        reserveService.delete(reserveNo);
        return "redirect:/mypage";
    }

  

    

    
    //restaurant
    @GetMapping("/restaurant")
    public String showRestaurants(Model model) {
    	restService.list(model);
        return "restaurant"; // 
    }
    
    //restaurant reservation
    @PostMapping("/restaurant")
    public String makeReservation(@RequestParam int restNo, @RequestParam int headCount, @RequestParam String reserveDate, HttpSession session, Model model) {
        UserVo user = (UserVo) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/"; 
        }

        try {
            reserveService.addReservation(restNo, headCount, reserveDate, user.getUserNo());
            return "redirect:/mypage"; 
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", "당일에 이미 예약된 레스토랑입니다.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "예약을 처리하는 중에 오류가 발생했습니다. 다시 시도해주세요.");
        }

        restService.list(model);
        return "restaurant"; 
    }

}


