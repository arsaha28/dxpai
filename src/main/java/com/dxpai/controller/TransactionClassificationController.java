package com.dxpai.controller;

import com.dxpai.config.AppConfiguration;
import com.dxpai.model.PredictScore;
import com.google.cloud.aiplatform.util.ValueConverter;
import com.google.cloud.aiplatform.v1.EndpointName;
import com.google.cloud.aiplatform.v1.PredictResponse;
import com.google.cloud.aiplatform.v1.PredictionServiceClient;
import com.google.cloud.aiplatform.v1.schema.predict.prediction.TabularClassificationPredictionResult;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.ListValue;
import com.google.protobuf.Value;
import com.google.protobuf.util.JsonFormat;
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

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Tag(name = "Transaction Classification", description = "Identify Transaction Category")
@RestController
public class TransactionClassificationController {

    @Autowired
    private AppConfiguration appConfiguration;
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

    @RequestMapping(value = "/category/v2", method = RequestMethod.POST)
    public PredictScore identifyCategoryV2(@RequestBody String instance){

        List<PredictScore> scoreList = new ArrayList<>();
        try (PredictionServiceClient predictionServiceClient =
                     PredictionServiceClient.create(appConfiguration.settings())) {
            String location = appConfiguration.getLocation();
            EndpointName endpointName = EndpointName.of(appConfiguration.getProject(), location, appConfiguration.getEndpointId());

            ListValue.Builder listValue = ListValue.newBuilder();
            JsonFormat.parser().merge(instance, listValue);
            List<com.google.protobuf.Value> instanceList = listValue.getValuesList();

            Value parameters = Value.newBuilder().setListValue(listValue).build();
            PredictResponse predictResponse =
                    predictionServiceClient.predict(endpointName, instanceList, parameters);
            for (Value prediction : predictResponse.getPredictionsList()) {
                TabularClassificationPredictionResult.Builder resultBuilder =
                        TabularClassificationPredictionResult.newBuilder();
                TabularClassificationPredictionResult result =
                        (TabularClassificationPredictionResult)
                                ValueConverter.fromValue(resultBuilder, prediction);

                for (int i = 0; i < result.getClassesCount(); i++) {
                    System.out.println("Class:"+result.getClasses(i));
                    System.out.println("Score:"+ result.getScores(i));
                    scoreList.add(new PredictScore(result.getClasses(i),result.getScores(i)));
                }
            }
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scoreList = scoreList.stream().filter(ps->ps.getScore()<1).collect(Collectors.toList());
        Collections.sort(scoreList);
        return scoreList.get(0);

    }

    @RequestMapping(value = "/suggest", method = RequestMethod.POST)
    public String suggest(@RequestBody String jsonData,String query) throws IOException {
        PromptTemplate promptTemplate = PromptTemplate.from(query+"{{jsonData}}");
        Map<String, Object> variables = new HashMap<>();
        variables.put("jsonData", jsonData);
        Prompt prompt = promptTemplate.apply(variables);
        Response<String> response = model.generate(prompt);
        return response.content();
    }
}
