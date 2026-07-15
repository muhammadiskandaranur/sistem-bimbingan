package com.example.sistembimbingan.service;

import com.example.sistembimbingan.entity.Dosen;
import com.example.sistembimbingan.entity.Mahasiswa;
import com.example.sistembimbingan.entity.PengajuanBimbingan;
import com.example.sistembimbingan.repository.PengajuanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PengajuanService {

    private final PengajuanRepository pengajuanRepository;

    public PengajuanService(PengajuanRepository pengajuanRepository) {
        this.pengajuanRepository = pengajuanRepository;
    }

    public PengajuanBimbingan ajukan(Mahasiswa mahasiswa, Dosen dosen, String judul, String topik) {
        PengajuanBimbingan pengajuan = new PengajuanBimbingan();
        pengajuan.setMahasiswa(mahasiswa);
        pengajuan.setDosen(dosen);
        pengajuan.setJudul(judul);
        pengajuan.setTopik(topik);
        pengajuan.setStatus("MENUNGGU");
        return pengajuanRepository.save(pengajuan);
    }

    public List<PengajuanBimbingan> getByMahasiswa(Mahasiswa mahasiswa) {
        return pengajuanRepository.findByMahasiswaOrderByCreatedAtDesc(mahasiswa);
    }

    public List<PengajuanBimbingan> getByDosen(Dosen dosen) {
        return pengajuanRepository.findByDosenOrderByCreatedAtDesc(dosen);
    }

    public List<PengajuanBimbingan> getMenungguByDosen(Dosen dosen) {
        return pengajuanRepository.findByDosenAndStatusOrderByCreatedAtDesc(dosen, "MENUNGGU");
    }

    public long countByMahasiswa(Mahasiswa mahasiswa) {
        return pengajuanRepository.countByMahasiswa(mahasiswa);
    }

    public long countMenungguByDosen(Dosen dosen) {
        return pengajuanRepository.countByDosenAndStatus(dosen, "MENUNGGU");
    }

    public PengajuanBimbingan getById(Long id) {
        return pengajuanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pengajuan tidak ditemukan"));
    }

    public PengajuanBimbingan tolak(Long id) {
        PengajuanBimbingan pengajuan = getById(id);
        pengajuan.setStatus("DITOLAK");
        return pengajuanRepository.save(pengajuan);
    }

    public PengajuanBimbingan terima(Long id) {
        PengajuanBimbingan pengajuan = getById(id);
        pengajuan.setStatus("DITERIMA");
        return pengajuanRepository.save(pengajuan);
    }

    public PengajuanBimbingan selesaikan(Long id) {
        PengajuanBimbingan pengajuan = getById(id);
        pengajuan.setStatus("SELESAI");
        return pengajuanRepository.save(pengajuan);
    }
    public PengajuanBimbingan save(PengajuanBimbingan pengajuan) {
        return pengajuanRepository.save(pengajuan);
    }
    public PengajuanBimbingan updateStatus(Long id, String status) {

        PengajuanBimbingan pengajuan = getById(id);

        pengajuan.setStatus(status);

        return pengajuanRepository.save(pengajuan);
    }

}