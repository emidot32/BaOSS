package edu.baoss.billingservice.services;

import edu.baoss.billingservice.exceptions.NotEnoughMoneyException;
import edu.baoss.billingservice.exceptions.UserNotFoundException;
import edu.baoss.billingservice.feign.OrderServiceFeignProxy;
import edu.baoss.billingservice.model.dtos.ProductInstance;
import edu.baoss.billingservice.model.entities.Payment;
import edu.baoss.billingservice.model.entities.User;
import edu.baoss.billingservice.repositories.PaymentRepository;
import edu.baoss.billingservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import static edu.baoss.billingservice.services.Constants.NRC_PAYMENT_PURPOSE;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final OrderServiceFeignProxy orderServiceFeignProxy;

    public final static SimpleDateFormat ONLY_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Transactional
    public void doNrcPayment(long userId, double totalNrc) {
        double balance = userRepository.findById(userId)
                .map(User::getBalance)
                .orElseThrow(UserNotFoundException::new);
        if (balance < totalNrc) {
            throw new NotEnoughMoneyException();
        }
        userRepository.doNrcPayment(userId, totalNrc);
        Payment payment = Payment.builder()
                .paymentDate(new Date())
                .externalId(UUID.randomUUID().toString())
                .value(totalNrc)
                .purpose(NRC_PAYMENT_PURPOSE)
                .build();
        paymentRepository.save(payment);
    }

    public boolean checkNrcPayment(long userId, double totalNrc) {
        return userRepository.findById(userId)
                .map(user -> user.getBalance() > totalNrc)
                .orElse(false);
    }

    @Scheduled(cron = "55 23 * * *")
    public void doMrcPayment() {
        orderServiceFeignProxy.getActiveProductInstances().stream()
                .filter(this::needToPay)
                .forEach(instance -> {
                    double balance = userRepository.findById(instance.getUserId())
                            .map(User::getBalance)
                            .orElseThrow(UserNotFoundException::new);
                    if (balance < instance.getOffer().getDiscountedPrice()) {
                        orderServiceFeignProxy.inactivateInstance(instance.getInstanceId());
                    } else {
                        userRepository.doMrcPayment(instance.getUserId(), instance.getOffer().getDiscountedPrice());
                        Payment payment = Payment.builder()
                                .paymentDate(new Date())
                                .externalId(UUID.randomUUID().toString())
                                .value(instance.getOffer().getDiscountedPrice())
                                .purpose("MRC fee for " + instance.getProduct())
                                .build();
                        paymentRepository.save(payment);
                    }
                });
    }

    private boolean needToPay(ProductInstance instance) {
        System.out.println(instance);
        System.out.println(new Date());
        Date activatedDate;
        try {
            activatedDate = ONLY_DATE_FORMAT.parse(instance.getActivatedDateStr());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Period period = Period.between(getLocalDate(activatedDate), LocalDate.now());
        System.out.println(period.getDays() % instance.getOffer().getRentTime() == 0);
        return period.getDays() % instance.getOffer().getRentTime() == 0;
    }

    public LocalDate getLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

}
