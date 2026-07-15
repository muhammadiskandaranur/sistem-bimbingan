package com.example.sistembimbingan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDosenDTO {

    private String nama;
    private String email;
    private String noHp;
    private String password;
}