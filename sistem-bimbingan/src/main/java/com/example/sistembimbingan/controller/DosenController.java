package com.example.sistembimbingan.controller;

import com.example.sistembimbingan.entity.Dosen;
import com.example.sistembimbingan.entity.JadwalBimbingan;
import com.example.sistembimbingan.entity.PengajuanBimbingan;
import com.example.sistembimbingan.service.DosenService;
import com.example.sistembimbingan.service.JadwalService;
import com.example.sistembimbingan.service.PengajuanService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
@RequestMapping("/dosen")
public class DosenController {

    private final DosenService dosenService;
    private final PengajuanService pengajuanService;
    private final JadwalService jadwalService;

    public DosenController(DosenService dosenService, PengajuanService pengajuanService, JadwalService jadwalService) {
        this.dosenService = dosenService;
        this.pengajuanService = pengajuanService;
        this.jadwalService = jadwalService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        Dosen dosen = dosenService.getByUserId(userId);

        long jumlahPengajuanBaru = pengajuanService.countMenungguByDosen(dosen);
        List<JadwalBimbingan> jadwalHariIni = jadwalService.getJadwalAktif(dosen);

        model.addAttribute("dosen", dosen);
        model.addAttribute("jumlahPengajuanBaru", jumlahPengajuanBaru);
        model.addAttribute("jadwalHariIni", jadwalHariIni);
        model.addAttribute("jumlahJadwalHariIni", jadwalHariIni.size());

        return "dosen/dashboard";
    }

    @GetMapping("/pengajuan-masuk")
    public String pengajuanMasuk(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        Dosen dosen = dosenService.getByUserId(userId);

        List<PengajuanBimbingan> pengajuanList =
                pengajuanService.getByDosen(dosen)
                        .stream()
                        .filter(p -> "MENUNGGU".equals(p.getStatus()))
                        .toList();
        model.addAttribute("pengajuanList", pengajuanList);

        return "dosen/pengajuan-masuk";
    }

    @GetMapping("/tambah-jadwal")
    public String tambahJadwalPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        Dosen dosen = dosenService.getByUserId(userId);

        List<PengajuanBimbingan> daftarPengajuanDiterima =
                pengajuanService.getByDosen(dosen).stream()
                        .filter(p ->
                                "DITERIMA".equals(p.getStatus()) ||
                                        "JADWAL_DIUBAH".equals(p.getStatus()))
                        .toList();

        model.addAttribute("daftarPengajuanDiterima", daftarPengajuanDiterima);

        return "dosen/tambah-jadwal";
    }


    @PostMapping("/jadwal/{id}/selesai")
    public String selesai(@PathVariable Long id) {

        jadwalService.ubahStatus(id, "SELESAI");

        return "redirect:/dosen/dashboard";
    }
    @GetMapping("/riwayat")
    public String riwayat(HttpSession session, Model model) {

        Long userId = (Long) session.getAttribute("userId");

        Dosen dosen = dosenService.getByUserId(userId);

        List<JadwalBimbingan> riwayatList =
                jadwalService.getByDosen(dosen)
                        .stream()
                        .filter(j ->
                                "SELESAI".equals(j.getStatus()))
                        .toList();

        model.addAttribute("riwayatList", riwayatList);

        return "dosen/riwayat";
    }
    @PostMapping("/jadwal/{id}/batalkan")
    public String batalkan(@PathVariable Long id) {

        jadwalService.ubahStatus(id, "DIBATALKAN");

        return "redirect:/dosen/dashboard";
    }


}