package edu.baoss.userservice;

import edu.baoss.userservice.model.Role;
import edu.baoss.userservice.model.User;
import edu.baoss.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class UserServiceApplication{

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}


//	@Override
//	public void run(String... args) throws Exception {
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//		userRepository.save(User.builder()
//							.name("Vasya")
//							.surname("Pupkin")
//							.email("vasya.pupkin@gmail.com")
//							.login("vasek")
//							.password(passwordEncoder.encode("test"))
//							.idCardNumber("01109903")
//							.contractNumber(100001)
//							.activityStatus(true)
//							.regDate(new Date())
//							.role(Role.USER)
//							.build()
//		);
//	}
}
