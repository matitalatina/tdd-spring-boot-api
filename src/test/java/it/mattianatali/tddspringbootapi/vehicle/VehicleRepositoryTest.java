package it.mattianatali.tddspringbootapi.vehicle;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class VehicleRepositoryTest {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    void findById_shouldGetVehicle() {
        var vehicleToSave =
                Vehicle.builder()
                        .brand("Ferrari")
                        .model("488 GTB")
                        .year(2019)
                        .build();
        var expectedVehicle = vehicleRepository.save(vehicleToSave);
        assertEquals(
                Optional.of(expectedVehicle),
                vehicleRepository.findById(expectedVehicle.getId())
        );
    }

    @Test
    void findById_shouldReturnEmptyIfVehicleNotFound() {
        assertEquals(
                Optional.empty(),
                vehicleRepository.findById(999999L)
        );
    }
}
