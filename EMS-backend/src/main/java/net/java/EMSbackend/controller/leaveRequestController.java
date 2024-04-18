package net.java.EMSbackend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.java.EMSbackend.model.LeaveRequest;
import net.java.EMSbackend.service.LeaveRequestService;

@RestController
@RequestMapping("/api")
public class leaveRequestController {
    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping("/leaveRequest")
    public LeaveRequest submitLeaveRequest(@RequestParam("reason") String reason,
            @RequestParam("leaveFromDate") LocalDate leaveFromDate, @RequestParam("leaveToDate") LocalDate leaveToDate,
            @RequestParam("description") String description, @RequestParam("register_id") String email) {
        // System.out.println(leaveRequest.getRegister());
        return leaveRequestService.submitLeaveRequest(reason, leaveFromDate, leaveToDate, description, email);
    }

    @PostMapping("/approveLeave/{requestId}")
    public LeaveRequest approveLeaveRequest(@PathVariable Long requestId) {
        return leaveRequestService.approveLeaveRequest(requestId);
    }

    @PostMapping("/rejectLeave/{requestId}")
    public LeaveRequest rejectLeaveRequest(@PathVariable Long requestId) {
        return leaveRequestService.rejectLeaveRequest(requestId);
    }

    @GetMapping("/pendingLeaves")
    public List<LeaveRequest> getPendingLeaveRequests() {
        return leaveRequestService.getAllPendingLeaveRequests();
    }

    @GetMapping("/getAllLeave")
    public Iterable<LeaveRequest> getAllLeaveDetails() {
        return leaveRequestService.getAllLeaveDetails();
    }

    @GetMapping("/pendingLeavesPage/{pageNumber}")
    public Page<LeaveRequest> getPendingLeaveRequests(@PathVariable int pageNumber) {
        return leaveRequestService.getAllPendingLeavesRequestsPage(pageNumber,5);
    }
}

