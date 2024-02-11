package com.dxpai.controller;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.vision.v1.*;
import com.google.gson.Gson;
import com.dxpai.config.AppConfiguration;
import com.dxpai.model.Destination;
import com.dxpai.predict.GenAIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Tag(name = "Wish", description = "Your next travel destination")
@RestController
public class WishController {

    @Autowired
    private AppConfiguration appConfiguration;

    @Autowired
    private GenAIService genAIService;
    @Operation(
            summary = "Travel destination",
            description = "Locate travel destination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @RequestMapping(value ="/wishupload",method = RequestMethod.POST,consumes = { MediaType.MULTIPART_FORM_DATA_VALUE } )
    public List<String> uploadLocation(@RequestParam("file") MultipartFile file) throws IOException {
        String fileLocation = "gs://"+appConfiguration.getBucket()+"/"+file.getOriginalFilename();
        List<Destination> location = new ArrayList<>();
        File tmpFile = new File(appConfiguration.getUploadLocation()+ UUID.randomUUID());
        file.transferTo(tmpFile);

        Storage storage = StorageOptions.newBuilder().setProjectId(appConfiguration.getProject()).build().getService();
        BlobId blobId = BlobId.of(appConfiguration.getBucket(), file.getOriginalFilename());
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.createFrom(blobInfo, Paths.get(tmpFile.getPath()));
        System.out.println("File " + tmpFile.getAbsolutePath() + " uploaded to bucket " + appConfiguration.getBucket() + " as " + file.getOriginalFilename());

        List<AnnotateImageRequest> requests = new ArrayList<>();
        ImageSource source = ImageSource.newBuilder().setGcsImageUri(fileLocation).build();
        Image image = Image.newBuilder().setSource(source).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.LANDMARK_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(image).build();
        requests.add(request);
        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                for (EntityAnnotation annotation : res.getLandmarkAnnotationsList()) {
                    LocationInfo info = annotation.getLocationsList().listIterator().next();
                    Destination destination = new Destination();
                    destination.setDescription(annotation.getDescription());
                    location.add(destination);
                }
            }
        }
        List<String> locations = genAIService.city(new Gson().toJson(location));
        return locations;
    }
}
