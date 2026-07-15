package com.example.sistembimbingan.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dosen")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dosen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private User user;

    @Column(name = "nip")
    private String nip;

    @Column(name = "bidang_keahlian")
    private String bidangKeahlian;
}