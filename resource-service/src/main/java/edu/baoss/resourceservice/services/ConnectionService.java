package edu.baoss.resourceservice.services;

import edu.baoss.resourceservice.exceptions.NoConnectedDeviceFoundException;
import edu.baoss.resourceservice.model.ConnectedAddress;
import edu.baoss.resourceservice.model.ConnectedBuilding;
import edu.baoss.resourceservice.repositories.ConnectedAddressesRepository;
import edu.baoss.resourceservice.repositories.ConnectedBuildingsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ConnectionService {
    private final ConnectedBuildingsRepository connectedBuildingsRepository;
    private final ConnectedAddressesRepository connectedAddressesRepository;

    public boolean isBuildingConnectedToNetwork(long buildingId) {
       return connectedBuildingsRepository.findConnectedBuildingByBuildingId(buildingId).isPresent();
    }

    public boolean isAddressConnected(long addressId) {
        return connectedAddressesRepository.findAll().stream()
                .map(ConnectedAddress::getAddressId)
                .anyMatch(address -> address == addressId);
    }

    public String getMacAddressByAddressId(long addressId) {
        return connectedAddressesRepository.findAll().stream()
                .filter(connectedAddress -> connectedAddress.getAddressId() == addressId)
                .map(ConnectedAddress::getMacAddress)
                .findFirst()
                .orElseThrow(NoConnectedDeviceFoundException::new);
    }

    public void connectUser(long buildingId, long addressId, String macAddress) {
        Optional<ConnectedBuilding> connectedBuilding = connectedBuildingsRepository.findConnectedBuildingByBuildingId(buildingId);
        connectedBuilding.orElseThrow(() -> new RuntimeException("The building is not connected to the network"));
        connectedBuilding.ifPresent(cb -> {
            boolean alreadyConnected = cb.getConnectedAddresses().stream()
                    .anyMatch(ca -> ca.getAddressId() == addressId && ca.getMacAddress().equals(macAddress));
            if (!alreadyConnected) {
                ConnectedAddress connectedAddress = connectedAddressesRepository
                        .save(new ConnectedAddress(addressId, macAddress));
                cb.getConnectedAddresses().add(connectedAddress);
                connectedBuildingsRepository.save(cb);
            }
        });
    }

    public void connectBuildings(List<Long> buildingIds) {
        Random rand = new Random();
        List<Long> connectedBuildingIds = connectedBuildingsRepository.findAll().stream()
                .peek(System.out::println)
                .map(ConnectedBuilding::getBuildingId)
                .toList();
        buildingIds.stream()
                .filter(buildingId -> !connectedBuildingIds.contains(buildingId))
                .map(buildingId -> ConnectedBuilding.builder()
                        .buildingId(buildingId)
                        .switchMacAddress(randomMACAddress())
                        .switchSerialNumber(String.format("2%04d", rand.nextInt(10000)))
                        .build())
                .forEach(connectedBuildingsRepository::save);
    }

    private String randomMACAddress(){
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
}
