package net.java.EMSbackend.model;

public class AttendanceCountDTO {
    private Long presentCount;
    private Long absentCount;
    private Long halfDayCount;

    public AttendanceCountDTO() {
    }

    public AttendanceCountDTO(Long presentCount, Long absentCount, Long halfDayCount) {
        this.presentCount = presentCount;
        this.absentCount = absentCount;
        this.halfDayCount = halfDayCount;
    }

    public Long getPresentCount() {
        return presentCount;
    }

    public void setPresentCount(Long presentCount) {
        this.presentCount = presentCount;
    }

    public Long getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(Long absentCount) {
        this.absentCount = absentCount;
    }

    public Long getHalfDayCount() {
        return halfDayCount;
    }

    public void setHalfDayCount(Long halfDayCount) {
        this.halfDayCount = halfDayCount;
    }

}
