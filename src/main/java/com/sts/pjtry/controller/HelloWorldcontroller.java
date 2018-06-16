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
	
	//국가를 구분할 String 인자값을 클라이언트로 받아서 국가별 인사를 리턴하는 컨트롤
	@RequestMapping(value="/hellowGetJson", method=RequestMethod.GET)
	public @ResponseBody  String Hello(@RequestParam String contry, HttpServletRequest request) {
	    	Map<String, Object> rtnMap = new HashMap<String, Object>();
	    String rtnStr =getHellowMap().get(contry);
	    rtnMap.put("result", rtnStr);
	    rtnMap.put("contry", contry);
	    rtnMap.put("comment", "현재의 결과값을 복사하여 hellowPostJson 에 입력값으로 사용하세요");
			return gson.toJson(rtnMap);
	}

    //위와 동일하나 포스트 방식으로 더많은 인자값을 받을 수 있다.(Json 형태로 MessageConverter 가 알아서 인자값의 형태(paramMap) 으로변환해준다. 
	@RequestMapping(value="/hellowPostJson", method=RequestMethod.POST)
    public @ResponseBody  String Hello(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) {
    		
		String contry = (String)paramMap.get("contry");
		Map<String, Object> rtnMap = new HashMap<String, Object>();
	    String rtnStr =getHellowMap().get(contry);
	    rtnMap.put("result", rtnStr);
		return gson.toJson(rtnMap);
	}
	
	//국가별 인사말배열을 제공하는 메써
    private Map<String, String> getHellowMap() {
	    	HashMap<String,String> hellowMap = new HashMap<String,String>();
	    	hellowMap.put("usa", "hellow");
	    	hellowMap.put("korea", "안녕하세");
	    	return hellowMap;
	}
	
	@RequestMapping("/")
	 public String index(){
	  return "index";
	 }
	
}
