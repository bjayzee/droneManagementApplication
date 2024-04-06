package com.musala.config;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class AuditLog {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id Long id;
    private String droneSerialNumber;
    private int batteryLevel;
    private LocalDateTime timeStamp;
}
