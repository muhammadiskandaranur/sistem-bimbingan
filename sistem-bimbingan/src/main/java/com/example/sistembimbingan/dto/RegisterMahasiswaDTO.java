package com.example.sistembimbingan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterMahasiswaDTO {

    private String nama;
    private String nim;
    private String prodi;
    private Integer semester;
    private String email;
    private String noHp;
    private String password;
}