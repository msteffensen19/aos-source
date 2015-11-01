package com.advantage.online.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.advantage.online.store.image.ImageManagement;
import com.advantage.online.store.image.ImageManagementAccess;

@Configuration
public class ImageManagementConfiguration {

	@Bean(name = "imageManagement")
	public ImageManagement getImageManagement() {

		return ImageManagementAccess.getImageManagement("C:/Temp/advantage");
	}
}