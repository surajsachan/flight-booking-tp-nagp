package com.nagp.flightbooking.model;

import com.nagp.flightbooking.util.FlightClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightDetails {
    private String flightName;
    private String depLocation;
    private String arrLocation;
    private String flightId;
    private Double price;
    private LocalDateTime depTime;
    private FlightClass flightClass;
}
