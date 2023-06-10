package com.nagp.flightbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightSearch {

    @NotNull(message = "flightClass cannot be null")
    @NotEmpty(message = "flightClass cannot be empty")
    private String flightClass;


    @NotNull(message = "arrivalLocation cannot be null")
    @NotEmpty(message = "arrivalLocation cannot be empty")
    @Pattern(regexp = "[A-Z a-z]+", message = "arrival location should have only alphabets")
    private String arrivalLocation;


    @NotNull(message = "departureDate cannot be null")
    @NotEmpty(message = "departureDate cannot be empty")
    @Pattern(regexp = "[A-Z a-z]+", message = "location should have only alphabets")
    private String departureLocation;

    private LocalDate departureDate;

    private LocalDate returnDate;

    private String searchType;

}
