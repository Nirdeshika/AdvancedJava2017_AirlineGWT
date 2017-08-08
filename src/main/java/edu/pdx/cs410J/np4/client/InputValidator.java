package edu.pdx.cs410J.np4.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import edu.pdx.cs410J.AirportNames;

/**
 * The helper class for the CS410J airline Project.
 *
 * @author Nirdeshika Polisetti
 */
public class InputValidator {
    /**
     * Returns the flight number after validating it. If it is not a positive integer, it throws an error.
     *
     * @param flightNumberAsString The command line argument corresponding to flight number.
     * @return The flight number
     * @throws IllegalArgumentException if the number is a negative integer or not a number at all.
     */
    static int getFlightNumber(String flightNumberAsString) {
        int flightNumber = 0;
        try {
            flightNumber = Integer.parseInt(flightNumberAsString);
            if (flightNumber < 0) {
                throw new IllegalArgumentException("Invalid flight number. Flight number should be a positive number.");
            }
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Invalid number: " + flightNumberAsString + ". Flight number should be a positive integer.");
        } catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException(iae.getMessage());
        }
        return flightNumber;
    }


    /**
     * Checks if the input is string of three characters. If not, throws an exception.
     *
     * @param airportCode A String of three lettered code for departure or arrival airport.
     * @throws IllegalAirportCodeException
     */
    static void checkAirportCodeFormat(String airportCode) {
        if (!airportCode.matches("[a-zA-z][a-zA-Z][a-zA-Z]"))
            throw new IllegalAirportCodeException("Please check. Not a valid airport code: " + airportCode + ". Airport code should be a three lettered string.");
        checkIfItIsAValidAirportCode(airportCode);

    }

    /**
     * Check if the given airport code corresponds to a known airport.
     *
     * @param airportCode The airport code that is to be checked.
     * @throws IllegalAirportCodeException If airportCode does not correspond to any known airport.
     */
    static void checkIfItIsAValidAirportCode(String airportCode) {
        if (AirportNames.getName(airportCode.toUpperCase()) == null) {
            throw new IllegalAirportCodeException(airportCode + " is not a known airport");
        }
    }

    /**
     * Checks if the input String is of the format mm/dd/yyyy. Month and day can be 1 or digits, but year should be of 4 digits.
     * It also checks if the input is a valid date. If not, throws an exception.
     *
     * @param date Departure date or Arrival date
     * @throws ErroneousDateTimeFormatException
     */
    static void checkDateFormat(String date) {
        if (!date.matches("\\d{1,2}/\\d{1,2}/\\d{4}"))
            throw new ErroneousDateTimeFormatException("Please check. Invalid date format: " + date);

        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("MM/dd/yyyy");

        try {
            dateTimeFormat.parseStrict(date);
        } catch (IllegalArgumentException iae) {
            throw new ErroneousDateTimeFormatException("Please check. Invalid date: " + date);
        }

    }

    /**
     * Checks if the input string is of the format hh:mm. Hours can be 1 or 2 digits, but minutes should be 2 digits.
     * It also checks if it is a valid time. If not, it throws an exception.
     *
     * @param time Departure time or Arrival time
     * @throws ErroneousDateTimeFormatException
     */
    static void checkTimeFormat(String time) {
        if (!time.matches("(1[0-2]|[1-9]):\\d{2} (am|pm)"))
            throw new ErroneousDateTimeFormatException("Please check. Invalid time format: " + time);

        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("hh:mm a");
        try {
            dateTimeFormat.parseStrict(time);
        } catch (IllegalArgumentException iae) {
            throw new ErroneousDateTimeFormatException("Please check. Invalid time: " + time);
        }
    }

    /**
     * Checks if the given String follows all the requirements of the Date i.e it should be of the format MM/dd/yyyy hh:mm am/pm.
     *
     * @param dateTimeAMPM The string that is to be checked.
     * @throws ErroneousDateTimeFormatException If the string dateTimeAMPM does not match any of the requirements.
     */
    static void checkDateTimeFormat(String dateTimeAMPM) {
        String[] dateTimeAMPMSplit = dateTimeAMPM.split("\\s");
        String timeComponent = "";
        if (dateTimeAMPMSplit.length != 3) {
            throw new ErroneousDateTimeFormatException("Invalid DateTime format: " + dateTimeAMPM + ". Format should be mm/dd/yyyy hh:mm am/pm");
        } else {
            checkDateFormat(dateTimeAMPMSplit[0]);
            timeComponent = dateTimeAMPMSplit[1] + " " + dateTimeAMPMSplit[2];
            checkTimeFormat(timeComponent);
        }
    }


    /**
     * Returns the README of the project, a brief description of what the project does.
     *
     * @return String containing the hard coded README.
     */
    private static String getReadMe() {
        String readMe = "Name: Nirdeshika Polisetti\n";
        readMe += "Name of the assignment: Project 3\n";
        readMe += "Purpose of the project: This project parses command line arguments to check for the validity of the values. " +
                "If everything is of correct format and type, it creates an airline object and a flight object with the passed in arguments. " +
                "It further adds the flight object to the airline object.\n" +
                "This project also takes three options: -README which prints a brief description of what the project does.\n" +
                "\t\t\t\t\t-print: Prints the details of the flight.\n" +
                "\t\t\t\t\t-textFile file: Adds the details of the airline and flight to the given file.\n" +
                "\t\t\t\t\t-pretty file: Adds the details of the airline and its flights in sorted and human readable way.\n\n";
        readMe += "Command Line Usage: java edu.pdx.cs410J.np4.Project1 [options] <args>\n" +
                "args are (in order)\n" +
                "name : Name of the flight : String\n" +
                "flightNumber: The flight number: positive int\n" +
                "source: A three letter code of departure airport: String containing only characters\n" +
                "departDate: Date on which the flight departs: String of the format mm/dd/yyyy (Month or day can one or two digits but year must be 4 digits.\n" +
                "departTime: Time at which the flight departs: String of hh:mm (Hours can be 1 or digits but minutes should be two digits.\n" +
                "destination: A three letter code of arrival airport: String containing only characters\n" +
                "arrivalDate: Date on which the flight arrives: String of the format mm/dd/yyyy (Month or day can one or two digits but year must be 4 digits.\n" +
                "arrivalTime: Time at which the flight arrives: String of hh:mm (Hours can be 1 or digits but minutes should be two digits.\n\n";
        readMe += "Note: \nIf the String contains a space, it should be enclosed in double quotes.\nDate Strings should also be enclosed in double quotes.\n" +
                "Options should precede args.\nIf the options contains -README, the program prints a brief description of the project and exits. " +
                "It will not do anything else. Even error checking.\nFor this project, we can add only one flight to the airline.\n" +
                "The -textFile option should be followed by the file name, it cannot be followed by an option." +
                "The -textFile option checks if the file given exists; if it doesn't, then it creates a new file and add the details of the airline and its flights to the file.\n" +
                "If the file exists, it checks if the name of the airline in the command line and the file matches; if it does, then adds the details, else exits gracefully with error message.\n" +
                "The file name should not be a directory. It creates a new file only if all the subdirectories in the path exists. It will not create subdirectories.\n" +
                "The -pretty option should be followed by a file name or -. If it is followed by a file name, then -pretty prints the details on to the file. It will not parse. It will overwrites on the file. If the file does not exist, then it will creare one.\n" +
                "If the -pretty option is followed by -, then the details are printed on the standard output, the console.\n" +
                "If the -textFile option fails to execute for any reason, the -pretty option will not go through. However, if the -textFile option is omitted, then" +
                "only the details of the newly added flight is printed.\n" +
                "The format of the file is:\n" +
                "Name of the airline\n" +
                "FlightNumber|Source airport code|DepatureDateTime AM/PM|Destination|ArrivalDateTime AM/PM.";


        return readMe;
    }
}