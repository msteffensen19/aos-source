package com.advantage.accountsoap;

import com.advantage.common.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@Configuration
@ComponentScan
@PropertySources({
		@PropertySource(Constants.FILE_PROPERTIES_DEMO_APP),
		@PropertySource("classpath:/db.properties")})
public class AccountserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountserviceApplication.class, args);
	}
}
