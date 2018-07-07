package com.sts.pjtry.util;

import org.springframework.web.multipart.MultipartFile;

public class TaskUtil extends Thread {
    int seq=0;
    MultipartFile file;
    public TaskUtil(int seq) {
        this.seq = seq;
    }
    public TaskUtil(MultipartFile file) {
        this.file = file;
    }
    
    public void run() {
    if(seq>0) {
    		for(int i=0;i<seq;i++) {
    			System.out.println("run run run !!!"+i);
    		}
    }else {
    	TextUtil.readXml(file);
        
    }
    	
    	   
      
       
        try {
            Thread.sleep(1000);
        }catch(Exception e) {

        }
        System.out.println(this.seq+" thread end.");
    }
	
}
