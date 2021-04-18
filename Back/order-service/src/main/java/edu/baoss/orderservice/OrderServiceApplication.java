package edu.baoss.orderservice;

import edu.baoss.orderservice.rabbitmq.configs.RabbitConfiguration;
import edu.baoss.orderservice.services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import java.text.SimpleDateFormat;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@Import(RabbitConfiguration.class)
public class OrderServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        String[] products = new String[] {"Mobile product", "Internet product"};
//        System.out.println(deliveryService.getAvailableHours(onlyDateFormat.parse("16/03/2021"), products));
    }
}
