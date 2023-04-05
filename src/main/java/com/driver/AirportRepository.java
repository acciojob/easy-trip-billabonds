package com.driver;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import java.util.*;

public class AirportRepository {

    HashMap<String,Airport> airportDb = new HashMap<>();                       // for Airport Name
    HashMap<Integer,Flight> flightDb = new HashMap<>();                        // for flight number
    HashMap<Integer,Passenger> passengerDb = new HashMap<>();                   // for passenger count number
    HashMap<Integer,List<Integer>> flightToPassengerDb= new HashMap<>();       // key : flight , Value : list of passenger

                                                                                                 // 1st API - Done
    public void addAirport(Airport airport){

        //Simply add airport details to your database
        //Return a String message "SUCCESS"
        String key = airport.getAirportName();
        airportDb.put(key,airport);
    }
                                                                                                // 2nd API
    public String getLargestAirportName(){

        // Largest airport is in terms of terminals. 3 terminal airport is larger than 2 terminal airport
        // Incase of a tie return the Lexicographically smallest airportName

        List<String> list = new ArrayList<>();
        int max = Integer.MIN_VALUE;

        for(Airport st : airportDb.values())
        {
            if (max < st.getNoOfTerminals())
                max = st.getNoOfTerminals();
        }

        for(Airport st : airportDb.values())
        {
            if(st.getNoOfTerminals() == max)
            {
                list.add(st.getAirportName());
            }
        }
        if(list.size() > 1)
            Collections.sort(list);

        return list.get(0);
    }
                                                                                                // 3rd API
    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity){

        //Find the duration by finding the shortest flight that connects these 2 cities directly
        //If there is no direct flight between 2 cities return -1.

        double shorter_dis = Integer.MAX_VALUE;

        for(Flight st : flightDb.values()){

            if(st.getFromCity().equals(fromCity) && st.getToCity().equals(toCity))
                shorter_dis = Math.min(shorter_dis,st.getDuration());
        }

        if(shorter_dis == Integer.MAX_VALUE)
            return -1;

        return shorter_dis;
    }
                                                                                                 // 4th API
    public int getNumberOfPeopleOn(Date date, String airportName){

        //Calculate the total number of people who have flights on that day on a particular airport
        //This includes both the people who have come for a flight and who have landed on an airport after their flight

        int count = 0;

        Airport airport = airportDb.get(airportName);
        if(Objects.isNull(airport))
            return 0;

        City city = airport.getCity();

        for(Flight flight : flightDb.values())
        {
            if(date.equals(flight.getFlightDate()))
            {
                if(flight.getToCity().equals(city) || flight.getFromCity().equals(city))
                {
                    int flightId = flight.getFlightId();
                    count += flightToPassengerDb.get(flightId).size();
                }
            }
        }

        return  count;
    }
                                                                                                // 5th API
    public int calculateFlightFare(Integer flightId){

        //Calculation of flight prices is a function of number of people who have booked the flight already.
        //Price for any flight will be : 3000 + noOfPeopleWhoHaveAlreadyBooked*50
        //Suppose if 2 people have booked the flight already : the price of flight for the third person will be 3000 + 2*50 = 3100
        //This will not include the current person who is trying to book, he might also be just checking price

        int noOfPeopleWhoHaveAlreadyBooked = flightToPassengerDb.get(flightId).size();
        return 3000 + noOfPeopleWhoHaveAlreadyBooked*50;
    }
                                                                                                // 6th API
    public String bookATicket(Integer flightId,Integer passengerId){

        //If the numberOfPassengers who have booked the flight is greater than : maxCapacity, in that case :
        //return a String "FAILURE"
        //Also if the passenger has already booked a flight then also return "FAILURE".
        //else if you are able to book a ticket then return "SUCCESS"

        if(Objects.nonNull(flightToPassengerDb.get(flightId)) &&
                                        flightToPassengerDb.get(flightId).size() < flightDb.get(flightId).getMaxCapacity() )
        {
            List<Integer> list = flightToPassengerDb.get(flightId);

            if(list.contains(passengerId))
                return "FAILURE";

            list.add(passengerId);
            flightToPassengerDb.put(flightId,list);
            return "SUCCESS";
        }

        else if(Objects.isNull(flightToPassengerDb.get(flightId)))
        {
            flightToPassengerDb.put(flightId,new ArrayList<>());
            List<Integer> list = flightToPassengerDb.get(flightId);

            if(list.contains(passengerId))
                return "FAILURE";

            list.add(passengerId);
            flightToPassengerDb.put(flightId,list);
            return "SUCCESS";
        }
        return "FAILURE";
    }
                                                                                                // 7th API
    public String cancelATicket(Integer flightId,Integer passengerId){

        //If the passenger has not booked a ticket for that flight or the flightId is invalid or in any other failure case
        // then return a "FAILURE" message
        // Otherwise return a "SUCCESS" message
        // and also cancel the ticket that passenger had booked earlier on the given flightId


        List<Integer> list = flightToPassengerDb.get(flightId);

        if(list == null)
           return "FAILURE";

        if(list.contains(passengerId))
        {
            list.remove(passengerId);
            return "SUCCESS";
        }

        return "FAILURE";
    }
                                                                                                // 8th API
    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){

        //Tell the count of flight bookings done by a passenger: This will tell the total count of flight bookings done by a passenger :

        int count = 0;

        for(List<Integer> list : flightToPassengerDb.values())
        {
            for(int i : list)
            {
                if(i == passengerId)
                    count++;
            }
        }

        return count;
    }
                                                                                                // 9th API
    public String addFlight(Flight flight){

        //Return a "SUCCESS" message string after adding a flight.
        int key = flight.getFlightId();
        flightDb.put(key,flight);
        return "SUCCESS";
    }
                                                                                                // 10th API
    public String getAirportNameFromFlightId(Integer flightId){

        //We need to get the starting airportName from where the flight will be taking off (Hint think of City variable if that can be of some use)
        //return null incase the flightId is invalid or you are not able to find the airportName

        if(flightDb.containsKey(flightId))
        {
            City city = flightDb.get(flightId).getFromCity();

            for(Airport airport : airportDb.values())
            {
                if(airport.getCity().equals(city))
                    return airport.getAirportName();
            }
        }

        return null;
    }
                                                                                                // 11th API
    public int calculateRevenueOfAFlight(Integer flightId){

        //Calculate the total revenue that a flight could have
        //That is of all the passengers that have booked a flight till now and then calculate the revenue
        //Revenue will also decrease if some passenger cancels the flight

        int noOfPeopleWhoHaveAlreadyBooked = flightToPassengerDb.get(flightId).size();
        int variable_Fare = (noOfPeopleWhoHaveAlreadyBooked*(noOfPeopleWhoHaveAlreadyBooked-1)) * 25;
        int fixedPrice =  3000 * noOfPeopleWhoHaveAlreadyBooked;

        int totalFare  = fixedPrice + variable_Fare;

        return totalFare;
    }
                                                                                                // 12th API
    public String addPassenger(Passenger passenger){

        //Add a passenger to the database
        //And return a "SUCCESS" message if the passenger has been added successfully.

        int key = passenger.getPassengerId();
        passengerDb.put(key,passenger);
        return "SUCCESS";
    }

}
