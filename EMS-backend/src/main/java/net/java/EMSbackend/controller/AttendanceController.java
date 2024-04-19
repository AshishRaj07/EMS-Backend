package net.java.EMSbackend.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.java.EMSbackend.DTO.AttendanceRequestDTO;
import net.java.EMSbackend.model.Attendance;
import net.java.EMSbackend.service.AttendanceService;

@RestController
@RequestMapping("/api")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    public AttendanceService getAttendanceService() {
        return attendanceService;
    }

    public void setAttendanceService(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/markAttendace")
    public ResponseEntity<Object> markAttendance(@RequestBody AttendanceRequestDTO request) {
        try {
            ResponseEntity<Object> results = attendanceService.markAttendance(request.getEmployeeIds(),
                    request.getPresentDate(), request.isPresent(),
                    request.isHalfDay());

            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error:" + e.getMessage());
        }
    }

    @GetMapping("/getAttendanceByDate/{Date}")
    public List<Attendance> getAttendancesByDate(@PathVariable LocalDate Date) {
        return attendanceService.getAttendancesByDate(Date);
    }

    @GetMapping("/getAttendanceByDatePage/{pageNumber}/{Date}")
    public Page<Attendance> getAttendancesByDate(@PathVariable int pageNumber, @PathVariable LocalDate Date) {
        return attendanceService.getAttendancesByDatePage(Date, pageNumber, 5);
    }

    @GetMapping("/getSearchedAttendance/{keyword}/{pageNumber}/{Date}")
    public Page<Attendance> getSearchedAttendances(@PathVariable String keyword, @PathVariable int pageNumber,
            @PathVariable LocalDate Date) {
        return attendanceService.getSearchedAttendance(keyword, pageNumber, 5, Date);
    }

}
