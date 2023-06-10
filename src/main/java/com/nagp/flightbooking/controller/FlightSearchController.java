package com.nagp.flightbooking.controller;

import com.nagp.flightbooking.entity.Flights;
import com.nagp.flightbooking.exception.CustomException;
import com.nagp.flightbooking.model.CheckFlightAvailability;
import com.nagp.flightbooking.model.FlightModel;
import com.nagp.flightbooking.model.FlightSearch;
import com.nagp.flightbooking.service.FlightSearchService;
import com.nagp.flightbooking.util.RequestValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping("search")
public class FlightSearchController {
    Logger logger = LoggerFactory.getLogger(FlightSearchController.class);

    @Autowired
    private FlightSearchService flightSearchService;

    @Autowired
    private RequestValidation requestValidation;

    @Value("${server.port}")
    private int port;

    @PostMapping("/flights")
    public List<List<Flights>> searchFlights(@RequestBody @Valid FlightSearch flightSearch,@RequestHeader("X-Authenticated-User") String username) throws CustomException{
        logger.info("Working from port " + port + " of flight booking service");
        requestValidation.validateFlightSearchRequest(flightSearch);
        return flightSearchService.searchFlight(flightSearch);
    }

    @PostMapping("/updateInventory")
    public boolean updateInventory(@RequestBody @Valid FlightModel FlightModel) {
        logger.info("Working from port " + port + " of flight booking service");
        return flightSearchService.updateInventory(FlightModel);
    }

    @PostMapping("/flightInfo")
    public Flights flightInfo(@RequestParam @NotNull @NotEmpty String flightId) {
        logger.info("Working from port " + port + " of flight booking service");
        return flightSearchService.flightInfo(flightId);
    }

    @PostMapping("/checkAvailability")
    public boolean checkAvailability(@RequestBody @Valid CheckFlightAvailability checkFlightAvailability) {
        logger.info("Working from port " + port + " of flight booking service");
        return flightSearchService.checkAvailability(checkFlightAvailability);
    }
}
