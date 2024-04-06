package com.musala.config;

import com.musala.models.Drone;
import com.musala.models.DroneModel;
import com.musala.models.DroneState;
import com.musala.models.Medication;
import com.musala.repositories.DroneRepository;
import com.musala.repositories.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final MedicationRepository medicationRepository;
    private final DroneRepository droneRepository;

    @Override
    public void run(String... args) throws Exception {
        loadInitialData();
    }
    private void loadInitialData() {

        Drone drone1 = new Drone(DroneModel.LIGHTWEIGHT, 300.0, 80, DroneState.IDLE);
        Drone drone2 = new Drone(DroneModel.MIDDLEWEIGHT, 450.0, 90, DroneState.IDLE);
        Drone drone3 = new Drone(DroneModel.CRUISERWEIGHT, 200.0, 80, DroneState.IDLE);
        Drone drone4 = new Drone(DroneModel.HEAVYWEIGHT, 600.0, 90, DroneState.LOADED);


        droneRepository.save(drone1);
        droneRepository.save(drone2);
        droneRepository.save(drone3);
        droneRepository.save(drone4);



        Medication medication1 = new Medication("Medication1", "CODE001", "image1.jpg", 100.0);
        Medication medication2 = new Medication("Medication2", "CODE002", "image2.jpg", 150.0);

        medicationRepository.save(medication1);
        medicationRepository.save(medication2);

    }
}
