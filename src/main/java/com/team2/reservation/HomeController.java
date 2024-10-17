package com.team2.reservation;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

<<<<<<< HEAD
@Controller
public class HomeController {
<<<<<<< HEAD
=======
=======
/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
>>>>>>> parent of 16e100b (bean, restDao, restVo, controller)

	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}
>>>>>>> parent of 16e100b (bean, restDao, restVo, controller)
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@GetMapping("review")
	public void review() {
		
	}
	
}
