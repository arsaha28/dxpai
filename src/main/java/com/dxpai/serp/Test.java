package com.dxpai.serp;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws SerpApiSearchException {
        Map<String, String> parameter = new HashMap<>();

        parameter.put("api_key", "ddff6b11528e018399a49d77678d915ee28fb5defa4a03ee5b272e816dc4be96");
        parameter.put("engine", "google_flights");
        parameter.put("departure_id", "CDG");
        parameter.put("arrival_id", "AUS");
        parameter.put("hl", "en");
        parameter.put("gl", "us");
        parameter.put("currency", "USD");
        parameter.put("outbound_date", "2024-01-24");
        parameter.put("return_date", "2024-01-30");

        GoogleSearch search = new GoogleSearch(parameter);

        try
        {
            JsonObject results = search.getJson();
            System.out.println(results);
        }
        catch (Exception ex)
        {
           ex.printStackTrace();
        }

    }
}
