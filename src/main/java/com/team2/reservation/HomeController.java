package com.team2.reservation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.team2.reservation.rest.service.RestService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
//	final RestService restService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
//		restService.list(model);
		return "index";
	}
	
	@GetMapping("intro")
	public void intro() {
		
	}
	@GetMapping("mypage")
	public void mypage() {
		
	}

	@GetMapping("/review")
	   public String review(@RequestParam("reservationId") String reservationId, Model model) {
	        // 예약 ID를 모델에 추가하여 JSP로 전달
	       model.addAttribute("reservationId", reservationId);
	       return "review";  // review.jsp 파일을 렌더링
	   }
	
}
