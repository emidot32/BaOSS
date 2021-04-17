package edu.baoss.orderservice.actions.checkers;

import edu.baoss.orderservice.dtos.OrderValue;

public interface CheckAction {
    void check(OrderValue orderValue);
}
