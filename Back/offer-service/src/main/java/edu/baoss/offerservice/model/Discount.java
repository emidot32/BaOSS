package edu.baoss.offerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "discounts")
public class Discount {
    @Id
    private String id;

    private int value;

    private Date start_date;

    private Date end_date;

    private String applied_for;
}
