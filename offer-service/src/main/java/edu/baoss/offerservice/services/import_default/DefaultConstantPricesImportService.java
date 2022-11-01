package edu.baoss.offerservice.services.import_default;

import edu.baoss.offerservice.model.ConstantPrices;
import edu.baoss.offerservice.repositories.ConstantPricesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultConstantPricesImportService extends DefaultOffersImportService<ConstantPrices> {

    private final ConstantPricesRepository constantPricesRepository;

    @Override
    protected String getFileName() {
        return "offers/constant_prices.json";
    }

    @Override
    protected MongoRepository<ConstantPrices, String> getRepository() {
        return constantPricesRepository;
    }

    @Override
    protected Class<ConstantPrices> getConcreteClass() {
        return ConstantPrices.class;
    }

    @Override
    protected boolean offersAreEmpty() {
        return constantPricesRepository.findAll().size() == 0;
    }
}
