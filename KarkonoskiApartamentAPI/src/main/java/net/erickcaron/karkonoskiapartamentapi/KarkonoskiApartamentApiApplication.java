package net.erickcaron.karkonoskiapartamentapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class KarkonoskiApartamentApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(KarkonoskiApartamentApiApplication.class, args);
    }

}
