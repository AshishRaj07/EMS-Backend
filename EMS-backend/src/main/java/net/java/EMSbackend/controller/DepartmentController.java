package net.java.EMSbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.java.EMSbackend.model.Department;
import net.java.EMSbackend.service.DepartmentService;

@RestController
@RequestMapping("/api")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    public DepartmentService getDepartmentService() {
        return departmentService;
    }

    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/addDepartment")
    public Department addDepartment(@RequestBody Department department) {
        Department dept = new Department();
        // System.out.println(department.getDepartmentName());
        dept.setDepartmentName(department.getDepartmentName());
        departmentService.addDepartment(dept);
        return dept;
    }

    @GetMapping("/getAllDepartment")
    public Iterable<Department> getAllDepartment() {
        Iterable<Department> dept = departmentService.getAllDepartment();
        return dept;
    }
    
    @GetMapping("/getAllDepartmentPage/{pageNumber}")
    public Page<Department> getAllDepartment(@PathVariable int pageNumber) {
        return departmentService.getAllDepartmentPage(pageNumber,5);
    }

    @DeleteMapping("/deleteDepartment/{id}")
    public void deleteDepartment(@PathVariable String id) {
        // System.out.println(id);
        departmentService.deleteDepartment(Long.parseLong(id));
    }

    @PatchMapping("/editDepartment/{id}")
    public Department editDepartment(@PathVariable String id, @RequestBody Department department) {
        Department dept = new Department();
        dept.setDepartmentName(department.getDepartmentName());
        // System.out.println(department.getDepartmentName());
        return departmentService.editDepartment(Long.parseLong(id), dept);
    }
}
