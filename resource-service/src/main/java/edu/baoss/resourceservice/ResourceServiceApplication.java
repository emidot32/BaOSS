package edu.baoss.resourceservice;

import edu.baoss.resourceservice.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class ResourceServiceApplication implements CommandLineRunner {
	@Autowired
	DeviceService deviceService;

	public static void main(String[] args) {
		SpringApplication.run(ResourceServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//deviceService.reserveDevice("601585a1badfc41531fed91f");
	}
}
