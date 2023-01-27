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
import java.util.Base64;

@Slf4j
public class StableDiffusionService {
	
	private RestTemplate restTemplate;	
	private String newImageUrl = "http://47.14.39.133:7861/sdapi/v1/txt2img";
	private String negativePrompt ="cartoon, 3d, {(disfigured)},{(bad art)},{(deformed)},{(poorly drawn)},{(extra limbs)},{(close up)},{(b&w)},{(weird colors)}, blurry";
	
	public String newImage(String prompt, int steps) {
		restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("Authorization", "Basic ZmFtaWxpYXJmYWNlc2FwaToxMjE5OGMwZS05ZDI2LTExZWQtYjM2Ny05MzdmZjgwZTM2YTI=");
	    JSONObject jsonObject = new JSONObject();
	    jsonObject.put("prompt", prompt);
	    jsonObject.put("negative_prompt", negativePrompt);
	    if(prompt.contains("danielryckman")){
	    	jsonObject.put("restore_faces", false);
	    }else{
	    	jsonObject.put("restore_faces", true);
	    }
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
	
	/*public void login() {
		restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    JSONObject jsonObject = new JSONObject();
	    jsonObject.put("username", "ling");
	    jsonObject.put("password", "stablediffusion");
		HttpEntity<String> request = 
			      new HttpEntity<String>(jsonObject.toString(), headers);
		String response = restTemplate.postForObject(loginUrl, request, String.class);
		init = true;
	}*/

    
}
