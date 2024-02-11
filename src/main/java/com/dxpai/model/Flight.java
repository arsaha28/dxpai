package com.dxpai.model;

import lombok.Data;

@Data
public class Flight {

    public DepartureAirport departure_airport;

    @Override
    public String toString() {
        return "[" +
                "Departure Airport=" + departure_airport +
                ", Arrival Airport=" + arrival_airport +
                ", Duration =" + duration +
                ", Airline ='" + airline + '\'' +
                ", Travel Class='" + travel_class + '\'' +
                ", Flight Number='" + flight_number + '\'' +
                ']';
    }

    public ArrivalAirport arrival_airport;
    public int duration;
    //public String airplane;
    public String airline;
    //public String airline_logo;
    public String travel_class;
    public String flight_number;
    //public String legroom;
    //public ArrayList<String> extensions;
    public boolean overnight;
    //public ArrayList<String> ticket_also_sold_by;
    //public boolean often_delayed_by_over_30_min;
}
