package com.dxpai.controller;

import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.model.vertexai.VertexAiLanguageModel;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "CPA", description = "Identify CPA Transactions")
@RestController
public class CPAController {

    @Autowired
    private VertexAiLanguageModel model;

    @RequestMapping(value = "/cpa", method = RequestMethod.POST)
    public String identifyCPATransaction(@RequestBody String jsonData){
        PromptTemplate promptTemplate = PromptTemplate.from("Can you add a flag for recurring transaction in{{jsonData}} if there are similar transactions present.A transaction is recurring if merchantName ,day of month and amount is same.Create valid JSON array.The JSON object:`.trim()");
        Map<String, Object> variables = new HashMap<>();
        variables.put("jsonData", jsonData);
        Prompt prompt = promptTemplate.apply(variables);
        Response<String> response = model.generate(prompt);
        return response.content();

    }


}
