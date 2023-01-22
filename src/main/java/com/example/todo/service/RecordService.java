package com.example.todo.service;

import com.example.todo.dto.RecordDTO;
import com.example.todo.entity.Record;
import com.example.todo.entity.Test;
import com.example.todo.repository.RecordsRepository;
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
public class RecordService {
	
    private RecordsRepository recordsRepository;
 
    public RecordService(RecordsRepository recordsRepository) {
        this.recordsRepository = recordsRepository;
    }

    public Page<Record> getRecords(Pageable pageable) {
        return recordsRepository.findAll(pageable);
    }
    
    public Record getRecord(long recordId) {
        Optional<Record> record = recordsRepository.findById(recordId);
        return record.get();
    }
    
    public void deleteRecord(long recordId) {
    	Record record = getRecord(recordId);
    	recordsRepository.delete(record);
    }

    public Record saveRecord(RecordDTO recordDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Record record = modelMapper.map(recordDTO, Record.class);
        return recordsRepository.save(record);
    }
    
}
