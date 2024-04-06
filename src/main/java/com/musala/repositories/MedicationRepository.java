package com.musala.repositories;

import com.musala.models.Drone;
import com.musala.models.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Integer> {
    List<Medication> findByDrone(Drone drone);
}
