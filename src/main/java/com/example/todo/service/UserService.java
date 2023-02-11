package com.example.todo.service;

import com.example.todo.dto.UserDTO;
import com.example.todo.entity.User;
import com.example.todo.entity.Test;
import com.example.todo.repository.UsersRepository;
import com.example.todo.repository.FamilymembersRepository;
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
public class UserService {
			
    private UsersRepository usersRepository;
 
    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Page<User> getUsers(Pageable pageable) {
        return usersRepository.findAll(pageable);
    }
    
    public User getUser(long userId) {
        Optional<User> user = usersRepository.findById(userId);
        return user.get();
    }
    
    public void deleteUser(long userId) {
    	User user = getUser(userId);
    	usersRepository.delete(user);
    }
    
    public User getUserByEmail(String email, Pageable pageable) {
        Optional<User> user = usersRepository.findByEmail(email);
        return user.isPresent() ? user.get(): null;
    }
    
    public User saveUser(UserDTO userDTO, Pageable pageable) {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userDTO, User.class);
        return usersRepository.save(user);
    }
    
    public User updateUser(UserDTO userDTO, Pageable pageable) {
        ModelMapper modelMapper = new ModelMapper();
        User user = getUser(userDTO.getId());
        if(user != null){
        	user.setFirstname(userDTO.getFirstname());
        	user.setLastname(userDTO.getLastname());
        	user.setDob(userDTO.getDob());
        	user.setGender(userDTO.getGender());
        	user.setHobbies(userDTO.getHobbies());
        	user.setNickname(userDTO.getNickname());
        }
        return usersRepository.save(user);
    }
}


