package it.mattianatali.tddspringbootapi.vehicle;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehicleController {
    @GetMapping("/api/v1/vehicles/{id}")
    void getVehicleDetails(@PathVariable Long id) {

    }
}
