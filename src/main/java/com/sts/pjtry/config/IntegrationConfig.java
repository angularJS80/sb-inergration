package com.sts.pjtry.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpReceivingChannelAdapter;
import org.springframework.integration.ip.tcp.TcpSendingMessageHandler;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.messaging.MessageChannel;

import com.sts.pjtry.util.TaskUtil;

/**
 * https://www.javacodegeeks.com/2014/05/spring-integration-4-0-a-complete-xml-free-example.html
 * https://stackoverflow.com/questions/19188360/how-do-i-create-a-tcp-inbound-gateway-which-detects-connection-loss-and-auto-rec
 * https://stackoverflow.com/questions/41211668/spring-integration-tcp-server-send-message-to-tcp-client
 * https://stackoverflow.com/questions/39062895/spring-integration-tcp-get-connection-id-of-the-connected-clients
 *
 */
@Configuration
//@ComponentScan("com.samsonan.integration.endpoint")				//@Component
//@IntegrationComponentScan("com.samsonan.integration.gateway")		//@MessagingGateway
@EnableIntegration
public class IntegrationConfig{

	private final int port = 1337;//SocketUtils.findAvailableTcpPort();
	
	/**
	 * 1. Server Connection Factory
	 * @return
	 */
	@Bean
	public AbstractServerConnectionFactory serverCF() {
		System.out.println("Connection Factory created. port:" + this.port);
		return new TcpNetServerConnectionFactory(this.port);
	}

	@Bean
	public TcpReceivingChannelAdapter inboundAdapter() {
		TcpReceivingChannelAdapter adapter = new TcpReceivingChannelAdapter();
		adapter.setOutputChannel(toSA());
		adapter.setConnectionFactory(serverCF());
		return adapter;
	}

	@Bean 
	@ServiceActivator(inputChannel="toObAdapter")
	public TcpSendingMessageHandler outboundAdapter() {
		TcpSendingMessageHandler adapter = new TcpSendingMessageHandler();
		adapter.setConnectionFactory(serverCF());
		return adapter;
	}
	
	
	@ServiceActivator(inputChannel = "toObAdapter")
    public void emailMessageSource(String message) {
		System.out.println("Connection Factory created. port:" + message);
    }
	
	
    @Bean
    public MessageChannel toSA() {
        return new DirectChannel(); // p2p channel
    }

    @Bean
    public MessageChannel toObAdapter() {
        return new DirectChannel(); // p2p channel
    }

    @Transformer(inputChannel="toObAdapter", outputChannel="toObAdapter")
	public String convert(String str) {
    	TaskUtil taskUtil = new TaskUtil(10000000);
    	taskUtil.start();
		String result = str;
		System.out.println("second. result: " + result);
		return result;
		
	}  
    
	@Transformer(inputChannel="toSA", outputChannel="toObAdapter")
	public String convert(byte[] bytes) {
		
		String result = new String(bytes);
		
		System.out.println("convert. result: " + result);
		
		return result;
		
	}    

}
