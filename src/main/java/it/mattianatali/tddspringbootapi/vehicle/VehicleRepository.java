package it.mattianatali.tddspringbootapi.vehicle;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class VehicleRepository {
    public Optional<Vehicle> findById(long vehicleId) {
        return null;
    }
}
