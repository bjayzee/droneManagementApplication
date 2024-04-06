package com.musala.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record DroneDTO(
                       @NotBlank String model,
                       @Max(value = 500, message = "Drone weight cannot be more than 500") double weightLimit,
                       @NotNull Integer batteryCapacity,
                       @NotBlank String state) {}
