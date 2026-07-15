package com.example.sistembimbingan.service;

import com.example.sistembimbingan.entity.Dosen;
import com.example.sistembimbingan.entity.User;
import com.example.sistembimbingan.repository.DosenRepository;
import com.example.sistembimbingan.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DosenService {

    private final DosenRepository dosenRepository;
    private final UserRepository userRepository;

    public DosenService(DosenRepository dosenRepository, UserRepository userRepository) {
        this.dosenRepository = dosenRepository;
        this.userRepository = userRepository;
    }

    public Dosen getByUserId(Long userId) {
        return dosenRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Data dosen tidak ditemukan"));
    }

    public List<Dosen> getAllDosen() {
        return dosenRepository.findAllByOrderByUser_NamaAsc();
    }

    public Dosen updateProfil(
            Long userId,
            String nama,
            String email,
            String noHp
    ) {

        Dosen dosen = getByUserId(userId);

        User user = dosen.getUser();

        user.setNama(nama);
        user.setEmail(email);
        user.setNoHp(noHp);

        userRepository.save(user);

        return dosenRepository.save(dosen);
    }
}