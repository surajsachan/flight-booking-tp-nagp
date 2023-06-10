package com.nagp.flightbooking.controller;

import com.nagp.flightbooking.exception.CustomException;
import com.nagp.flightbooking.model.BookingResponse;
import com.nagp.flightbooking.model.FlightBookingDetail;
import com.nagp.flightbooking.service.FlightBookingService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("booking")
public class FlightBookingController {
    Logger logger = LoggerFactory.getLogger(FlightBookingController.class);

    @Autowired
    private FlightBookingService flightBookingService;

    @Value("${server.port}")
    private int port;

    @PostMapping("/createFlightBooking")
    @HystrixCommand(fallbackMethod = "getFallbackResponse")
    public BookingResponse createBooking(@RequestBody @Valid FlightBookingDetail flightBookingDetail) throws CustomException {
        logger.info("Working from port " + port + " of flight booking service");
        return flightBookingService.createBooking(flightBookingDetail);
    }

    @PostMapping("/cancelFlightBooking")
    @HystrixCommand(fallbackMethod = "getFallbackResponseForCancellation")
    public BookingResponse cancelFlightBooking(@RequestParam @NotNull @NotEmpty String bookingId) throws CustomException{
        logger.info("Working from port " + port + " of flight booking service");
        return flightBookingService.cancelFlightBooking(bookingId);
    }

    public BookingResponse getFallbackResponse(@RequestBody @Valid FlightBookingDetail flightBookingDetail) {
        return new BookingResponse("", "1", "Your request can't be processed. In case ,any amount debited will be refunded back to your payment source within 2-3 working days");
    }

    public BookingResponse getFallbackResponseForCancellation(@RequestParam @NotNull @NotEmpty String bookingId) {
        return new BookingResponse("", "1", "Your request can't be processed. Please try to cancelling after sometime.");
    }

}
