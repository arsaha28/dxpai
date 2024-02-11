package com.dxpai.config;


import dev.langchain4j.model.vertexai.VertexAiLanguageModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Value("${endpoint}")
    private String endpoint;
    @Value("${project}")
    private String project;
    @Value("${location}")
    private String location;
    @Value("${publisher}")
    private String publisher;
    @Value("${modelName}")
    private String modelName;
    @Value("${maxOutputTokens}")
    private String maxOutputTokens;

    @Value("${uploadLocation}")
    private String uploadLocation;

    @Value("${bucketName}")
    private String bucket;

    @Value("${serpAPIKey}")
    private String serpAPIKey;

    @Bean
    public VertexAiLanguageModel model(){
        VertexAiLanguageModel model = VertexAiLanguageModel.builder()
                .endpoint(endpoint)
                .project(project)
                .location(location)
                .publisher(publisher)
                .modelName(modelName)
                .maxOutputTokens(Integer.parseInt(maxOutputTokens))
                .build();
        return model;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getProject() {
        return project;
    }

    public String getLocation() {
        return location;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getModelName() {
        return modelName;
    }

    public String getMaxOutputTokens() {
        return maxOutputTokens;
    }

    public String getUploadLocation() {
        return uploadLocation;
    }

    public String getBucket() {
        return bucket;
    }

    public String getSerpAPIKey() {
        return serpAPIKey;
    }
}
