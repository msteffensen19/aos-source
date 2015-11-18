package com.advantage.online.store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import com.advantage.online.store.image.ImageManagement;
import com.advantage.online.store.image.ImageManagementAccess;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Configuration
@PropertySources(value = {@PropertySource("classpath:imageManagement.properties")})
public class ImageManagementConfiguration {

    public static final String PROPERTY_IMAGE_MANAGEMENT_REPOSITORY = "advantage.imageManagement.repository";

    @Autowired
    private Environment environment;

    @Bean(name = "imageManagement")
    public ImageManagement getImageManagement() throws IOException {

        final String imageManagementRepository = environment.getProperty(ImageManagementConfiguration.PROPERTY_IMAGE_MANAGEMENT_REPOSITORY);
        return ImageManagementAccess.getImageManagement(getPath() + imageManagementRepository);
    }

    private String getPath() throws UnsupportedEncodingException {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        String fullPath = URLDecoder.decode(path, "UTF-8");
        //String pathArr[] = fullPath.split("/WEB-INF/classes/");
        String pathArr[] = fullPath.split("/target/");
        fullPath = pathArr[0];

        return fullPath.substring(1);
    }
}