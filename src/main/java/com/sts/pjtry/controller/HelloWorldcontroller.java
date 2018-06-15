package com.sts.pjtry.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class HelloWorldcontroller {
	
	public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create(); 
	
	@RequestMapping("/hellow")
	public String Hello(Map<String,Object> model) {
		model.put("message", "Hellow World");
		return "welcome";
	}
	
	@RequestMapping(value="/hellowGetJson", method=RequestMethod.GET)
	    public @ResponseBody  String Hello(@RequestParam String contry, HttpServletRequest request) {
	    	Map<String, Object> rtnMap = new HashMap<String, Object>();
	    	rtnMap.put("korea", "안녕하세요");
			return gson.toJson(rtnMap);
	}
	 
	
    
    @RequestMapping(value="/hellowPostJson", method=RequestMethod.POST)
    public @ResponseBody  String Hello(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) {
    	Map<String, Object> rtnMap = new HashMap<String, Object>();
    	rtnMap.put("korea", "안녕하세요");
		return gson.toJson(rtnMap);
	}
    
	
	@RequestMapping("/")
	 public String index(){
	  return "index";
	 }
	
}
