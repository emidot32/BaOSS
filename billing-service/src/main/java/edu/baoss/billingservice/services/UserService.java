package edu.baoss.billingservice.services;

import edu.baoss.billingservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

//    public Set<BillingAccount> getBillingAccountsForUser(String login){
//        return userRepository.getUserByLogin(login).getBillingAccounts();
//    }
}
