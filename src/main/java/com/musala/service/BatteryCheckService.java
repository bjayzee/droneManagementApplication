package com.musala.service;

import com.musala.config.AuditLog;
import com.musala.models.Drone;
import com.musala.repositories.AuditLogRepository;
import com.musala.repositories.DroneRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BatteryCheckService {
    private static final Logger logger = LoggerFactory.getLogger(BatteryCheckService.class);

    private final DroneRepository droneRepository;

    private final AuditLogRepository auditLogRepository;

    @Scheduled(cron = "0 */3 * * * *")
    public void checkDroneBatteryLevels() {
        List<Drone> drones = droneRepository.findAll();
        for (Drone drone : drones) {
            int batteryLevel = drone.getBatteryCapacity();
            logger.info("Drone {} - Battery Level: {}%", drone.getSerialNumber(), batteryLevel);

            logBatteryLevel(drone.getSerialNumber().toString(), batteryLevel);
        }
    }

    private void logBatteryLevel(String droneSerialNumber, int batteryLevel) {
        AuditLog auditLog = new AuditLog();
        auditLog.setDroneSerialNumber(droneSerialNumber);
        auditLog.setBatteryLevel(batteryLevel);
        auditLog.setTimeStamp(LocalDateTime.now());
        auditLogRepository.save(auditLog);
    }
}
