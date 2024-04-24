package net.java.EMSbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import net.java.EMSbackend.model.Todo;
import net.java.EMSbackend.model.TodoStatus;
import net.java.EMSbackend.repository.TodoRepository;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public TodoRepository getTodoRepository() {
        return todoRepository;
    }

    public void setTodoRepository(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo addTask(Todo todo) {
        Todo td = todoRepository.save(todo);
        return td;
    }

    public List<Todo> getTaskByEmail(String email) {
        List<Todo> td = todoRepository.findByEmail(email);
        return td;
    }

    public Todo getTaskById(Long id) {
        Todo td = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        ;
        return td;
    }

    public Todo updateTaskStatus(Long id, TodoStatus status) {
        Todo todo = todoRepository.findById(id).orElse(null);
        if (todo != null) {
            todo.setStatus(status);
            return todoRepository.save(todo);
        }
        return null;
    }

    public void deleteTask(Long id) {
        todoRepository.deleteById(id);
    }

    public Todo updateTaskDescription(Long id, String newDescription) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo with id " + id + " not found"));
        todo.setDescription(newDescription);
        return todoRepository.save(todo);
    }
}
