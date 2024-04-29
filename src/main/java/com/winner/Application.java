package com.winner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author smart-dxw 2022年7月21日 11:27:04
 */
@SpringBootApplication
public class Application {

	private static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		try {

		} catch (Exception e) {
			logger.info("KeyToolInit.initialize = " + e);
		}
	}
}