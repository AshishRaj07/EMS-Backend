package net.java.EMSbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import net.java.EMSbackend.model.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update Todo td set td.email=:newEmail where td.email=:email")
    public void updateEmail(String email, String newEmail);
}
