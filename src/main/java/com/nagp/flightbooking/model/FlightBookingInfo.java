package com.nagp.flightbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightBookingInfo {
   @NotNull(message = "customerInfo list cannot be null")
   @NotEmpty(message = "customerInfo list cannot be empty")
   private List<Customer> customerInfo;
   @NotNull(message = "flightId cannot be null")
   @NotEmpty(message = "flightId cannot be empty")
   private String flightId;

   private LocalDateTime bookedOn;
}
