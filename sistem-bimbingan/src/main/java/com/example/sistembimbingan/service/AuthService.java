package com.example.sistembimbingan.service;

import com.example.sistembimbingan.dto.LoginDTO;
import com.example.sistembimbingan.dto.RegisterDosenDTO;
import com.example.sistembimbingan.dto.RegisterMahasiswaDTO;
import com.example.sistembimbingan.entity.Dosen;
import com.example.sistembimbingan.entity.Mahasiswa;
import com.example.sistembimbingan.entity.User;
import com.example.sistembimbingan.repository.DosenRepository;
import com.example.sistembimbingan.repository.MahasiswaRepository;
import com.example.sistembimbingan.repository.UserRepository;
import com.example.sistembimbingan.util.PasswordUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final MahasiswaRepository mahasiswaRepository;
    private final DosenRepository dosenRepository;

    public AuthService(UserRepository userRepository,
                       MahasiswaRepository mahasiswaRepository,
                       DosenRepository dosenRepository) {
        this.userRepository = userRepository;
        this.mahasiswaRepository = mahasiswaRepository;
        this.dosenRepository = dosenRepository;
    }

    public User login(LoginDTO loginDTO) {
        Optional<User> userOpt = userRepository.findByEmailAndRole(loginDTO.getEmail(), loginDTO.getRole());

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Email atau role tidak ditemukan");
        }

        User user = userOpt.get();

        if (!PasswordUtil.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Password salah");
        }

        return user;
    }

    public Mahasiswa registerMahasiswa(RegisterMahasiswaDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email sudah terdaftar");
        }
        if (mahasiswaRepository.existsByNim(dto.getNim())) {
            throw new RuntimeException("NIM sudah terdaftar");
        }

        User user = new User();
        user.setNama(dto.getNama());
        user.setEmail(dto.getEmail());
        user.setPassword(PasswordUtil.hash(dto.getPassword()));
        user.setRole("MAHASISWA");
        user.setNoHp(dto.getNoHp());
        user = userRepository.save(user);

        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa.setUser(user);
        mahasiswa.setNim(dto.getNim());
        mahasiswa.setProdi(dto.getProdi());
        mahasiswa.setSemester(dto.getSemester());

        return mahasiswaRepository.save(mahasiswa);
    }

    public Dosen registerDosen(RegisterDosenDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email sudah terdaftar");
        }

        User user = new User();
        user.setNama(dto.getNama());
        user.setEmail(dto.getEmail());
        user.setPassword(PasswordUtil.hash(dto.getPassword()));
        user.setRole("DOSEN");
        user.setNoHp(dto.getNoHp());
        user = userRepository.save(user);

        Dosen dosen = new Dosen();
        dosen.setUser(user);

        return dosenRepository.save(dosen);
    }

    public void ubahPassword(Long userId, String passwordLama, String passwordBaru) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        if (!PasswordUtil.matches(passwordLama, user.getPassword())) {
            throw new RuntimeException("Password lama salah");
        }

        user.setPassword(PasswordUtil.hash(passwordBaru));
        userRepository.save(user);
    }
}