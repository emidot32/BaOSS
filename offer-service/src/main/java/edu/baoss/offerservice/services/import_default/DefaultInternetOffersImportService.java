package edu.baoss.offerservice.services.import_default;

import edu.baoss.offerservice.model.InternetOffer;
import edu.baoss.offerservice.repositories.InternetOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultInternetOffersImportService extends DefaultOffersImportService<InternetOffer> {

    private final InternetOfferRepository internetOfferRepository;

    @Override
    protected String getFileName() {
        return "offers/internet_offers.json";
    }

    @Override
    protected MongoRepository<InternetOffer, String> getRepository() {
        return internetOfferRepository;
    }

    @Override
    protected Class<InternetOffer> getConcreteClass() {
        return InternetOffer.class;
    }

    @Override
    protected boolean offersAreEmpty() {
        return internetOfferRepository.findAll().size() == 0;
    }
}
