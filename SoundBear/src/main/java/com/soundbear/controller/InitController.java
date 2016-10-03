package com.soundbear.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class InitController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}
	
	
	@RequestMapping(value = "/play", method = RequestMethod.GET)
	public String play(Model model) {
		return "play";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model) {
		return "register";
	}

}
