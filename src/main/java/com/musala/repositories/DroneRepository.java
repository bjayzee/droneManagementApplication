package com.musala.repositories;

import com.musala.models.DroneState;
import com.musala.models.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DroneRepository extends JpaRepository<Drone, UUID> {
    List<Drone> findByState(DroneState state);
}
