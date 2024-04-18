package net.java.EMSbackend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.java.EMSbackend.model.Attendance;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByEmployeeIdAndDate(Long id, LocalDate date);

    List<Attendance> findByDate(LocalDate date);

    Page<Attendance> findByDate(LocalDate date,PageRequest pageRequest);
}
