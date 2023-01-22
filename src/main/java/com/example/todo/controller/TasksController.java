package com.example.todo.controller;

import com.example.todo.dto.TaskDTO;
import com.example.todo.dto.TestDTO;
import com.example.todo.dto.UserDTO;
import com.example.todo.dto.FamilymemberDTO;
import com.example.todo.dto.PhotoDTO;
import com.example.todo.entity.Task;
import com.example.todo.entity.Test;
import com.example.todo.entity.User;
import com.example.todo.entity.Familymember;
import com.example.todo.entity.Photo;
import com.example.todo.links.TaskLinks;
import com.example.todo.service.TaskService;
import com.example.todo.service.TestService;
import com.example.todo.service.UserService;
import com.example.todo.service.FamilymemberService;
import com.example.todo.service.PhotoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import java.util.UUID;


@Slf4j
@RepositoryRestController
@RequestMapping("/todo/")
@RequiredArgsConstructor
public class TasksController {

    private final PagedResourcesAssembler pagedResourcesAssembler;
    private final TaskService taskService;
    private final TestService testService;
    private final UserService userService;
    private final FamilymemberService familymemberService;
    private final PhotoService photoService;

    @GetMapping(path = TaskLinks.TASKS)
    public ResponseEntity<?> getTasks(TaskDTO taskDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: " + taskDTO);
        Page<Task> events = taskService.getTasks(pageable);
        PagedResources<?> resource = pagedResourcesAssembler.toResource(events, resourceAssembler);
        return ResponseEntity.ok(resource);
    }

    @RequestMapping(value = TaskLinks.QUERY_TASK, method=RequestMethod.GET)
    public @ResponseBody List<Task> queryTasks(@RequestParam(value = "begin", required = true) String begin,@RequestParam(value = "end", required = true) String end, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        long beginTime = Long.parseLong(begin);
        long endTime = Long.parseLong(end);
    	log.info("TasksController query from: " + beginTime + " to " + endTime);
        List<Task> events = taskService.queryTasks(beginTime, endTime, pageable);
        //PagedResources<?> resource = pagedResourcesAssembler.toResource(events, resourceAssembler);
        return events;
    }
    
