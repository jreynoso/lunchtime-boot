package com.dispassionproject.lunchtime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LunchtimeApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(LunchtimeApplication.class);
		app.setBanner(new LunchtimeBanner());
		app.run(args);
	}

}
