package edu.baoss.orderservice.model;

import edu.baoss.orderservice.model.dtos.OrderValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationContext;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionContext {
    protected OrderValue orderValue;
    protected ApplicationContext applicationContext;
}
