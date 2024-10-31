package com.team2.reservation;

import java.util.List;

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

import com.team2.reservation.reserve.service.ReserveService;
import com.team2.reservation.restaurant.service.RestaurantService;
import com.team2.reservation.review.model.ReviewVo;
import com.team2.reservation.review.service.ReviewService;
import com.team2.reservation.user.model.UserDao;
import com.team2.reservation.user.model.UserVo;
import com.team2.reservation.user.service.UserService;

@Controller
public class HomeController {
    private final UserService userService;
    private final RestaurantService restService;
    private final ReserveService reserveService;
    private final UserDao userDao;
    private final ReviewService reviewService;

    @Autowired
    public HomeController(RestaurantService restService, ReserveService reserveService, UserService userService, UserDao userDao, ReviewService reviewService) {
        this.restService = restService;
        this.userService = userService; 
        this.reserveService = reserveService;  
        this.userDao = userDao;
        this.reviewService = reviewService;
    }

    // Index page
    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        UserVo user = (UserVo) session.getAttribute("loggedInUser"); 
        model.addAttribute("user", user); 
        restService.list(1, model); // 기본적으로 첫 페이지로 로드 (size는 10으로 설정)
        return "index";
    }

    // Register
    @PostMapping("/")
    public String add(@ModelAttribute UserVo bean) {
        userService.add(bean);
        return "redirect:/";
    }
    
    // Check email
    @PostMapping("/check-email")
    public ResponseEntity<String> checkEmail(@RequestParam String userEmail) {
        System.out.println("Received msg : " + userEmail);  
        boolean isAvailable = userService.isEmailAvailable(userEmail);
        return isAvailable ? ResponseEntity.ok("available") : ResponseEntity.ok("exists");
    }
    
    // Login
    @PostMapping("/login")
    public String login(@RequestParam String userEmail, @RequestParam String userPw, HttpSession session, Model model) {
        UserVo user = userService.login(userEmail, userPw);
        if (user != null) {
            System.out.println("Login Success : " + user);
            session.setAttribute("loggedInUser", user);
            return "redirect:/"; 
        } else {
            model.addAttribute("errorMessage", "Wrong email or password");
            return "index"; 
        }
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/";
    }
   
    // My Page - CRUD
    @GetMapping("/mypage")
    public String myPage(Model model, HttpSession session) {
        UserVo user = (UserVo) session.getAttribute("loggedInUser");
        if (user != null) {
            reserveService.listByUser(user.getUserNo(), model);
            String alertMessage = (String) session.getAttribute("alertMessage");
            String alertType = (String) session.getAttribute("alertType");
            
            if (alertMessage != null) {
                model.addAttribute("alertMessage", alertMessage);
                model.addAttribute("alertType", alertType);
                session.removeAttribute("alertMessage");
                session.removeAttribute("alertType");
            }
        }
        return "mypage";
    }
    
    @PostMapping("/mypage/edit")
    public String editReservation(@RequestParam int reserveNo, @RequestParam int restNo, @RequestParam int headCount, @RequestParam String reserveDate,
            HttpSession session, Model model) {
        UserVo user = (UserVo) session.getAttribute("loggedInUser");

        try {
            // 예약 수정 로직에 reserveNo 전달
            reserveService.updateReservation(reserveNo, restNo, headCount, reserveDate, user.getUserNo());
            return "redirect:/mypage"; 
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", "당일에 이미 예약된 레스토랑입니다.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "예약을 수정하는 중에 오류가 발생했습니다. 다시 시도해주세요.");
        }
	
        restService.list(1, model);
        return "mypage"; 
    }
    
    @PostMapping("/mypage/delete")
    public String deleteReservation(@RequestParam("reserveNo") int reserveNo) {
        reserveService.deleteReservation(reserveNo);
        return "redirect:/mypage";
    }

    // Restaurant list with pagination
    // 추가1 - 페이지네이션 처리
    @GetMapping("/restaurant")
    public String showRestaurants(
            @RequestParam(value = "page", defaultValue = "1") int page, //URL 파라미터에서 page값을 가져옴, 기본값은 1로 설정되어 있어 사용자가 페이지 번호를 지정하지 않으면 1 페이지 로드
            Model model) {

        restService.list(page, model); // 지정된 페이지(page 파라미터)에 해당하는 레스토랑 목록을 model에 추가
        return "restaurant"; 
    }
    
    // Restaurant reservation
    // 추가2 - 페이지네이션 관련 매개변수 추가
    // 사용자가 예약을 할 때 현재 페이지 정보를 유지하도록 페이지와 크기를 매개변수로 받음, 사용자가 에약 후에도 이전에 보고 있던 레스토랑 목록 페이지로 돌아갈 수 있도록 함
    @PostMapping("/restaurant") 
    public String makeReservation(
            @RequestParam int restNo, // 예약할 레스토랑의 ID
            @RequestParam int headCount, // 예약 인원 수
            @RequestParam String reserveDate, // 예약 날짜
            @RequestParam(value = "page", defaultValue = "1") int page, // 현재 보고 있는 페이지 번호, 기본값은 1
            @RequestParam(value = "size", defaultValue = "10") int size, // 한 페이지에 표시할 레스토랑 수, 기본값은 10
            HttpSession session, 
            Model model) {
    	
        UserVo user = (UserVo) session.getAttribute("loggedInUser"); // 세션 확인 : 세션에서 로그인된 사용자 정보를 가져옴

        if (user == null) return "redirect:/"; // 로그인 상태가 아닐 경우 리다이렉트

        try {
            reserveService.addReservation(restNo, headCount, reserveDate, user.getUserNo()); // reserveService.addReservation() 메서드를 호출하여 실제 예약을 처리, 이 때 사용자 ID도 함께 전달
            return "redirect:/mypage"; 
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", "당일에 이미 예약된 레스토랑입니다.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "예약을 처리하는 중에 오류가 발생했습니다. 다시 시도해주세요.");
        }

        restService.list(page, model); // 페이지네이션을 적용하여 레스토랑 목록을 로드
        return "restaurant"; 
    }
    
    // Review - Add Review
    @PostMapping("/review/add")
    public String addReview(@ModelAttribute ReviewVo bean, HttpSession session) {
        UserVo user = (UserVo) session.getAttribute("loggedInUser");
        if (user != null) {
            try {
                bean.setUserNo(user.getUserNo());
                
                if (bean.getRestNo() == 0) throw new IllegalArgumentException("식당 정보가 필요합니다.");
                if (bean.getReviewContent() == null || bean.getReviewContent().trim().isEmpty()) throw new IllegalArgumentException("리뷰 내용을 입력해주세요.");
                if (bean.getReviewScore() <= 0) bean.setReviewScore(1); // 기본값 설정

                reviewService.add(bean);
                session.setAttribute("alertType", "success");
                session.setAttribute("alertMessage", "리뷰가 성공적으로 작성되었습니다!");

            } catch (IllegalStateException e) {
                session.setAttribute("alertType", "danger");
                session.setAttribute("alertMessage", e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("alertType", "danger");
                session.setAttribute("alertMessage", "리뷰 작성 중 오류가 발생했습니다. 필수 정보를 모두 입력했는지 확인해주세요.");
            }
            return "redirect:/mypage";
        }
        return "redirect:/";
    }
    
    @GetMapping("/api/reviews/{restNo}")
    @ResponseBody
    public ResponseEntity<List<ReviewVo>> getRestaurantReviews(@PathVariable int restNo) {
        try {
            List<ReviewVo> reviews = reviewService.getReviewsByRestaurant(restNo);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}