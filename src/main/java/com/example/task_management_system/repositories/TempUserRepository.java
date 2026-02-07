package com.example.task_management_system.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.task_management_system.entities.TempUser;

public interface TempUserRepository extends JpaRepository<TempUser, String> {
    Optional<TempUser> findByEmail(String email);
    boolean existsByEmail(String email);
}
