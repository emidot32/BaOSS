package edu.baoss.billingservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.baoss.billingservice.feign.OrderServiceFeignProxy;
import edu.baoss.billingservice.model.dtos.OrderDto;
import edu.baoss.billingservice.model.dtos.ProductInstance;
import edu.baoss.billingservice.model.entities.Payment;
import edu.baoss.billingservice.model.entities.User;
import edu.baoss.billingservice.repositories.PaymentRepository;
import edu.baoss.billingservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import static edu.baoss.billingservice.services.PaymentService.ONLY_DATE_FORMAT;

@Service
@RequiredArgsConstructor
public class DefaultImportService implements ApplicationRunner {
    private final static Random RANDOM = new Random();
    private final static SimpleDateFormat DATE_AND_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Value("${baoss.billing.number-of-users-for-generation}")
    private int numberOfUsersForGeneration;
    @Value("${baoss.billing.end-date}")
    private String endDateStr;

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final OrderServiceFeignProxy orderServiceFeignProxy;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (numberOfUsersForGeneration == 0) {
            return;
        }
        long millis = numberOfUsersForGeneration * (numberOfUsersForGeneration < 1000 ? 100L : 2000L);
        System.out.println(millis*(numberOfUsersForGeneration < 1000 ? 5L : 50L));
        Thread.sleep(millis*(numberOfUsersForGeneration < 1000 ? 5L : 50L));
        System.out.println("Payments generation started");
        List<OrderDto> orders = orderServiceFeignProxy.getAllOrders();
        List<ProductInstance> productInstances = orderServiceFeignProxy.getActiveProductInstances();
        Map<Long, User> idToUser = userRepository.findAll().stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
        for (OrderDto order : orders) {
            Date orderDate = DATE_AND_TIME_FORMAT.parse(order.getCompletionDateStr());
            Payment nrcPayment = Payment.builder()
                    .paymentDate(orderDate)
                    .value(order.getTotalNRC())
                    .purpose("NRC payment")
                    .user(idToUser.get(order.getUserId()))
                    .build();
            paymentRepository.save(nrcPayment);
        }
        LocalDateTime endDate = LocalDateTime.parse(endDateStr);
        for (ProductInstance productInstance: productInstances) {
            LocalDateTime mrcDateTime =  ONLY_DATE_FORMAT.parse(productInstance.getActivatedDateStr()).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            do {
                Payment mrcPayment = Payment.builder()
                        .paymentDate(Date.from(mrcDateTime.atZone(ZoneId.systemDefault()).toInstant()))
                        .value(productInstance.getOffer().getDiscountedPrice())
                        .purpose("MRC fee for " + productInstance.getProduct())
                        .user(idToUser.get(productInstance.getUserId()))
                        .build();
                paymentRepository.save(mrcPayment);
                mrcDateTime = mrcDateTime.plusMonths(1);
            } while (mrcDateTime.isBefore(endDate));
        }
        System.out.println("Payments generation finished");

    }
}
