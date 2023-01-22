package com.example.todo.service;

import com.example.todo.dto.QuestionDTO;
import com.example.todo.entity.Question;
import com.example.todo.entity.Test;
import com.example.todo.repository.QuestionsRepository;
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
public class QuestionService {
	
	public static final String[] GENERAL_QUESTIONS = {"Which day of the week is today?", "Which month of the year is it?", "What year is this?"};
	public static final String[] GENERAL_HINTS = {"relax", "relax", "relax"};
	public static final String[] GENERAL_SOLUTIONS = {"monday", "April", "2023"};

	public static final String[] MONEY_QUESTIONS = {"How many 20 cent pieces are in $2.40?", "You are buying $19.40 of groceries. How much change would you receive back from a $20 note?", "How many quarters are in $8.75?"};
	public static final String[] MONEY_HINTS = {"relax", "relax", "relax"};
	public static final String[] MONEY_SOLUTIONS = {"12", "60", "35"};
	
	public static final String[] RELATIONSHIP_QUESTIONS = {"corkscrew/hammer", "rose/tulip", "watch/ruler"};
	public static final String[] RELATIONSHIP_HINTS = {"relax", "relax", "relax"};
	public static final String[] RELATIONSHIP_SOLUTIONS = {"tools", "flower plant", "measure"};
	
	public static final String[] MEMORY_QUESTIONS = {"blue/computer/rain", "boots/cute/violet", "phone/rainbow/church"};
	public static final String[] MEMORY_HINTS = {"relax", "relax", "relax"};
	public static final String[] MEMORY_SOLUTIONS = {"+blue, +computer, +rain", "+boots, +cute, +violet", "+phone, +rainbow, +church"};
    
	
    private QuestionsRepository questionsRepository;
 
    public QuestionService(QuestionsRepository questionsRepository) {
        this.questionsRepository = questionsRepository;
    }

    public Page<Question> getQuestions(Pageable pageable) {
        return questionsRepository.findAll(pageable);
    }
    
    public Question getQuestion(long questionId) {
        Optional<Question> question = questionsRepository.findByQid(questionId);
        return question.get();
    }
    
    public void deleteQuestion(long questionId) {
    	Question question = getQuestion(questionId);
    	questionsRepository.delete(question);
    }

    public Question saveQuestion(QuestionDTO questionDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Question question = modelMapper.map(questionDTO, Question.class);
        return questionsRepository.save(question);
    }
    
}
