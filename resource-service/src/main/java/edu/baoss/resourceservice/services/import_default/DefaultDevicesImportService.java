package edu.baoss.resourceservice.services.import_default;

import edu.baoss.resourceservice.model.Device;
import edu.baoss.resourceservice.repositories.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DefaultDevicesImportService extends DefaultResourcesImportService<Device> {

    private final DeviceRepository deviceRepository;

    @Override
    protected String getFileName() {
        return "resources/devices.json";
    }

    @Override
    protected MongoRepository<Device, String> getRepository() {
        return deviceRepository;
    }

    @Override
    protected Class<Device> getConcreteClass() {
        return Device.class;
    }

    @Override
    protected boolean resourceIsEmpty() {
        return deviceRepository.findAll().isEmpty();
    }
}
