package net.java.EMSbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.java.EMSbackend.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{


}
