package com.nagp.flightbooking.service;

import com.nagp.flightbooking.dao.FlightSearchDao;
import com.nagp.flightbooking.entity.Flights;
import com.nagp.flightbooking.model.CheckFlightAvailability;
import com.nagp.flightbooking.model.FlightModel;
import com.nagp.flightbooking.model.FlightSearch;
import com.nagp.flightbooking.util.FlightClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FlightSearchService {
    @Autowired
    private FlightSearchDao flightSearchDao;


    public List<List<Flights>> searchFlight(FlightSearch flightSearch) {
        List<List<Flights>> flightSearchResult = new ArrayList<>(2);
        flightSearchResult.add(findAvailableFlights(flightSearch));
        if (flightSearch.getSearchType().toString().equalsIgnoreCase("RoundTrip")) {
            FlightSearch returnFlight = FlightSearch.builder().flightClass(flightSearch.getFlightClass()).arrivalLocation(flightSearch.getDepartureLocation()).departureLocation(flightSearch.getArrivalLocation()).departureDate(flightSearch.getReturnDate()).build();
            flightSearchResult.add(findAvailableFlights(returnFlight));
        }
        return flightSearchResult;
    }

    private List<Flights> findAvailableFlights(FlightSearch flightSearch) {
        Boolean checkFlightClass = flightSearch.getFlightClass().equalsIgnoreCase(FlightClass.Both.toString());
        List<Flights> flightsList = flightSearchDao.flightCatalog().values().stream().filter(i -> (i.getDepLocation().equalsIgnoreCase(flightSearch.getDepartureLocation()) && i.getArrLocation().equalsIgnoreCase(flightSearch.getArrivalLocation()) && i.getDepTime().toLocalDate().equals(flightSearch.getDepartureDate()) && i.getAvailableSeats() > 0)).collect(Collectors.toList());
        return checkFlightClass ? flightsList : flightsList.stream().filter(i -> i.getFlightClass().toString().equalsIgnoreCase(flightSearch.getFlightClass())).collect(Collectors.toList());
    }

    public boolean updateInventory(FlightModel flightModel) {
        Long availableSeats = flightSearchDao.flightCatalog().get(flightModel.getFlightId()).getAvailableSeats();
        if (availableSeats - flightModel.getNumOfPassengers() >= 0) {
            Flights flight = flightSearchDao.flightCatalog().get(flightModel.getFlightId());
            flight.setAvailableSeats(flight.getAvailableSeats() - flightModel.getNumOfPassengers());
            return true;
        }
        return false;
    }


    public void addSeatAfterCancellaionFromUser(Map<String,String>cancelBookingMap) {
        Flights flight = flightSearchDao.flightCatalog().get(cancelBookingMap.get("flightId"));
        flight.setAvailableSeats(Long.valueOf(flight.getAvailableSeats() + Long.valueOf(cancelBookingMap.get("seatCount"))));
    }

    public boolean checkAvailability(CheckFlightAvailability checkFlightAvailability) {
        for (int i = 0; i < checkFlightAvailability.getListOfFlightId().length; i++) {
            if (flightSearchDao.flightCatalog().get(checkFlightAvailability.getListOfFlightId()[i]).getAvailableSeats() < Long.valueOf(checkFlightAvailability.getNumOfPassengers()[0])) {
                return false;
            }
        }
        return true;
    }

    public Flights flightInfo(String flightId) {
        return flightSearchDao.flightCatalog().get(flightId);
    }

}
