package com.example.todo.service;

import com.example.todo.dto.TestDTO;
import com.example.todo.entity.Test;
import com.example.todo.entity.User;
import com.example.todo.entity.Question;
import com.example.todo.repository.TestsRepository;
import com.example.todo.repository.QuestionsRepository;
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
import java.util.UUID;
import java.util.Random;

@Slf4j
@Service
public class TestService {

    private TestsRepository testsRepository;
    private QuestionsRepository questionsRepository;
    private UsersRepository usersRepository;
    private Random random;

    public TestService(TestsRepository testsRepository, QuestionsRepository questionsRepository, UsersRepository usersRepository) {
        this.testsRepository = testsRepository;
        this.questionsRepository = questionsRepository;
        this.usersRepository = usersRepository;
        random = new Random();
    }

    public Page<Test> getTests(Pageable pageable) {
        return testsRepository.findAll(pageable);
    }
    
    public Test getTest(long testId) {
        Optional<Test> test = testsRepository.findById(testId);
        return test.get();
    }
    
    public void deleteTest(long testId) {
    	Test test = getTest(testId);
    	testsRepository.delete(test);
    }

    public Test createTest(Pageable pageable) {
    	Test test = new Test();
    	test.setName("temp");
    	testsRepository.save(test);
    	Page<Question> questions= questionsRepository.findAll(pageable);
    	List<Question> questionList = questions.getContent();
    	for(Question question : questionList){
    		question.setTest(test);
    	}
    	test.setQuestion(questions.getContent());
        return testsRepository.save(test);
    }
    
    public Test saveTest(TestDTO testDTO, Long userid, Pageable pageable) {
    	Optional<User> userList = usersRepository.findById(userid);
    	User user = userList.get();
        ModelMapper modelMapper = new ModelMapper();
        Test test = modelMapper.map(testDTO, Test.class);
        testsRepository.save(test);
        int index = random.nextInt(3);
    	return testsRepository.save(test);
    }
    
    public Test addQuestions(long testId, int index){
    	Optional<Test> testList = testsRepository.findById(testId);
    	Test test = testList.get();
    	List<Question> questionList = new ArrayList<Question>();
    	Question generalQuestion = new Question(test.getName(), QuestionService.GENERAL_HINTS[index],QuestionService.GENERAL_QUESTIONS[index],QuestionService.GENERAL_SOLUTIONS[index], "general", null);
    	Question moneyQuestion = new Question(test.getName(), QuestionService.MONEY_HINTS[index],QuestionService.MONEY_QUESTIONS[index],QuestionService.MONEY_SOLUTIONS[index], "money", null);
    	Question relationshipQuestion = new Question(test.getName(), QuestionService.RELATIONSHIP_HINTS[index],QuestionService.RELATIONSHIP_QUESTIONS[index],QuestionService.RELATIONSHIP_SOLUTIONS[index], "relationship", null);
    	Question memoryQuestion = new Question(test.getName(), QuestionService.MEMORY_HINTS[index],QuestionService.MEMORY_QUESTIONS[index],QuestionService.MEMORY_SOLUTIONS[index], "memory", null);
    	generalQuestion.setTest(test);
    	moneyQuestion.setTest(test);
    	relationshipQuestion.setTest(test);
    	memoryQuestion.setTest(test);
    	questionsRepository.save(generalQuestion);
    	questionsRepository.save(moneyQuestion);
    	questionsRepository.save(relationshipQuestion);
    	questionsRepository.save(memoryQuestion);
    	questionList.add(generalQuestion);
    	questionList.add(moneyQuestion);
    	questionList.add(relationshipQuestion);
    	questionList.add(memoryQuestion);
    	test.setQuestion(questionList);
    	return testsRepository.save(test);
    }
    
    public Test updateTest(TestDTO testDTO, Pageable pageable) {
        ModelMapper modelMapper = new ModelMapper();
        Test test = getTest(testDTO.getId());
        if(test != null){
        	test.setName(testDTO.getName());
        	test.setStarttime(testDTO.getStarttime());
        	test.setEndtime(testDTO.getEndtime());
        	test.setScore(testDTO.getScore());
        	test.setSubscores(testDTO.getSubscores());
        }
        return testsRepository.save(test);
    }
}
