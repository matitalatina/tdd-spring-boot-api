package it.mattianatali.tddspringbootapi.vehicle;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {
    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    void get_shouldReturnVehicle() {
        var vehicleId = 1L;
        var returnedVehicle = Optional.of(
                Vehicle.builder()
                        .id(vehicleId)
                        .brand("Ferrari")
                        .model("488 GTB")
                        .year(2019)
                        .build()
        );
        when(vehicleRepository.findById(vehicleId))
                .thenReturn(returnedVehicle);

        assertEquals(
                returnedVehicle,
                vehicleService.get(vehicleId)
        );

    }

    @Test
    void get_shouldReturnOptionalEmptyIfNotFound() {
        var vehicleId = 1L;
        when(vehicleRepository.findById(vehicleId))
                .thenReturn(Optional.empty());

        assertTrue(vehicleService.get(vehicleId).isEmpty());
    }
}
