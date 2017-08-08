package edu.pdx.cs410J.np4.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.Date;

/**
 * A basic GWT class that makes sure that we can send an airline back from the server
 */
public class AirlineGwt implements EntryPoint {
    private final AirlineServiceAsync airlineService = GWT.create(AirlineService.class);
    ;

    @Override
    public void onModuleLoad() {
        setUpUncaughtExceptionHandler();
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                setupUI();
            }
        });
    }

    private void setupUI() {
        RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
        final StackLayoutPanel stackLayoutPanel = new StackLayoutPanel(Style.Unit.EM);
        stackLayoutPanel.add(getAddFlightWidget(), "Add a Flight", 2);
        stackLayoutPanel.add(getGetFlightsWidget(), "Get all the Flights", 2);
        stackLayoutPanel.add(getSearchFlightWidget(), "Search for the Flights", 2);

        stackLayoutPanel.addSelectionHandler(new SelectionHandler<Integer>() {
            @Override
            public void onSelection(SelectionEvent<Integer> selectionEvent) {

            }
        });

        stackLayoutPanel.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {
            @Override
            public void onBeforeSelection(BeforeSelectionEvent<Integer> beforeSelectionEvent) {
                int selectedIndex = beforeSelectionEvent.getItem();
                stackLayoutPanel.remove(selectedIndex);
                if (selectedIndex == 0) {
                    stackLayoutPanel.insert(getAddFlightWidget(), "Add a Flight", 2, selectedIndex);
                } else if (selectedIndex == 1) {
                    stackLayoutPanel.insert(getGetFlightsWidget(), "Get all the Flights", 2, selectedIndex);
                } else if (selectedIndex == 2) {
                    stackLayoutPanel.insert(getSearchFlightWidget(), "Search for the Flights", 2, selectedIndex);
                }
            }
        });

        rootLayoutPanel.add(stackLayoutPanel);
    }

    private Widget getAddFlightWidget() {
        Button addAFlight = new Button("Add a flight");

        final FlexTable flexTable = new FlexTable();
        flexTable.setWidget(0, 0, new HTML("Name of the Airline"));
        flexTable.setWidget(0, 1, new TextBox());
        flexTable.setWidget(1, 0, new HTML("Flight Number"));
        flexTable.setWidget(1, 1, new TextBox());
        flexTable.setWidget(2, 0, new HTML("Source"));
        flexTable.setWidget(2, 1, new TextBox());
        flexTable.setWidget(3, 0, new HTML("Depart Time (MM/dd/yyyy HH:mm am/pm)"));
        flexTable.setWidget(3, 1, new TextBox());
        flexTable.setWidget(4, 0, new HTML("Destination"));
        flexTable.setWidget(4, 1, new TextBox());
        flexTable.setWidget(5, 0, new HTML("Arrive Time (MM/dd/yyyy HH:mm am/pm)"));
        flexTable.setWidget(5, 1, new TextBox());
        flexTable.setWidget(6, 0, addAFlight);

        addAFlight.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                addAFlight(flexTable);
            }
        });

        return flexTable;
    }

    private void addAFlight(FlexTable flexTable) {
        String nameOfTheAirline = ((TextBox) flexTable.getWidget(0, 1)).getValue().trim();
        String flightNumberAsString = ((TextBox) flexTable.getWidget(1, 1)).getValue().trim();
        String source = ((TextBox) flexTable.getWidget(2, 1)).getValue().trim();
        String departTimeAsString = ((TextBox) flexTable.getWidget(3, 1)).getValue().trim();
        String destination = ((TextBox) flexTable.getWidget(4, 1)).getValue().trim();
        String arriveTimeAsString = ((TextBox) flexTable.getWidget(5, 1)).getValue().trim();

        int flightNumber = -1;
        Date departTime;
        Date arriveTime;
        if (nameOfTheAirline.equals("") || nameOfTheAirline == null) {
            Window.alert("Name of the airline cannot be empty.");
            return;
        }
        if (flightNumberAsString.equals("") || flightNumberAsString == null) {
            Window.alert("Flight number cannot be empty.");
            return;
        }
        if (source.equals("") || source == null) {
            Window.alert("Source cannot be empty.");
            return;
        }
        if (departTimeAsString.equals("") || departTimeAsString == null) {
            Window.alert("Depart Time cannot be empty.");
        }
        if (destination.equals("") || destination == null) {
            Window.alert("Destination cannot be empty.");
            return;
        }
        if (arriveTimeAsString.equals("") || arriveTimeAsString == null) {
            Window.alert("Arrival Time cannot be empty.");
        }

        try {
            flightNumber = InputValidator.getFlightNumber(flightNumberAsString);
        } catch (IllegalArgumentException iae) {
            Window.alert(iae.getMessage());
            return;
        }

        try {
            InputValidator.checkAirportCodeFormat(source);
        } catch (IllegalAirportCodeException iace) {
            Window.alert("Error in source airport code! " + iace.getMessage());
            return;
        }

        try {
            InputValidator.checkAirportCodeFormat(destination);
        } catch (IllegalAirportCodeException iace) {
            Window.alert("Error in destination airport code! " + iace.getMessage());
            return;
        }

        try {
            InputValidator.checkDateTimeFormat(departTimeAsString);
            departTime = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm aa").parseStrict(departTimeAsString);
        } catch (ErroneousDateTimeFormatException edte) {
            Window.alert("Error in DepartTime! " + edte.getMessage());
            return;
        } catch (IllegalArgumentException edte) {
            Window.alert("Error in DepartTime! " + edte.getMessage());
            return;
        }

        try {
            InputValidator.checkDateTimeFormat(arriveTimeAsString);
            arriveTime = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm aa").parseStrict(arriveTimeAsString);
        } catch (ErroneousDateTimeFormatException edte) {
            Window.alert("Error in ArrivalTime! " + edte.getMessage());
            return;
        } catch (IllegalArgumentException edte) {
            Window.alert("Error in ArrivalTime! " + edte.getMessage());
            return;
        }
        Flight flight = new Flight(flightNumber, source, destination, departTime, arriveTime);
        airlineService.addAFlightToAirline(nameOfTheAirline, flight, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Error while adding a flight. " + throwable.getMessage());
            }

            @Override
            public void onSuccess(Void aVoid) {
                Window.alert("Flight successfully added");
            }
        });
    }


    private Widget getGetFlightsWidget() {
        Button getAllTheFlights = new Button("Get all the flights");
        final FlexTable flexTable = new FlexTable();

        flexTable.setWidget(0, 0, new HTML("Name of the Airline"));
        flexTable.setWidget(0, 1, new TextBox());
        flexTable.setWidget(1, 0, getAllTheFlights);
        TextArea textArea = new TextArea();
        textArea.setVisible(false);
        textArea.setCharacterWidth(100);
        textArea.setVisibleLines(10);
        flexTable.setWidget(2, 0, textArea);
        flexTable.getFlexCellFormatter().setColSpan(2, 0, 2);

        getAllTheFlights.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                getAllTheFlights(flexTable);
            }
        });

        return flexTable;
    }

    private void getAllTheFlights(FlexTable flexTable) {
        String nameOfTheAirline = ((TextBox) flexTable.getWidget(0, 1)).getValue().trim();
        final TextArea textArea = (TextArea) flexTable.getWidget(2, 0);
        if (nameOfTheAirline.equals("") || nameOfTheAirline == null) {
            Window.alert("Name of the airline cannot be empty.");
            return;
        }
        airlineService.getAirline(nameOfTheAirline, new AsyncCallback<Airline>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Error while retrieving all the flights. " + throwable.getMessage());
            }

            @Override
            public void onSuccess(Airline airline) {
                textArea.setText(PrettyPrinter.getPrettyPrintContent(airline));
                textArea.setVisible(true);
                textArea.setReadOnly(true);
            }
        });
    }

    private Widget getSearchFlightWidget() {
        TextArea textArea = new TextArea();
        textArea.setVisible(false);
        textArea.setVisibleLines(10);
        textArea.setCharacterWidth(100);
        Button searchForTheFlights = new Button("Search for the flights");

        final FlexTable flexTable = new FlexTable();
        flexTable.setWidget(0, 0, new HTML("Name of the Airline"));
        flexTable.setWidget(0, 1, new TextBox());
        flexTable.setWidget(1, 0, new HTML("Source"));
        flexTable.setWidget(1, 1, new TextBox());
        flexTable.setWidget(2, 0, new HTML("Destination"));
        flexTable.setWidget(2, 1, new TextBox());
        flexTable.setWidget(3, 0, searchForTheFlights);
        flexTable.getFlexCellFormatter().setColSpan(4, 0, 2);
        flexTable.setWidget(4, 0, textArea);

        searchForTheFlights.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                searchForTheFlights(flexTable);
            }
        });

        return flexTable;
    }

    private void searchForTheFlights(FlexTable flexTable) {
        String nameOfTheAirline = ((TextBox) flexTable.getWidget(0, 1)).getValue().trim();
        final String source = ((TextBox) flexTable.getWidget(1, 1)).getValue().trim();
        final String destination = ((TextBox) flexTable.getWidget(2, 1)).getValue().trim();
        final TextArea textArea = (TextArea) flexTable.getWidget(4, 0);

        if (nameOfTheAirline.equals("") || nameOfTheAirline == null) {
            Window.alert("Name of the airline cannot be empty.");
            return;
        }
        if (source.equals("") || source == null) {
            Window.alert("Source cannot be empty.");
            return;
        }
        if (destination.equals("") || destination == null) {
            Window.alert("Destination cannot be empty.");
            return;
        }
        try {
            InputValidator.checkAirportCodeFormat(source);
        } catch (IllegalAirportCodeException iace) {
            Window.alert("Error in source airport code! " + iace.getMessage());
            return;
        }

        try {
            InputValidator.checkAirportCodeFormat(destination);
        } catch (IllegalAirportCodeException iace) {
            Window.alert("Error in destination airport code! " + iace.getMessage());
            return;
        }
        airlineService.searchFlights(nameOfTheAirline, source, destination, new AsyncCallback<Airline>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Error while searching for the flight. " + throwable.getMessage());
            }

            @Override
            public void onSuccess(Airline searchedAirline) {
                if (searchedAirline.getFlights().size() == 0) {
                    textArea.setText("No flights found between " + source + " and " + destination);
                } else {
                    textArea.setText(PrettyPrinter.getPrettyPrintContent(searchedAirline));
                }
                textArea.setVisible(true);
                textArea.setReadOnly(true);
            }
        });

    }


    private void setUpUncaughtExceptionHandler() {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            @Override
            public void onUncaughtException(Throwable throwable) {
                alertOnException(throwable);
            }
        });
    }

    private void alertOnException(Throwable throwable) {
        Throwable unwrapped = unwrapUmbrellaException(throwable);
        Window.alert("Could not load the page. " + unwrapped.getMessage());
    }

    private Throwable unwrapUmbrellaException(Throwable throwable) {
        if (throwable instanceof UmbrellaException) {
            UmbrellaException umbrella = (UmbrellaException) throwable;
            if (umbrella.getCauses().size() == 1) {
                return unwrapUmbrellaException(umbrella.getCauses().iterator().next());
            }
        }
        return throwable;
    }
}
