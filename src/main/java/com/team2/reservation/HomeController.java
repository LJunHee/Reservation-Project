package com.team2.reservation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

<<<<<<< Updated upstream
/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
=======
import com.team2.reservation.rest.service.RestService;
>>>>>>> Stashed changes

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	final RestService restService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		restService.list(model);
		return "index";
	}
	
	@GetMapping("intro")
	public void intro() {
		
	}
	
}
