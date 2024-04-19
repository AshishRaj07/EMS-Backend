package net.java.EMSbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import net.java.EMSbackend.model.Department;
import net.java.EMSbackend.repository.DepartmentRepository;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public DepartmentRepository getDepartmentRepo() {
        return departmentRepository;
    }

    public void setDepartmentRepo(DepartmentRepository departmentRepo) {
        this.departmentRepository = departmentRepo;
    }

    public Department addDepartment(Department department) {
        Department addDept = departmentRepository.save(department);
        return addDept;
    }

    public Iterable<Department> getAllDepartment() {
        Iterable<Department> dept = departmentRepository.findAll();
        return dept;
    }

    public Page<Department> getAllDepartmentPage(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return departmentRepository.findAll(pageRequest);
    }

    public Page<Department> getSearchedDepartment(String keyword, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return departmentRepository.getSearchedDepartment(keyword.toLowerCase(), pageRequest);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    public Department editDepartment(Long id, Department dept) {
        Department depart = departmentRepository.findById(id).orElse(null);
        if (depart == null) {
            throw new RuntimeException("Error");
        }
        depart.setDepartmentName(dept.getDepartmentName());
        return departmentRepository.save(depart);

    }
}
