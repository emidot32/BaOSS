package edu.baoss.orderservice.model.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DtvProductInstance extends ProductInstance {
    long internetProductId;
    DtvOffer dtvOffer;
}
