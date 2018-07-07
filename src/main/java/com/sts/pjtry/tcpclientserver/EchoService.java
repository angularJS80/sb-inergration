package com.sts.pjtry.tcpclientserver;

import org.springframework.integration.MessageTimeoutException;

/**
 * Sample service used in XML configuration 
 */
public class EchoService {

	public String test(String input) throws InterruptedException {
		if ("FAIL".equals(input)) {
			throw new RuntimeException("Failure Demonstration");
		}
		else if(input.startsWith("TIMEOUT_TEST")) {
			Thread.sleep(3000);
		}

		return input + ":echo";
	}

	public MessageTimeoutException noResponse(String input) {
		if ("TIMEOUT_TEST_THROW".equals(input)) {
			throw new MessageTimeoutException("No response received for " + input);
		}
		else {
			return new MessageTimeoutException("No response received for " + input);
		}
	}

}
