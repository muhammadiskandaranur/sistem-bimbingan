package com.example.sistembimbingan.controller;

import com.example.sistembimbingan.dto.LoginDTO;
import com.example.sistembimbingan.dto.RegisterDosenDTO;
import com.example.sistembimbingan.dto.RegisterMahasiswaDTO;
import com.example.sistembimbingan.entity.User;
import com.example.sistembimbingan.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    public String index(HttpSession session) {
        Object role = session.getAttribute("role");
        if (role == null) {
            return "redirect:/login";
        }
        if ("MAHASISWA".equals(role)) {
            return "redirect:/mahasiswa/dashboard";
        }
        return "redirect:/dosen/dashboard";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginDTO loginDTO, Model model, HttpSession session) {
        try {
            User user = authService.login(loginDTO);

            session.setAttribute("userId", user.getId());
            session.setAttribute("nama", user.getNama());
            session.setAttribute("email", user.getEmail());
            session.setAttribute("role", user.getRole());

            if ("MAHASISWA".equals(user.getRole())) {
                return "redirect:/mahasiswa/dashboard";
            } else {
                return "redirect:/dosen/dashboard";
            }
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("loginDTO", loginDTO);
            return "auth/login";
        }
    }

    @GetMapping("/register-mahasiswa")
    public String registerMahasiswaPage(Model model) {
        model.addAttribute("registerMahasiswaDTO", new RegisterMahasiswaDTO());
        return "auth/register-mahasiswa";
    }

    @PostMapping("/register-mahasiswa")
    public String registerMahasiswa(@ModelAttribute RegisterMahasiswaDTO dto, Model model) {
        try {
            authService.registerMahasiswa(dto);
            model.addAttribute("success", "Registrasi berhasil! Silakan login.");
            model.addAttribute("loginDTO", new LoginDTO());
            return "auth/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("registerMahasiswaDTO", dto);
            return "auth/register-mahasiswa";
        }
    }

    @GetMapping("/register-dosen")
    public String registerDosenPage(Model model) {
        model.addAttribute("registerDosenDTO", new RegisterDosenDTO());
        return "auth/register-dosen";
    }

    @PostMapping("/register-dosen")
    public String registerDosen(@ModelAttribute RegisterDosenDTO dto, Model model) {
        try {
            authService.registerDosen(dto);
            model.addAttribute("success", "Registrasi berhasil! Silakan login.");
            model.addAttribute("loginDTO", new LoginDTO());
            return "auth/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("registerDosenDTO", dto);
            return "auth/register-dosen";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}