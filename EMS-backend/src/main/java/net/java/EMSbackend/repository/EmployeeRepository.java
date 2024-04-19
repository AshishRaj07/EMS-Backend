package net.java.EMSbackend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.java.EMSbackend.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmail(String email);

    public List<Employee> findAllByIdNotIn(List<Long> employeeIds);

    @Query("select e from Employee e where LOWER(concat(e.firstName,' ',e.email,' ',e.contact,' ',e.department,' ',e.lastName)) LIKE %?1% or LOWER(CONCAT(e.firstName, ' ', e.lastName)) LIKE %?1%")
    public Page<Employee> getSearchedEmployee(String keyword, Pageable pageable);

}
