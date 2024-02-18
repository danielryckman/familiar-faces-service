package com.example.todo.service;

import com.example.todo.dto.TaskDTO;
import com.example.todo.entity.Task;
import com.example.todo.repository.TasksRepository;
import com.example.todo.entity.User;
import com.example.todo.entity.Familymember;
import com.example.todo.repository.UsersRepository;
import com.example.todo.entity.Photo;
import com.example.todo.repository.PhotosRepository;
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
import java.util.Date;
import java.util.Set;

@Slf4j
@Service
public class TaskService {

    private TasksRepository tasksRepository;
    private UsersRepository usersRepository;
    private PhotosRepository photosRepository;
    
    public TaskService(TasksRepository tasksRepository, UsersRepository usersRepository, PhotosRepository photosRepository) {
        this.tasksRepository = tasksRepository;
        this.usersRepository = usersRepository;
        this.photosRepository = photosRepository;
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
    
    /*private boolean isTaskToday(long beginTime, long endTime, long schedule, int repeat){
    	if(schedule > endTime){
    		return false;
    	}
    	if(schedule < endTime && schedule > startTime){
    		return true;
    	}
    	if(schedule < startTime){
    		if(repeat ==0){
    			return false;
    		}
    		//daily
    		int repeatUnit =86400;
    		//repeat weekly
    		if(repeat ==2){
    			Calendar cal = Calendar.getInstance();
    			int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

    			String dayOfMonthStr = String.valueOf(dayOfMonth);
    			repeatUnit = 604800;
    		}else if(repeat == 3)
    			repeatUnit =
    	}
    	if ( ( currentSystemTime - 86400) >= (eventSystemTime - eventSystemTime mod 86400) ) {
    		// we are on the day after the event or later, so move the event forward to next week
    		eventSystemTime += repetitionFrequency
    		}
    		else if (currentSystemTime >= (eventSystemTime - eventSystemTime mod 86400) ) {
    		// we are on the same day as the event... do event stuff
    		}
    }*/
    
    public Page<Task> queryTasks(long beginTime, long endTime, Pageable pageable) {
        Page<Task> allTasks = tasksRepository.findAll(pageable);
        List<Task> selectedTasks = new ArrayList<>();
        for(Task task : allTasks){
        	if(task.getSchedule() < endTime && task.getSchedule() > beginTime){
        		selectedTasks.add(task);
        	}
        }
        return new PageImpl<>(selectedTasks, pageable, selectedTasks.size());
    }
    
    public Task getTask(Long taskId) {
        Optional<Task> task = tasksRepository.findById(taskId);
        return task.get();
    }
    
    public void deleteTask(long taskid) {
    	Optional<Task> task = tasksRepository.findById(taskid);
    	for (Photo photo : task.get().getPhotos()){
            log.info("Deleting photo for this id: " + photo.getId());
    		photosRepository.delete(photo);
    	}
    	tasksRepository.delete(task.get());
    }

    public Task saveTask(TaskDTO taskDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Task task = modelMapper.map(taskDTO, Task.class);
        StableDiffusionService sdService = new StableDiffusionService();
        String image = sdService.newImage(task.getDescription(), 5);
        task.setImage(image);
        return tasksRepository.save(task);
    }
    
    private String getPartOfDay(long time) {
        Date dt = new Date(time);
        int hours = dt.getHours();
        int min = dt.getMinutes();

        if(hours>=1 || hours<=12){
            return "this morning";
        }else if(hours>=12 || hours<=16){
            return "this afternoon";
        }else if(hours>=16 || hours<=21){
            return "this evening";
        }else{
            return "tonight";
        }
    }
    
    public Task saveTask(Long userId, TaskDTO taskDTO) {  	
    	Optional<User> userList = usersRepository.findById(userId);
    	User user = userList.get();
    	log.info("Task is saved for user: " + user.getFirstname());
        ModelMapper modelMapper = new ModelMapper();
        Task task = modelMapper.map(taskDTO, Task.class);
        Photo photo = new Photo();
        photo.setDescription(task.getDescription());
        photo.setName(task.getName());
        photo.setDatecreated(System.currentTimeMillis());
        photo.setDatetoshow(task.getSchedule());
        photo.setPtype("auto");
        String prompt = task.getDescription();
        photo.setTitle(task.getDescription());
        Set<Familymember> familymembers = user.getFamilymembers();
        for(Familymember familymember : familymembers){
        	if(task.getDescription().toLowerCase().contains(familymember.getFirstname().toLowerCase())){
        		photo.setPersoninpic("Your " + familymember.getRelationship() + " " + familymember.getFirstname() + "(" + familymember.getNickname()+ ")");
        		photo.setTitle(task.getDescription() + " " + getPartOfDay(task.getSchedule()));
        		String fullname = familymember.getFirstname() +familymember.getLastname();
        		prompt = "a photo of " + fullname.toLowerCase() +" "+ task.getDescription().replaceAll(familymember.getFirstname(),"<lora:"+fullname.toLowerCase()+":0.8>") ;
        		break;
        	}
        }        
        StableDiffusionService sdService = new StableDiffusionService();        
        String image = sdService.newImage(prompt, 20);
        task.setImage(image);
        photo.setImage(image);
        photo.setMyuser(user);
        //photo.setTask(task);
        byte[] decoded = Base64.getDecoder().decode(image);
        task.setMyuser(user);
        task.getPhotos().add(photo);       
        Task returnTask = tasksRepository.save(task);
        return returnTask;
    }
  
    public Task updateTask(TaskDTO taskDTO, Pageable pageable) {
        ModelMapper modelMapper = new ModelMapper();
        Task task = getTask(taskDTO.getId());
        if(task != null){
        	task.setName(taskDTO.getName());
        	task.setDescription(taskDTO.getDescription());
        	task.setUrl(taskDTO.getUrl());
        	task.setSchedule(taskDTO.getSchedule());
        	task.setRepeat(taskDTO.getRepeat());
        	task.setRepeatstart(taskDTO.getRepeatstart());
        	task.setRepeatend(taskDTO.getRepeatend());
        }
        return tasksRepository.save(task);
    }
}
