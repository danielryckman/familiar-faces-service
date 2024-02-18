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
import org.springframework.web.client.HttpClientErrorException;
import org.json.simple.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;

@Slf4j
public class StableDiffusionService {
	
	private RestTemplate restTemplate;	
	//private String newImageUrl = "http://47.14.39.133:7861/sdapi/v1/txt2img";
	//private String newImageUrl = "http://192.168.4.171:7860/sdapi/v1/txt2img";
        private String newImageUrl = "http://127.0.0.1:7860/sdapi/v1/txt2img";
	//private String negativePrompt ="cartoon, 3d, {(disfigured)},{(bad art)},{(deformed)},{(poorly drawn)},{(extra limbs)},{(close up)},{(b&w)},{(weird colors)}, blurry";
	private String negativePrompt ="bad anatomy, extra people, (deformed iris, deformed pupils, mutated hands and fingers:1.4), (deformed, distorted, disfigured:1.3), poorly drawn, bad anatomy, wrong anatomy, extra limb, missing limb, floating limbs, disconnected limbs, mutation, mutated, ugly, disgusting, amputation, signature, watermark, airbrush, photoshop, plastic doll, (ugly eyes, deformed iris, deformed pupils, fused lips and teeth:1.2), text, cropped, out of frame, worst quality, low quality, jpeg artifacts, ugly, duplicate, morbid, mutilated, extra fingers, mutated hands, poorly drawn hands, poorly drawn face, mutation, deformed, blurry, dehydrated, bad anatomy, bad proportions, extra limbs, cloned face, disfigured, gross proportions, malformed limbs, missing arms, missing legs, extra arms, extra legs, fused fingers, too many fingers, long neck, masculine, obese, fat, out of frame, caricature, body horror, mutant, facebook, youtube, food, lowres, text, error, cropped, worst quality, low quality, jpeg artifacts, ugly, duplicate, morbid, mutilated, out of frame, extra fingers, mutated hands, poorly drawn hands, poorly drawn face, mutation, deformed, blurry, dehydrated, bad anatomy, bad proportions, extra limbs, cloned face, disfigured, gross proportions, malformed limbs, missing arms, missing legs, extra arms, extra legs, fused fingers, too many fingers, long neck, username, watermark, signature, glasses";
	
	public String newImage(String prompt, int steps) {
		restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("Authorization", "Basic ZmFtaWxpYXJmYWNlc2FwaToxMjE5OGMwZS05ZDI2LTExZWQtYjM2Ny05MzdmZjgwZTM2YTI=");
	    log.info("invoking the stable diffusion service:" + prompt);
	    JSONObject jsonObject = new JSONObject();
	    prompt += " (smiling:0.5), skin pores, couture, (30mm Sigma f/1.4 ZEISS lens, F1.4, 1/800s, ISO 100, photography:1.1), delicate, masterpiece, beautiful detailed, colorful, finely detailed, detailed lips, intricate details, film grain";
	    jsonObject.put("prompt", prompt);
	    jsonObject.put("negative_prompt", negativePrompt);
	    jsonObject.put("save_images", true);
	    if(prompt.contains("zdanielryckman")){
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
