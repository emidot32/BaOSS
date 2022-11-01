package edu.baoss.offerservice.services.import_default;

import edu.baoss.offerservice.model.Tariff;
import edu.baoss.offerservice.repositories.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DefaultTariffsImportService extends DefaultOffersImportService<Tariff> {

    private final TariffRepository tariffRepository;

    @Override
    protected String getFileName() {
        return "offers/tariffs.json";
    }

    @Override
    protected MongoRepository<Tariff, String> getRepository() {
        return tariffRepository;
    }

    @Override
    protected Class<Tariff> getConcreteClass() {
        return Tariff.class;
    }

    @Override
    protected boolean offersAreEmpty() {
        return tariffRepository.findAll().size() == 0;
    }
}