    @GetMapping(path = TaskLinks.TASK)
    public ResponseEntity<?> getTask(@PathVariable("id") Long taskId, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
            log.info("TasksController::: " + taskId);
            Task task = taskService.getTask(taskId);
            Link selfLink = linkTo(methodOn(TasksController.class).getTask(taskId, pageable, resourceAssembler)).withSelfRel();
            Link allTasksLink = linkTo(TasksController.class).slash("/tasks").withRel("all tasks");

            Resource<Task> taskResource = new Resource<>(task);
            taskResource.add(selfLink, allTasksLink);
            return ResponseEntity.ok(taskResource);
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Resource Not Found", exc);
        }
    }

    @PostMapping(path = TaskLinks.CREATE_TASK_USER)
    public ResponseEntity<?> createTaskForUser(@RequestBody TaskDTO taskDTO, @RequestParam(value = "userid", required = true) Long userid,Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: " + taskDTO);
        Task events = taskService.saveTask(userid, taskDTO);
        if(events !=null){
        Resource<Task> taskResource = new Resource<>(events);
        HttpHeaders responseHeaders = new HttpHeaders();
            return new ResponseEntity<Resource<Task>>(taskResource, responseHeaders, HttpStatus.CREATED);
        }else{
        	return ResponseEntity.ok(null);
        }
    }

    @PostMapping(path = TaskLinks.CREATE_TASK)
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: " + taskDTO);
        Task events = taskService.saveTask(taskDTO);

        Resource<Task> taskResource = new Resource<>(events);
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<Resource<Task>>(taskResource, responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = TaskLinks.TASK, method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteTask(@PathVariable("id") Long taskId, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
            log.info("TasksController::: " + taskId);
            taskService.deleteTask(taskId);
            Link selfLink = linkTo(methodOn(TasksController.class).getTask(taskId, pageable, resourceAssembler)).withSelfRel();
            Link allTasksLink = linkTo(TasksController.class).slash("/tasks").withRel("all tasks");
            return ResponseEntity.ok(null);
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Resource Not Found", exc);
        }
    }
    
    @RequestMapping(value = TaskLinks.DELETE_RANGE, method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteTask(@RequestParam(value = "begin", required = true) String begin,@RequestParam(value = "end", required = true) String end, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
            long beginTime = Long.parseLong(begin);
            long endTime = Long.parseLong(end);
        	log.info("TasksController query from: " + beginTime + " to " + endTime);
            taskService.deleteTaskRange(beginTime, endTime, pageable);
            return ResponseEntity.ok(null);
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Resource Not Found", exc);
        }
    }
   /**-------------------------- tests ----------------**/
    @GetMapping(path = TaskLinks.TESTS)
    public ResponseEntity<?> getTests(TestDTO testDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TestsController: " + testDTO);
        Page<Test> events = testService.getTests(pageable);
        PagedResources<?> resource = pagedResourcesAssembler.toResource(events, resourceAssembler);
        return ResponseEntity.ok(resource);
    }
    
    @GetMapping(path = TaskLinks.TEST)
    public ResponseEntity<?> createTest(Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        Test events = testService.createTest(pageable);
        Resource<Test> testResource = new Resource<>(events);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyResponseHeader", "MyValue");
        return new ResponseEntity<Resource<Test>>(testResource, responseHeaders, HttpStatus.CREATED);
    }
    
    @PostMapping(path = TaskLinks.TEST)
    public ResponseEntity<?> createTest(@RequestBody TestDTO testDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: " + testDTO);
        Test events = testService.saveTest(testDTO, pageable);

        //Link selfLink = linkTo(methodOn(TasksController.class).createTest(testDTO, pageable, resourceAssembler)).withSelfRel();
        //Link allTestsLink = linkTo(TasksController.class).slash("/tests").withRel("all tests");

        Resource<Test> testResource = new Resource<>(events);
        //testResource.add(selfLink,  allTestsLink);

        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.set("MyResponseHeader", "MyValue");
        return new ResponseEntity<Resource<Test>>(testResource, responseHeaders, HttpStatus.CREATED);
    }

    /**-------------------------- users ----------------**/
    @GetMapping(path = TaskLinks.USERS)
    public ResponseEntity<?> getUsers(UserDTO userDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: " + userDTO);
        Page<User> events = userService.getUsers(pageable);
        PagedResources<?> resource = pagedResourcesAssembler.toResource(events, resourceAssembler);
        return ResponseEntity.ok(resource);
    }
    
    @PostMapping(path = TaskLinks.USER)
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: " + userDTO);
        User events = userService.saveUser(userDTO, pageable);

        //Link selfLink = linkTo(methodOn(TasksController.class).createUser(userDTO, pageable, resourceAssembler)).withSelfRel();
        //Link allTestsLink = linkTo(TasksController.class).slash("/users").withRel("all users");

        Resource<User> userResource = new Resource<>(events);
        //userResource.add(selfLink,  allTestsLink);

        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.set("MyResponseHeader", "MyValue");
        return new ResponseEntity<Resource<User>>(userResource, responseHeaders, HttpStatus.CREATED);
    }
    
    /**-------------------------- family members ----------------**/
    @GetMapping(path = TaskLinks.FAMILYMEMBERS)
    public ResponseEntity<?> getFamilymembers(FamilymemberDTO familymemberDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: " + familymemberDTO);
        Page<Familymember> events = familymemberService.getFamilymembers(pageable);
        PagedResources<?> resource = pagedResourcesAssembler.toResource(events, resourceAssembler);
        return ResponseEntity.ok(resource);
    }
    
    @PostMapping(path = TaskLinks.FAMILYMEMBER)
    public ResponseEntity<?> createFamilymember(@RequestBody FamilymemberDTO familymemberDTO, @RequestParam(value = "userid", required = true) Long userid, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: " + familymemberDTO);
        Familymember events = familymemberService.saveFamilymember(familymemberDTO, userid, pageable);
        if(events !=null){
        Resource<Familymember> familymemberResource = new Resource<>(events);
        HttpHeaders responseHeaders = new HttpHeaders();
            return new ResponseEntity<Resource<Familymember>>(familymemberResource, responseHeaders, HttpStatus.CREATED);
        }else{
        	return ResponseEntity.ok(null);
        }
    }

    /**-------------------------- photos ----------------**/
    @GetMapping(path = TaskLinks.PHOTOS)
    public ResponseEntity<?> getPhotos(PhotoDTO photoDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: " + photoDTO);
        Page<Photo> events = photoService.getPhotos(pageable);
        PagedResources<?> resource = pagedResourcesAssembler.toResource(events, resourceAssembler);
        return ResponseEntity.ok(resource);
    }
    
    @PostMapping(path = TaskLinks.PHOTO)
    public ResponseEntity<?> createPhoto(@RequestBody PhotoDTO photoDTO, @RequestParam(value = "userid", required = true) Long userid, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: " + photoDTO);
        Photo events = photoService.savePhoto(photoDTO, userid, pageable);
        if(events !=null){
        Resource<Photo> photoResource = new Resource<>(events);
        HttpHeaders responseHeaders = new HttpHeaders();
            return new ResponseEntity<Resource<Photo>>(photoResource, responseHeaders, HttpStatus.CREATED);
        }else{
        	return ResponseEntity.ok(null);
        }
    }
    
    @GetMapping(path = TaskLinks.PHOTO_RANGE)
    public ResponseEntity<?> getPhotosRange(@RequestParam(value = "begin", required = true) String begin,@RequestParam(value = "end", required = true) String end,@RequestParam(value = "userid", required = true) Long userid, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
            long beginTime = Long.parseLong(begin);
            long endTime = Long.parseLong(end);
        	log.info("TasksController query from: " + beginTime + " to " + endTime);
            Page<Photo> events = photoService.getPhotoRange(beginTime, endTime, userid, pageable);
            PagedResources<?> resource = pagedResourcesAssembler.toResource(events, resourceAssembler);
            return ResponseEntity.ok(resource);
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Resource Not Found", exc);
        }
    }

}
