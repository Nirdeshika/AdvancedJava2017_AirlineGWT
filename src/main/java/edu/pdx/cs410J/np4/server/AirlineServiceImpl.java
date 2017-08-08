package edu.pdx.cs410J.np4.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.np4.client.*;

import java.awt.*;

/**
 * The server-side implementation of the Airline service
 */
public class AirlineServiceImpl extends RemoteServiceServlet implements AirlineService {
    Airline airline;

    @Override
    public Airline getAirline(String nameOfTheAirline) throws IllegalStateException {
        if (airline == null)
            throw new IllegalStateException("No information regarding Airline: " + nameOfTheAirline);
        if (!airline.getName().equals(nameOfTheAirline))
            throw new IllegalStateException("No information regarding Airline: " + nameOfTheAirline);

        return airline;
    }

    @Override
    public void addAFlightToAirline(String nameOfTheAirline, Flight flight) throws IllegalArgumentException {
        if (airline == null) {
            airline = new Airline(nameOfTheAirline);
        } else if (!airline.getName().equals(nameOfTheAirline))
            throw new IllegalArgumentException("Airline " + nameOfTheAirline + " does not exists.");

        airline.addFlight(flight);
    }

    @Override
    public Airline searchFlights(String nameOfTheAirline, String source, String destination)
            throws IllegalStateException {
        Airline searchedAirline = new Airline(nameOfTheAirline);
        if (airline == null) {
            throw new IllegalStateException("Airline is empty.");
        } else if (!airline.getName().equals(nameOfTheAirline))
            throw new IllegalStateException("Airline " + nameOfTheAirline + " does not exists.");
        else {
            for (Flight flight : airline.getFlights()) {
                if (flight.getSource().equalsIgnoreCase(source) && flight.getDestination().equalsIgnoreCase(destination))
                    searchedAirline.addFlight(flight);
            }
            return searchedAirline;
        }
    }


    /**
     * Log unhandled exceptions to standard error
     *
     * @param unhandled The exception that wasn't handled
     */
    @Override
    protected void doUnexpectedFailure(Throwable unhandled) {
        unhandled.printStackTrace(System.err);
        super.doUnexpectedFailure(unhandled);
    }
}
