package com.dxpai.controller;

import com.dxpai.config.AppConfiguration;
import com.dxpai.model.BestFlight;
import com.dxpai.predict.GenAIService;
import com.dxpai.serp.GoogleSearch;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Flight Search", description = "Search for available flights")
@RestController
public class FlightSearchController {

    @Autowired
    private GenAIService genAIService;

    @Autowired
    private AppConfiguration appConfiguration;

    @Operation(
            summary = "Flight Search",
            description = "Search for available flights")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @RequestMapping(value = "/flight", method = RequestMethod.GET)
    @CrossOrigin
    public List<String> flight(String departure ,String arrival,String outBoundDate,String returnDate){
        Map<String, String> parameter = new HashMap<>();

        parameter.put("api_key", appConfiguration.getSerpAPIKey());
        parameter.put("engine", "google_flights");
        parameter.put("departure_id", departure);
        parameter.put("arrival_id", arrival);
        parameter.put("hl", "en");
        parameter.put("gl", "us");
        parameter.put("currency", "GBP");
        //parameter.put("outbound_date", "2024-01-24");
        //parameter.put("return_date", "2024-01-30");
        parameter.put("outbound_date", outBoundDate);
        parameter.put("return_date", returnDate);

        GoogleSearch search = new GoogleSearch(parameter);

        try
        {
            JsonObject results = search.getJson();
            JsonElement flights = results.get("best_flights");
            Type collectionType = new TypeToken<List<BestFlight>>(){}.getType();
            List<BestFlight> bestFlight  = new Gson().fromJson( flights.toString() , collectionType);
            //String flightResponse = genAIService.summary(new Gson().toJson(bestFlight));
            List<String> bestFls = bestFlight.stream().limit(3).map(f->f.toString()).toList();
            return bestFls;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
}
