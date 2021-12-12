package com;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableDubbo
public class DubboProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(DubboProviderApplication.class, args);
    }

    @ResponseBody
    @RestController
    class TestController{

        @Value("${spring.application.name}")
        private String applicationName;

        @GetMapping
        public String version(){
            return String.format("MESH_SVC_NAME:[%s], MESH_SVC_VERSION:[%s], Provider [%s] : Hello, time: %s ",
                    System.getenv("MESH_SVC_NAME"), System.getenv("MESH_SVC_VERSION"),
                    applicationName, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }

    }


}
