package edu.baoss.orderservice.service;

import edu.baoss.orderservice.Constants;
import edu.baoss.orderservice.model.entities.Address;
import edu.baoss.orderservice.model.entities.Delivery;
import edu.baoss.orderservice.model.entities.Employee;
import edu.baoss.orderservice.model.entities.Order;
import edu.baoss.orderservice.model.enums.DeliveryStatus;
import edu.baoss.orderservice.model.enums.Position;
import edu.baoss.orderservice.repositories.DeliveryRepository;
import edu.baoss.orderservice.repositories.EmployeeRepository;
import edu.baoss.orderservice.services.DeliveryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.baoss.orderservice.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeliveryServiceTest {

    private DeliveryService deliveryService;
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void beforeEach() {
        deliveryService = new DeliveryService();
        employeeRepository = mock(EmployeeRepository.class);
        deliveryService.setEmployeeRepository(employeeRepository);
        when(employeeRepository.findAllByPosition(Position.FITTER.getName())).thenReturn(getFitters());
    }

    @Test
    void checkGettingAvailableHours1() throws ParseException {
        String[] products = new String[] {MOBILE_PRODUCT_STR, INTERNET_PRODUCT_STR, DTV_PRODUCT_STR};
        List<String> availableHours = deliveryService.getAvailableHours(Constants.DATE_HOUR_FORMAT.parse("16/03/2021 14"), products);
        assertEquals(List.of("10:00", "16:00", "17:00", "18:00"), availableHours);
    }

    @Test
    void checkGettingAvailableHours2() throws ParseException {
        String[] products = new String[] {MOBILE_PRODUCT_STR, INTERNET_PRODUCT_STR};
        List<String> availableHours = deliveryService.getAvailableHours(Constants.DATE_HOUR_FORMAT.parse("16/03/2021 12"), products);
        assertEquals(List.of("10:00", "11:00", "12:00", "16:00", "17:00", "18:00"), availableHours);
    }

    private List<Employee> getFitters() {
        List<Employee> allFitters = IntStream.range(1, 6).mapToObj(Employee::new).collect(Collectors.toList());
        List<Delivery> deliveries = new ArrayList<>();

        try {
            deliveries.add(new Delivery(1, Constants.DATE_HOUR_FORMAT.parse("16/03/2021 10"), 2, DeliveryStatus.NOT_STARTED,
                    new Order(), deliveryEmployers(allFitters, 1, 2).iterator().next(), true,
                    deliveryEmployers(allFitters, 1, 2), new Address()));
            deliveries.add(new Delivery(2, Constants.DATE_HOUR_FORMAT.parse("16/03/2021 12"), 1, DeliveryStatus.NOT_STARTED,
                    new Order(), deliveryEmployers(allFitters, 3).iterator().next(), true, deliveryEmployers(allFitters, 3), new Address()));
            deliveries.add(new Delivery(3, Constants.DATE_HOUR_FORMAT.parse("16/03/2021 13"), 3, DeliveryStatus.NOT_STARTED,
                    new Order(), deliveryEmployers(allFitters, 4, 1).iterator().next(), true,
                    deliveryEmployers(allFitters, 4, 1), new Address()));
            deliveries.add(new Delivery(4, Constants.DATE_HOUR_FORMAT.parse("16/03/2021 14"), 2, DeliveryStatus.NOT_STARTED,
                    new Order(), deliveryEmployers(allFitters, 2, 3).iterator().next(), true,
                    deliveryEmployers(allFitters, 2, 3), new Address()));
            deliveries.add(new Delivery(5, Constants.DATE_HOUR_FORMAT.parse("16/03/2021 14"), 1, DeliveryStatus.NOT_STARTED,
                    new Order(), deliveryEmployers(allFitters, 5).iterator().next(), true,
                    deliveryEmployers(allFitters, 5), new Address()));
            allFitters.get(0).setDeliveries(employeesDeliveries(deliveries, 1, 3));
            allFitters.get(1).setDeliveries(employeesDeliveries(deliveries, 1, 4));
            allFitters.get(2).setDeliveries(employeesDeliveries(deliveries, 2, 4));
            allFitters.get(3).setDeliveries(employeesDeliveries(deliveries,3));
            allFitters.get(4).setDeliveries(employeesDeliveries(deliveries,5));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return allFitters;
    }

    Set<Employee> deliveryEmployers(List<Employee> allFitters, int ... employerIds) {
        return Arrays.stream(employerIds)
                .mapToObj(id -> allFitters.get(id - 1))
                .collect(Collectors.toSet());
    }

    Set<Delivery> employeesDeliveries(List<Delivery> allDeliveries, int ... deliveryIndexes) {
        return Arrays.stream(deliveryIndexes)
                .mapToObj(id -> allDeliveries.get(id - 1))
                .collect(Collectors.toSet());
    }
}
