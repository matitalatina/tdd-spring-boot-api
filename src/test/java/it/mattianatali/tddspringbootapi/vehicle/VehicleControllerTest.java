package it.mattianatali.tddspringbootapi.vehicle;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VehicleService vehicleService;

    @Test
    void getDetail_shouldReturnVehicleIfFound() throws Exception {
        var vehicleId = 1L;
        var returnedVehicle = Vehicle.builder()
                .id(vehicleId)
                .brand("Ferrari")
                .model("488 GTB")
                .year(2019)
                .build();

        when(vehicleService.get(vehicleId)).thenReturn(Optional.of(returnedVehicle));

        mvc.perform(get("/api/v1/vehicles/" + vehicleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(returnedVehicle.getId().intValue())))
                .andExpect(jsonPath("$.brand", is(returnedVehicle.getBrand())))
                .andExpect(jsonPath("$.model", is(returnedVehicle.getModel())))
                .andExpect(jsonPath("$.year", is(returnedVehicle.getYear())));
    }

    @Test
    void getDetail_shouldReturn404IfVehicleNotFound() throws Exception {
        var vehicleId = 1L;

        when(vehicleService.get(vehicleId)).thenReturn(Optional.empty());

        mvc.perform(get("/api/v1/vehicles/" + vehicleId))
                .andExpect(status().isNotFound());
    }
}
