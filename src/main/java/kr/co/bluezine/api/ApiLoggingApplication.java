package kr.co.bluezine.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "kr.co.bluezine.api")
public class ApiLoggingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiLoggingApplication.class, args);
	}

}
