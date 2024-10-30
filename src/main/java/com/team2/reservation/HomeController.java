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
    
    //index page
    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        UserVo user = (UserVo) session.getAttribute("loggedInUser"); 
        model.addAttribute("user", user); 
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
   
    
    
    // mypage - CRUD
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
    public String editReservation(@RequestParam int reserveNo, @RequestParam int restNo,@RequestParam int headCount,@RequestParam String reserveDate,
            HttpSession session,Model model) {
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
	
	        restService.list(model);
	        return "mypage"; 
    }
    
    @PostMapping("/mypage/delete")
    public String deleteReservation(@RequestParam("reserveNo") int reserveNo) {
        reserveService.deleteReservation(reserveNo);
        return "redirect:/mypage";
    }

    
    
    //restaurant
    @GetMapping("/restaurant")
    public String showRestaurants(Model model) {
    	restService.list(model);
        return "restaurant"; 
    }
    
    //restaurant - reservation
    @PostMapping("/restaurant")
    public String makeReservation(@RequestParam int restNo, @RequestParam int headCount, @RequestParam String reserveDate, HttpSession session, Model model) {
        UserVo user = (UserVo) session.getAttribute("loggedInUser");

        if (user == null) return "redirect:/"; // 로그인 상태가 아닐 경우 리다이렉트

        try {
            reserveService.addReservation(restNo, headCount, reserveDate, user.getUserNo());
            return "redirect:/mypage"; 
        }  catch (IllegalStateException e) {
            model.addAttribute("errorMessage", "당일에 이미 예약된 레스토랑입니다.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "예약을 처리하는 중에 오류가 발생했습니다. 다시 시도해주세요.");
        }

        restService.list(model);
        return "restaurant"; 
    }
    
    //review
    @PostMapping("/review/add")
    public String addReview(@ModelAttribute ReviewVo bean, HttpSession session) {
        UserVo user = (UserVo) session.getAttribute("loggedInUser");
        if (user != null) {
            try {
                // 먼저 userNo 설정
                bean.setUserNo(user.getUserNo());
                
                // 필수 필드 검증
                if (bean.getRestNo() == 0) {
                    throw new IllegalArgumentException("식당 정보가 필요합니다.");
                }
                if (bean.getReviewContent() == null || bean.getReviewContent().trim().isEmpty()) {
                    throw new IllegalArgumentException("리뷰 내용을 입력해주세요.");
                }
                if (bean.getReviewScore() <= 0) {
                    bean.setReviewScore(1); // 기본값 설정
                }
                
                System.out.println("리뷰 데이터 확인:");
                System.out.println("UserNo: " + bean.getUserNo());
                System.out.println("RestNo: " + bean.getRestNo());
                System.out.println("Content: " + bean.getReviewContent());
                System.out.println("Score: " + bean.getReviewScore());
                
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