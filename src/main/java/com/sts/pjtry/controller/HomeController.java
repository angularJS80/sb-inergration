package com.sts.pjtry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@Autowired
	ApplicationContext context;
	
	@Autowired 
	private AbstractServerConnectionFactory connFactory;
		
	@RequestMapping("/connections")
	public String getConnectionsCount() {
		return connFactory.getOpenConnectionIds().size()+"";
	}

	@RequestMapping("/send")
	public void sendMessage() throws Exception {
		List<String> openConns = connFactory.getOpenConnectionIds();
	    if(null == openConns || openConns.size() == 0){

	        throw new Exception("No Client connection registered");
	    }

		MessageChannel serverOutAdapter = (MessageChannel) context.getBean("toObAdapter");
	    
	    for (String connId: openConns) {
	    	Message<String> msg = MessageBuilder.withPayload("text out")
	    			.setHeader(IpHeaders.CONNECTION_ID, connId)
	    			.build();
	    	serverOutAdapter.send(msg);
	    }	
	}	
	
}
