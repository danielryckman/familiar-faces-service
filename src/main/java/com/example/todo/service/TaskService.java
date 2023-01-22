package com.example.todo.service;

import com.example.todo.dto.TaskDTO;
import com.example.todo.entity.Task;
import com.example.todo.repository.TasksRepository;
import com.example.todo.entity.User;
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
import java.util.Base64;
import java.io.File;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;

@Slf4j
@Service
public class TaskService {

    private TasksRepository tasksRepository;
    private UsersRepository usersRepository;

    public TaskService(TasksRepository tasksRepository, UsersRepository usersRepository) {
        this.tasksRepository = tasksRepository;
        this.usersRepository = usersRepository;
    }  

    public Page<Task> getTasks(Pageable pageable) {
        return tasksRepository.findAll(pageable);
    }

    public void deleteTaskRange(long beginTime, long endTime, Pageable pageable) {
        Page<Task> allTasks = tasksRepository.findAll(pageable);
        for(Task task : allTasks){
        	if(task.getSchedule() < endTime && task.getSchedule() > beginTime){
        		tasksRepository.delete(task);
        	}
        }
    }
    
    public List<Task> queryTasks(long beginTime, long endTime, Pageable pageable) {
        Page<Task> allTasks = tasksRepository.findAll(pageable);
        List<Task> selectedTasks = new ArrayList<>();
        for(Task task : allTasks){
        	if(task.getSchedule() < endTime && task.getSchedule() > beginTime){
        		selectedTasks.add(task);
        	}
        }
        return selectedTasks;
    }
    
    public Task getTask(Long taskId) {
        Optional<Task> task = tasksRepository.findById(taskId);
        return task.get();
    }
    
    public void deleteTask(Long taskId) {
    	Task task = getTask(taskId);
    	tasksRepository.delete(task);
    }

    public Task saveTask(TaskDTO taskDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Task task = modelMapper.map(taskDTO, Task.class);
        StableDiffusionService sdService = new StableDiffusionService();
        String image = sdService.newImage(task.getDescription(), 5);
        task.setImage(image);
        return tasksRepository.save(task);
    }
    
    public Task saveTask(Long userId, TaskDTO taskDTO) {  	
    	Optional<User> userList = usersRepository.findById(userId);
    	User user = userList.get();
    	log.info("Task is saved for user: " + user.getFirstname());
        ModelMapper modelMapper = new ModelMapper();
        Task task = modelMapper.map(taskDTO, Task.class);
        StableDiffusionService sdService = new StableDiffusionService();
        String image = sdService.newImage(task.getDescription(), 5);
        task.setImage(image);
        byte[] decoded = Base64.getDecoder().decode(image);
        File outputFile =new File("/tmp/output.jpg");
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(decoded);
        }catch(Exception ex){
        	log.info("Error writing the image to local file: " + ex.getMessage());
        }
        task.setMyuser(user);
        return tasksRepository.save(task);
    }
}
