package net.java.EMSbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.java.EMSbackend.model.Todo;
import net.java.EMSbackend.model.TodoStatus;
import net.java.EMSbackend.service.TodoService;

@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    private TodoService todoService;

    public TodoService getTodoService() {
        return todoService;
    }

    public void setTodoService(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/getTask/{email}")
    public List<Todo> getTodosByEmail(@PathVariable String email) {
        return todoService.getTaskByEmail(email);
    }

    @PostMapping("/addTask")
    public Todo addTodo(@RequestBody Todo todo) {
        return todoService.addTask(todo);
    }

    @PutMapping("/changeStatus/{taskId}/status/{status}")
    public Todo updateTaskStatus(@PathVariable("taskId") Long id, @PathVariable String status) {
        TodoStatus todoStatus = null;
        if ("inProgress".equalsIgnoreCase(status)) {
            todoStatus = TodoStatus.IN_PROGRESS;
        } else {
            todoStatus = TodoStatus.valueOf(status.toUpperCase());
        }
        return todoService.updateTaskStatus(id, todoStatus);
    }

    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        todoService.deleteTask(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/updateTask/{id}")
    public Todo updateTaskDescription(@PathVariable Long id, @RequestBody String newDescription) {
        return todoService.updateTaskDescription(id, newDescription);
    }
}
