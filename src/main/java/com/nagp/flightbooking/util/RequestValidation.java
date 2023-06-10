package com.nagp.flightbooking.util;

import com.nagp.flightbooking.exception.CustomException;
import com.nagp.flightbooking.model.FlightSearch;
import org.springframework.stereotype.Component;

@Component
public class RequestValidation {

    public void validateFlightSearchRequest(FlightSearch flightSearch) throws CustomException {
        if (!(flightSearch.getSearchType().toString().equalsIgnoreCase("OneWay") || flightSearch.getSearchType().toString().equalsIgnoreCase("RoundTrip"))) {
            throw new CustomException("Search Type can be OneWay or RoundTrip only !!");
        }
        if (flightSearch.getDepartureDate().compareTo(flightSearch.getReturnDate()) > 0) {
            throw new CustomException("Date of departure should be earlier than return date !!");
        }
    }
}
