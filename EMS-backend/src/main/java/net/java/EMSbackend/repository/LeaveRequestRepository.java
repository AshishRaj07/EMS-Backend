package net.java.EMSbackend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;
import net.java.EMSbackend.model.LeaveRequest;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByStatus(String status);

    @Query("SELECT l FROM LeaveRequest l WHERE l.status = 'Pending'")
    Page<LeaveRequest> getPendingLeaves(Pageable pageable);

    LeaveRequest findByEmployeeEmail(String email);

    @Query(value = "select count(*) from leave_request lr where lr.status='Pending'", nativeQuery = true)
    Integer noOfLeaveRequest();

    int countByEmployeeEmail(String email);

    @Query("SELECT lr FROM LeaveRequest lr " +
            "JOIN lr.employee e " +
            "WHERE (LOWER(CONCAT(e.firstName, ' ', e.lastName,' ',e.department)) LIKE %?1% " +
            "   OR LOWER(lr.reason) LIKE %?1%) " + " AND lr.status='Pending'")
    Page<LeaveRequest> getSearchedLeaveDetails(String keyword, Pageable pageable);

    @Query("SELECT lr FROM LeaveRequest lr " +
            "JOIN lr.employee e " +
            "WHERE LOWER(CONCAT(e.firstName, ' ', e.lastName,' ',e.department,' ',e.email)) LIKE %?1% " +
            "   OR LOWER(lr.reason) LIKE %?1% " +
            "   OR LOWER(lr.status) LIKE %?1% " +
            "   OR LOWER(lr.description) LIKE %?1% ")
    Page<LeaveRequest> getAllSearchedLeaveDetails(String keyword, Pageable pageable);
}
