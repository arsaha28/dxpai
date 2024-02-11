package com.dxpai.predict;


import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.model.vertexai.VertexAiLanguageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GenAIService {


    @Autowired
    private VertexAiLanguageModel model;

    public String summary(@RequestBody String jsonData){
        PromptTemplate promptTemplate = PromptTemplate.from("Can you make a summary from JSON {{jsonData}}");
        Map<String, Object> variables = new HashMap<>();
        variables.put("jsonData", jsonData);
        Prompt prompt = promptTemplate.apply(variables);
        Response<String> response = model.generate(prompt);
        return response.content();
    }

    public List<String> city(@RequestBody String jsonData){
        PromptTemplate promptTemplate = PromptTemplate.from("Which city does this places belong in JSON{{jsonData}}.Create a CSV with unique city name and unique airport code");
        Map<String, Object> variables = new HashMap<>();
        variables.put("jsonData", jsonData);
        Prompt prompt = promptTemplate.apply(variables);

        Response<String> response = model.generate(prompt);
        return Arrays.stream(Arrays.asList(response.content().split("\\n")).get(2).split("\\|")).toList().subList(1,3);
    }
}
