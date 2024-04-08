package net.java.EMSbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.java.EMSbackend.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
