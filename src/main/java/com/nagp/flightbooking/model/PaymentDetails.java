package com.nagp.flightbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDetails {

    @NotNull(message = "cardNumber cannot be null")
    @NotEmpty(message = "cardNumber cannot be empty")
    private String cardNumber;

    @NotNull(message = "cvv cannot be null")
    @NotEmpty(message = "cvv cannot be empty")
    private String cvv;

    @Min(value = 1,message = "flight amount must be greater then 0")
    private Double amount;
}
