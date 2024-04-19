package net.java.EMSbackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.java.EMSbackend.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("select d from Department d where LOWER(d.departmentName) LIKE %?1%")
    public Page<Department> getSearchedDepartment(String keyword, Pageable pageable);
}
