package com.dbs.bgcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class BGCPDataProcessorApplication {
	public static void main(String[] args) {
		ApplicationContext context =  SpringApplication.run(BGCPDataProcessorApplication.class, args);

	}
}
