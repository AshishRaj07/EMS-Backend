package net.java.EMSbackend.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import net.java.EMSbackend.model.Employee;
import net.java.EMSbackend.model.LeaveRequest;
import net.java.EMSbackend.repository.EmployeeRepository;
import net.java.EMSbackend.repository.LeaveRequestRepository;

@Service
public class LeaveRequestService {
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public LeaveRequestRepository getLeaveRequestRepository() {
        return leaveRequestRepository;
    }

    public void setLeaveRequestRepository(LeaveRequestRepository leaveRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
    }

    @Transactional
    public LeaveRequest submitLeaveRequest(String reason, LocalDate leaveFromDate, LocalDate leaveToDate,
            String description,
            String email) {
        Employee reg = employeeRepository.findByEmail(email);
        LeaveRequest lr = new LeaveRequest();
        lr.setEmployee(reg);
        lr.setReason(reason);
        lr.setLeaveFromDate(leaveFromDate);
        lr.setLeaveToDate(leaveToDate);
        lr.setDescription(description);
        lr.setStatus("Pending");
        lr.setAppliedOn(LocalDateTime.now());
        return leaveRequestRepository.save(lr);
    }

    @Transactional
    public List<LeaveRequest> getAllPendingLeaveRequests() {
        return leaveRequestRepository.findByStatus("Pending");
    }

    public LeaveRequest approveLeaveRequest(Long requestId) {
        LeaveRequest lr = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        lr.setStatus("Approved");
        lr.setReplyOn(LocalDateTime.now());
        return leaveRequestRepository.save(lr);
    }

    public LeaveRequest rejectLeaveRequest(Long requestId) {
        LeaveRequest lr = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        lr.setStatus("Rejected");
        lr.setReplyOn(LocalDateTime.now());
        return leaveRequestRepository.save(lr);
    }

    public Iterable<LeaveRequest> getAllLeaveDetails() {
        Iterable<LeaveRequest> lr = leaveRequestRepository.findAll();
        return lr;
    }

    public Page<LeaveRequest> getAllLeaveDetailsPage(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return leaveRequestRepository.findAll(pageRequest);
    }

    public Page<LeaveRequest> getAllPendingLeavesRequestsPage(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return leaveRequestRepository.findAll(pageRequest);
    }
}
