package com.sts.pjtry.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sts.pjtry.util.TextUtil;

@Controller
public class CommController {
	
	public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create(); 

	@Autowired
	private CommService commService ;
	
	@RequestMapping(value = "/readText", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }) 
    @ResponseBody
    public String readExcel( @RequestParam("file") MultipartFile file) {
    	Map<String, Object> rtnMap = new HashMap<String, Object>();
    	rtnMap.put("parseResult", TextUtil.readXml(file));    	
    	commService.savePage(rtnMap);
    	return gson.toJson(rtnMap);
    }
	
}
