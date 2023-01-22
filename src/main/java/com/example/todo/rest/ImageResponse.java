package com.example.todo.service;

import com.example.todo.dto.PhotoDTO;
import com.example.todo.entity.Photo;
import com.example.todo.entity.Test;
import com.example.todo.entity.User;
import com.example.todo.repository.PhotosRepository;
import com.example.todo.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageResponse {
	
	@JsonProperty("images")
	public List<String> images;
  
}
