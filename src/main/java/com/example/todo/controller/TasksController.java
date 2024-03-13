package com.example.todo.controller;

import com.example.todo.dto.TaskDTO;
import com.example.todo.dto.TestDTO;
import com.example.todo.dto.AuthTokenDTO;
import com.example.todo.dto.UserDTO;
import com.example.todo.dto.FamilymemberDTO;
import com.example.todo.dto.PhotoDTO;
import com.example.todo.dto.RecordDTO;
import com.example.todo.entity.Task;
import com.example.todo.entity.Test;
import com.example.todo.entity.User;
import com.example.todo.entity.Record;
import com.example.todo.entity.Familymember;
import com.example.todo.entity.Photo;
import com.example.todo.links.TaskLinks;
import com.example.todo.service.TaskService;
import com.example.todo.service.TestService;
import com.example.todo.service.UserService;
import com.example.todo.service.FamilymemberService;
import com.example.todo.service.PhotoService;
import com.example.todo.service.RecordService;
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
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.MediaType;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import java.util.UUID;
import java.io.IOException;
import java.util.Random;


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
    private final RecordService recordService;
    private Random random = new Random();

    @GetMapping(path = TaskLinks.TASKS)
    public ResponseEntity<?> getTasks(TaskDTO taskDTO, @RequestParam(value = "userid", required = true) long userId, @RequestHeader(value = "authtoken", required = true) String auth_token, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
            log.info("TasksController:::getTasks " + taskDTO);
            Boolean authorize_check = taskService.authorizeToken(auth_token, userId);
            if (authorize_check == false){
                log.info("Unauthorized Tasks Get Request");
                throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Authorization Token");
            } else{
                log.info("Get Tasks successful");
                Page<Task> events = taskService.getTasks(pageable);
                PagedResources<?> resource = pagedResourcesAssembler.toResource(events, resourceAssembler);
                return ResponseEntity.ok(events.getContent());
            }
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Resource Not Found", exc);
        }
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
    public ResponseEntity<?> getTask(@PathVariable("id") Long taskId, @RequestParam(value = "userid", required = true) long userId, @RequestHeader(value = "authtoken", required = true) String auth_token, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
         try {
            log.info("TasksController:::getTask " + taskId);
            Boolean authorize_check = taskService.authorizeToken(auth_token, userId);
            if (authorize_check == false){
                log.info("Unauthorized Task Get Request");
                throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Authorization Token");
            } 
            else{
                try {
                    log.info("TasksController:::getTask successful");
                    Task task = taskService.getTask(taskId);
                    return ResponseEntity.ok(task);
                }catch (RuntimeException exc) {
                    throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Resource Not Found", exc);
                }
            }
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Resource Not Found", exc);
        }
    }

    @PostMapping(path = TaskLinks.CREATE_TASK_USER)
    public ResponseEntity<?> createTaskForUser(@RequestBody TaskDTO taskDTO, @RequestParam(value = "userid", required = true) Long userid, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
    	try{
	    log.info("TasksController: " + taskDTO);
	    Task events = taskService.saveTask(userid, taskDTO);
	    return ResponseEntity.ok(events);
	}catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error creating Task: " +taskDTO, exc);
        }
    }

    @PostMapping(path = TaskLinks.CREATE_TASK)
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO, @RequestParam(value = "userid", required = true) long userId, @RequestHeader(value = "authtoken", required = true) String auth_token, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
            log.info("TasksController:::createTask " + taskDTO);
            Boolean authorize_check = taskService.authorizeToken(auth_token, userId);
            if (authorize_check == false){
                log.info("Unauthorized Task Get Request");
                throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Authorization Token");
            } 
            else{
                    log.info("TasksController:::CreateTask: " + taskDTO);
                    Task events = taskService.saveTask(taskDTO);
                    return ResponseEntity.ok(events);
                }
            }
        catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error creating task: " + taskDTO, exc);
        }
    }

    @RequestMapping(value = TaskLinks.CREATE_TASK, method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteTask(@RequestParam(value = "taskid", required = true) long taskId, @RequestParam(value = "userid", required = true) long userId, @RequestHeader(value = "authtoken", required = true) String auth_token, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
            log.info("TasksController:::delete " + taskId);
            Boolean authorize_check = taskService.authorizeToken(auth_token, userId);
            log.info("authorize_check" + authorize_check);
            if (authorize_check == false){
                log.info("Invalid Authorization Token");
                throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Authorization Token");
            } else{
                log.info("Delete Successful");
                taskService.deleteTask(taskId);
                return ResponseEntity.ok(null);
            }
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Resource Not Found", exc);
        }
    }
    
    @RequestMapping(value = TaskLinks.DELETE_RANGE, method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteTask(@RequestParam(value = "begin", required = true) String begin, @RequestParam(value = "end", required = true) String end, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
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
    
    @PutMapping(path = TaskLinks.CREATE_TASK)
    public ResponseEntity<?> updateTask(@RequestBody TaskDTO taskDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try{
            log.info("TasksController: updating " + taskDTO);
            Task events = taskService.updateTask(taskDTO, pageable);
            return ResponseEntity.ok(events);
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error updating Task: " +taskDTO, exc);
        }    
    }
   /**-------------------------- tests ----------------**/
    @GetMapping(path = TaskLinks.TESTS)
    public ResponseEntity<?> getTests(TestDTO testDTO, @RequestParam(value = "userid", required = true) long userId, @RequestHeader(value = "authtoken", required = true) String auth_token, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
            log.info("TasksController:::GetTests " + testDTO);
            Boolean authorize_check = taskService.authorizeToken(auth_token, userId);
            if (authorize_check == false){
                log.info("Invalid Authorization Token");
                throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Authorization Token");
            } else{
                log.info("TestsController: getTests" + testDTO);
                Page<Test> events = testService.getTests(pageable);
                return ResponseEntity.ok(events.getContent());
            }
        } catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error get Tests: " +testDTO, exc);
        }
    }
    
    @PostMapping(path = TaskLinks.TEST)
    public ResponseEntity<?> createTest(Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        Test events = testService.createTest(pageable);
        return ResponseEntity.ok(events);
    }
    
    @PostMapping(path = TaskLinks.TEST_USER)
    public ResponseEntity<?> createTestForUser(@RequestBody TestDTO testDTO, @RequestParam(value = "userid", required = true) Long userid, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: creating new test for user " + userid +", "+ testDTO);
        Test test = testService.saveTest(testDTO, userid, pageable);
        System.out.println("save test " + test.getName() + " with id = " + test.getId());
        int index = random.nextInt(3);
        Test events = testService.addQuestions(test.getId(),index);
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
    public ResponseEntity<?> getUsers(UserDTO userDTO, @RequestParam(value = "userid", required = true) long userId, @RequestHeader(value = "authtoken", required = true) String auth_token, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
            log.info("TasksController:::GetUsers " + userDTO);
            Boolean authorize_check = taskService.authorizeToken(auth_token, userId);
            if (authorize_check == false){
                log.info("Invalid Authorization Token");
                throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Authorization Token");
            } else{
                log.info("TasksController:::GetUsers Successful " + userDTO);
                Page<User> events = userService.getUsers(pageable);
                return ResponseEntity.ok(events.getContent());
            }
        } catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error getUsers: " + userDTO, exc);
        }
    }
    
    @PostMapping(path = TaskLinks.USER)
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
            log.info("TasksController:::CreateUser " + userDTO);
            User events = userService.saveUser(userDTO, pageable);
            return ResponseEntity.ok(events);
            
        } catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error createUser: " + userDTO, exc);
        }

    }
    
    @PutMapping(path = TaskLinks.USER)
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        log.info("TasksController: updating " + userDTO);
        User events = userService.updateUser(userDTO, pageable);
        return ResponseEntity.ok(events);
    }
    
    @GetMapping(path = TaskLinks.USER)
    public ResponseEntity<?> getUserByEmail(@RequestParam(value = "email", required = true) String email, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
            log.info("TasksController:::getUserbyEmail " + email);
            UserDTO events = userService.getUserByEmail(email, pageable);
            return ResponseEntity.ok(events);
            }
        catch (RuntimeException exc) {
            log.info("TasksController::exception " + exc.getMessage());
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error getUserByEmail: " + email, exc);
            }
        }
    
    /**-------------------------- family members ----------------**/
    @GetMapping(path = TaskLinks.FAMILYMEMBERS)
    public ResponseEntity<?> getFamilymembers(FamilymemberDTO familymemberDTO, @RequestParam(value = "userid", required = true) long userId, @RequestHeader(value = "authtoken", required = true) String auth_token, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
            log.info("TasksController:::getFamilymembers " + familymemberDTO);
            Boolean authorize_check = taskService.authorizeToken(auth_token, userId);
            if (authorize_check == false){
                log.info("Invalid Authorization Token");
                throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Authorization Token");
            } else{
                log.info("TasksController: get family members by " + familymemberDTO);
                Page<Familymember> events = familymemberService.getFamilymembers(pageable);
                return ResponseEntity.ok(events.getContent());
            }
        } catch (RuntimeException exc) {
            log.info("TasksController::exception " + exc.getMessage());
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error getFamilymembers: " + familymemberDTO, exc);
        }    
    }
    
    @PostMapping(path = TaskLinks.FAMILYMEMBER_USER)
    public ResponseEntity<?> createFamilymemberForUser(@RequestBody FamilymemberDTO familymemberDTO, @RequestParam(value = "userid", required = true) Long userid, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try{
            log.info("TasksController: " + familymemberDTO);
            Familymember events = familymemberService.saveFamilymember(familymemberDTO, userid, pageable);
            return ResponseEntity.ok(events);
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error creating Familymember " + "for userid =" +userid, exc);
        }
    }

    @GetMapping(path = TaskLinks.FAMILYMEMBER)
    public ResponseEntity<?> getFamilymemberByEmail(@RequestParam(value = "email", required = true) String email, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
            log.info("TasksController:::getFamilymemberByEmail " + email);
            FamilymemberDTO events = familymemberService.getFamilymemberByEmail(email, pageable); 
            return ResponseEntity.ok(events);
            }
        catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error getting Familymember by email: " + email, exc);
        }
    }
    /**-------------------------- photos ----------------**/
    @GetMapping(path = TaskLinks.PHOTOS)
    public ResponseEntity<?> getPhotos(PhotoDTO photoDTO, @RequestParam(value = "userid", required = true) long userId, @RequestHeader(value = "authtoken", required = true) String auth_token, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
            log.info("TasksController:::getPhotos " + photoDTO);
            Boolean authorize_check = taskService.authorizeToken(auth_token, userId);
            if (authorize_check == false){
                log.info("Invalid Authorization Token");
                throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Authorization Token");
            } else{
                log.info("TasksController: get photos by " + photoDTO);
                Page<Photo> events = photoService.getPhotos(pageable);
                return ResponseEntity.ok(events.getContent());
            }
        } catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error getPhotos: " + photoDTO, exc);
        }    
    }
    
    @PostMapping(path = TaskLinks.PHOTO)
    public ResponseEntity<?> createPhoto(@RequestBody PhotoDTO photoDTO, @RequestParam(value = "userid", required = true) Long userId, @RequestHeader(value = "authtoken", required = true) String auth_token, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
            log.info("TasksController:::createPhoto " + photoDTO);
            Boolean authorize_check = taskService.authorizeToken(auth_token, userId);
            if (authorize_check == false){
                log.info("Invalid Authorization Token");
                throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Authorization Token");
            } else{
                log.info("TasksController: create photo by " + photoDTO);
                Photo events = photoService.savePhoto(photoDTO, userId, pageable);
                return ResponseEntity.ok(events);
            }
        } catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error creating Photo: " + photoDTO.getName(), exc);
        }
    }
    
    @GetMapping(path = TaskLinks.PHOTO_RANGE)
    public ResponseEntity<?> getPhotosRange(@RequestParam(value = "begin", required = true) String begin, @RequestParam(value = "end", required = true) String end,@RequestParam(value = "userid", required = true) Long userid, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
            long beginTime = Long.parseLong(begin);
            long endTime = Long.parseLong(end);
        	log.info("TasksController query from: " + beginTime + " to " + endTime);
            List<Photo> events = photoService.getPhotoRange(beginTime, endTime, userid);
            return ResponseEntity.ok(events);
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Error getting photo.", exc);
        }
    }
    
    @PutMapping(path = TaskLinks.PHOTO)
    public ResponseEntity<?> updatePhoto(@RequestBody PhotoDTO photoDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try{
            log.info("TasksController: updating " + photoDTO);
            Photo events = photoService.updatePhoto(photoDTO, pageable);
            return ResponseEntity.ok(events);
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error updating Photo: " + photoDTO.getName(), exc);
        }
    }
    
    @RequestMapping(value = TaskLinks.PHOTO, method=RequestMethod.DELETE)
    public ResponseEntity<?> deletePhoto(@RequestParam(value = "name", required = true) String name,Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
            log.info("TasksController delete photo: " + name);
            photoService.deletePhoto(name, pageable);
            return ResponseEntity.ok(null);
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Resource Not Found", exc);
        }
    }
    
    //@PostMapping(path = TaskLinks.PHOTO_UPLOAD)
    //@RequestMapping(path = TaskLinks.PHOTO_UPLOAD, method = RequestMethod.POST, consumes = {MediaType.IMAGE_JPEG_VALUE,MediaType.APPLICATION_JSON_VALUE})
    @RequestMapping(path = TaskLinks.PHOTO_UPLOAD, method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadPhoto(@RequestPart(value = "image", required = true) MultipartFile image,@RequestPart(value = "photo", required = true) PhotoDTO photo, @RequestParam(value = "userid", required = true) Long userid,@RequestParam(value = "album", required = true) String album, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
    	try {
            log.info("TasksController upload photo: " + photo.getName());
            photoService.uploadPhoto(photo, image, userid, album);
            return ResponseEntity.ok(null);
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }catch (IOException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Error uploading photo:" + photo.getName(), ex);
            }
    }

    @GetMapping(path = TaskLinks.PHOTO_UPLOAD)
    public ResponseEntity<?> getUploadedPhoto(@RequestParam(value = "userid", required = true) Long userid,@RequestParam(value = "album", required = true) String album, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
        	log.info("TasksController query all photo in: " + userid+"/" + album);
            Page<Photo> events = photoService.getPhotoFromAlbum(album, userid, pageable);
            return ResponseEntity.ok(events.getContent());
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Resource Not Found", exc);
        }
    }
    
    @RequestMapping(value = TaskLinks.PHOTO_UPLOAD, method=RequestMethod.DELETE)
    public ResponseEntity<?> deleteUploadedPhoto(@RequestParam(value = "userid", required = true) Long userid,@RequestParam(value = "album", required = true) String album, @RequestParam(value = "photoname", required = true) String photoname, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
        	log.info("TasksController delete photo " + photoname + " from album " + album);
            photoService.deletePhotoFromAlbum(album, userid, photoname, pageable);
            return ResponseEntity.ok(null);
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Resource Not Found", exc);
        }
    }
    
    @PostMapping(path = TaskLinks.ALBUM_CREATE)
    public ResponseEntity<?> createAlbum(@RequestParam(value = "userid", required = true) Long userid, @RequestParam(value = "album", required = true) String album, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try{
            log.info("TasksController: create album " + album);
            photoService.createAlbum(userid, album, pageable);
            return ResponseEntity.ok(null);
        }catch (RuntimeException exc) {
            log.info("Error creating Album: " +album, exc);
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);        
            //throw new ResponseStatusException(
            //    HttpStatus.BAD_REQUEST, "Error creating Album: " +album, exc);
        }
    }
    
    @GetMapping(path = TaskLinks.ALBUMS)
    public ResponseEntity<?> getAllAlbums(@RequestParam(value = "userid", required = true) Long userid, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try {
        	log.info("TasksController query all albums for user: " + userid);
            Page<String> events = photoService.getAlbums(userid, pageable);
            return ResponseEntity.ok(events.getContent());
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Resource Not Found", exc);
        }
    }
    
    /**-------------------------- records ----------------**/
    @GetMapping(path = TaskLinks.RECORDS)
    public ResponseEntity<?> getRecords(RecordDTO recordDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try{
            log.info("TasksController: " + recordDTO);
            Page<Record> events = recordService.getRecords(pageable);
            return ResponseEntity.ok(events.getContent());
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Error getting Records. ", exc);
        }
    }
    
    
    @GetMapping(path = TaskLinks.RECORD_RANGE)
    public ResponseEntity<?> getRecords(@RequestParam(value = "begin", required = true) Long begin,@RequestParam(value = "end", required = true) Long end, @RequestParam(value = "userid", required = true) Long userid,Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try{
            Page<Record> events = recordService.getRecordRange(begin, end, userid, pageable);
            return ResponseEntity.ok(events.getContent());
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Error getting Records for userid = " + userid, exc);
        }
    }
    
    @PostMapping(path = TaskLinks.RECORD)
    public ResponseEntity<?> createRecord(@RequestBody RecordDTO recordDTO, @RequestParam(value = "userid", required = true) Long userid, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try{
            log.info("TasksController: " + recordDTO);
            Record events = recordService.saveRecord(recordDTO, userid, pageable);
            return ResponseEntity.ok(events);
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error creating Record: ", exc);
        }
    }
    
    @PutMapping(path = TaskLinks.RECORD)
    public ResponseEntity<?> updateRecord(@RequestBody RecordDTO recordDTO, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
    	try{
            log.info("TasksController: updating " + recordDTO);
            Record events = recordService.updateRecord(recordDTO, pageable);
            return ResponseEntity.ok(events);
        }catch (RuntimeException exc) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error updating Record: ", exc);
        }
    }
    
    @PostMapping(path = TaskLinks.AUTH)
    public ResponseEntity<?> login(@RequestParam(value = "userinfo", required = true) String userinfo, Pageable pageable, PersistentEntityResourceAssembler resourceAssembler) {
        try{      
            log.info("TasksController: creating auth token for " + userinfo);
            Boolean authorize_check = userService.authorize(userinfo);
            if (authorize_check == false){
                log.info("Unauthorized token creation request");
                throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unauthorized token creation request");
            } else{
                AuthTokenDTO token = userService.createToken(userinfo);
                log.info("command successful");
                return ResponseEntity.ok(token);
            }
        }catch (RuntimeException exc) {
            log.info("command failed" + exc.getMessage());
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Error parsing login information: ", exc);
        }
    }
    
}

