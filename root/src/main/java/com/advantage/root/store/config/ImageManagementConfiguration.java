package com.advantage.root.store.config;

import com.advantage.root.store.image.ImageManagement;
import com.advantage.root.store.image.ImageManagementAccess;
import com.advantage.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

@Configuration
@PropertySources(value = {@PropertySource("classpath:imageManagement.properties")})
public class ImageManagementConfiguration {

    @Autowired
    private Environment environment;

    @Bean(name = "imageManagement")
    public ImageManagement getImageManagement() throws IOException {
        final String imageManagementRepository = environment.getProperty(Constants.PROPERTY_IMAGE_MANAGEMENT_REPOSITORY);
        return ImageManagementAccess.getImageManagement(getPath(imageManagementRepository));
    }

    public static String getPath(String pathArg) throws IOException {
        ClassPathResource filePath = new ClassPathResource("app.properties");
        File file = filePath.getFile();
        System.out.println(file.getPath());

        return file.getPath().split("WEB-INF")[0] + pathArg;
    }
}