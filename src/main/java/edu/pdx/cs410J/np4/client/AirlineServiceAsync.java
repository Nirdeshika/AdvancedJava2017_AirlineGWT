package edu.pdx.cs410J.np4.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The client-side interface to the airline service
 */
public interface AirlineServiceAsync {

    /**
     * Return an airline created on the server
     */
    void getAirline(String nameOfTheAirline, AsyncCallback<Airline> async);

    void searchFlights(String nameOfTheAirline, String source, String destination,
                       AsyncCallback<Airline> async);

    void addAFlightToAirline(String nameOfTheAirline, Flight flight, AsyncCallback<Void> async);
}
