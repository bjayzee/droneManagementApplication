package com.musala.service;

import com.musala.controllers.DroneController;
import com.musala.dtos.DroneDTO;
import com.musala.models.Drone;
import com.musala.models.DroneModel;
import com.musala.models.DroneState;
import com.musala.models.Medication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DroneServiceImplementationTest {

    @Mock
    private DroneService droneService;

    @InjectMocks
    private DroneController droneController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerDrones_ValidDto_Success() {
        // Given
        DroneDTO droneDTO = new DroneDTO("LIGHTWEIGHT", 500, 300, "IDLE");
        Drone registeredDrone = new Drone(UUID.randomUUID(), DroneModel.LIGHTWEIGHT, 300.0, 80, DroneState.IDLE);
        when(droneService.registerDrone(droneDTO)).thenReturn(registeredDrone);

        // When
        ResponseEntity<?> responseEntity = droneController.registerDrones(droneDTO);

        // Then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(registeredDrone, responseEntity.getBody());
    }

    @Test
    void loadMedications_ValidData_Success() {
        // Given
        UUID droneSerialNum = UUID.randomUUID();
        List<Medication> medications = new ArrayList<>();
        medications.add(new Medication("Medication1", "CODE001", "image1.jpg", 100.0));
//        when(droneService.loadMedication(eq(droneSerialNum), anyList())).thenReturn(null);


        // When
        ResponseEntity<?> responseEntity = droneController.loadMedications(medications, droneSerialNum.toString());

        // Then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Medications loaded successfully", responseEntity.getBody());
    }

    @Test
    void findMedicationInADrone_ValidData_Success() {
        // Given
        Integer medicationId = 1;
        UUID droneSerialNum = UUID.randomUUID();
        when(droneService.checkMedicationInADrone(eq(medicationId), eq(droneSerialNum))).thenReturn(true);

        // When
        ResponseEntity<?> responseEntity = droneController.findMedicationInADrone(medicationId, droneSerialNum.toString());

        // Then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue((boolean) responseEntity.getBody());
    }

    @Test
    void findAllMedicationsInADrone_ValidSerialNum_Success() {
        // Given
        UUID droneSerialNum = UUID.randomUUID();
        List<Medication> medications = new ArrayList<>();
        when(droneService.checkLoadedMedicationForADrone(droneSerialNum)).thenReturn(medications);

        // When
        ResponseEntity<?> responseEntity = droneController.findAllMedicationsInADrone(droneSerialNum.toString());

        // Then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(medications, responseEntity.getBody());
    }

    @Test
    void checkAvailableDronesForLoading_ValidState_Success() {
        // Given
        String state = "Loading";
        List<Drone> drones = new ArrayList<>();
        when(droneService.checkAvailableDrones(DroneState.LOADING)).thenReturn(drones);

        // When
        ResponseEntity<?> responseEntity = droneController.checkAvailableDronesForLoading(state);

        // Then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(drones, responseEntity.getBody());
    }

    @Test
    void checkDroneBatteryLevel_ValidSerialNum_Success() {
        // Given
        UUID droneSerialNum = UUID.randomUUID();
        int batteryLevel = 80;
        when(droneService.checkDroneBatteryLevel(droneSerialNum)).thenReturn(batteryLevel);

        // When
        ResponseEntity<?> responseEntity = droneController.checkDroneBatteryLevel(droneSerialNum.toString());

        // Then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(batteryLevel, responseEntity.getBody());
    }
}
