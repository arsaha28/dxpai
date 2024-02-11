package com.dxpai.controller;

import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.model.vertexai.VertexAiLanguageModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Budget", description = "Ask WishAI how much your trip is going to cost")
@RestController
public class BudgetController {

    @Autowired
    private VertexAiLanguageModel model;

    @Operation(
            summary = "Budget",
            description = "Ask WishAI how much your trip is going to cost")
    @ApiResponses(value = {
            @ApiResponse(responseCode    = "200", description = "successful operation")
    })
    @RequestMapping(value = "/budget", method = RequestMethod.GET)
    public String estimate(int familyMemberCount, int numberOfDays,String travelLocation){
        PromptTemplate promptTemplate = PromptTemplate.from("What is the approximate total cost of traveling to {{location}} from London for a family of {{family_member}} for {{number_of_days}} days.Just a give a numerical answer");
        Map<String, Object> variables = new HashMap<>();
        variables.put("location", travelLocation);
        variables.put("family_member", familyMemberCount);
        variables.put("number_of_days", numberOfDays);
        Prompt prompt = promptTemplate.apply(variables);
        Response<String> response = model.generate(prompt);
        return response.content();
    }

}
