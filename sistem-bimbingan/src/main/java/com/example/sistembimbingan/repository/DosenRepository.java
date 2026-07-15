package com.example.sistembimbingan.repository;


import com.example.sistembimbingan.entity.Dosen;
import com.example.sistembimbingan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DosenRepository extends JpaRepository<Dosen, Long> {

    Optional<Dosen> findByUser(User user);

    Optional<Dosen> findByUserId(Long userId);

    List<Dosen> findAllByOrderByUser_NamaAsc();
}