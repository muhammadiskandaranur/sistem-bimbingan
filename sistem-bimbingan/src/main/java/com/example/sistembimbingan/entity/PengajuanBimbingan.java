package com.example.sistembimbingan.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "pengajuan_bimbingan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PengajuanBimbingan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mahasiswa_id", nullable = false)
    private Mahasiswa mahasiswa;

    @ManyToOne
    @JoinColumn(name = "dosen_id", nullable = false)
    private Dosen dosen;

    @Column(nullable = false)
    private String judul;

    @Column(columnDefinition = "TEXT")
    private String topik;

    @Column(nullable = false)
    private String status; // MENUNGGU, DITERIMA, DITOLAK, SELESAI

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "MENUNGGU";
        }
    }
}