package com.nagp.flightbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckFlightAvailability {

    @NotNull
    @NotEmpty
    private String[] listOfFlightId = new String[2];

    @NotNull
    @NotEmpty
    private String[] numOfPassengers;
}
