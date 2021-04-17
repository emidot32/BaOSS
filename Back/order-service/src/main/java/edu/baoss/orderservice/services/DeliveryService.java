package edu.baoss.orderservice.services;

import edu.baoss.orderservice.model.*;
import edu.baoss.orderservice.repositories.DeliveryRepository;
import edu.baoss.orderservice.repositories.EmployeeRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.baoss.orderservice.services.Constants.onlyDateFormat;

@Service
public class DeliveryService {
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    EmployeeRepository employeeRepository;
//    final String[] HOURS = new String[]{"9:00", "10:00", "11:00", "12:00", "13:00",
//            "14:00", "15:00", "16:00", "17:00", "18:00"};
    final int[] HOURS =  IntStream.rangeClosed(9, 18).toArray();
    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH");
    SimpleDateFormat onlyHourFormat = new SimpleDateFormat("HH");


    public List<String> getAvailableHours(Date deliveryDate, String[] products) {
        List<Employee> allEmployees = employeeRepository.findAllByPosition("Fitter");
        Pair<Integer, Integer> durationAndNumberOfEmployees = getDurationAndNumberOfEmployees(products);
        List<String> availableHours = new ArrayList<>();
        for (int hour : HOURS) {
            List<Employee> freeEmployees = allEmployees.stream()
                    .filter(employee ->
                            isEmployeeFreeForHour(employee, hour, deliveryDate, durationAndNumberOfEmployees.getLeft()))
                    .collect(Collectors.toList());
            if (freeEmployees.size() >= durationAndNumberOfEmployees.getRight())
                availableHours.add(String.format("%d:00", hour));
        }
        return availableHours;
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

    private Pair<Integer, Integer> getDurationAndNumberOfEmployees(String[] products) {
        if (hasProduct(Constants.DTV_PRODUCT_STR, products))
            return new ImmutablePair<>(3, 2);
        else if (hasProduct(Constants.INTERNET_PRODUCT_STR, products))
            return new ImmutablePair<>(2, 2);
        else
            return new ImmutablePair<>(1, 1);
    }

    boolean hasProduct(String product, String[] products) {
        for (String product_: products) {
            if (product_.equals(product)) return true;
        }
        return false;
    }

    private boolean sameDay(Date date1, Date date2) {
        return onlyDateFormat.format(date1).equals(onlyDateFormat.format(date2));
    }
}
