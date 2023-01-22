package com.example.todo.service;

import com.example.todo.entity.Photo;
import com.example.todo.entity.Test;
import com.example.todo.entity.User;
import com.example.todo.repository.PhotosRepository;
import com.example.todo.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.json.simple.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
public class StableDiffusionService {
	
	private RestTemplate restTemplate;
	
	private String newImageUrl = "http://192.168.4.171:7860/sdapi/v1/txt2img";
	
	public String newImage(String prompt, int steps) {
		restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    JSONObject jsonObject = new JSONObject();
	    jsonObject.put("prompt", prompt);
	    jsonObject.put("steps", steps);
		HttpEntity<String> request = 
			      new HttpEntity<String>(jsonObject.toString(), headers);
		String response = restTemplate.postForObject(newImageUrl, request, String.class);
		try{
			ObjectMapper mapper = new ObjectMapper();
			ImageResponse responseObj = mapper.readValue(response, ImageResponse.class);
			return responseObj.images.get(0);
		}catch(Exception exception){
			log.info("Error parsing the StableDiffusion service response:" + exception.getMessage());
		}
		return "";
	}

    
}
