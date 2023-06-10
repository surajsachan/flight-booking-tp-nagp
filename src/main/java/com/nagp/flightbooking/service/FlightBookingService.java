package com.nagp.flightbooking.service;

import com.nagp.flightbooking.dao.FlightBookingDao;
import com.nagp.flightbooking.exception.CustomException;
import com.nagp.flightbooking.model.*;
import com.nagp.flightbooking.response.TransactionDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class FlightBookingService {

    @Autowired
    private FlightBookingDao flightBookingDao;
    @Autowired
    private FlightSearchService flightSearchService;
    @Autowired
    private RestTemplate restTemplate;

    Logger log = LoggerFactory.getLogger(FlightBookingService.class);

    private static final Object LOCK = new Object();

    public BookingResponse createBooking(FlightBookingDetail flightBookingDetail) throws CustomException{
        String bookingId = "";
        List<FlightBookingInfo> flightBookingInfo = flightBookingDetail.getFlightBookingInfo();
        synchronized (LOCK) {
            if (isSeatAvailable(flightBookingInfo)) {
                for (FlightBookingInfo booking : flightBookingInfo) {
                    Boolean result = updateSeatAvailability(booking);
                    booking.setBookedOn(LocalDateTime.now());
                    if (result.equals(true)) {
                        bookingId = String.valueOf(UUID.randomUUID());
                        flightBookingDao.createBooking(flightBookingDetail.getUserId(), bookingId, booking);
                    } else {
                        return BookingResponse.builder().message("Booking failed due to unavailability").bookingStatus("1").build();
                    }
                }
                log.info("====Your seat has been reserved , please proceed for the payment to confirm the booking !!====");
                Map paymentResponse = makePayment(flightBookingDetail);
                if (paymentResponse.get("message").toString().equals("Payment processed successfully")) {
                    flightBookingDao.insertToBookingIdTransactioIdMap(bookingId, paymentResponse.get("transactionId").toString());
                    log.info("====Congratulations , Your booking is confirmed now !! and Booking Details are below====");
                    log.info("====Booking ID : " + bookingId + "====");
                    for (FlightBookingInfo flightBookingInfo1 : flightBookingInfo) {
                        log.info("==== Dear Customer , Please find below the flight details !! Wish you a very happy journey !!==== ");
                        log.info("====Flight Name : " + flightSearchService.flightInfo(flightBookingInfo1.getFlightId()).getFlightName() + "====");
                        log.info("====Flight Class : " + flightSearchService.flightInfo(flightBookingInfo1.getFlightId()).getFlightClass() + "====");
                        log.info("====Departure Location : " + flightSearchService.flightInfo(flightBookingInfo1.getFlightId()).getDepLocation() + "====");
                        log.info("====Arrival Location : " + flightSearchService.flightInfo(flightBookingInfo1.getFlightId()).getArrLocation() + "====");
                        log.info("====Departure Time : " + flightSearchService.flightInfo(flightBookingInfo1.getFlightId()).getDepTime() + "====");
                        log.info("====  Please find below the passenger details !! for this booking id : " + bookingId + " ==== ");
                        log.info("====  Booking done by the USER ID : " + flightBookingDetail.getUserId() + " ==== ");
                        for (Customer i : flightBookingInfo1.getCustomerInfo()) {
                            log.info("   Passenger Name : " + i.getCustName());
                            log.info("   Passenger Address : " + i.getCustAddress());
                            log.info("   Passenger Contact : " + i.getCustContactNumber());
                        }

                    }
                    return BookingResponse.builder().bookingId(bookingId).message("Booking Successfull").bookingStatus("0").build();
                } else {
                    BookingResponse cancelledBookingResponse = cancelFlightBooking(bookingId);
                    if (cancelledBookingResponse.getMessage().equals("Booking Cancelled")) {
                        log.info("====Your reserved seat has been cancelled due to payment failure !!====");
                    }
                    return BookingResponse.builder().bookingId(bookingId).message("Booking failed due to payment failure").bookingStatus("1").build();
                }
            }
        }

        return BookingResponse.builder().message("Booking failed due to unavailability").bookingStatus("1").build();
    }

    private Boolean isSeatAvailable(List<FlightBookingInfo> flightBookingInfo) {
        String[] listOfFlightIds = new String[flightBookingInfo.size()];
        listOfFlightIds[0] = flightBookingInfo.get(0).getFlightId();
        if (flightBookingInfo.size() > 1) {
            listOfFlightIds[1] = flightBookingInfo.get(1).getFlightId();
        }
        CheckFlightAvailability checkFlightAvailability = CheckFlightAvailability.builder().listOfFlightId(listOfFlightIds).numOfPassengers(new String[]{String.valueOf(flightBookingInfo.get(0).getCustomerInfo().size())}).build();
        return flightSearchService.checkAvailability(checkFlightAvailability);
    }

    private Boolean updateSeatAvailability(FlightBookingInfo flightBookingInfo) {
        FlightModel flightModel = FlightModel.builder().flightId(flightBookingInfo.getFlightId()).numOfPassengers(Long.valueOf(flightBookingInfo.getCustomerInfo().size())).build();
        return flightSearchService.updateInventory(flightModel);
    }

    public BookingResponse cancelFlightBooking(String bookingId) throws  CustomException{
        Map<String, String> cancelBookingMap = flightBookingDao.cancelFlightBooking(bookingId);
        flightSearchService.addSeatAfterCancellaionFromUser(cancelBookingMap);
        log.info("==== The seat reserved for you has been cancelled !! ====");
        Map refundResponse = processRefund(bookingId);
        log.info("==== Your booking with "+bookingId+" has been cancelled !! ====");
        return BookingResponse.builder().bookingId(bookingId).message("Booking Cancelled").bookingStatus("1").build();
    }

    private Map makePayment(FlightBookingDetail flightBookingDetail) {
        Double bookingAmount = 0D;
        for (FlightBookingInfo i : flightBookingDetail.getFlightBookingInfo()) {
            bookingAmount += i.getCustomerInfo().size() * flightSearchService.flightInfo(i.getFlightId()).getAmount();
        }
        PaymentDetails paymentDetails = flightBookingDetail.getPaymentDetails();
        paymentDetails.setAmount(bookingAmount);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<PaymentDetails> entity = new HttpEntity<>(paymentDetails, headers);
        Map result = restTemplate.exchange("http://payment-service/makePayment", HttpMethod.POST, entity, Map.class).getBody();
        return result;
    }


    private Map processRefund(String bookingId) throws  CustomException{
        String transactionId = flightBookingDao.fetchTransactionIdByBookingId(bookingId);
        if(transactionId.equals("") || transactionId.equals(null)){
            throw new CustomException("Booking is already cancelled");
        }
        TransactionDetails transactionDetails = TransactionDetails.builder().transactionId(transactionId).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        headers.add("X-Authenticated-User", flightBookingDetail.getUserId());
        HttpEntity entity = new HttpEntity<>(transactionDetails, headers);
        Map result = restTemplate.exchange("http://payment-service/refund", HttpMethod.POST, entity, Map.class).getBody();
        return result;
    }
}
