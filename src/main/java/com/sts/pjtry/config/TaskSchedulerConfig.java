package com.sts.pjtry.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class TaskSchedulerConfig {

 @Bean
 public TaskScheduler taskScheduler() {
     //org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
     return new ThreadPoolTaskScheduler();
 }
 
 @Bean
	public Executor taskExcutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(5);
		return executor;
	}
 
}