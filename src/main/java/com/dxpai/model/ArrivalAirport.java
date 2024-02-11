package com.dxpai.model;

import lombok.Data;

@Data
public class ArrivalAirport {

    @Override
    public String toString() {
        return name;
    }

    public String name;
    //public String id;
    public String time;
}
