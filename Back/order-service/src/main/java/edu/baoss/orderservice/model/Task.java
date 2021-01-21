package edu.baoss.orderservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    Order order;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "description")
    String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    TaskStatus status;

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date startDate;

    @Column(name = "completion_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date completionDate;

    @ManyToMany
    @JoinTable(name = "dependency",
            joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "dependency_id", referencedColumnName = "task_id"))
    Set<Task> dependencies;

    @OneToMany(mappedBy = "task")
    Set<Error> errors;

}
