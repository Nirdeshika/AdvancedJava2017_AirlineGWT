package edu.pdx.cs410J.np4.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * A GWT remote service that returns a dummy airline
 */
@RemoteServiceRelativePath("airline")
public interface AirlineService extends RemoteService {

    Airline getAirline(String nameOfTheAirline) throws IllegalStateException;

    void addAFlightToAirline(String nameOfTheAirline, Flight flight) throws IllegalArgumentException;

    Airline searchFlights(String nameOfTheAirline, String source, String destination) throws IllegalStateException;
}
