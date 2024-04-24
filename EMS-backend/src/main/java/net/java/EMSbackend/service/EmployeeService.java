package net.java.EMSbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.Optional;
import net.java.EMSbackend.model.Employee;
import net.java.EMSbackend.repository.EmployeeRepository;
import net.java.EMSbackend.repository.TodoRepository;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    @Autowired
    private TodoRepository todoRepository;

    public TodoRepository getTodoRepository() {
        return todoRepository;
    }

    public void setTodoRepository(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Page<Employee> getAllEmployeesPage(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return employeeRepository.findAll(pageRequest);
    }

    @Transactional
    public Page<Employee> getSearchedEmployee(String keyword, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return employeeRepository.getSearchedEmployee(keyword.toLowerCase(), pageRequest);
    }

    @Transactional
    public Employee getEmpByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    // @Transactional
    // public Employee getEmpById(int id) {
    // return employeeRepository.findById(id);
    // }

    public Employee getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.orElse(null);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee existingEmployee = getEmployeeById(id);

        if (existingEmployee != null) {

            if (existingEmployee.getEmail() != employeeDetails.getEmail()) {
                if (todoRepository.findByEmail(existingEmployee.getEmail()) != null) {
                    todoRepository.updateEmail(existingEmployee.getEmail(), employeeDetails.getEmail());
                }
            }
            return employeeRepository.save(employeeDetails);
        }

        return null;
    }

    public boolean deleteEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            employeeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    @Modifying
    public Employee addSalary(String email, Float salary) {
        Employee reg = employeeRepository.findByEmail(email);
        if (reg == null) {
            throw new RuntimeException("Error");
        }
        reg.setSalary(salary);
        return employeeRepository.save(reg);
    }
}
