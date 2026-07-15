package com.example.sistembimbingan.repository;


import com.example.sistembimbingan.entity.Dosen;
import com.example.sistembimbingan.entity.Mahasiswa;
import com.example.sistembimbingan.entity.PengajuanBimbingan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PengajuanRepository extends JpaRepository<PengajuanBimbingan, Long> {

    List<PengajuanBimbingan> findByMahasiswaOrderByCreatedAtDesc(Mahasiswa mahasiswa);

    List<PengajuanBimbingan> findByDosenOrderByCreatedAtDesc(Dosen dosen);

    List<PengajuanBimbingan> findByDosenAndStatusOrderByCreatedAtDesc(Dosen dosen, String status);

    long countByMahasiswa(Mahasiswa mahasiswa);

    long countByDosenAndStatus(Dosen dosen, String status);
}