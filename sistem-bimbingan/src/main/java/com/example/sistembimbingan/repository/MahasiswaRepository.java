package com.example.sistembimbingan.repository;


import com.example.sistembimbingan.entity.Mahasiswa;
import com.example.sistembimbingan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MahasiswaRepository extends JpaRepository<Mahasiswa, Long> {

    Optional<Mahasiswa> findByUser(User user);

    Optional<Mahasiswa> findByUserId(Long userId);

    Optional<Mahasiswa> findByNim(String nim);

    boolean existsByNim(String nim);
}