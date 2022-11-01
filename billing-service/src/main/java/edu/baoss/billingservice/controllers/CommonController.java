package edu.baoss.billingservice.controllers;

import edu.baoss.billingservice.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RequestMapping("/")
public class CommonController {

    private final PaymentService paymentService;

//    @GetMapping("/billing-accounts")
//    public Set<BillingAccount> getBillingAccountsForUser(@RequestParam String login) {
//        return userService.getBillingAccountsForUser(login);
//    }

//    @PostMapping("/user-billing-account")
//    public Set<BillingAccount> addOrDeleteBillingAccountForUser(@RequestBody BillingAccount billingAccount) {
//        Set<BillingAccount> billingAccounts = baService.addOrDeleteNewBillingAccount(billingAccount);
//        System.out.println(billingAccounts);
//        return billingAccounts;
//    }
//
    @PutMapping("/nrc-payment")
    public void doNrcPayment(@RequestParam("userId") long userId,
                             @RequestParam("totalNRC") double totalNRC) {
        paymentService.doNrcPayment(userId, totalNRC);
    }

    @GetMapping("/check-nrc-payment")
    public boolean checkNrcPayment(@RequestParam("userId") long userId,
                                   @RequestParam("totalNRC") double totalNRC) {
        return paymentService.checkNrcPayment(userId, totalNRC);
    }

}
