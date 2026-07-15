package com.example.sistembimbingan.service;

import com.example.sistembimbingan.entity.Mahasiswa;
import com.example.sistembimbingan.entity.User;
import com.example.sistembimbingan.repository.MahasiswaRepository;
import com.example.sistembimbingan.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class MahasiswaService {

    private final MahasiswaRepository mahasiswaRepository;
    private final UserRepository userRepository;

    public MahasiswaService(
            MahasiswaRepository mahasiswaRepository,
            UserRepository userRepository
    ) {
        this.mahasiswaRepository = mahasiswaRepository;
        this.userRepository = userRepository;
    }

    public Mahasiswa getByUserId(Long userId) {
        return mahasiswaRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Data mahasiswa tidak ditemukan"));
    }

    public Mahasiswa updateProfil(
            Long userId,
            String nama,
            String email,
            String noHp,
            String nim,
            String prodi,
            Integer semester
    ) {

        Mahasiswa mahasiswa = mahasiswaRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Data mahasiswa tidak ditemukan"));

        User user = mahasiswa.getUser();

        user.setNama(nama);
        user.setEmail(email);
        user.setNoHp(noHp);

        userRepository.save(user);

        mahasiswa.setNim(nim);
        mahasiswa.setProdi(prodi);
        mahasiswa.setSemester(semester);

        return mahasiswaRepository.save(mahasiswa);
    }
}