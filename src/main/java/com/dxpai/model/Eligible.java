package com.dxpai.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Eligible {
    private double amount = 0;
    private boolean isEligible = false;
    private Map<String,String> actions = new HashMap<>();

    public Eligible(double amount, boolean isEligible) {
        this.amount = amount;
        this.isEligible = isEligible;
    }
}
