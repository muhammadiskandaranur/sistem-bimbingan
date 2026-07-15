package com.example.sistembimbingan.controller;

import com.example.sistembimbingan.service.PengajuanService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pengajuan")
public class PengajuanController {

    private final PengajuanService pengajuanService;

    public PengajuanController(PengajuanService pengajuanService) {
        this.pengajuanService = pengajuanService;
    }

    @GetMapping("/terima/{id}")
    public String terima(@PathVariable Long id) {
        pengajuanService.terima(id);
        return "redirect:/dosen/pengajuan-masuk";
    }

    @GetMapping("/tolak/{id}")
    public String tolak(@PathVariable Long id) {
        pengajuanService.tolak(id);
        return "redirect:/dosen/pengajuan-masuk";
    }
}