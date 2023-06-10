package com.nagp.flightbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Valid
public class FlightBookingDetail {
    @NotNull(message = "userId cannot be null")
    @NotEmpty(message = "userId cannot be empty")
    private String userId;
    @NotNull(message = "flightBookingInfo list cannot be null")
    @NotEmpty(message = "flightBookingInfo list cannot be empty")
    private List<FlightBookingInfo> flightBookingInfo;

    private PaymentDetails paymentDetails;

}
