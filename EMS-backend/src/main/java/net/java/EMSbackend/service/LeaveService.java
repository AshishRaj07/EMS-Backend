package net.java.EMSbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.java.EMSbackend.model.LeaveRequest;
import net.java.EMSbackend.repository.LeaveRequestRepository;

@Service
public class LeaveService {

    private final LeaveRequestRepository leaveRequestRepository;

    @Autowired
    public LeaveService(LeaveRequestRepository leaveRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
    }

    public boolean applyLeaveRequest(LeaveRequest leaveRequest) {
        try {
            leaveRequestRepository.save(leaveRequest);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean approveLeaveRequest(Long leaveRequestId) {
        try {
            LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                    .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));
            leaveRequest.setApproved(true);
            leaveRequestRepository.save(leaveRequest);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean rejectLeaveRequest(Long leaveRequestId) {
        try {
            LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                    .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));
            leaveRequest.setApproved(false);
            leaveRequestRepository.save(leaveRequest);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
