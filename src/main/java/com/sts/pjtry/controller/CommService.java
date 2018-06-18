package com.sts.pjtry.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Service
public class CommService {

//	@Autowired RestTemplate restTemplate;
	private RestTemplate restTemplate = new RestTemplate();
	private static final Logger logger = LoggerFactory.getLogger(CommService.class);
	
	@Autowired
	private CommMapper commMapper;
	public static Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create(); 
	

	public Map<String, Object> savePage(Map<String, Object> paramMap) {
		Map<String, Object> rtnmap = new HashMap<String, Object>();
		List<Map<String, Object>> pageList = (List<Map<String, Object>>) paramMap.get("rtnList");
		List<Map<String, Object>> pageRtnList = new ArrayList();
		int pageSeq =0;
		System.out.println(pageList.size());
		for(int i=0;i<pageList.size();i++) {
			Map<String, Object> pageRtnMap = new HashMap<String, Object>();
			Map<String, Object> itemMap = pageList.get(i);
			
			Map<String, Object> pageMap = (Map<String, Object>) itemMap.get("page");
			System.out.println(pageMap.get("revision"));
			
			
			
			//pageMap.get("")
			
			try {
				//pageSeq =  commMapper.savePage(pageMap);	
			} catch (Exception e) {
				e.printStackTrace();
			}
					
			pageRtnMap.put("pageSeq", pageSeq);
			pageRtnList.add(pageRtnMap);
		}
		rtnmap.put("pageList",pageList);
		return rtnmap;
	}
	
}
