package com.nagp.flightbooking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nagp.flightbooking.util.FlightClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Flights {
    private String flightName;
    private String depLocation;
    private String arrLocation;
    private String flightId;
    private LocalDateTime depTime;
    private FlightClass flightClass;
    @JsonIgnore
    private Long availableSeats;
    private Double amount;
}



