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

@Slf4j
@Service
public class PhotoService {
	
    private PhotosRepository photosRepository;
    private UsersRepository usersRepository;
 
    public PhotoService(PhotosRepository photosRepository, UsersRepository usersRepository) {
        this.photosRepository = photosRepository;
        this.usersRepository= usersRepository;
    }

    public Page<Photo> getPhotos(Pageable pageable) {
        return photosRepository.findAll(pageable);
    }
    
    public Photo getPhoto(long photoId) {
        Optional<Photo> photo = photosRepository.findById(photoId);
        return photo.get();
    }
    
    public void deletePhoto(String name, Pageable pageable) {
    	List<Photo> photo = photosRepository.findByName(name);
    	photosRepository.delete(photo.get(0));
    }
    
    public void deletePhoto(long photoId) {
    	Photo photo = getPhoto(photoId);
    	photosRepository.delete(photo);
    }

    public Photo savePhoto(PhotoDTO photoDTO, Long userId, Pageable pageable) {
        ModelMapper modelMapper = new ModelMapper();
        Photo photo = modelMapper.map(photoDTO, Photo.class);
        Optional<User> userList = usersRepository.findById(userId);
        User user = userList.get();
        photo.setMyuser(user);
        return photosRepository.save(photo);
    }
    
    public Page<Photo> getPhotoRange(long beginTime, long endTime, long userid, Pageable pageable) {
        Page<Photo> allPhotos = photosRepository.findAll(pageable);
        List<Photo> returnPhotos = new ArrayList<>();
        for(Photo photo : allPhotos){
        	if(photo.getDatetoshow() < endTime && photo.getDatetoshow() > beginTime && photo.getMyuser().getId()== userid){
        		returnPhotos.add(photo);
        	}
        }
        Page<Photo> page = new PageImpl<>(returnPhotos, pageable, returnPhotos.size());
        return page;
    }
    
    public Photo updatePhoto(PhotoDTO photoDTO, Pageable pageable) {
        ModelMapper modelMapper = new ModelMapper();
        Photo photo = getPhoto(photoDTO.getId());
        if(photo != null){
        	photo.setName(photoDTO.getName());
        	photo.setDescription(photoDTO.getDescription());
        	photo.setTitle(photoDTO.getTitle());
        	photo.setPersoninpic(photoDTO.getPersoninpic());
        	photo.setDatetoshow(photoDTO.getDatetoshow());
        	photo.setDatecreated(photoDTO.getDatecreated());
        	photo.setDatelastviewed(photoDTO.getDatelastviewed());
        	photo.setComment(photoDTO.getComment());    	
        }
        return photosRepository.save(photo);
    }
    
}
