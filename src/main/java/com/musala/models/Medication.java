package com.musala.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medication {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id int id;

    @NotEmpty(message = "Name cannot be empty")
    @Pattern(regexp = "[a-zA-Z0-9_-]+", message = "Name must contain only letters, numbers, hyphens, and underscores")
    private String name;

    @NotEmpty(message = "Code cannot be empty")
    @Pattern(regexp = "[A-Z0-9_]+", message = "Code must contain only uppercase letters, numbers, and underscores")
    private String code;

    @NotEmpty(message = "Image cannot be empty")
    private String image;

    @NotNull(message = "Weight cannot be null")
    @DecimalMin(value = "0.01", message = "Weight must be greater than 0")
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "droneId", referencedColumnName = "serialNumber")
    private Drone drone;

    public Medication(String name, String code, String image, double weight) {
        this.name = name;
        this.code = code;
        this.image = image;
        this.weight = weight;
    }
}