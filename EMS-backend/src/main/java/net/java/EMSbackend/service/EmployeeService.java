package net.java.EMSbackend.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.Optional;
import net.java.EMSbackend.model.Employee;
import net.java.EMSbackend.repository.EmployeeRepository;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();

    }

    @Transactional
    public Employee getEmpByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public Employee getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.orElse(null);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee existingEmployee = getEmployeeById(id);

        if (existingEmployee != null) {

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
