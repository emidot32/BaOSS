package edu.baoss.orderservice.services;

import edu.baoss.orderservice.Constants;
import edu.baoss.orderservice.model.Flow;
import edu.baoss.orderservice.model.dtos.DeliveryAdditionalInfo;
import edu.baoss.orderservice.model.dtos.DeliveryDto;
import edu.baoss.orderservice.model.dtos.OrderValue;
import edu.baoss.orderservice.model.dtos.UserDevice;
import edu.baoss.orderservice.exceptions.NoEmployeeFoundException;
import edu.baoss.orderservice.model.entities.*;
import edu.baoss.orderservice.model.enums.DeliveryStatus;
import edu.baoss.orderservice.model.enums.OrderStatus;
import edu.baoss.orderservice.model.enums.Position;
import edu.baoss.orderservice.repositories.DeliveryRepository;
import edu.baoss.orderservice.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.baoss.orderservice.Constants.ONLY_DATE_FORMAT;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final EmployeeRepository employeeRepository;
    private final InstanceService instanceService;
    private final RabbitTemplate template;
    private final FulfilmentService fulfilmentService;
    private final FlowComposer flowComposer;

    private static final int[] HOURS =  IntStream.rangeClosed(10, 21).toArray();
    private final SimpleDateFormat dateHourFormat = new SimpleDateFormat("dd/MM/yyyy HH");
    private final SimpleDateFormat onlyHourFormat = new SimpleDateFormat("HH");

    public Delivery createDelivery(OrderValue orderValue, Order order) throws ParseException {
        Pair<Integer, Integer> durationAndNumberOfEmployees = getDurationAndNumberOfEmployees(orderValue.getSelectedProducts());
        int hour = Integer.parseInt(orderValue.getDeliveryTime().substring(0, 2));
        Date deliveryDate = dateHourFormat.parse(orderValue.getDeliveryDateStr() + " " + hour);
        List<Employee> allEmployees = employeeRepository.findAllByPosition(Position.FITTER.getName());
        Set<Employee> workers = getFreeEmployees(deliveryDate, allEmployees, durationAndNumberOfEmployees.getLeft(), hour)
                .stream()
                .limit(durationAndNumberOfEmployees.getRight())
                .collect(Collectors.toSet());
        Employee responsible = workers.stream().findFirst().orElseThrow(NoEmployeeFoundException::new);
        return deliveryRepository.save(Delivery.builder()
                .deliveryDate(deliveryDate)
                .address(new Address(orderValue.getSelectedAddress()))
                .duration(durationAndNumberOfEmployees.getLeft())
                .status(DeliveryStatus.NOT_STARTED)
                .order(order)
                .workers(workers)
                .responsible(responsible)
                .needInfo(orderValue.getSelectedSpeed() != null && orderValue.getSelectedDevice() == null)
                .build());
    }

    public List<DeliveryDto> getTodayDeliveriesForEmployeeAndUpdateIfStarted(long userId) {
        Employee employee = employeeRepository.getEmployeeByUserId(userId).orElseThrow(NoEmployeeFoundException::new);

        System.out.println(employee);
        Date now = new Date();
        List<DeliveryDto> deliveriesForEmployee = new ArrayList<>();
        List<Delivery> employeeDeliveries = employee.getDeliveries().stream()
                .sorted(Comparator.comparing(Delivery::getDeliveryDate).reversed()).toList();
        System.out.println(employeeDeliveries);
        for (Delivery delivery : employeeDeliveries) {
            if (sameDay(delivery.getDeliveryDate(), now)) {
                boolean responsible = employee.equals(delivery.getResponsible());
                boolean deliveryStarted = isDeliveryStarted(delivery);
                if (deliveryStarted) {
                    updateDelivery(delivery, DeliveryStatus.IN_PROGRESS, OrderStatus.IN_DELIVERY);
                }
                System.out.println(delivery);
                deliveriesForEmployee.add(
                        new DeliveryDto(delivery, getAdditionalInfo(delivery), responsible, deliveryStarted));
            }
        }
        return deliveriesForEmployee;

    }

    public List<String> getAvailableHours(Date deliveryDate, String[] products) {
        List<Employee> allEmployees = employeeRepository.findAllByPosition(Position.FITTER.getName());
        Pair<Integer, Integer> durationAndNumberOfEmployees = getDurationAndNumberOfEmployees(products);
        List<String> availableHours = new ArrayList<>();
        for (int hour : HOURS) {
            List<Employee> freeEmployees = getFreeEmployees(deliveryDate, allEmployees,
                    durationAndNumberOfEmployees.getLeft(), hour);
            if (freeEmployees.size() >= durationAndNumberOfEmployees.getRight())
                availableHours.add(String.format("%d:00", hour));
        }
        return availableHours;
    }

    public DeliveryDto finishDelivery(OrderValue orderValue) {
        Delivery delivery = deliveryRepository.findById(orderValue.getDeliveryId()).orElseThrow(RuntimeException::new);
        orderValue.setOrder(delivery.getOrder());
        updateDelivery(delivery, DeliveryStatus.COMPLETED, OrderStatus.PROCESSING);
        //template.convertAndSend(Constants.ORDER_FULFILMENT_EXCHANGE, Constants.CONTINUE, orderValue);
        Flow executionFlowAfterDelivery = flowComposer.getActionsAfterDelivery(orderValue);
        fulfilmentService.continueFulfilment(executionFlowAfterDelivery);
        return new DeliveryDto(delivery, getAdditionalInfo(delivery), false, false);
    }

    private List<Employee> getFreeEmployees(Date deliveryDate, List<Employee> allEmployees, int duration, int hour) {
        return allEmployees.stream()
                .filter(employee ->
                        isEmployeeFreeForHour(employee, hour, deliveryDate, duration))
                .collect(Collectors.toList());
    }

    private boolean isEmployeeFreeForHour(Employee employee, int hour, Date deliveryDate, int duration) {
        return employee.getDeliveries()
                .stream()
                .filter(delivery -> sameDay(deliveryDate, delivery.getDeliveryDate())
                        && isDeliveriesConflicted(hour, delivery.getDeliveryDate(), duration)
                        && isDeliveriesConflicted(hour, delivery))
                .findAny()
                .isEmpty();
    }

    private boolean isDeliveriesConflicted(int possibleHour, Date plannedDate, int duration) {
        int endOfPossibleDelivery = possibleHour + duration;
        return endOfPossibleDelivery > Integer.parseInt(onlyHourFormat.format(plannedDate));
    }

    private boolean isDeliveriesConflicted(int possibleHour, Delivery delivery) {
        int endOfPlannedDelivery = Integer.parseInt(onlyHourFormat.format(delivery.getDeliveryDate())) + delivery.getDuration();
        return endOfPlannedDelivery > possibleHour;
    }

    public Pair<Integer, Integer> getDurationAndNumberOfEmployees(String[] products) {
        if (hasProduct(Constants.DTV_PRODUCT_STR, products))
            return new ImmutablePair<>(3, 2);
        else if (hasProduct(Constants.INTERNET_PRODUCT_STR, products))
            return new ImmutablePair<>(2, 2);
        else
            return new ImmutablePair<>(1, 1);
    }

    private DeliveryAdditionalInfo getAdditionalInfo(Delivery delivery) {
        Instance instance = delivery.getOrder().getInstance();
        UserDevice userDevice = instanceService.getDeviceIfPresentInternetProduct(instance);
        return DeliveryAdditionalInfo.builder()
                .simCardNumber(instanceService.getSimCardNumberIfPresentMobileProduct(instance))
                .cableLength(instanceService.getCableLengthIfPresentInternetProduct(instance))
                .deviceSerialNumber(userDevice == null ? null : userDevice.getSerialNumber())
                .macAddress(userDevice == null ? null : userDevice.getMacAddress())
                .build();
    }

    private boolean hasProduct(String product, String[] products) {
        return Arrays.asList(products).contains(product);
    }

    private boolean isDeliveryStarted(Delivery delivery) {
        Date now = new Date();
        return (delivery.getStatus().equals(DeliveryStatus.NOT_STARTED) || delivery.getStatus().equals(DeliveryStatus.IN_PROGRESS))
                && now.after(delivery.getDeliveryDate()) && now.before(DateUtils.addHours(delivery.getDeliveryDate(), delivery.getDuration()));
    }

    private void updateDelivery(Delivery delivery, DeliveryStatus deliveryStatus, OrderStatus orderStatus) {
        delivery.setStatus(deliveryStatus);
        delivery.getOrder().setStatus(orderStatus);
        deliveryRepository.save(delivery);
    }

    private boolean sameDay(Date date1, Date date2) {
        return ONLY_DATE_FORMAT.format(date1).equals(ONLY_DATE_FORMAT.format(date2));
    }
}
