package it.mattianatali.tddspringbootapi.vehicle;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.stream.Stream;

import static it.mattianatali.tddspringbootapi.VehicleFixtures.aValidVehicle;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    void create_shouldSaveTheVehicle() throws Exception {
        var vehicleId = 1L;
        var vehicleToSave = aValidVehicle();

        when(vehicleService.save(vehicleToSave))
                .thenReturn(
                        vehicleToSave
                                .toBuilder()
                                .id(vehicleId)
                                .build()
                );

        mvc.perform(
                post("/api/v1/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(vehicleToSave))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is((int) vehicleId)))
                .andExpect(jsonPath("$.brand", is(vehicleToSave.getBrand())))
                .andExpect(jsonPath("$.model", is(vehicleToSave.getModel())))
                .andExpect(jsonPath("$.year", is(vehicleToSave.getYear())));
    }

    @ParameterizedTest
    @MethodSource("getInvalidVehicles")
    void create_shouldReturnBadRequestIfBodyIsInvalid(Vehicle invalidVehicle) throws Exception {
        mvc.perform(
                post("/api/v1/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(invalidVehicle))
        )
                .andExpect(status().isBadRequest());
    }

    private static Stream<Vehicle> getInvalidVehicles() {
        var validVehicle = aValidVehicle();

        return Stream.of(
                validVehicle.toBuilder().brand(null).build(),
                validVehicle.toBuilder().brand("").build(),
                validVehicle.toBuilder().model(null).build(),
                validVehicle.toBuilder().model("").build(),
                validVehicle.toBuilder().year(null).build(),
                validVehicle.toBuilder().year(1949).build()
        );
    }
}
