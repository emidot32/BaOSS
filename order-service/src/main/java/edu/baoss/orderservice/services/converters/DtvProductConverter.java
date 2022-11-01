package edu.baoss.orderservice.services.converters;

import edu.baoss.orderservice.Constants;
import edu.baoss.orderservice.feign.OfferServiceFeignProxy;
import edu.baoss.orderservice.feign.ResourceServiceFeignProxy;
import edu.baoss.orderservice.model.dtos.DtvProductInstance;
import edu.baoss.orderservice.model.entities.Instance;
import edu.baoss.orderservice.model.entities.DtvProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DtvProductConverter extends ProductConverter<DtvProduct, DtvProductInstance> {
    private final OfferServiceFeignProxy offerServiceFeignProxy;
    
    @Override
    public DtvProductInstance entityToDto(DtvProduct entity, boolean isShort) {
        DtvProductInstance dtvProductInstance = new DtvProductInstance();
        dtvProductInstance.setId(entity.getId());
        dtvProductInstance.setProduct(Constants.DTV_PRODUCT_STR);
        setCommonParamsToDto(dtvProductInstance, entity);
        if (isShort) {
            return dtvProductInstance;
        }
        dtvProductInstance.setDtvOffer(offerServiceFeignProxy.getDtvOfferById(entity.getDtvOfferId()));
        dtvProductInstance.setInternetProductId(entity.getInternetProduct().getId());
        return dtvProductInstance;
    }

    @Override
    public DtvProduct dtoToEntity(DtvProductInstance dto) {
        return null;
    }
}
