package com.nagp.flightbooking.dao;

import com.nagp.flightbooking.entity.Flights;
import com.nagp.flightbooking.util.FlightClass;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FlightSearchDao {
    public static Map<String, Flights> resultList;
    static {
        resultList = new ConcurrentHashMap<>();
        resultList.put("01B", (new Flights("Indigo", "NEW DELHI", "GOA", "01B", LocalDateTime.parse("2023-03-30T12:45:30"), FlightClass.Bussiness, 25L,2000D)));
        resultList.put("01E", (new Flights("Indigo", "NEW DELHI", "GOA", "01E", LocalDateTime.parse("2023-03-30T12:45:30"), FlightClass.Economy, 69L,3000D)));
        resultList.put("02B", (new Flights("Vistara", "NEW DELHI", "GOA", "02B", LocalDateTime.parse("2023-03-30T12:45:30"), FlightClass.Bussiness, 37L,4000D)));
        resultList.put("02E", (new Flights("Vistara", "NEW DELHI", "GOA", "02E", LocalDateTime.parse("2023-03-30T12:45:30"), FlightClass.Economy, 29L,2500D)));
        resultList.put("03B", (new Flights("Air India", "NEW DELHI", "GOA", "03B", LocalDateTime.parse("2023-03-30T12:45:30"), FlightClass.Bussiness, 39L,5000D)));
        resultList.put("03E", (new Flights("Air India", "NEW DELHI", "GOA", "03E", LocalDateTime.parse("2023-03-30T12:45:30"), FlightClass.Economy, 49L,5500D)));
        resultList.put("04B", (new Flights("Indigo", "NEW DELHI", "GOA", "04B", LocalDateTime.parse("2023-03-15T12:45:30"), FlightClass.Bussiness, 51L,6000D)));
        resultList.put("05B", (new Flights("Air India", "NEW DELHI", "GOA", "05B", LocalDateTime.parse("2023-03-16T12:45:30"), FlightClass.Bussiness, 72L,3500D)));
        resultList.put("06B", (new Flights("Vistara", "NEW DELHI", "GOA", "06B", LocalDateTime.parse("2023-03-17T12:45:30"), FlightClass.Bussiness, 28L,3500D)));
        resultList.put("07B", (new Flights("Indigo", "GOA", "NEW DELHI", "07B", LocalDateTime.parse("2023-03-30T12:45:30"), FlightClass.Bussiness, 29L,3000D)));
        resultList.put("07E", (new Flights("Indigo", "GOA", "NEW DELHI", "07E", LocalDateTime.parse("2023-03-30T12:45:30"), FlightClass.Economy, 31L,2750D)));
        resultList.put("08B", (new Flights("Vistara", "GOA", "NEW DELHI", "08B", LocalDateTime.parse("2023-03-30T12:45:30"), FlightClass.Bussiness, 33L,2300D)));
        resultList.put("08E", (new Flights("Vistara", "GOA", "NEW DELHI", "08E", LocalDateTime.parse("2023-03-30T12:45:30"), FlightClass.Economy, 42L,3100D)));
        resultList.put("09B", (new Flights("Air India", "GOA", "NEW DELHI", "09B", LocalDateTime.parse("2023-03-30T12:45:30"), FlightClass.Bussiness, 3L,3200D)));
        resultList.put("09E", (new Flights("Air India", "GOA", "NEW DELHI", "09E", LocalDateTime.parse("2023-03-30T12:45:30"), FlightClass.Economy, 51L,4200D)));
        resultList.put("10B", (new Flights("Indigo", "GOA", "NEW DELHI", "10B", LocalDateTime.parse("2023-03-15T12:45:30"), FlightClass.Bussiness, 61L,4500D)));
        resultList.put("11B", (new Flights("Air India", "GOA", "NEW DELHI", "11B", LocalDateTime.parse("2023-03-16T12:45:30"), FlightClass.Bussiness, 31L,6100D)));
        resultList.put("12B", (new Flights("Vistara", "GOA", "NEW DELHI", "12B", LocalDateTime.parse("2023-03-17T12:45:30"), FlightClass.Bussiness, 11L,3400D)));
    }
    public Map<String, Flights> flightCatalog() {
        return resultList;
    }
}
