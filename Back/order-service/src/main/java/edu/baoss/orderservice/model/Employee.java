package edu.baoss.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empl_id")
    long id;
    
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", unique = true)
    User userId;

    @Column(name = "position", nullable = false)
    String position;

    @Column(name = "empl_date", nullable = false)
    Date emplDate;

    @Column(name = "dismissal_date")
    Date dismissalDate;

    @Column(name = "salary")
    int salary;

    @ManyToMany
    @JoinTable(name = "delivery_worker",
            joinColumns = @JoinColumn(name = "empl_id", referencedColumnName = "empl_id"),
            inverseJoinColumns = @JoinColumn(name = "delivery_id", referencedColumnName = "delivery_id"))
    Set<Delivery> deliveries;
}
