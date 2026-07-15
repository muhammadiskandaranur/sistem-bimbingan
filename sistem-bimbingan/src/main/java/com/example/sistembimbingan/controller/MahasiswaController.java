package com.example.sistembimbingan.controller;

import com.example.sistembimbingan.entity.Dosen;
import com.example.sistembimbingan.entity.Mahasiswa;
import com.example.sistembimbingan.entity.PengajuanBimbingan;
import com.example.sistembimbingan.entity.JadwalBimbingan;
import com.example.sistembimbingan.service.AuthService;
import com.example.sistembimbingan.service.DosenService;
import com.example.sistembimbingan.service.JadwalService;
import com.example.sistembimbingan.service.MahasiswaService;
import com.example.sistembimbingan.service.PengajuanService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mahasiswa")
public class MahasiswaController {

    private final MahasiswaService mahasiswaService;
    private final DosenService dosenService;
    private final PengajuanService pengajuanService;
    private final JadwalService jadwalService;
    private final AuthService authService;

    public MahasiswaController(
            MahasiswaService mahasiswaService,
            DosenService dosenService,
            PengajuanService pengajuanService,
            JadwalService jadwalService,
            AuthService authService) {

        this.mahasiswaService = mahasiswaService;
        this.dosenService = dosenService;
        this.pengajuanService = pengajuanService;
        this.jadwalService = jadwalService;
        this.authService = authService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        Long userId = (Long) session.getAttribute("userId");

        Mahasiswa mahasiswa =
                mahasiswaService.getByUserId(userId);

        List<PengajuanBimbingan> pengajuanList =
                pengajuanService.getByMahasiswa(mahasiswa)
                        .stream()
                        .filter(p ->
                                !"SELESAI".equals(p.getStatus()))
                        .toList();

        List<JadwalBimbingan> jadwalMendatang =
                jadwalService.getJadwalMendatang(mahasiswa);

        model.addAttribute("pengajuanList", pengajuanList);

        model.addAttribute("jumlahPengajuan",
                pengajuanList.size());

        model.addAttribute("jadwalMendatang",
                jadwalMendatang);

        model.addAttribute("jumlahJadwalMendatang",
                jadwalMendatang.size());

        return "mahasiswa/dashboard";
    }

    @GetMapping("/ajukan-bimbingan")
    public String ajukanBimbinganPage(Model model) {

        model.addAttribute(
                "daftarDosen",
                dosenService.getAllDosen()
        );

        return "mahasiswa/ajukan-bimbingan";
    }

    @PostMapping("/ajukan-bimbingan")
    public String ajukanBimbingan(
            HttpSession session,
            @RequestParam Long dosenId,
            @RequestParam String judul,
            @RequestParam String topik,
            Model model) {

        Long userId = (Long) session.getAttribute("userId");

        Mahasiswa mahasiswa =
                mahasiswaService.getByUserId(userId);

        Dosen dosen =
                dosenService.getAllDosen()
                        .stream()
                        .filter(d -> d.getId().equals(dosenId))
                        .findFirst()
                        .orElseThrow(() ->
                                new RuntimeException("Dosen tidak ditemukan"));

        pengajuanService.ajukan(
                mahasiswa,
                dosen,
                judul,
                topik
        );

        model.addAttribute(
                "success",
                "Pengajuan bimbingan berhasil dikirim!"
        );

        model.addAttribute(
                "daftarDosen",
                dosenService.getAllDosen()
        );

        return "mahasiswa/ajukan-bimbingan";
    }

    @GetMapping("/jadwal")
    public String jadwal(HttpSession session,
                         Model model) {

        Long userId =
                (Long) session.getAttribute("userId");

        Mahasiswa mahasiswa =
                mahasiswaService.getByUserId(userId);

        List<JadwalBimbingan> jadwalList =
                jadwalService.getByMahasiswa(mahasiswa);

        model.addAttribute("jadwalList", jadwalList);

        return "mahasiswa/jadwal";
    }

    @GetMapping("/riwayat")
    public String riwayat(HttpSession session, Model model) {

        Long userId = (Long) session.getAttribute("userId");

        Mahasiswa mahasiswa =
                mahasiswaService.getByUserId(userId);

        List<PengajuanBimbingan> pengajuanList =
                pengajuanService.getByMahasiswa(mahasiswa)
                        .stream()
                        .filter(p ->
                                "DITOLAK".equals(p.getStatus()) ||
                                        "DIBATALKAN".equals(p.getStatus()) ||
                                        "SELESAI".equals(p.getStatus()))
                        .toList();

        List<JadwalBimbingan> riwayatJadwal =
                jadwalService.getRiwayatMahasiswa(mahasiswa);

        model.addAttribute("pengajuanList", pengajuanList);
        model.addAttribute("riwayatJadwal", riwayatJadwal);

        return "mahasiswa/riwayat";
    }
    @GetMapping("/profil")
    public String profil(HttpSession session,
                         Model model) {

        Long userId =
                (Long) session.getAttribute("userId");

        Mahasiswa mahasiswa =
                mahasiswaService.getByUserId(userId);

        model.addAttribute("mahasiswa", mahasiswa);

        return "mahasiswa/profil";
    }

    @PostMapping("/profil/update")
    public String updateProfil(
            HttpSession session,
            @RequestParam String nama,
            @RequestParam String email,
            @RequestParam String noHp,
            @RequestParam String nim,
            @RequestParam String prodi,
            @RequestParam Integer semester
    ) {

        Long userId = (Long) session.getAttribute("userId");

        mahasiswaService.updateProfil(
                userId,
                nama,
                email,
                noHp,
                nim,
                prodi,
                semester
        );

        return "redirect:/mahasiswa/profil";
    }

    @PostMapping("/password")
    public String ubahPassword(
            HttpSession session,
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            Model model) {

        Long userId =
                (Long) session.getAttribute("userId");

        try {

            authService.ubahPassword(
                    userId,
                    oldPassword,
                    newPassword
            );

            model.addAttribute(
                    "successPassword",
                    "Password berhasil diubah"
            );

        } catch (Exception e) {

            model.addAttribute(
                    "errorPassword",
                    e.getMessage()
            );
        }

        Mahasiswa mahasiswa =
                mahasiswaService.getByUserId(userId);

        model.addAttribute("mahasiswa", mahasiswa);

        return "mahasiswa/profil";
    }
}