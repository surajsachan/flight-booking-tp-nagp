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
public class FlightModel {
    @NotNull
    @NotEmpty
    private String flightId;
    @NotNull
    @NotEmpty
    @Pattern(regexp="[0-9]+",message="number of passengers can only have numerical value")
    private Long numOfPassengers;

}
