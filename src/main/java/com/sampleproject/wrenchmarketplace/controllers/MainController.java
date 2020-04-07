package com.sampleproject.wrenchmarketplace.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class MainController {

	@GetMapping("/")
	public String showMainPageUnlogged(Model theModel) {
		return "mainpage_unlogged";
	}
	
	@GetMapping("/logged")
	public String showMainPageLogged(Model theModel) {
		return "mainpage_logged";
	}
	
}
