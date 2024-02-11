package com.dxpai.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class BestFlight {

    public ArrayList<Flight> flights;
    //public ArrayList<Layover> layovers;
    public int total_duration;
    //public CarbonEmissions carbon_emissions;
    public int price;
    //public String type;
    public String airline_logo;

    @Override
    public String toString() {
        return "[" +
                "Flights=" + flights +
                ", Duration=" + total_duration +
                ", Price=" + price +
                ']';
    }
//public String departure_token;
}
