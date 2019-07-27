package it.mattianatali.tddspringbootapi.vehicle;

import it.mattianatali.tddspringbootapi.vehicle.errors.VehicleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class VehicleController {
    private VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/api/v1/vehicles/{id}")
    Vehicle getVehicleDetails(@PathVariable Long id) {
        return vehicleService
                .get(id)
                .orElseThrow(VehicleNotFoundException::new);
    }

    @PostMapping("/api/v1/vehicles")
    @ResponseStatus(HttpStatus.CREATED)
    Vehicle saveVehicle(@RequestBody @Valid Vehicle vehicle) {
        return vehicleService.save(vehicle);
    }
}
