package edu.baoss.billingservice.services;

import edu.baoss.billingservice.model.BillingAccount;
import edu.baoss.billingservice.model.User;
import edu.baoss.billingservice.repositories.BillingAccountRepository;
import edu.baoss.billingservice.repositories.UserRepository;
import edu.baoss.userservice.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
public class BillingAccountService {
    @Autowired
    BillingAccountRepository baRepository;
    @Autowired
    UserRepository userRepository;

    public Set<BillingAccount> addOrDeleteNewBillingAccount(BillingAccount billingAccount) {
        if (userRepository.existsById(billingAccount.getUser().getId())) {
            User user = billingAccount.getUser();
            Optional<BillingAccount> inMemoryBA = baRepository.findAll()
                    .stream()
                    .filter(ba -> ba.getAccountNumber().equalsIgnoreCase(billingAccount.getAccountNumber()))
                    .findFirst();

            if (inMemoryBA.isPresent()){
                baRepository.delete(inMemoryBA.get());
            } else {
                billingAccount.setBalance(50+new Random().nextInt(150));
                baRepository.save(billingAccount);
            }
            return userRepository.getUserByLogin(user.getLogin()).getBillingAccounts();
        } else
            throw new UserNotFoundException("User is not found");

    }
}
