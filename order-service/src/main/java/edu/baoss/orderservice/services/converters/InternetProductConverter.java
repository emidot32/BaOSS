package edu.baoss.orderservice.services.converters;

import edu.baoss.orderservice.Constants;
import edu.baoss.orderservice.feign.OfferServiceFeignProxy;
import edu.baoss.orderservice.feign.ResourceServiceFeignProxy;
import edu.baoss.orderservice.model.dtos.AddressDto;
import edu.baoss.orderservice.model.dtos.InternetProductInstance;
import edu.baoss.orderservice.model.dtos.MobileProductInstance;
import edu.baoss.orderservice.model.entities.Instance;
import edu.baoss.orderservice.model.entities.InternetProduct;
import edu.baoss.orderservice.model.entities.MobileProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InternetProductConverter extends ProductConverter<InternetProduct, InternetProductInstance> {
    private final ResourceServiceFeignProxy resourceServiceFeignProxy;
    private final OfferServiceFeignProxy offerServiceFeignProxy;
    
    @Override
    public InternetProductInstance entityToDto(InternetProduct entity, boolean isShort) {
        InternetProductInstance internetProductInstance = new InternetProductInstance();
        internetProductInstance.setId(entity.getId());
        internetProductInstance.setProduct(Constants.INTERNET_PRODUCT_STR);
        setCommonParamsToDto(internetProductInstance, entity);
        if (isShort) {
            return internetProductInstance;
        }
        internetProductInstance.setAddress(new AddressDto(entity.getAddress()));
        internetProductInstance.setDevice(entity.getDeviceId() == null
                ? null : resourceServiceFeignProxy.getDetailDeviceById(
                entity.getDeviceId(), entity.getAddress().getId()));
        internetProductInstance.setCableLen(entity.getCableLen());
        internetProductInstance.setFixedIp(entity.getFixedIp());
        internetProductInstance.setInternetOffer(offerServiceFeignProxy.getInternetOfferById(entity.getInternetOfferId()));
        return internetProductInstance;
    }

    @Override
    public InternetProduct dtoToEntity(InternetProductInstance dto) {
        return null;
    }
}
