package edu.baoss.orderservice.feign;

import edu.baoss.orderservice.model.dtos.UserDevice;
import edu.baoss.orderservice.model.dtos.PhoneNumber;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "resource-service", url="${tb-api.url}/resource-service")
public interface ResourceServiceFeignProxy {
    @GetMapping("/connection/connected-buildings")
    boolean isBuildingConnectedToNetwork(@RequestParam("buildingId") long buildingId);

    @GetMapping("/connection/connected-address")
    boolean isAddressConnected(@RequestParam("addressId") long addressId);

    @PostMapping("/connection/connect-buildings")
    void connectBuildings(@RequestBody List<Long> buildingIds);

    @GetMapping("/devices/{deviceId}")
    UserDevice getDetailDeviceById(@PathVariable("deviceId") String deviceId,
                                   @RequestParam("addressId") long addressId);

    @GetMapping("/phone-numbers/{phoneNumber}")
    PhoneNumber getPhoneNumber(@PathVariable("phoneNumber") String phoneNumberStr);

    @GetMapping("/phone-numbers/all")
    List<PhoneNumber> getPhoneNumbers();

    @PutMapping("/phone-numbers/reserve-sim-card")
    PhoneNumber reserveSimCard(@RequestBody PhoneNumber phoneNumber);

    @PutMapping("/cables")
    void reserveCable(@RequestParam("cableLength") int cableLength);

    @PutMapping("/devices/reserve-device")
    UserDevice reserveDevice(@RequestParam("deviceId") String deviceId);

    @PutMapping("/connection/connect-user")
    void connectUser(@RequestParam("buildingId") long buildingId,
                     @RequestParam("addressId") long addressId,
                     @RequestParam("macAddress") String macAddress);

    @GetMapping("/fixed-ip")
    String getFixedIP();

    @GetMapping("/devices/reserved-device")
    UserDevice getReservedDeviceByDeviceId(@RequestParam("deviceId") String deviceId);

}
