package com.example.sistembimbingan.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "jadwal_bimbingan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JadwalBimbingan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pengajuan_id", nullable = false)
    private PengajuanBimbingan pengajuan;

    @ManyToOne
    @JoinColumn(name = "mahasiswa_id", nullable = false)
    private Mahasiswa mahasiswa;

    @ManyToOne
    @JoinColumn(name = "dosen_id", nullable = false)
    private Dosen dosen;

    @Column(name = "tanggal", nullable = false)
    private LocalDate tanggal;

    @Column(name = "jam_mulai", nullable = false)
    private LocalTime jamMulai;

    @Column(name = "jam_selesai", nullable = false)
    private LocalTime jamSelesai;

    @Column(nullable = false)
    private String tempat;

    @Column(columnDefinition = "TEXT")
    private String catatan;

    @Column(nullable = false)
    private String status; // TERJADWAL, SELESAI, DIBATALKAN
}