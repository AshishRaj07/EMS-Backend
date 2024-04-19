package net.java.EMSbackend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import net.java.EMSbackend.model.Attendance;
import net.java.EMSbackend.model.Employee;
import net.java.EMSbackend.repository.AttendanceRepository;
import net.java.EMSbackend.repository.EmployeeRepository;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    public AttendanceRepository getAttendanceRepository() {
        return attendanceRepository;
    }

    public void setAttendanceRepository(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public ResponseEntity<Object> markAttendance(List<Long> employeeIds, LocalDate date, boolean present,
            boolean halfDay) {
        List<String> results = new ArrayList<>();
        if (present && !halfDay) {
            for (Long employeeId : employeeIds) {
                Attendance newAttendance = new Attendance();
                Employee reg = employeeRepository.findById(employeeId).orElse(null);
                if (reg != null) {
                    newAttendance.setEmployee(reg);
                }
                newAttendance.setDate(date);
                newAttendance.setPresent(present);
                newAttendance.setHalfDay(false);
                attendanceRepository.save(newAttendance);
                results.add("Marked Attendance for:" + employeeId);
            }
            List<Employee> register = employeeRepository.findAllByIdNotIn(employeeIds);
            if (register != null) {
                for (Employee reg1 : register) {
                    Attendance attendance1 = new Attendance();
                    attendance1.setEmployee(reg1);
                    attendance1.setDate(date);
                    attendance1.setHalfDay(false);
                    attendanceRepository.save(attendance1);
                    results.add("Attendance Marked as Absent for Employee Id:" + reg1.getId());
                }
            }
        } else if (present && halfDay) {

            for (Long employeeId : employeeIds) {
                Optional<Attendance> existingAttendance = attendanceRepository.findByEmployeeIdAndDate(employeeId,
                        date);
                if (existingAttendance.isPresent()) {
                    Attendance attendance = existingAttendance.get();
                    attendance.setPresent(present);
                    attendance.setHalfDay(present && halfDay);
                    attendanceRepository.save(attendance);
                    results.add("Marked Half Leave for:" + employeeId);
                } else {
                    Attendance newAttendance = new Attendance();
                    Employee reg = employeeRepository.findById(employeeId).orElse(null);
                    if (reg != null) {
                        newAttendance.setEmployee(reg);
                    }
                    newAttendance.setDate(date);
                    newAttendance.setPresent(present);
                    newAttendance.setHalfDay(true);
                    attendanceRepository.save(newAttendance);
                    results.add("Marked Attendance for:" + employeeId);
                }
            }
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @Transactional
    public List<Attendance> getAttendancesByDate(LocalDate Date) {
        return attendanceRepository.findByDate(Date);
    }

    @Transactional
    public Page<Attendance> getAttendancesByDatePage(LocalDate Date, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return attendanceRepository.findByDate(Date, pageRequest);
    }

    @Transactional
    public Page<Attendance> getSearchedAttendance(String keyword, int pageNumber, int pageSize, LocalDate date) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return attendanceRepository.getSearchedAttendance(keyword, date, pageRequest);
    }
}
