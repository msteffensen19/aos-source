package com.advantage.online.store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import com.advantage.online.store.image.ImageManagement;
import com.advantage.online.store.image.ImageManagementAccess;

@Configuration
@PropertySources(value = {@PropertySource("classpath:imageManagement.properties")})
public class ImageManagementConfiguration {

	public static final String PROPERTY_IMAGE_MANAGEMENT_REPOSITORY = "advantage.imageManagement.repository";

	@Autowired
	private Environment environment;

	@Bean(name = "imageManagement")
	public ImageManagement getImageManagement() {

		final String imageManagementRepository = environment.getProperty(ImageManagementConfiguration.PROPERTY_IMAGE_MANAGEMENT_REPOSITORY);
		return ImageManagementAccess.getImageManagement(imageManagementRepository);
	}
}