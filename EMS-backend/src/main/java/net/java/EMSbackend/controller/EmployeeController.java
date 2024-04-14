package net.java.EMSbackend.controller;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import net.java.EMSbackend.DTO.EmployeeDTO;
import net.java.EMSbackend.model.Employee;
import net.java.EMSbackend.service.EmployeeService;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@RestController
@RequestMapping("/api")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<?> addEmployee(EmployeeDTO employeeDTO) {
        Employee emp = employeeService.getEmpByEmail(employeeDTO.getEmail());
        if (emp == null) {
            Employee emply = new Employee();
            emply.setFirstName(employeeDTO.getFirstName());
            emply.setLastName(employeeDTO.getLastName());
            emply.setEmail(employeeDTO.getEmail());
            emply.setContact(employeeDTO.getContact());
            emply.setGender(employeeDTO.getGender());
            emply.setDepartment(employeeDTO.getDepartment());
            emply.setBirthdate(employeeDTO.getBirthdate());
            String filename = StringUtils.cleanPath(employeeDTO.getImage().getOriginalFilename());
            if (filename.contains("..")) {
                System.out.println("Invalid file");
            }
            try {
                emply.setImage(Base64.getEncoder().encodeToString(employeeDTO.getImage().getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            emply.setCompany(employeeDTO.getCompany());
            emply.setPassword(employeeDTO.getPassword());
            emply.setExperience(employeeDTO.getExperience());
            emply.setSalary(employeeDTO.getSalary());
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

    @GetMapping("/search{id}")
    public Employee getEmployeeById(@RequestParam Long id){
        return employeeService.findEmployeeById(id);
    }

    @GetMapping("/employee/{email}")
    public ResponseEntity<Employee> getEmployeeByEmail(@PathVariable String email) {
        Employee employee = employeeService.getEmpByEmail(email);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
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
