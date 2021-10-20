package com.clearbank.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class ClearBankTest {


    @Value("${api.key}")
    private String apiKey;

    final private String photoSearchUrl = "https://api.thecatapi.com/v1/images/search";

    @Autowired
    private RestTemplate restTemplate;



    @ResponseBody
    @RequestMapping(value = "/catImages", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public void getCatImages() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key",apiKey);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(photoSearchUrl)
                .queryParam("size", "full")
                .queryParam("limit", 1);


        byte[] imageBytes = restTemplate.getForObject(builder.toUriString(), byte[].class);

        try (FileOutputStream stream = new FileOutputStream("./image.jpg")) {
            stream.write(imageBytes);
        }

    }

}
