package edu.baoss.orderservice.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Position {
    FITTER("Fitter");
    private final String name;
}
