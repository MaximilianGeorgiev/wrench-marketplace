package com.sampleproject.wrenchmarketplace.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/test")
@Controller
public class TestController {

	@GetMapping("/showTestPage")
	public String showTestPage(Model theModel) {
		
		return "helloworld-view";
	}
	
}
