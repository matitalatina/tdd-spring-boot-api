package it.mattianatali.tddspringbootapi.vehicle;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Vehicle {
    private Long id;
    private String brand;
    private String model;
    private Integer year;
}
