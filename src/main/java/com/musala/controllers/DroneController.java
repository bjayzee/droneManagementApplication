package com.musala.controllers;

import com.musala.config.DroneServiceException;
import com.musala.dtos.DroneDTO;
import com.musala.models.Drone;
import com.musala.models.DroneState;
import com.musala.models.Medication;
import com.musala.service.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/drones")
public class DroneController {

    private final DroneService droneService;

    @PostMapping("/register")
    public ResponseEntity<?> registerDrones(@RequestBody DroneDTO droneDTO){
        try{
            Drone newDrone = droneService.registerDrone(droneDTO);
            System.out.println(newDrone);
            return ResponseEntity.status(HttpStatus.CREATED).body(newDrone);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

    @PostMapping("/{droneSerialNum}")
    public ResponseEntity<?> loadMedications(@RequestBody List<Medication> medicationItems, @PathVariable @NotBlank String droneSerialNum){
        try{
            if(medicationItems.isEmpty()){
                throw new DroneServiceException("Medication list cannot be empty");
            }
            droneService.loadMedication(UUID.fromString(droneSerialNum), medicationItems);
            return ResponseEntity.ok("Medications loaded successfully");
        }catch (DataIntegrityViolationException | NullPointerException | OptimisticLockingFailureException | DroneServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

    @PostMapping("/medications")
    public ResponseEntity<?> findMedicationInADrone(@RequestParam Integer medicationId, @RequestParam String droneSerialNum){
        try{
            return ResponseEntity.ok(droneService.checkMedicationInADrone(medicationId, UUID.fromString(droneSerialNum)));
        }catch (DroneServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }
    @GetMapping("/{droneSerialNum}/medications")
    public ResponseEntity<?> findAllMedicationsInADrone(@PathVariable @NotBlank String droneSerialNum){
        try{
            return ResponseEntity.ok(droneService.checkLoadedMedicationForADrone(UUID.fromString(droneSerialNum)));
        }catch (DroneServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }

    @GetMapping("/state")
    public ResponseEntity<?> checkAvailableDronesForLoading(@RequestParam(name = "state", defaultValue = "Loading") String state){
        try{
            return ResponseEntity.ok(droneService.checkAvailableDrones(DroneState.valueOf(state.toUpperCase())));
        }catch (DroneServiceException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }
    @GetMapping("/battery-level")
    public ResponseEntity<?> checkDroneBatteryLevel(@RequestParam String droneSerialNum){
        try{
            return ResponseEntity.ok(droneService.checkDroneBatteryLevel(UUID.fromString(droneSerialNum)));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getLocalizedMessage());
        }
    }
}
