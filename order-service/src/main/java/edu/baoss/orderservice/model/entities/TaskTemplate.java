package edu.baoss.orderservice.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.baoss.orderservice.model.enums.Product;
import edu.baoss.orderservice.model.enums.TaskType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="task_templates")
public class TaskTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tt_id")
    long id;

    @Column(name = "name", nullable = false)
    String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    TaskType type;

    //@Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="tt_products", joinColumns=@JoinColumn(name="tt_id", referencedColumnName = "tt_id"))
    @Column(name="products")
    List<String> products;

    @Column(name = "description")
    String description;

    @Column(name = "action_name")
    String actionName;

    @ElementCollection
    @CollectionTable(name = "task_template_params",
            joinColumns = {@JoinColumn(name = "tt_id", referencedColumnName = "tt_id")})
    @MapKeyColumn(name = "param_name")
    @Column(name = "value")
    private Map<String, String> params;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "task_template_dependencies",
            joinColumns = @JoinColumn(name = "tt_id", referencedColumnName = "tt_id"),
            inverseJoinColumns = @JoinColumn(name = "dependency_id", referencedColumnName = "tt_id"))
    @JsonIgnore
    Set<TaskTemplate> dependencies;

}
