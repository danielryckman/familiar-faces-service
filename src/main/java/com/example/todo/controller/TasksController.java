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
        return ResponseEntity.ok(events.getContent());
    }

    @RequestMapping(value = TaskLinks.QUERY_TASK, method=RequestMethod.GET)
    public ResponseEntity<?> queryTasks(@RequestParam(value = "begin", required = true) String begin,@RequestParam(value = "end", required = true) String end, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        long beginTime = Long.parseLong(begin);
        long endTime = Long.parseLong(end);
    	log.info("TasksController query from: " + beginTime + " to " + endTime);
        Page<Task> events = taskService.queryTasks(beginTime, endTime, pageable);
        return ResponseEntity.ok(events.getContent());
    }
    
    @GetMapping(path = TaskLinks.TASK)
    public ResponseEntity<?> getTask(@PathVariable("id") Long taskId, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
            log.info("TasksController::: " + taskId);
            Task task = taskService.getTask(taskId);
            return ResponseEntity.ok(task);
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Resource Not Found", exc);
        }
    }

    @PostMapping(path = TaskLinks.CREATE_TASK_USER)
    public ResponseEntity<?> createTaskForUser(@RequestBody TaskDTO taskDTO, @RequestParam(value = "userid", required = true) Long userid,Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: " + taskDTO);
        Task events = taskService.saveTask(userid, taskDTO);
        return ResponseEntity.ok(events);
    }

    @PostMapping(path = TaskLinks.CREATE_TASK)
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: " + taskDTO);
        Task events = taskService.saveTask(taskDTO);
        return ResponseEntity.ok(events);
    }

    @RequestMapping(value = TaskLinks.TASK, method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteTask(@PathVariable("id") Long taskId, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
            log.info("TasksController::: " + taskId);
            taskService.deleteTask(taskId);
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
    
    @PutMapping(path = TaskLinks.TASK)
    public ResponseEntity<?> updateTask(@RequestBody TaskDTO taskDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: updating " + taskDTO);
        Task events = taskService.updateTask(taskDTO, pageable);
        return ResponseEntity.ok(events);
    }
   /**-------------------------- tests ----------------**/
    @GetMapping(path = TaskLinks.TESTS)
    public ResponseEntity<?> getTests(TestDTO testDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TestsController: " + testDTO);
        Page<Test> events = testService.getTests(pageable);
        return ResponseEntity.ok(events.getContent());
    }
    
    @PostMapping(path = TaskLinks.TEST)
    public ResponseEntity<?> createTest(Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        Test events = testService.createTest(pageable);
        return ResponseEntity.ok(events);
    }
    
    @PostMapping(path = TaskLinks.TEST_USER)
    public ResponseEntity<?> createTestForUser(@RequestBody TestDTO testDTO, @RequestParam(value = "userid", required = true) Long userid, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: " + testDTO);
        Test events = testService.saveTest(testDTO, userid, pageable);
        return ResponseEntity.ok(events);
    }
    
    @PutMapping(path = TaskLinks.TEST)
    public ResponseEntity<?> updateTest(@RequestBody TestDTO testDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: updating " + testDTO);
        Test events = testService.updateTest(testDTO, pageable);
        return ResponseEntity.ok(events);
    }

    /**-------------------------- users ----------------**/
    @GetMapping(path = TaskLinks.USERS)
    public ResponseEntity<?> getUsers(UserDTO userDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: " + userDTO);
        Page<User> events = userService.getUsers(pageable);
        return ResponseEntity.ok(events.getContent());
    }
    
    @PostMapping(path = TaskLinks.USER)
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: " + userDTO);
        User events = userService.saveUser(userDTO, pageable);
        return ResponseEntity.ok(events);
    }
    
    @PutMapping(path = TaskLinks.USER)
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: updating " + userDTO);
        User events = userService.updateUser(userDTO, pageable);
        return ResponseEntity.ok(events);
    }
    
    /**-------------------------- family members ----------------**/
    @GetMapping(path = TaskLinks.FAMILYMEMBERS)
    public ResponseEntity<?> getFamilymembers(FamilymemberDTO familymemberDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: " + familymemberDTO);
        Page<Familymember> events = familymemberService.getFamilymembers(pageable);
        return ResponseEntity.ok(events.getContent());
    }
    
    @PostMapping(path = TaskLinks.FAMILYMEMBER_USER)
    public ResponseEntity<?> createFamilymemberForUser(@RequestBody FamilymemberDTO familymemberDTO, @RequestParam(value = "userid", required = true) Long userid, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: " + familymemberDTO);
        Familymember events = familymemberService.saveFamilymember(familymemberDTO, userid, pageable);
        return ResponseEntity.ok(events);
    }

    /**-------------------------- photos ----------------**/
    @GetMapping(path = TaskLinks.PHOTOS)
    public ResponseEntity<?> getPhotos(PhotoDTO photoDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: " + photoDTO);
        Page<Photo> events = photoService.getPhotos(pageable);
        return ResponseEntity.ok(events.getContent());
    }
    
    @PostMapping(path = TaskLinks.PHOTO)
    public ResponseEntity<?> createPhoto(@RequestBody PhotoDTO photoDTO, @RequestParam(value = "userid", required = true) Long userid, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: " + photoDTO);
        Photo events = photoService.savePhoto(photoDTO, userid, pageable);
        return ResponseEntity.ok(events);
    }
    
    @GetMapping(path = TaskLinks.PHOTO_RANGE)
    public ResponseEntity<?> getPhotosRange(@RequestParam(value = "begin", required = true) String begin,@RequestParam(value = "end", required = true) String end,@RequestParam(value = "userid", required = true) Long userid, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
            long beginTime = Long.parseLong(begin);
            long endTime = Long.parseLong(end);
        	log.info("TasksController query from: " + beginTime + " to " + endTime);
            Page<Photo> events = photoService.getPhotoRange(beginTime, endTime, userid, pageable);
            return ResponseEntity.ok(events.getContent());
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Resource Not Found", exc);
        }
    }

}
