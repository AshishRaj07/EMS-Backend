package net.java.EMSbackend.controller;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import net.java.EMSbackend.model.Employee;
import net.java.EMSbackend.service.EmployeeService;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@RestController
@RequestMapping("/api")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<?> addEmployee(@RequestParam("fname") String fname,
            @RequestParam("lname") String lname,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("gender") String gender,
            @RequestParam("department") String department,
            @RequestParam("dateOfBirth") LocalDate dateOfBirth, @RequestParam("file") MultipartFile file,
            @RequestParam("company") String company, @RequestParam("password") String password,
            @RequestParam("experience") String experience, @RequestParam("salary") String salary) {
        Employee emp = employeeService.getEmpByEmail(email);
        if (emp == null) {
            Employee emply = new Employee();
            emply.setFirstName(fname);
            emply.setLastName(lname);
            emply.setEmail(email);
            emply.setContact(phone);
            emply.setGender(gender);
            emply.setDepartment(department);
            emply.setBirthdate(dateOfBirth);
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            if (filename.contains("..")) {
                System.out.println("Invalid file");
            }
            try {
                emply.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            emply.setCompany(company);
            emply.setPassword(password);
            emply.setExperience(Integer.parseInt(experience));
            emply.setSalary(Double.parseDouble(salary));
            employeeService.saveEmployee(emply);
            return ResponseEntity.ok(emply);
        } else {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("User With This Email Already Exist");
        }

    }

    @GetMapping("/getAllEmployee")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        boolean b = employeeService.deleteEmployee(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    // @GetMapping("/{id}")
    // public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
    // Employee employee = employeeService.getEmployeeById(id);
    // if (employee != null) {
    // return ResponseEntity.ok(employee);
    // } else {
    // return ResponseEntity.notFound().build();
    // }
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,
    // @RequestBody Employee employeeDetails) {
    // Employee existingEmployee = employeeService.getEmployeeById(id);

    // if (existingEmployee == null) {
    // return ResponseEntity.notFound().build();
    // }
    // BeanUtils.copyProperties(employeeDetails, existingEmployee, "id");

    // Employee updatedEmployee = employeeService.saveEmployee(existingEmployee);
    // return ResponseEntity.ok(updatedEmployee);
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
    // boolean isDeleted = employeeService.deleteEmployee(id);
    // if (isDeleted) {
    // return ResponseEntity.ok("Employee with id " + id + " deleted successfully");
    // } else {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with id " +
    // id + " not found");
    // }
    // }

}
