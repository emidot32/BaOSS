package edu.baoss.orderservice.services;


import edu.baoss.orderservice.Constants;
import edu.baoss.orderservice.feign.ResourceServiceFeignProxy;
import edu.baoss.orderservice.model.dtos.*;
import edu.baoss.orderservice.model.entities.*;
import edu.baoss.orderservice.model.enums.InstanceStatus;
import edu.baoss.orderservice.repositories.DtvProductRepository;
import edu.baoss.orderservice.repositories.InstanceRepository;
import edu.baoss.orderservice.repositories.InternetProductRepository;
import edu.baoss.orderservice.repositories.MobileProductRepository;
import edu.baoss.orderservice.services.converters.DtvProductConverter;
import edu.baoss.orderservice.services.converters.InternetProductConverter;
import edu.baoss.orderservice.services.converters.MobileProductConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstanceService {
    private final InstanceRepository instanceRepository;
    private final MobileProductRepository mobileProductRepository;
    private final InternetProductRepository internetProductRepository;
    private final DtvProductRepository dtvProductRepository;
    @Lazy
    private final ApplicationContext applicationContext;
    private final ResourceServiceFeignProxy resourceServiceFeignProxy;

    @Transactional
    Instance createInstances(OrderValue orderValue) {
        Instance instance = instanceRepository.save(Instance.builder()
                .userId(orderValue.getUserId())
                .status(InstanceStatus.ENTERING)
                .build());
        System.out.println(instance);
        InternetProduct internetProduct = null;
        for (String product : orderValue.getSelectedProducts()) {
            if (Constants.MOBILE_PRODUCT_STR.equals(product))
                mobileProductRepository.save(MobileProduct.builder()
                        .instance(instance)
                        .balance(100)
                        .support5g(orderValue.isSupport5g())
                        .phoneNumber(orderValue.getSelectedPhoneNumber().getPhoneNumber())
                        .tariffId(orderValue.getSelectedTariff().getId())
                        .build());
            if (Constants.INTERNET_PRODUCT_STR.equals(product))
                internetProduct = internetProductRepository.save(
                        InternetProduct.builder()
                        .cableLen(orderValue.getCableLength())
                        .internetOfferId(orderValue.getSelectedSpeed().getId())
                        .address(new Address(orderValue.getSelectedAddress()))
                        .instance(instance)
                        .build());
            if (Constants.DTV_PRODUCT_STR.equals(product)) {
                dtvProductRepository.save(DtvProduct.builder()
                        .dtvOfferId(orderValue.getSelectedChannelNumber().getId())
                        .instance(instance)
                        .internetProduct(internetProduct)
                        .build());
            }
        }
        return instance;
    }

    public List<ProductInstance> getAllProductInstancesForUser(long userId) {
        return instanceRepository.getInstancesByUserId(userId).stream()
                .flatMap(instance -> getProductInstancesForInstance(instance, true).stream())
                .collect(Collectors.toList());
    }

    public List<ProductInstance> getActiveProductInstances() {
        return instanceRepository.getInstanceByStatus(InstanceStatus.ACTIVE).stream()
                .flatMap(instance -> getProductInstancesForInstance(instance, false).stream())
                .collect(Collectors.toList());
    }

    public List<ProductInstance> getProductInstancesForInstance(Instance instance, boolean isShort) {
        List<ProductInstance> productInstances = new ArrayList<>();
        mobileProductRepository.getMobileProductByInstance(instance)
                .ifPresent(mobileProduct -> productInstances.add(applicationContext
                        .getBean(MobileProductConverter.class).entityToDto(mobileProduct, isShort)));
        internetProductRepository.getInternetProductByInstance(instance)
                .ifPresent(internetProduct -> productInstances.add(applicationContext
                        .getBean(InternetProductConverter.class).entityToDto(internetProduct, isShort)));
        dtvProductRepository.getDtvProductByInstance(instance)
                .ifPresent(dtvProduct -> productInstances.add(applicationContext
                        .getBean(DtvProductConverter.class).entityToDto(dtvProduct, isShort)));
        return productInstances;
    }
    
    public String getSimCardNumberIfPresentMobileProduct(Instance instance) {
        MobileProduct mobileProduct = mobileProductRepository.getMobileProductByInstance(instance).orElse(null);
        if (mobileProduct == null) {
            return null;
        }
        return String.valueOf(resourceServiceFeignProxy.getPhoneNumber(mobileProduct.getPhoneNumber()).getSimCardNumber());
    }

    public UserDevice getDeviceIfPresentInternetProduct(Instance instance) {
        InternetProduct internetProduct = internetProductRepository.getInternetProductByInstance(instance).orElse(null);
        if (internetProduct == null || internetProduct.getDeviceId() == null) {
            return null;
        }
        return resourceServiceFeignProxy.getReservedDeviceByDeviceId(internetProduct.getDeviceId());
    }

    public String getCableLengthIfPresentInternetProduct(Instance instance) {
        InternetProduct internetProduct = internetProductRepository.getInternetProductByInstance(instance).orElse(null);
        if (internetProduct == null) {
            return null;
        }
        return String.valueOf(internetProduct.getCableLen());
    }
    
    public MobileProductInstance getMobileProductInstance(long mobileProductId) {
        return mobileProductRepository.findById(mobileProductId)
                .map(mobileProduct -> applicationContext.getBean(MobileProductConverter.class).entityToDto(mobileProduct, false))
                .orElse(null);
    }

    public List<MobileProductInstance> getMobileProductInstances() {
        return mobileProductRepository.findAll().stream()
                .filter(mobileProduct -> InstanceStatus.ACTIVE.equals(mobileProduct.getInstance().getStatus()))
                .map(mobileProduct -> applicationContext.getBean(MobileProductConverter.class).entityToDto(mobileProduct, false))
                .collect(Collectors.toList());
    }

    public InternetProductInstance getInternetProductInstance(long internetProductId) {
        return internetProductRepository.findById(internetProductId)
                .map(internetProduct -> applicationContext.getBean(InternetProductConverter.class).entityToDto(internetProduct, false))
                .orElse(null);
    }

    public List<InternetProductInstance> getInternetProductInstances() {
        return internetProductRepository.findAll().stream()
                .filter(internetProduct -> InstanceStatus.ACTIVE.equals(internetProduct.getInstance().getStatus()))
                .map(internetProduct -> applicationContext.getBean(InternetProductConverter.class).entityToDto(internetProduct, false))
                .collect(Collectors.toList());
    }

    public DtvProductInstance getDtvProductInstance(long dtvProductId) {
        return dtvProductRepository.findById(dtvProductId)
                .map(dtvProduct -> applicationContext.getBean(DtvProductConverter.class).entityToDto(dtvProduct, false))
                .orElse(null);
    }

    public List<DtvProductInstance> getDtvProductInstances() {
        return dtvProductRepository.findAll().stream()
                .filter(dtvProduct -> InstanceStatus.ACTIVE.equals(dtvProduct.getInstance().getStatus()))
                .map(dtvProduct -> applicationContext.getBean(DtvProductConverter.class).entityToDto(dtvProduct, false))
                .collect(Collectors.toList());
    }

    public void setDeviceIdToInternetProduct(OrderValue orderValue) {
        //Instance instance = instanceRepository.findById(orderValue.getOrder().getInstance().getId()).get();
        //System.out.println(instance);
        internetProductRepository.getInternetProductByInstance(orderValue.getOrder().getInstance())
                .ifPresent(internetProduct -> {
                    System.out.println(internetProduct);
                    internetProduct.setDeviceId(orderValue.getSelectedDevice().getId());
                    internetProductRepository.save(internetProduct);
                });
    }

    public void setFixedIpToInternetProduct(String fixedIp, OrderValue orderValue) {
//        Instance instance = instanceRepository.findById(orderValue.getOrder().getInstance().getId()).get();
//        System.out.println(instance);
        internetProductRepository.getInternetProductByInstance(orderValue.getOrder().getInstance())
                .ifPresent(internetProduct -> {
                    internetProduct.setFixedIp(fixedIp);
                    internetProductRepository.save(internetProduct);
                });
    }

    public Instance saveInstance(Instance instance) {
        return instanceRepository.save(instance);
    }

    public void inactivateInstance(long instanceId) {
        instanceRepository.inactivateInstance(instanceId);
    }
}
