package edu.baoss.orderservice.model.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.baoss.orderservice.model.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.CascadeType.DETACH;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="tasks")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    long id;

    @ManyToOne(cascade={MERGE, REMOVE, REFRESH, DETACH})
    @JoinColumn(name = "tt_id", referencedColumnName = "tt_id")
    TaskTemplate taskTemplate;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @JsonIgnore
    Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    TaskStatus status;

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date startDate;

    @Column(name = "completion_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date completionDate;

    @ManyToMany(cascade={MERGE, REMOVE, REFRESH, DETACH})
    @JoinTable(name = "task_dependencies",
            joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "dependency_id", referencedColumnName = "task_id"))
    @JsonIgnore
    Set<Task> dependencies = new HashSet<>();

    @OneToMany(mappedBy = "task")
    Set<Error> errors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addDependency(Task task) {
        if (dependencies != null) {
            dependencies.add(task);
        } else {
            dependencies = new HashSet<>();
            dependencies.add(task);
        }

    }
}
