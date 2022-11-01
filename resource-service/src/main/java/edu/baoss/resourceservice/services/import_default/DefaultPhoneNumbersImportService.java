package edu.baoss.resourceservice.services.import_default;

import edu.baoss.resourceservice.model.PhoneNumber;
import edu.baoss.resourceservice.repositories.PhoneNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultPhoneNumbersImportService extends DefaultResourcesImportService<PhoneNumber> {

    private final PhoneNumberRepository phoneNumberRepository;

    @Value("${baoss.resources.number-of-phone-numbers}")
    private int numberOfPhoneNumbers;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        super.run(args);
        if (importDefault && numberOfPhoneNumbers > 0 && phoneNumbersAreEmpty()) {
            for (int i = 0; i < numberOfPhoneNumbers; i++) {
                PhoneNumber phoneNumber = PhoneNumber.builder()
                        .phoneNumber(String.format("09512%05d", i))
                        .countryCode("38")
                        .simCardNumber(String.format("8134%04d", i))
                        .pinCode("1111")
                        .pukCode("11112222")
                        .price(2)
                        .currency("dollar")
                        .used(false)
                        .build();
                System.out.println("Phone number:" + phoneNumber);
                phoneNumberRepository.save(phoneNumber);
            }
        }
        System.out.println("phone number gen end");
    }

    @Override
    protected String getFileName() {
        return "resources/phone_numbers.json";
    }

    @Override
    protected MongoRepository<PhoneNumber, String> getRepository() {
        return phoneNumberRepository;
    }

    @Override
    protected Class<PhoneNumber> getConcreteClass() {
        return PhoneNumber.class;
    }

    @Override
    protected boolean resourceIsEmpty() {
        return phoneNumberRepository.findAll().isEmpty();
    }

    private boolean phoneNumbersAreEmpty() {
        return phoneNumberRepository.findAll().size() < numberOfPhoneNumbers;
    }
}
