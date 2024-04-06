package com.musala.config;

public class DroneServiceException extends RuntimeException{
    public DroneServiceException(){
        super();
    }
    public DroneServiceException(String message) {
        super(message);
    }
}
