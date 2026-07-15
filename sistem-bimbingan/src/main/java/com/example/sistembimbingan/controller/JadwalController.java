package com.example.sistembimbingan.controller;

import com.example.sistembimbingan.entity.PengajuanBimbingan;
import com.example.sistembimbingan.service.JadwalService;
import com.example.sistembimbingan.service.PengajuanService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/jadwal")
public class JadwalController {

    private final JadwalService jadwalService;
    private final PengajuanService pengajuanService;

    public JadwalController(JadwalService jadwalService, PengajuanService pengajuanService) {
        this.jadwalService = jadwalService;
        this.pengajuanService = pengajuanService;
    }

    @PostMapping("/tambah")
    public String tambahJadwal(@RequestParam("pengajuanId") Long pengajuanId,
                               @RequestParam("tanggal") String tanggal,
                               @RequestParam("jamMulai") String jamMulai,
                               @RequestParam("jamSelesai") String jamSelesai,
                               @RequestParam("tempat") String tempat,
                               @RequestParam(value = "catatan", required = false) String catatan) {

        PengajuanBimbingan pengajuan = pengajuanService.getById(pengajuanId);


                pengajuan.setStatus("TERJADWAL");
                pengajuanService.save(pengajuan);

        jadwalService.tambahJadwal(
                pengajuan,
                LocalDate.parse(tanggal),
                LocalTime.parse(jamMulai),
                LocalTime.parse(jamSelesai),
                tempat,
                catatan
        );

        return "redirect:/dosen/tambah-jadwal";
    }

    @GetMapping("/selesai/{id}")
    public String selesaikanJadwal(@org.springframework.web.bind.annotation.PathVariable Long id) {
        jadwalService.ubahStatus(id, "SELESAI");
        return "redirect:/dosen/riwayat";
    }

    @GetMapping("/batal/{id}")
    public String batalkanJadwal(@org.springframework.web.bind.annotation.PathVariable Long id) {
        jadwalService.ubahStatus(id, "BATAL");
        return "redirect:/dosen/riwayat";
    }
}