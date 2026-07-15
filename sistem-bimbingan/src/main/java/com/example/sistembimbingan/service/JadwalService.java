package com.example.sistembimbingan.service;

import com.example.sistembimbingan.entity.Dosen;
import com.example.sistembimbingan.entity.JadwalBimbingan;
import com.example.sistembimbingan.entity.Mahasiswa;
import com.example.sistembimbingan.entity.PengajuanBimbingan;
import com.example.sistembimbingan.repository.JadwalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class JadwalService {

    private final JadwalRepository jadwalRepository;
    private final PengajuanService pengajuanService;

    public JadwalService(
            JadwalRepository jadwalRepository,
            PengajuanService pengajuanService
    ) {
        this.jadwalRepository = jadwalRepository;
        this.pengajuanService = pengajuanService;
    }

    public JadwalBimbingan tambahJadwal(PengajuanBimbingan pengajuan, LocalDate tanggal,
                                        LocalTime jamMulai, LocalTime jamSelesai,
                                        String tempat, String catatan) {
        JadwalBimbingan jadwal = new JadwalBimbingan();
        jadwal.setPengajuan(pengajuan);
        jadwal.setMahasiswa(pengajuan.getMahasiswa());
        jadwal.setDosen(pengajuan.getDosen());
        jadwal.setTanggal(tanggal);
        jadwal.setJamMulai(jamMulai);
        jadwal.setJamSelesai(jamSelesai);
        jadwal.setTempat(tempat);
        jadwal.setCatatan(catatan);
        jadwal.setStatus("TERJADWAL");

// update status pengajuan
        pengajuan.setStatus("TERJADWAL");
        pengajuanService.save(pengajuan);

        return jadwalRepository.save(jadwal);
    }

    public List<JadwalBimbingan> getByMahasiswa(Mahasiswa mahasiswa) {
        return jadwalRepository.findByMahasiswaAndStatusOrderByTanggalAscJamMulaiAsc(
                mahasiswa,
                "TERJADWAL"
        );
    }

    public List<JadwalBimbingan> getByDosen(Dosen dosen) {
        return jadwalRepository.findByDosenOrderByTanggalAscJamMulaiAsc(dosen);
    }

    public List<JadwalBimbingan> getJadwalMendatang(Mahasiswa mahasiswa) {

        return jadwalRepository
                .findByMahasiswaAndStatusOrderByTanggalAscJamMulaiAsc(
                        mahasiswa,
                        "TERJADWAL"
                );
    }

    public List<JadwalBimbingan> getJadwalHariIni(Dosen dosen) {
        return jadwalRepository.findByDosenAndTanggal(dosen, LocalDate.now());
    }

    public List<JadwalBimbingan> getRiwayatMahasiswa(Mahasiswa mahasiswa) {
        return jadwalRepository.findByMahasiswaAndStatusOrderByTanggalDesc(mahasiswa, "SELESAI");
    }

    public List<JadwalBimbingan> getRiwayatDosen(Dosen dosen) {
        return jadwalRepository.findByDosenAndStatusOrderByTanggalDesc(dosen, "SELESAI");
    }

    public JadwalBimbingan getById(Long id) {
        return jadwalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jadwal tidak ditemukan"));
    }

    public JadwalBimbingan ubahStatus(Long id, String status) {

        JadwalBimbingan jadwal = getById(id);

        jadwal.setStatus(status);

        PengajuanBimbingan pengajuan =
                jadwal.getPengajuan();

        if ("SELESAI".equals(status)) {

            pengajuan.setStatus("SELESAI");

        } else if ("DIBATALKAN".equals(status)) {

            pengajuan.setStatus("JADWAL_DIUBAH");

        }

        pengajuanService.save(pengajuan);

        return jadwalRepository.save(jadwal);
    }
    public List<JadwalBimbingan> getJadwalAktif(Dosen dosen) {
        return jadwalRepository.findByDosenAndStatusOrderByTanggalAscJamMulaiAsc(
                dosen,
                "TERJADWAL"
        );
    }
}