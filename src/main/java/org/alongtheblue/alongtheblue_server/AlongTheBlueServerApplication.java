package org.alongtheblue.alongtheblue_server;

import org.alongtheblue.alongtheblue_server.global.data.tourData.TourData;
import org.alongtheblue.alongtheblue_server.global.data.tourData.TourDataService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.ArrayList;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class AlongTheBlueServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlongTheBlueServerApplication.class, args);
	}

}
