package com.musala.service;

import com.musala.dtos.DroneDTO;
import com.musala.models.Drone;
import com.musala.models.DroneState;
import com.musala.models.Medication;

import java.util.List;
import java.util.UUID;


public interface DroneService {

    Drone registerDrone(DroneDTO drone);

    void loadMedication(UUID droneSerialNum, List<Medication> medications);

    boolean checkMedicationInADrone(Integer medicationId, UUID droneId);


    List<Medication> checkLoadedMedicationForADrone(UUID droneId);

    List<Drone> checkAvailableDrones(DroneState state);

    int checkDroneBatteryLevel(UUID droneId);
}
