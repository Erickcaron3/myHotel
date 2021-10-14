package net.erickcaron.apartmentapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ApartmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApartmentApplication.class, args);
    }

}
