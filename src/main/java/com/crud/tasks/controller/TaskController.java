package com.crud.tasks.controller;

import com.crud.tasks.domain.dto.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class TaskController {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private DbService dbService;

    @RequestMapping(method = RequestMethod.GET, value = "/tasks")
    public List<TaskDto> getTasks() {return taskMapper.mapToTaskDtoList(dbService.getAllTasks());}

    @RequestMapping(method = RequestMethod.DELETE, value = "/tasks/{taskId}")
    public void deleteTask(@PathVariable Long taskId) throws TaskNotFoundException {
        dbService.deleteTask(dbService.getTaskById(taskId).orElseThrow(TaskNotFoundException::new)); }

    @RequestMapping(method = RequestMethod.PUT, value = "/tasks")
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {return  taskMapper.mapToTaskDto(
            dbService.saveTask(taskMapper.mapToTask(taskDto)));}

    @RequestMapping(method = RequestMethod.POST, value = "/tasks", consumes = APPLICATION_JSON_VALUE)
    public void createTask(@RequestBody TaskDto taskDto) { dbService.saveTask(taskMapper.mapToTask(taskDto));}

    @RequestMapping(method = RequestMethod.GET, value = "/tasks/{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) throws TaskNotFoundException {
        return taskMapper.mapToTaskDto(dbService.getTaskById(taskId).orElseThrow(TaskNotFoundException::new));}

}