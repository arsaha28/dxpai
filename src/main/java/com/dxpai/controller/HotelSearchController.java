package com.dxpai.controller;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.dxpai.config.AppConfiguration;
import com.dxpai.model.Property;
import com.dxpai.predict.GenAIService;
import com.dxpai.serp.GoogleSearch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Hotel Search", description = "Search for available Hotels")
@RestController
public class HotelSearchController {

    @Autowired
    private GenAIService genAIService;

    @Autowired
    private AppConfiguration appConfiguration;
    @Operation(
            summary = "Hotel Search",
            description = "Search for available Hotels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @RequestMapping(value = "/hotel", method = RequestMethod.GET)
    public List<String> hotel(String location, String checkInDate, String checkOutDate){
        Map<String, String> parameter = new HashMap<>();

        parameter.put("api_key", appConfiguration.getSerpAPIKey());
        parameter.put("engine", "google_hotels");
        parameter.put("q", location);
        parameter.put("hl", "en");
        parameter.put("gl", "us");
        parameter.put("check_in_date",checkInDate);
        parameter.put("check_out_date", checkOutDate);
        parameter.put("currency", "GBP");

        GoogleSearch search = new GoogleSearch(parameter);

        try
        {
            JsonObject results = search.getJson();
            JsonElement hotelsElement = results.get("properties");
            Type collectionType = new TypeToken<List<Property>>(){}.getType();
            List<Property> hotels  = new Gson().fromJson( hotelsElement.toString() , collectionType);
            //String hotelResponse = genAIService.summary(new Gson().toJson(hotels));
            List<String> hotelCol = hotels.stream().limit(3).map(h->h.toString()).toList();
            return hotelCol;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
}
