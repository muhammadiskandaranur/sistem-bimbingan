package com.example.sistembimbingan.controller;

import com.example.sistembimbingan.entity.Dosen;
import com.example.sistembimbingan.service.AuthService;
import com.example.sistembimbingan.service.DosenService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfilController {

    private final DosenService dosenService;
    private final AuthService authService;

    public ProfilController(
            DosenService dosenService,
            AuthService authService
    ) {
        this.dosenService = dosenService;
        this.authService = authService;
    }

    // ===================== DOSEN =====================

    @GetMapping("/dosen/profil")
    public String profilDosen(HttpSession session, Model model) {

        Long userId = (Long) session.getAttribute("userId");

        Dosen dosen = dosenService.getByUserId(userId);

        model.addAttribute("dosen", dosen);

        return "dosen/profil";
    }

    @PostMapping("/dosen/profil/edit")
    public String editProfilDosen(
            HttpSession session,
            @RequestParam String nama,
            @RequestParam String email,
            @RequestParam String noHp,
            Model model) {

        Long userId = (Long) session.getAttribute("userId");

        Dosen dosen = dosenService.updateProfil(
                userId,
                nama,
                email,
                noHp
        );

        session.setAttribute("nama", nama);

        model.addAttribute("dosen", dosen);
        model.addAttribute("success", "Profil berhasil diperbarui!");

        return "dosen/profil";
    }

    @PostMapping("/dosen/profil/ubah-password")
    public String ubahPasswordDosen(
            HttpSession session,
            @RequestParam String passwordLama,
            @RequestParam String passwordBaru,
            Model model
    ) {

        Long userId = (Long) session.getAttribute("userId");

        Dosen dosen = dosenService.getByUserId(userId);

        try {

            authService.ubahPassword(
                    userId,
                    passwordLama,
                    passwordBaru
            );

            model.addAttribute(
                    "success",
                    "Password berhasil diubah!"
            );

        } catch (RuntimeException e) {

            model.addAttribute(
                    "error",
                    e.getMessage()
            );
        }

        model.addAttribute("dosen", dosen);

        return "dosen/profil";
    }
}