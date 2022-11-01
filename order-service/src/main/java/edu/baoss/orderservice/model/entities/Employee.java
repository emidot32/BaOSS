package edu.baoss.orderservice.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empl_id")
    long id;
    
    @Column(name = "user_id")
    long userId;

    @Column(name = "position", nullable = false)
    String position;

    @Column(name = "empl_date", nullable = false)
    Date emplDate;

    @Column(name = "dismissal_date")
    Date dismissalDate;

    @Column(name = "salary")
    int salary;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "delivery_worker",
            joinColumns = @JoinColumn(name = "empl_id", referencedColumnName = "empl_id"),
            inverseJoinColumns = @JoinColumn(name = "delivery_id", referencedColumnName = "delivery_id"))
    Set<Delivery> deliveries = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", userId=" + userId +
                ", position='" + position + '\'' +
                ", emplDate=" + emplDate +
                ", dismissalDate=" + dismissalDate +
                ", salary=" + salary +
                '}';
    }
}
