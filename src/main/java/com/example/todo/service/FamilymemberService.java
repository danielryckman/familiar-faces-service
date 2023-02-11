package com.example.todo.service;

import com.example.todo.dto.FamilymemberDTO;
import com.example.todo.entity.Familymember;
import com.example.todo.dto.UserDTO;
import com.example.todo.entity.User;
import com.example.todo.entity.Test;
import com.example.todo.repository.FamilymembersRepository;
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
public class FamilymemberService {
	
    private FamilymembersRepository familymembersRepository;
    private UsersRepository usersRepository;
 
    public FamilymemberService(FamilymembersRepository familymembersRepository, UsersRepository usersRepository) {
        this.familymembersRepository = familymembersRepository;
        this.usersRepository = usersRepository;
    }

    public Page<Familymember> getFamilymembers(Pageable pageable) {
        return familymembersRepository.findAll(pageable);
    }
    
    public Familymember getFamilymember(long familymemberId) {
        Optional<Familymember> familymember = familymembersRepository.findById(familymemberId);
        return familymember.isPresent() ? familymember.get(): null;
    }
    
    public Familymember getFamilymemberByFirstname(String firstname) {
        Optional<Familymember> familymember = familymembersRepository.findByFirstname(firstname);
        return familymember.isPresent() ? familymember.get(): null;
    }
    
    public Familymember getFamilymemberByEmail(String email, Pageable pageable) {
        Optional<Familymember> familymember = familymembersRepository.findByEmail(email);
        return familymember.isPresent() ? familymember.get(): null;
    }
    
    public void deleteFamilymember(long familymemberId) {
    	Familymember familymember = getFamilymember(familymemberId);
    	familymembersRepository.delete(familymember);
    }

    public Familymember saveFamilymember(FamilymemberDTO familymemberDTO, Long userId, Pageable pageable) {
    	Optional<User> userList = usersRepository.findById(userId);
    	User user = userList.get();
        ModelMapper modelMapper = new ModelMapper();
        Familymember familymember = getFamilymemberByFirstname(familymemberDTO.getFirstname());
        if(familymember != null && familymember.getLastname().equals(familymemberDTO.getLastname())){
        	//do nothing since the family member already exists
        	return null;
        }else{
        	familymember = modelMapper.map(familymemberDTO, Familymember.class);
        	familymember.setMyuser(user);
        	familymember.setUserid(userId);
        	return familymembersRepository.save(familymember);
        }
    }
    
}
