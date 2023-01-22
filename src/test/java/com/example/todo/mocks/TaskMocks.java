package com.example.todo.mocks;

import com.example.todo.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

public class TaskMocks {

    public static Page<Task> createTasks() {

        List<Task> taskList = new ArrayList<>();
        Task task1 = new Task();
        task1.setName("name1");
        task1.setDescription("description1");
        task1.setUrl("http://testurl1.com");
        task1.setSchedule(1673316940);
        task1.setRepeat(0);
        
        Task task2 = new Task();
        task2.setName("name2");
        task2.setDescription("description2");
        task2.setUrl("http://testurl2.com");
        task2.setSchedule(1673316941);
        task2.setRepeat(1);
        
        taskList.add(task1);
        taskList.add(task2);
        Page<Task> pagedResponse = new PageImpl(taskList);
        return pagedResponse;
    }

}
