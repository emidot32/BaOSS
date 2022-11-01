package edu.baoss.billingservice.services;

import edu.baoss.billingservice.repositories.BillingAccountRepository;
import edu.baoss.billingservice.repositories.PaymentRepository;
import edu.baoss.billingservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillingAccountService {
    @Autowired
    BillingAccountRepository baRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    UserRepository userRepository;


//    @Transactional
//    public Set<BillingAccount> addOrDeleteNewBillingAccount(BillingAccount billingAccount) {
//        if (userRepository.existsById(billingAccount.getUser().getId())) {
//            User user = billingAccount.getUser();
//            Optional<BillingAccount> inMemoryBA = baRepository.findAll()
//                    .stream()
//                    .filter(ba -> ba.getAccountNumber().equalsIgnoreCase(billingAccount.getAccountNumber()))
//                    .findFirst();
//
//            if (inMemoryBA.isPresent()){
//                baRepository.delete(inMemoryBA.get());
//            } else {
//                billingAccount.setBalance(50+new Random().nextInt(250));
//                baRepository.save(billingAccount);
//            }
//            return userRepository.getUserByLogin(user.getLogin()).getBillingAccounts();
//        } else
//            throw new UserNotFoundException("User is not found");
//    }
//
//    @Transactional
//    public BillingAccount doNrcPayment(long billingAccountId, double totalNRC) {
//        BillingAccount userBa = baRepository.findById(billingAccountId).orElseThrow(BillingAccountFoundException::new);
//        BillingAccount ownerBa = baRepository.findById(OWNER_BILLING_ACCOUNT_ID).orElseThrow(BillingAccountFoundException::new);
//        userBa.setBalance(userBa.getBalance()-totalNRC);
//        ownerBa.setBalance(ownerBa.getBalance()+totalNRC);
//        userBa = baRepository.save(userBa);
//        ownerBa = baRepository.save(ownerBa);
//        Payment payment = Payment.builder()
//                .paymentDate(new Date())
//                .fromBillingAccount(userBa)
//                .toBillingAccount(ownerBa)
//                .value(totalNRC)
//                .purpose(NRC_PAYMENT_PURPOSE)
//                .build();
//        paymentRepository.save(payment);
//        return userBa;
//    }

}
