package com.advantage.accountsoap;

import com.advantage.common.Constants;

import com.sun.xml.messaging.saaj.soap.AttachmentPartImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;

@SpringBootApplication
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@PropertySources({
        @PropertySource(Constants.FILE_PROPERTIES_DEMO_APP),
        @PropertySource(Constants.FILE_PROPERTIES_INTERNAL),
        @PropertySource(Constants.FILE_PROPERTIES_EXTERNAL),
        @PropertySource(Constants.FILE_PROPERTIES_GLOBAL)})

public class AccountserviceApplication {


    public static void main(String[] args) {
        AttachmentPartImpl a;
        SpringApplication.run(AccountserviceApplication.class, args);
    }
}
