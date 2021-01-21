package edu.baoss.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="used_device")
public class UsedDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    long id;

    @Column(name = "device_name", nullable = false)
    String deviceName;

    @Column(name = "device_num", nullable = false)
    int deviceNum;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inet_prod_id", referencedColumnName = "inet_prod_id")
    InternetProduct internetProduct;


}
