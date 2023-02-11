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
import org.springframework.web.multipart.MultipartFile;
import java.util.Base64;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.InvalidParameterException;
import java.io.IOException;

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
    
    public Photo uploadPhoto(PhotoDTO photoDTO, MultipartFile file, Long userid, String album) throws IOException{
        ModelMapper modelMapper = new ModelMapper();
        Photo photo = modelMapper.map(photoDTO, Photo.class);
        Optional<User> userList = usersRepository.findById(userid);
        User user = userList.get();
        photo.setMyuser(user);
        //try{
        	byte[] decoded = file.getBytes();
        	String dirPath = "./upload/" + userid+ "/" + album;
        	Path targetLocation = Paths.get(dirPath + "/" + photoDTO.getName()+".jpg");
        	Files.createDirectories(Paths.get(dirPath));
        	String image = Base64.getEncoder().encodeToString(decoded);
        	photo.setImage(image);
        	photo.setUploaddir(dirPath);
        	if(Files.exists(targetLocation)) {
        		throw new InvalidParameterException("Photo " + photoDTO.getName() + " already exists in the album. Please use another name.");
        	}
        	Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
       // }catch(Exception ex){
        //	log.info("Error writing the image to local file: " + ex.getMessage());
        //}
        return photosRepository.save(photo);
    }
    
    public Page<Photo> getPhotoFromAlbum(String album, long userid, Pageable pageable) {
        Page<Photo> allPhotos = photosRepository.findAll(pageable);
        List<Photo> returnPhotos = new ArrayList<>();
        for(Photo photo : allPhotos){
        	if(photo.getUploaddir().contains("./upload/" +userid +"/" + album)){
        		returnPhotos.add(photo);
        	}
        }
        Page<Photo> page = new PageImpl<>(returnPhotos, pageable, returnPhotos.size());
        return page;
    }
    
}
