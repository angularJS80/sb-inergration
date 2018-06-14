package com.sts.pjtry.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldcontroller {
	
	@RequestMapping("/hellow")
	public String Hello(Map<String,Object> model) {
		model.put("message", "Hellow World");
		return "welcome";
	}
	
	@RequestMapping("/")
	 public String index(){
	  return "index";
	 }
	
}
