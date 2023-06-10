package com.nagp.flightbooking.dao;


import com.nagp.flightbooking.exception.CustomException;
import com.nagp.flightbooking.model.FlightBookingInfo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class FlightBookingDao {
    public static Map<String, FlightBookingInfo> flightBookingList = new HashMap<>();

    public static Map<String, List<String>> userIdBookingIdMap = new HashMap<>();

    public static Map<String, String> bookingIdTransactionIdMap = new HashMap<>();


    public Map<String, String> cancelFlightBooking(String bookingId) throws CustomException {
        HashMap<String, String> cancelBookingMap = new HashMap<>();
        Integer numberOfPassengers = flightBookingList.get(bookingId).getCustomerInfo().size();
        String flightId = flightBookingList.get(bookingId).getFlightId();
        cancelBookingMap.put("seatCount", String.valueOf(numberOfPassengers));
        cancelBookingMap.put("flightId", flightId);
        flightBookingList.remove(bookingId);
        return cancelBookingMap;
    }


    public void createBooking(String userId, String bookingId, FlightBookingInfo booking) {
        flightBookingList.put(bookingId, booking);
        Optional<List<String>> userBookingIdList = Optional.ofNullable(userIdBookingIdMap.get(userId));
        if (userBookingIdList.isPresent()) {
            userBookingIdList.get().add(bookingId);
        } else {
            List<String> newUserBookingIdList = new ArrayList<>();
            newUserBookingIdList.add(bookingId);
            userIdBookingIdMap.put(userId, newUserBookingIdList);
        }
    }


    public void insertToBookingIdTransactioIdMap(String bookingId, String transactionId) {
        bookingIdTransactionIdMap.put(bookingId, transactionId);
    }


    public String fetchTransactionIdByBookingId(String bookingId) {
        return bookingIdTransactionIdMap.get(bookingId);
    }
}
