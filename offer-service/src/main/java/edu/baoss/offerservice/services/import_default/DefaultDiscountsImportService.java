package edu.baoss.offerservice.services.import_default;

import edu.baoss.offerservice.model.Discount;
import edu.baoss.offerservice.repositories.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultDiscountsImportService extends DefaultOffersImportService<Discount> {

    private final DiscountRepository discountRepository;

    @Override
    protected String getFileName() {
        return "offers/discounts.json";
    }

    @Override
    protected MongoRepository<Discount, String> getRepository() {
        return discountRepository;
    }

    @Override
    protected Class<Discount> getConcreteClass() {
        return Discount.class;
    }

    @Override
    protected boolean offersAreEmpty() {
        return discountRepository.findAll().size() == 0;
    }
}
