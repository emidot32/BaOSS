package edu.baoss.orderservice.services;

import edu.baoss.orderservice.Constants;
import edu.baoss.orderservice.actions.provisioning.ProvisioningAction;
import edu.baoss.orderservice.feign.OfferServiceFeignProxy;
import edu.baoss.orderservice.feign.ResourceServiceFeignProxy;
import edu.baoss.orderservice.feign.UserServiceFeignProxy;
import edu.baoss.orderservice.model.dtos.*;
import edu.baoss.orderservice.model.entities.*;
import edu.baoss.orderservice.model.enums.*;
import edu.baoss.orderservice.repositories.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.MarshalledObject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultImportService implements ApplicationRunner {

    private final static Random RANDOM = new Random();

    @Value("${baoss.orders.number-of-users-for-generation}")
    private int numberOfUsersForGeneration;
    @Value("${baoss.orders.end-date}")
    private String endDateStr;

    private final ResourceServiceFeignProxy resourceServiceFeignProxy;
    private final OfferServiceFeignProxy offerServiceFeignProxy;
    private final FlowComposer flowComposer;
    private final DeliveryService deliveryService;
    private final OrderRepository orderRepository;
    private final InstanceRepository instanceRepository;
    private final AddressRepository addressRepository;
    private final DeliveryRepository deliveryRepository;
    private final MobileProductRepository mobileProductRepository;
    private final InternetProductRepository internetProductRepository;
    private final DtvProductRepository dtvProductRepository;
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (numberOfUsersForGeneration == 0) {
            return;
        }
        long millis = numberOfUsersForGeneration * (numberOfUsersForGeneration < 1000 ? 100L : 2000L);
        System.out.println(millis);
        Thread.sleep(millis);
        System.out.println("Orders generation started");
        List<InternetOffer> internetOffers = offerServiceFeignProxy.getInternetOffers();
        List<DtvOffer> dtvOffers = offerServiceFeignProxy.getDtvOffers();
        List<Tariff> tariffs = offerServiceFeignProxy.getTariffs();
        ConstantPrices constantPrices = offerServiceFeignProxy.getConstantPrices();
        List<PhoneNumber> phoneNumbers = resourceServiceFeignProxy.getPhoneNumbers().stream()
                .sorted(Comparator.comparing(PhoneNumber::getId)).toList();
        List<Address> addresses = addressRepository.findAll(Sort.by("id"));
        List<Employee> employees = employeeRepository.findAllByPosition(Position.FITTER.getName());
        int fixedIpIndex = 0;
        int phoneNumberIndex = 0;
        for (Address address: addresses) {
            List<String> allProducts = new ArrayList<>();
            boolean fixedIpGenerated = generateOrder(address, internetOffers, dtvOffers, tariffs, constantPrices,
                    phoneNumbers, employees, allProducts, fixedIpIndex, phoneNumberIndex);
            if (fixedIpGenerated) {
                fixedIpIndex++;
            }
            if (allProducts.contains(Constants.MOBILE_PRODUCT_STR)) {
                phoneNumberIndex++;
            }
            fixedIpGenerated = generateOrder(address, internetOffers, dtvOffers, tariffs, constantPrices,
                    phoneNumbers, employees, allProducts, fixedIpIndex, phoneNumberIndex);
            if (fixedIpGenerated) {
                fixedIpIndex++;
            }
            if (allProducts.contains(Constants.MOBILE_PRODUCT_STR)) {
                phoneNumberIndex++;
            }
        }
        System.out.println("Orders generation finished");
    }

    private boolean generateOrder(Address address, List<InternetOffer> internetOffers, List<DtvOffer> dtvOffers,
                                  List<Tariff> tariffs, ConstantPrices constantPrices, List<PhoneNumber> phoneNumbers, List<Employee> employees,
                                  List<String> allProducts, int fixedIpIndex, int phoneNumberIndex) {
        InternetProduct internetProduct = null;
        DtvProduct dtvProduct = null;
        MobileProduct mobileProduct = null;
        Date orderDate = getOrderDate(address.getUserIds().get(0), !allProducts.isEmpty());
        double possibility = allProducts.isEmpty() ? 0.7 : 0.45;
        if (RANDOM.nextDouble() < possibility) {
            Instance instance = instanceRepository.save(Instance.builder()
                    .userId(address.getUserIds().get(0))
                    .activatedDate(orderDate)
                    .status(RANDOM.nextDouble() < 0.95 ? InstanceStatus.ACTIVE : InstanceStatus.INACTIVE)
                    .build());
            List<String> products = new ArrayList<>();
            if (!allProducts.contains(Constants.MOBILE_PRODUCT_STR) && phoneNumberIndex < phoneNumbers.size() && RANDOM.nextDouble() < 0.3) {
                mobileProduct = mobileProductRepository.save(MobileProduct.builder()
                        .instance(instance)
                        .balance(100)
                        .support5g(RANDOM.nextBoolean())
                        .phoneNumber(phoneNumbers.get(phoneNumberIndex).getPhoneNumber())
                        .tariffId(tariffs.get(RANDOM.nextInt(tariffs.size())).getId())
                        .build());
                resourceServiceFeignProxy.reserveSimCard(phoneNumbers.get(phoneNumberIndex));
                products.add(Constants.MOBILE_PRODUCT_STR);
                allProducts.add(Constants.MOBILE_PRODUCT_STR);
            }
            if (!allProducts.contains(Constants.INTERNET_PRODUCT_STR) && RANDOM.nextDouble() < 0.65) {
                internetProduct = internetProductRepository.save(
                        InternetProduct.builder()
                                .cableLen(RANDOM.nextInt(20)+3)
                                .internetOfferId(internetOffers.get(RANDOM.nextInt(internetOffers.size())).getId())
                                .address(address)
                                .instance(instance)
                                .fixedIp(RANDOM.nextDouble() < 0.009 ? "144.120.133." + (100+fixedIpIndex) : null)
                                .build());
                products.add(Constants.INTERNET_PRODUCT_STR);
                allProducts.add(Constants.INTERNET_PRODUCT_STR);
                resourceServiceFeignProxy.connectBuildings(List.of(address.getBuilding().getId()));
                resourceServiceFeignProxy.connectUser(address.getBuilding().getId(), address.getId(), randomMacAddress());
                if (!allProducts.contains(Constants.DTV_PRODUCT_STR) && RANDOM.nextDouble() < 0.3) {
                    dtvProduct = dtvProductRepository.save(DtvProduct.builder()
                            .dtvOfferId(dtvOffers.get(RANDOM.nextInt(dtvOffers.size())).getId())
                            .instance(instance)
                            .internetProduct(internetProduct)
                            .build());
                    products.add(Constants.DTV_PRODUCT_STR);
                    allProducts.add(Constants.DTV_PRODUCT_STR);
                }
            }
            double totalMrc = 0;
            double totalNrc = 0;
            if (mobileProduct != null) {
                totalMrc += getOfferById(mobileProduct.getTariffId(), tariffs).getDiscountedPrice();
                totalNrc += phoneNumbers.get(phoneNumberIndex).getPrice();
                if (mobileProduct.isSupport5g()) {
                    totalNrc += constantPrices.getSupportOf5gPrices()[1];
                }

            }
            if (internetProduct != null) {
                totalMrc += getOfferById(internetProduct.getInternetOfferId(), internetOffers).getDiscountedPrice();
                if (internetProduct.getFixedIp() != null) {
                    totalMrc += constantPrices.getFixedIpPrices()[1];
                }
                totalNrc += internetProduct.getCableLen() * constantPrices.getCableOneMeterPrice()[1];
            }
            if (dtvProduct != null) {
                totalMrc += getOfferById(dtvProduct.getDtvOfferId(), dtvOffers).getDiscountedPrice();
            }
            totalNrc = round(totalNrc, 2);
            if (!products.isEmpty()) {
                Pair<Integer, Integer> durationAndNumberOfEmployees = deliveryService.getDurationAndNumberOfEmployees(products.toArray(String[]::new));
                Order order = orderRepository.save(Order.builder()
                        .startDate(orderDate)
                        .status(OrderStatus.COMPLETED)
                        .userId(address.getUserIds().get(0))
                        .instance(instance)
                        .products(String.join(";", products))
                        .totalMRC(totalMrc)
                        .totalNRC(totalNrc)
                        .orderAim("New")
                        .completionDate(orderDate)
                        .workersNum(durationAndNumberOfEmployees.getRight())
                        .build());
                Set<Employee> workers = generateEmployees(durationAndNumberOfEmployees.getRight(), employees);
                Delivery delivery = deliveryRepository.save(Delivery.builder()
                        .deliveryDate(orderDate)
                        .address(address)
                        .duration(durationAndNumberOfEmployees.getLeft())
                        .status(DeliveryStatus.COMPLETED)
                        .order(order)
                        .workers(workers)
                        .responsible(workers.iterator().next())
                        .needInfo(internetProduct != null)
                        .build());
                OrderValue orderValue = OrderValue.builder()
                        .cableLength(internetProduct != null ? internetProduct.getCableLen() : 0)
                        .cablePriceTotal(constantPrices.getCableOneMeterPrice()[1])
                        .deliveryId(delivery.getId())
                        .deliveryDateStr(delivery.getDeliveryDate().toString())
                        .deliveryAndActivationMobile(mobileProduct != null)
                        .fixedIpSupport(internetProduct != null && internetProduct.getFixedIp() != null)
                        .installation(internetProduct != null)
                        .selectedAddress(new AddressDto())
                        .deliveryTime("df")
                        .selectedChannelNumber(dtvProduct != null ? getOfferById(dtvProduct.getDtvOfferId(), dtvOffers) : null)
                        .selectedSpeed(internetProduct != null ? getOfferById(internetProduct.getInternetOfferId(), internetOffers) : null)
                        .totalMRC(totalMrc)
                        .totalMRC(totalNrc)
                        .selectedPhoneNumber(mobileProduct != null ? new PhoneNumber() : null)
                        .selectedTariff(mobileProduct != null ? getOfferById(mobileProduct.getTariffId(), tariffs) : null)
                        .support5g(mobileProduct != null && mobileProduct.isSupport5g())
                        .selectedProducts(products.toArray(String[]::new))
                        .order(order)
                        .build();
                flowComposer.createExecutionFlow(orderValue)
                        .getActionList().stream()
                        .map(ProvisioningAction::getTask)
                        .forEach(task -> {
                            task.setStatus(TaskStatus.COMPLETED);
                            task.setStartDate(orderDate);
                            task.setCompletionDate(orderDate);
                            taskRepository.save(task);
                        });
            }
        }
        return internetProduct != null && internetProduct.getFixedIp() != null;
    }

    private <T extends Offer> T getOfferById(String id, List<T> offers) {
        return offers.stream()
                .filter(offer -> offer.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private Set<Employee> generateEmployees(int numberOfEmployees, List<Employee> employees) {
        Collections.shuffle(employees);
        return employees.stream()
                .limit(numberOfEmployees)
                .collect(Collectors.toSet());
    }

    private String randomMacAddress(){
        Random rand = new Random();
        byte[] macAddr = new byte[6];
        rand.nextBytes(macAddr);

        StringBuilder sb = new StringBuilder(18);
        for(byte b : macAddr){
            if(sb.length() > 0){
                sb.append(":");
            }else{ //first byte, we need to set some options
                b = (byte)(b | (byte)(0x01 << 6)); //locally adminstrated
                b = (byte)(b | (byte)(0x00 << 7)); //unicast

            }
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private Date getOrderDate(long userId, boolean secondGen) {
        LocalDateTime regDate = userRepository.getRegDateByUserId(userId)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime endDate = LocalDateTime.parse(endDateStr);
        LocalDateTime generated;
        do {
            generated = regDate;
            int maxDays = secondGen ? 200 : 3;
            generated = generated.plusDays(RANDOM.nextInt(maxDays));
            generated = RANDOM.nextDouble() < 0.5 ? generated.plusHours(RANDOM.nextInt(3)) : generated.minusHours(RANDOM.nextInt(3));
            generated = generated.plusMinutes(RANDOM.nextInt(60));
        } while (generated.isAfter(endDate));
        System.out.println(generated);
        return Date.from(generated.atZone(ZoneId.systemDefault()).toInstant());
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
