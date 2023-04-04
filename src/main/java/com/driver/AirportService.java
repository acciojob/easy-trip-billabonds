package com.driver;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

public class AirportService {

    AirportRepository airportRepository = new AirportRepository();
                                                                                       // 1st API
    public void addAirport(Airport airport){
        airportRepository.addAirport(airport);
    }
                                                                                      // 2nd API
    public String getLargestAirportName(){
        return airportRepository.getLargestAirportName();
    }
                                                                                    // 3rd API
    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity){
        return airportRepository.getShortestDurationOfPossibleBetweenTwoCities(fromCity,toCity);
    }
                                                                                      // 4th API
    public int getNumberOfPeopleOn(Date date,String airportName){
        return airportRepository.getNumberOfPeopleOn(date, airportName);
    }
                                                                                     // 5th API
    public int calculateFlightFare(Integer flightId){
        return airportRepository.calculateFlightFare(flightId);
    }

                                                                                      // 6th API
    public String bookATicket(Integer flightId,Integer passengerId){
        return airportRepository.bookATicket(flightId,passengerId);
    }
                                                                                    // 7th API
    public String cancelATicket(Integer flightId,Integer passengerId){
        return airportRepository.cancelATicket(flightId,passengerId);
    }

                                                                                      // 8th API
    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){
        return airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }
                                                                                     // 9th API
    public String addFlight(Flight flight){
        return airportRepository.addFlight(flight);
    }

                                                                                      // 10th API
    public String getAirportNameFromFlightId(Integer flightId){
       return airportRepository.getAirportNameFromFlightId(flightId);
    }

                                                                                      // 11th API
    public int calculateRevenueOfAFlight(Integer flightId){
        return airportRepository.calculateRevenueOfAFlight(flightId);
    }

                                                                                      // 12th API
    public String addPassenger(Passenger passenger){
        return airportRepository.addPassenger(passenger);
    }

}
