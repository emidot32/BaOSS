package edu.baoss.orderservice.services.converters;

import edu.baoss.orderservice.Constants;
import edu.baoss.orderservice.feign.OfferServiceFeignProxy;
import edu.baoss.orderservice.feign.ResourceServiceFeignProxy;
import edu.baoss.orderservice.model.dtos.MobileProductInstance;
import edu.baoss.orderservice.model.dtos.ProductInstance;
import edu.baoss.orderservice.model.entities.Instance;
import edu.baoss.orderservice.model.entities.MobileProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MobileProductConverter extends ProductConverter<MobileProduct, MobileProductInstance> {
    private final ResourceServiceFeignProxy resourceServiceFeignProxy;
    private final OfferServiceFeignProxy offerServiceFeignProxy;
    
    @Override
    public MobileProductInstance entityToDto(MobileProduct entity, boolean isShort) {
        MobileProductInstance mobileProductInstance = new MobileProductInstance();
        mobileProductInstance.setId(entity.getId());
        mobileProductInstance.setProduct(Constants.MOBILE_PRODUCT_STR);
        setCommonParamsToDto(mobileProductInstance, entity);
        if (isShort) {
            return mobileProductInstance;
        }
        mobileProductInstance.setPhoneNumber(resourceServiceFeignProxy.getPhoneNumber(entity.getPhoneNumber()));
        mobileProductInstance.setTariff(offerServiceFeignProxy.getTariffById(entity.getTariffId()));
        mobileProductInstance.setSupport5g(entity.isSupport5g());
        mobileProductInstance.setBalance(entity.getBalance());
        return mobileProductInstance;
    }

    @Override
    public MobileProduct dtoToEntity(MobileProductInstance mobileProductInstance) {
        return null;
    }
}
