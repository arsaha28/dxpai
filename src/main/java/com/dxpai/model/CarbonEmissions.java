package com.dxpai.model;

import lombok.Data;

@Data
public class CarbonEmissions {
    public int this_flight;
    public int typical_for_this_route;
    public int difference_percent;
}
