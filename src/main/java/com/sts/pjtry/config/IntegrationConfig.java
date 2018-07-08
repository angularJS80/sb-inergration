package com.sts.pjtry.config;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.ByteArrayLfSerializer;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.TaskScheduler;

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
	TcpNetServerConnectionFactory factory;
	
	@Autowired
	private TaskScheduler taskScheduler; 
	
	
	/**
	 * 1. Server Connection Factory
	 * @return
	 */
	
	/*서버를 띠워주기위한 컨넥션 펙토리 */
	@Bean
	public AbstractServerConnectionFactory serverCF() {
		System.out.println("Connection Factory created. port:" + this.port);
		if(factory==null) {
			 factory = new TcpNetServerConnectionFactory(this.port);
	        factory.setSingleUse(false);
	        factory.setSoTimeout(10000);
	        factory.setSerializer(new ByteArrayLfSerializer());
	        factory.setDeserializer(new ByteArrayLfSerializer());
		}
	        return factory;   
	}
	/*게이트웨이를 통한 수발신 설*/
	@Bean
    public TcpInboundGateway gatewayCrLf() {
        TcpInboundGateway gateway = new TcpInboundGateway();
        gateway.setConnectionFactory(serverCF());
        gateway.setRequestChannel(checkChannel());
        gateway.setReplyChannel(inputTaskChannel());
      
        gateway.setTaskScheduler(taskScheduler);
        return gateway;
    }
	
	
	/* 수신 어뎁터를 컨넥션에 세팅해줌으로서 채널 수신  역활을 어뎁터가 한다.  
	@Bean
	public TcpReceivingChannelAdapter inboundAdapter() {
		TcpReceivingChannelAdapter adapter = new TcpReceivingChannelAdapter();
		adapter.setOutputChannel(checkChannel());
		adapter.setConnectionFactory(serverCF());
		return adapter;
	}*/
	
	/* 발신 에뎁터를 컨넥션에 세팅해줌으로서 채널 발신 역활을 어뎁터가 한다. 
	@Bean 
	@ServiceActivator(inputChannel="inputTaskChannel")
	public TcpSendingMessageHandler outboundAdapter() {
		TcpSendingMessageHandler adapter = new TcpSendingMessageHandler();
		adapter.setConnectionFactory(serverCF());
		return adapter;
	}*/
	
	
	
	/*수신될 메시지의 채널 형태*/
    @Bean
    public MessageChannel checkChannel() {
        //return new DirectChannel(); // p2p channel
        return new PublishSubscribeChannel(); // p2p channel
        
    }

    /*되돌려줄 메시지의 채널 형태 */
    @Bean
    public MessageChannel inputTaskChannel() {
        return new DirectChannel(); // p2p channel
    }

    @Bean
    public MessageChannel taskChannel() {
        return new DirectChannel(); // p2p channel
    }
    
  
    @Transformer(inputChannel="inputTaskChannel", outputChannel="taskChannel")
	public String convert(String str) {
    //	TaskUtil taskUtil = new TaskUtil(5);
    //taskUtil.start();
    	
		String result = str;
		System.out.println("second. result: " + result);
		return result;
		
	}  
   
	@Transformer(inputChannel="checkChannel", outputChannel="inputTaskChannel")
	public String convert(byte[] bytes) {
		String result = new String(bytes);
		System.out.println("convert. result: " + result);
		return result;
		
	}    
	
	//@Bean 
	
	@ServiceActivator(inputChannel="taskChannel")
	public String taskRunner(String str) {
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				for(int i=0;i<1000000;i++) {
					System.out.println("i running taskSc: " + str);
				}
				
			}
		};
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());	
		c.add(Calendar.MILLISECOND, 200); // 동시 발송시 1초식 딜레이를 준다 교착상태 방지용 
		
		taskScheduler.schedule(runnable,c.getTime());
		
			
		return "1234"+str;
	}
	
	
  /*
   	@Bean
   public GatewayProxyFactoryBean gateway(	) {	
	GatewayProxyFactoryBean gateway = new GatewayProxyFactoryBean(NoAnnotationsAllowed.class);
       gateway.setDefaultRequestChannelName("checkChannel");
      
       return gateway;
   }
    public static interface NoAnnotationsAllowed {
        public void foo(String out);
    }
*/
    
}
