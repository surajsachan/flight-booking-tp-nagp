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
public class Customer {

    @NotNull(message="custName cannot be null")
    @NotEmpty(message="custName cannot be empty")
    @Pattern(regexp = "[A-Za-z]+",message="custName can only have alphabets")
    private String custName;
    @NotNull(message="custAddress cannot be null")
    @NotEmpty(message="custAddress cannot be empty")
    private String custAddress;
    @NotNull(message="custContactNumber cannot be null")
    @NotEmpty(message="custContactNumber cannot be empty")
    @Pattern(regexp = "[0-9]+",message="custContactNumber can only have numerical values")
    private String custContactNumber;
}
