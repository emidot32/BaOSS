package edu.baoss.resourceservice.services.import_default;

import edu.baoss.resourceservice.model.IpAddress;
import edu.baoss.resourceservice.repositories.IpAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
public class DefaultIpAddressesImportService extends DefaultResourcesImportService<IpAddress> {
    public final static SimpleDateFormat DATE_AND_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private final IpAddressRepository ipAddressRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        super.run(args);
        for (int i = 0; i < 100; i++) {
            IpAddress ipAddress = IpAddress.builder()
                    .ipAddress("144.120.133." + 100+i)
                    .used(true)
                    .expiredDate(DATE_AND_TIME_FORMAT.parse("22/01/2023 23:59"))
                    .build();
            ipAddressRepository.save(ipAddress);
        }
    }

    @Override
    protected String getFileName() {
        return "resources/IP_addresses_pool.json";
    }

    @Override
    protected MongoRepository<IpAddress, String> getRepository() {
        return ipAddressRepository;
    }

    @Override
    protected Class<IpAddress> getConcreteClass() {
        return IpAddress.class;
    }

    @Override
    protected boolean resourceIsEmpty() {
        return ipAddressRepository.findAll().isEmpty();
    }
}
