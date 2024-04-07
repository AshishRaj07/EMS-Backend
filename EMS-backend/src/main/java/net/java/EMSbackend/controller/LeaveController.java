package net.java.EMSbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.java.EMSbackend.model.LeaveRequest;
import net.java.EMSbackend.service.LeaveService;

@RestController
@RequestMapping("/api/leave-requests")
public class LeaveController {

    private final LeaveService leaveService;

    @Autowired
    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping("/apply")
    public ResponseEntity<String> applyLeave(@RequestBody LeaveRequest leaveRequest) {
        boolean isSuccess = leaveService.applyLeaveRequest(leaveRequest);
        if (isSuccess) {
            return ResponseEntity.ok("Leave request submitted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to submit leave request");
        }
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<String> approveLeave(@PathVariable Long id) {
        boolean isSuccess = leaveService.approveLeaveRequest(id);
        if (isSuccess) {
            return ResponseEntity.ok("Leave request approved successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to approve leave request");
        }
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<String> rejectLeave(@PathVariable Long id) {
        boolean isSuccess = leaveService.rejectLeaveRequest(id);
        if (isSuccess) {
            return ResponseEntity.ok("Leave request rejected successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to reject leave request");
        }
    }
}
