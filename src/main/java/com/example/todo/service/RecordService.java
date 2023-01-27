package com.example.todo.service;

import com.example.todo.dto.RecordDTO;
import com.example.todo.entity.Record;
import com.example.todo.dto.UserDTO;
import com.example.todo.entity.User;
import com.example.todo.entity.Test;
import com.example.todo.repository.RecordsRepository;
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
import java.util.Collections;
import java.util.Comparator;

@Slf4j
@Service
public class RecordService {
	
    private RecordsRepository recordsRepository;
    private UsersRepository usersRepository;
 
    public RecordService(RecordsRepository recordsRepository, UsersRepository usersRepository) {
        this.recordsRepository = recordsRepository;
        this.usersRepository= usersRepository;
    }

    public Page<Record> getRecords(Pageable pageable) {
        return recordsRepository.findAll(pageable);
    }
    
    public Page<Record> getRecordRange(long beginTime, long endTime, long userid, Pageable pageable) {
        Page<Record> allRecords = recordsRepository.findAll(pageable);
        List<Record> returnRecords = new ArrayList<>();
        if(endTime == 0){
        	for(Record record : allRecords){
            	if(record.getRdate() > beginTime && record.getMyuser().getId()== userid){
            		returnRecords.add(record);
            	}
            }        	
        }else{
        	for(Record record : allRecords){
        		if(record.getRdate() < endTime && record.getRdate() > beginTime && record.getMyuser().getId()== userid){
        			returnRecords.add(record);
        		}
        	}
        }
        Collections.sort(returnRecords, new Comparator<Record>() {
            public int compare(Record o1, Record o2) {
                if(o2.getRdate()>o1.getRdate()){
                	return 1;
                }else if(o2.getRdate()==o1.getRdate()){
                	return 0;
                }
                return -1;
            }
        });
        Page<Record> page = new PageImpl<>(returnRecords, pageable, returnRecords.size());
        return page;
    }

    public Record getRecord(long recordId) {
        Optional<Record> record = recordsRepository.findById(recordId);
        return record.get();
    }
    
    public void deleteRecord(long recordId) {
    	Record record = getRecord(recordId);
    	recordsRepository.delete(record);
    }

   
    public Record saveRecord(RecordDTO recordDTO, Long userid, Pageable pageable) {
    	Optional<User> userList = usersRepository.findById(userid);
    	User user = userList.get();
        ModelMapper modelMapper = new ModelMapper();
        List<Record> records = recordsRepository.findByRdate(recordDTO.getRdate());
        if(records != null && !records.isEmpty()){
        	return records.get(0);
        }else{
        	Record record = modelMapper.map(recordDTO, Record.class);
        	record.setMyuser(user);
        	return recordsRepository.save(record);
        }
    }
    
    public Record updateRecord(RecordDTO recordDTO, Pageable pageable) {
        ModelMapper modelMapper = new ModelMapper();
        Record record = getRecord(recordDTO.getId());
        if(record != null){
        	record.setRdate(recordDTO.getRdate());
        	record.setApptime(recordDTO.getApptime());
        	record.setPhototime(recordDTO.getPhototime());
        	record.setTesttime(recordDTO.getTesttime());
        	record.setTestnumber(recordDTO.getTestnumber());
        	record.setAveragescore(recordDTO.getAveragescore());
        	record.setCommentnumber(recordDTO.getCommentnumber());
        }
        return recordsRepository.save(record);
    }
    
}
