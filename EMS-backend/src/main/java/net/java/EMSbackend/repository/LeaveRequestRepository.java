package net.java.EMSbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import net.java.EMSbackend.model.LeaveRequest;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByStatus(String status);

    LeaveRequest findByEmployeeEmail(String email);

    @Query(value = "select count(*) from leave_request lr where lr.status='Pending'", nativeQuery = true)
    Integer noOfLeaveRequest();

    int countByEmployeeEmail(String email);
}
