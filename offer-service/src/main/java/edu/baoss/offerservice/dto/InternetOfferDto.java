package edu.baoss.offerservice.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InternetOfferDto extends OfferDto {
    int speed;
}
