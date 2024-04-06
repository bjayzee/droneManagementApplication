package com.musala.models;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import java.util.Collection;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Drone {
    @Column(length = 100)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private @Id UUID serialNumber;
    @Enumerated(EnumType.STRING)
    private DroneModel model;

    @DecimalMax(value = "500.0", message = "Drone weight cannot be more than 500")
    private Double weightLimit;
    private int batteryCapacity;
    @Enumerated(EnumType.STRING)
    private DroneState state;

    public Drone(DroneModel model, double weightLimit, int batteryCapacity, DroneState state) {
        this.model = model;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
    }
}
