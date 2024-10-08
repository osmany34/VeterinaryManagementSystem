package dev.patika.VeterinaryManagementSystem.dao;

import dev.patika.VeterinaryManagementSystem.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Object> findByMail(String mail);
    Optional<Object> findByPhone(String mail);
}
