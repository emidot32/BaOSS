package edu.baoss.userservice.dto;

import edu.baoss.userservice.model.Address;
import edu.baoss.userservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddressDto {
    UserDto user;
    AddressDto address;
}
