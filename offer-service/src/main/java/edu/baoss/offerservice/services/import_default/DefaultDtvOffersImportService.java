package edu.baoss.offerservice.services.import_default;

import edu.baoss.offerservice.model.DtvOffer;
import edu.baoss.offerservice.repositories.DtvOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultDtvOffersImportService extends DefaultOffersImportService<DtvOffer> {

    private final DtvOfferRepository dtvOfferRepository;

    @Override
    protected String getFileName() {
        return "offers/dtv_offers.json";
    }

    @Override
    protected MongoRepository<DtvOffer, String> getRepository() {
        return dtvOfferRepository;
    }

    @Override
    protected Class<DtvOffer> getConcreteClass() {
        return DtvOffer.class;
    }

    @Override
    protected boolean offersAreEmpty() {
        return dtvOfferRepository.findAll().size() == 0;
    }
}
