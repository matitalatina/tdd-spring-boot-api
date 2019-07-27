package it.mattianatali.tddspringbootapi;

import it.mattianatali.tddspringbootapi.vehicle.Vehicle;

public class VehicleFixtures {
    public static Vehicle aValidVehicle() {
        return Vehicle.builder()
                .brand("Ferrari")
                .model("488 GTB")
                .year(2019)
                .build();
    }
}
