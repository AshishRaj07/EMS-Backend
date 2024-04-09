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

    @PatchMapping("/updateEmployee/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id,
            @RequestParam("fname") String fname,
            @RequestParam("lname") String lname,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("department") String department,
            @RequestParam("dateOfBirth") LocalDate dateOfBirth, @RequestParam("salary") String salary) {
        Employee emp = employeeService.getEmployeeById(id);
        emp.setFirstName(fname);
        emp.setLastName(lname);
        emp.setDepartment(department);
        emp.setEmail(email);
        emp.setBirthdate(dateOfBirth);
        emp.setSalary(Double.parseDouble(salary));
        emp.setContact(phone);
        return ResponseEntity.ok(employeeService.saveEmployee(emp));

    }

    @PatchMapping("/addSalary/{email}")
    public void addSalary(@PathVariable String email, @RequestParam("salary") String salary) {
        employeeService.addSalary(email, Float.parseFloat(salary));
    }
}
