package edu.baoss.resourceservice.services;

import edu.baoss.resourceservice.exceptions.NoPhoneNumberFoundException;
import edu.baoss.resourceservice.feignproxies.OfferFeignProxy;
import edu.baoss.resourceservice.model.PhoneNumber;
import edu.baoss.resourceservice.repositories.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhoneNumberService {
    @Autowired
    PhoneNumberRepository phoneNumberRepository;
    @Autowired
    OfferFeignProxy offerFeignProxy;

    public List<PhoneNumber> getPhoneNumbersForSale() {
        List<PhoneNumber> phoneNumbers = phoneNumberRepository.findAll(Sort.by(Sort.Direction.DESC, "price"))
                .stream()
                .filter(phoneNumber -> !phoneNumber.isUsed())
                .peek(phoneNumber -> phoneNumber.setPrice(
                        offerFeignProxy.getDiscountedPrice("Mobile product", phoneNumber.getPrice())))
                .collect(Collectors.toList());
        if (phoneNumbers.isEmpty()) {
            throw new NoPhoneNumberFoundException("Sorry, but we don't have any SIM card yet (");
        }
        return phoneNumbers;
    }
}
