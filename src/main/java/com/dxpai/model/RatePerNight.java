package com.dxpai.model;

import lombok.Data;

@Data
public class RatePerNight {
    public String lowest;
    //public int extracted_lowest;
    //public String before_taxes_fees;
    //public int extracted_before_taxes_fees;
    @Override
    public String toString() {
        return lowest;
    }
}
