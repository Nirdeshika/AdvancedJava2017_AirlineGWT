package edu.pdx.cs410J.np4.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import edu.pdx.cs410J.AirlineDumper;
import edu.pdx.cs410J.AirportNames;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A class that implements AirlineDumper with methods to write the details of the Airline (name + its flights) to a text file in a pretty manner.
 *
 * @author Nirdeshika Polisetti
 * @version 1.0
 */
public class PrettyPrinter {

    /**
     * Creates the contents for the file. It sorts and prints only unique flight objects of the airline.
     *
     * @param airline The airline object whose details are to be printed.
     * @return Contents to be printed.
     */
    static String getPrettyPrintContent(Airline airline) {
        String prettyPrintContent = "";
        if (airline != null) {
            SortedSet<Flight> flights = new TreeSet<>(airline.getFlights());
            DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("MMM dd yyyy 'at' hh:mm a");

            prettyPrintContent += "Name of the airline: " + airline.getName() + "\n";
            for (Flight flight : flights) {
                prettyPrintContent += "Flight number " + flight.getNumber() + " starting from " + flight.getSource().toUpperCase() + "(" + AirportNames.getName(flight.getSource().toUpperCase()) + ")" +
                        " on " + dateTimeFormat.format(flight.getDeparture()) + " reaches " + flight.getDestination().toUpperCase() + "(" + AirportNames.getName(flight.getDestination().toUpperCase()) + ")" +
                        " on " + dateTimeFormat.format(flight.getArrival()) + ".";
                prettyPrintContent += "The duration of this flight is " + calculateDuration(flight.getDeparture(), flight.getArrival()) + " minutes.\n";
            }
        }
        return prettyPrintContent;
    }

    /**
     * Calculates the duration between two dates in minutes.
     *
     * @param startDate The start date
     * @param endDate   The end date
     * @return Duration between startDate and endDate in minutes.
     */
    private static long calculateDuration(Date startDate, Date endDate) {
        long timeDifferenceInMilliSeconds = endDate.getTime() - startDate.getTime();
        long noOfMinutes = timeDifferenceInMilliSeconds / (1000 * 60);
        return noOfMinutes;
    }


}
