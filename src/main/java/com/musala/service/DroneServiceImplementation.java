package com.musala.service;

import com.musala.config.DroneServiceException;
import com.musala.dtos.DroneDTO;
import com.musala.models.Drone;
import com.musala.models.DroneModel;
import com.musala.models.DroneState;
import com.musala.models.Medication;
import com.musala.repositories.DroneRepository;
import com.musala.repositories.MedicationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DroneServiceImplementation implements DroneService{

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;
    @Override
    public Drone registerDrone(DroneDTO drone) {

        validateDroneDTO(drone);

        return droneRepository.save(Drone.builder()
                .model(DroneModel.valueOf(drone.model().toUpperCase()))
                .weightLimit(drone.weightLimit())
                .batteryCapacity(drone.batteryCapacity())
                .state(DroneState.valueOf(drone.state().toUpperCase()))
                .build());
    }

    private Drone getDroneByID(UUID id){
       return droneRepository.findById(id)
                .orElseThrow(() -> new DroneServiceException("Drone with this Id does not exist"));
    }
    private void validateDroneDTO(DroneDTO droneDTO) {
        try {
            DroneModel.valueOf(droneDTO.model().toUpperCase());

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid drone model");
        }

        try {
            DroneState.valueOf(droneDTO.state().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid drone state");
        }
        if(droneDTO.weightLimit() > 500){
            throw new IllegalArgumentException("Weight exceeds maximum limit: " + droneDTO.weightLimit());
        }
    }

    @Override
    @Transactional
    public void loadMedication(UUID droneSerialNum, List<Medication> medications) {
        Drone drone = getDroneByID(droneSerialNum);

        int addedWeight = 0;

        for (Medication medication : medications) {
            if (addedWeight >= drone.getWeightLimit() || drone.getBatteryCapacity() < 25) {
                drone.setState(DroneState.LOADED);
                break;
            }

            addedWeight += medication.getWeight();
            if (addedWeight > drone.getWeightLimit()) {
                drone.setState(DroneState.LOADED);
                break;
            }

            medication.setDrone(drone);
            medicationRepository.save(medication);
        }

        droneRepository.save(drone);
    }

    @Override
    public boolean checkMedicationInADrone(Integer medicationId, UUID droneId) {
        Medication medication = medicationRepository.findById(medicationId).orElseThrow(
                () -> new DroneServiceException("Medication does not exist"));
        return checkLoadedMedicationForADrone(droneId).contains(medication);
    }

    @Override
    public List<Medication> checkLoadedMedicationForADrone(UUID droneId) {

        return medicationRepository.findByDrone(getDroneByID(droneId));
    }

    @Override
    public List<Drone> checkAvailableDrones(DroneState state) {

        return droneRepository.findByState(state);
    }

    @Override
    public int checkDroneBatteryLevel(UUID droneId) {

        return getDroneByID(droneId).getBatteryCapacity();
    }
}
