package com.team2.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.team2.reservation.rest.service.RestService;
import com.team2.reservation.user.model.UserVo;
import com.team2.reservation.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	final RestService restService;
	final UserService userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		restService.list(model);
		return "index";
	}
	
	@PostMapping("/")
	public String add(@ModelAttribute UserVo bean) {
		userService.add(bean);
		return "redirect:/";
	}
	
}
