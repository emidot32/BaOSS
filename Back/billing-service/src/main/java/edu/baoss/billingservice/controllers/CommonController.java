package edu.baoss.billingservice.controllers;

import edu.baoss.billingservice.model.BillingAccount;
import edu.baoss.billingservice.services.BillingAccountService;
import edu.baoss.billingservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/")
public class CommonController {
    @Autowired
    UserService userService;
    @Autowired
    BillingAccountService baService;

    @GetMapping("/billing-accounts")
    public Set<BillingAccount> getBillingAccountsForUser(@RequestParam String login) {
        return userService.getBillingAccountsForUser(login);
    }

    @PostMapping("/user-billing-account")
    public Set<BillingAccount> addOrDeleteBillingAccountForUser(@RequestBody BillingAccount billingAccount) {
        Set<BillingAccount> billingAccounts = baService.addOrDeleteNewBillingAccount(billingAccount);
        System.out.println(billingAccounts);
        return billingAccounts;
    }
}
