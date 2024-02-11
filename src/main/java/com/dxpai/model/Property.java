package com.dxpai.model;

import lombok.Data;

@Data
public class Property {

    public String type;
    public String name;
    public String description;
    public String link;
    public String check_in_time;
    public String check_out_time;
    public RatePerNight rate_per_night;
    public TotalRate total_rate;
    public String hotel_class;
    //public int extracted_hotel_class;
    public double overall_rating;
    public int reviews;
    //public ArrayList<Rating> ratings;
    public double location_rating;

    @Override
    public String toString() {
        return "Property[" +
                "name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", check_in_time='" + check_in_time + '\'' +
                ", check_out_time='" + check_out_time + '\'' +
                ", rate_per_night=" + rate_per_night +
                ", total_rate=" + total_rate +
                ", overall_rating=" + overall_rating +
                ']';
    }
    //public ArrayList<String> amenities;
    //public String deal;
    //public String deal_description;
}
