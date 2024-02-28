package com.example.todo.service;
import com.example.todo.dto.UserDTO;
import com.example.todo.entity.User;
import com.example.todo.dto.AuthTokenDTO;
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
import java.util.Base64;
import java.util.UUID;
import java.nio.charset.StandardCharsets;

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

    public boolean authorize(String userinfo){
        byte[] bytes_decoded = Base64.getMimeDecoder().decode(userinfo);
        String decodedStr = new String(bytes_decoded, StandardCharsets.UTF_8);
        String email = decodedStr.split("/")[0];
        String password = decodedStr.split("/")[1];
        Optional<User> user_optional = usersRepository.findByEmail(email);
        User user = user_optional.get();
        String userPassword = user.getPassword();
        if(password.equals(userPassword)){
            return true;
        } else{
            return false;
        }
    }

    public AuthTokenDTO createToken(){
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        return new AuthTokenDTO(uuidAsString);
    }
    
    public void deleteUser(long userId) {
    	User user = getUser(userId);
    	usersRepository.delete(user);
    }
    
    public UserDTO getUserByEmail(String email, Pageable pageable) {
        Optional<User> user = usersRepository.findByEmail(email);
        if(user.isPresent()){
            User dto_user = user.get();
        log.info("TasksController: FOUND");
        return new UserDTO(dto_user.getId(), dto_user.getFirstname(), dto_user.getLastname(),  dto_user.getDob(), dto_user.getNickname(), dto_user.getHobbies(), dto_user.getGender(), dto_user.getEmail(),  dto_user.getPassword(), dto_user.getAuthToken());
        }
        return null;
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


