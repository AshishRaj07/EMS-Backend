package net.java.EMSbackend.model;

import java.time.LocalDate;
import java.util.List;

public class AttendanceRequestDTO {
    private List<Long> employeeIds;
    private boolean present;
    private boolean halfDay;
    private LocalDate presentDate;

    public AttendanceRequestDTO() {
    }

    public AttendanceRequestDTO(List<Long> employeeIds, boolean present, boolean halfDay, LocalDate presentDate) {
        this.employeeIds = employeeIds;
        this.present = present;
        this.halfDay = halfDay;
        this.presentDate = presentDate;
    }

    public List<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public boolean isHalfDay() {
        return halfDay;
    }

    public void setHalfDay(boolean halfDay) {
        this.halfDay = halfDay;
    }

    public LocalDate getPresentDate() {
        return presentDate;
    }

    public void setPresentDate(LocalDate presentDate) {
        this.presentDate = presentDate;
    }

}
