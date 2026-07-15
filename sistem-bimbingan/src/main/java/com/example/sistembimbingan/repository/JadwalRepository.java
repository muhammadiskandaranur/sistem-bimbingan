package com.example.sistembimbingan.repository;


import com.example.sistembimbingan.entity.Dosen;
import com.example.sistembimbingan.entity.JadwalBimbingan;
import com.example.sistembimbingan.entity.Mahasiswa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface JadwalRepository extends JpaRepository<JadwalBimbingan, Long> {

    List<JadwalBimbingan> findByMahasiswaOrderByTanggalAscJamMulaiAsc(Mahasiswa mahasiswa);

    List<JadwalBimbingan> findByDosenOrderByTanggalAscJamMulaiAsc(Dosen dosen);

    List<JadwalBimbingan> findByMahasiswaAndTanggalGreaterThanEqualOrderByTanggalAscJamMulaiAsc(Mahasiswa mahasiswa, LocalDate tanggal);

    List<JadwalBimbingan> findByDosenAndTanggal(Dosen dosen, LocalDate tanggal);

    List<JadwalBimbingan> findByMahasiswaAndStatusOrderByTanggalDesc(Mahasiswa mahasiswa, String status);

    List<JadwalBimbingan> findByDosenAndStatusOrderByTanggalDesc(Dosen dosen, String status);

    List<JadwalBimbingan> findByDosenAndStatusOrderByTanggalAscJamMulaiAsc(
            Dosen dosen,
            String status
    );

    List<JadwalBimbingan> findByMahasiswaAndStatusOrderByTanggalAscJamMulaiAsc(
            Mahasiswa mahasiswa,
            String status
    );
    List<JadwalBimbingan> findByMahasiswaOrderByTanggalDesc(
            Mahasiswa mahasiswa
    );

}