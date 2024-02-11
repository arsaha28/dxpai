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
@Tag(name = "Transaction Classification", description = "Identify Transaction Category")
@RestController
public class TransactionClassificationController {


    @Autowired
    private VertexAiLanguageModel model;

    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public String identifyCategory(@RequestBody String jsonData){
        PromptTemplate promptTemplate = PromptTemplate.from("Can you add a transactions category for each transactions in {{jsonData}}.Create valid JSON array.The JSON object:`.trim()");
        Map<String, Object> variables = new HashMap<>();
        variables.put("jsonData", jsonData);
        Prompt prompt = promptTemplate.apply(variables);
        Response<String> response = model.generate(prompt);
        return response.content();

    }

    @RequestMapping(value = "/suggest", method = RequestMethod.POST)
    public String suggest(@RequestBody String jsonData){
        PromptTemplate promptTemplate = PromptTemplate.from("These are my transactions {{jsonData}}.Can you filter only london transactions?Create valid JSON array.The JSON object:`.trim()\"" );
        Map<String, Object> variables = new HashMap<>();
        variables.put("jsonData", jsonData);
        Prompt prompt = promptTemplate.apply(variables);
        Response<String> response = model.generate(prompt);
        return response.content();

    }
}
