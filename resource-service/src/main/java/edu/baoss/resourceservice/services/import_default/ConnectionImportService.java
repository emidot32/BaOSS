package edu.baoss.resourceservice.services.import_default;

import edu.baoss.resourceservice.model.ConnectedAddress;
import edu.baoss.resourceservice.model.ConnectedBuilding;
import edu.baoss.resourceservice.repositories.ConnectedAddressesRepository;
import edu.baoss.resourceservice.repositories.ConnectedBuildingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ConnectionImportService extends DefaultResourcesImportService<ConnectedBuilding> {

    private final ConnectedBuildingsRepository connectedBuildingsRepository;
    private final ConnectedAddressesRepository connectedAddressesRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (resourceIsEmpty()) {
            ConnectedAddress connectedAddress = connectedAddressesRepository.save(new ConnectedAddress(0, "00:00:00:00:00"));
            ConnectedBuilding connectedBuilding = ConnectedBuilding.builder()
                    .buildingId(0)
                    .switchSerialNumber("000000")
                    .switchMacAddress("00:00:00:00:00")
                    .connectedAddresses(Set.of(connectedAddress))
                    .build();
            connectedBuildingsRepository.save(connectedBuilding);
        }
    }

    @Override
    protected String getFileName() {
        return null;
    }

    @Override
    protected MongoRepository<ConnectedBuilding, String> getRepository() {
        return null;
    }

    @Override
    protected Class<ConnectedBuilding> getConcreteClass() {
        return null;
    }

    @Override
    protected boolean resourceIsEmpty() {
        return connectedBuildingsRepository.findAll().isEmpty();
    }
}
