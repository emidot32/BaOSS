package edu.baoss.orderservice.services;

public class DeliveryServiceTest {
    //List<Employee> allEmployees = IntStream.range(1, 6).mapToObj(Employee::new).collect(Collectors.toList());
//    List<Delivery> deliveryListTest = new ArrayList<>();
//
//    {
//        try {
//            deliveryListTest.add(new Delivery(1, dateTimeFormat.parse("16/03/2021 10"), 2, DeliveryStatus.NOT_STARTED,
//                    new Order(), deliveryEmployers(1, 2), new Address()));
//            deliveryListTest.add(new Delivery(2, dateTimeFormat.parse("16/03/2021 12"), 1, DeliveryStatus.NOT_STARTED,
//                    new Order(), deliveryEmployers(3), new Address()));
//            deliveryListTest.add(new Delivery(3, dateTimeFormat.parse("16/03/2021 13"), 3, DeliveryStatus.NOT_STARTED,
//                    new Order(), deliveryEmployers(4, 1), new Address()));
//            deliveryListTest.add(new Delivery(4, dateTimeFormat.parse("16/03/2021 14"), 2, DeliveryStatus.NOT_STARTED,
//                    new Order(), deliveryEmployers(2, 3), new Address()));
//            deliveryListTest.add(new Delivery(5, dateTimeFormat.parse("16/03/2021 14"), 1, DeliveryStatus.NOT_STARTED,
//                    new Order(), deliveryEmployers(5), new Address()));
//            allEmployees.get(0).setDeliveries(employeesDeliveries(1, 3));
//            allEmployees.get(1).setDeliveries(employeesDeliveries(1, 4));
//            allEmployees.get(2).setDeliveries(employeesDeliveries(2, 4));
//            allEmployees.get(3).setDeliveries(employeesDeliveries(3));
//            allEmployees.get(4).setDeliveries(employeesDeliveries(5));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//
//    Set<Employee> deliveryEmployers(int ... employerIds) {
//        Set<Employee> employees = new HashSet<>();
//        for (int i = 0; i < employerIds.length; i++) {
//            employees.add(allEmployees.get(employerIds[i]-1));
//        }
//        return employees;
//    }
//
//    Set<Delivery> employeesDeliveries(int ... deliveryIndexes) {
//        Set<Delivery> deliveries = new HashSet<>();
//        for (int i = 0; i < deliveryIndexes.length; i++) {
//            deliveries.add(deliveryListTest.get(deliveryIndexes[i]-1));
//        }
//        return deliveries;
//    }
}
