# 🎓 Sistem Informasi Jadwal Bimbingan Mahasiswa

Sistem Informasi Jadwal Bimbingan Mahasiswa merupakan aplikasi berbasis web yang dirancang untuk membantu proses pengajuan, penjadwalan, dan pengelolaan bimbingan akademik antara mahasiswa dan dosen secara lebih terstruktur, efisien, dan terdokumentasi.

Aplikasi ini dibangun menggunakan **Spring Boot**, **Thymeleaf**, **Bootstrap**, dan **MySQL** dengan menerapkan arsitektur **MVC (Model-View-Controller)**.



## ✨ Fitur Utama

### 👨‍🎓 Mahasiswa

* Registrasi akun mahasiswa
* Login menggunakan email
* Mengajukan bimbingan
* Melihat status pengajuan
* Melihat jadwal bimbingan
* Melihat riwayat bimbingan
* Mengelola profil akun
* Mengubah password

### 👨‍🏫 Dosen

* Registrasi akun dosen
* Login menggunakan email
* Melihat pengajuan bimbingan masuk
* Menerima pengajuan mahasiswa
* Menentukan jadwal bimbingan
* Menyelesaikan bimbingan
* Menjadwalkan ulang bimbingan yang dibatalkan
* Melihat riwayat bimbingan
* Mengelola profil akun
* Mengubah password



## 🛠️ Teknologi yang Digunakan

### Backend

* Java 25
* Spring Boot
* Spring Data JPA
* Hibernate

### Frontend

* Thymeleaf
* Bootstrap 5
* Bootstrap Icons
* HTML5
* CSS3

### Database

* MySQL

### Tools

* IntelliJ IDEA
* Maven
* XAMPP
* Git & GitHub



## 📂 Struktur Proyek

   text
src
├── controller
├── service
├── repository
├── entity
├── dto
├── util
├── templates
├── static
└── resources


### Penjelasan Folder

| Folder     | Fungsi                                  |
| ---------- | --------------------------------------- |
| controller | Mengelola request dan response pengguna |
| service    | Menyimpan logika bisnis aplikasi        |
| repository | Menghubungkan aplikasi dengan database  |
| entity     | Representasi tabel database             |
| dto        | Transfer data antar layer               |
| util       | Utility seperti enkripsi password       |
| templates  | Halaman web Thymeleaf                   |
| static     | CSS, JavaScript, dan aset lainnya       |



## 🔄 Alur Sistem

### Pengajuan Bimbingan

   text
Mahasiswa
    ↓
Ajukan Bimbingan
    ↓
Dosen Menerima Pengajuan
    ↓
Dosen Menentukan Jadwal
    ↓
Status TERJADWAL
    ↓
Bimbingan Dilaksanakan
    ↓
Status SELESAI
    ↓
Masuk Riwayat Bimbingan


### Penjadwalan Ulang

   text
Dosen
    ↓
Batalkan Jadwal
    ↓
Status JADWAL_DIUBAH
    ↓
Mahasiswa Mendapat Informasi
    ↓
Dosen Menentukan Jadwal Baru
    ↓
Status TERJADWAL




## 🗄️ Database

Database yang digunakan:

   sql
db_bimbingan


Tabel utama:

* users
* mahasiswa
* dosen
* pengajuan_bimbingan
* jadwal_bimbingan



## 🚀 Cara Menjalankan Proyek

### Clone Repository

   bash
git clone https://github.com/username/sistem-bimbingan.git


### Masuk ke Folder

   bash
cd sistem-bimbingan


### Konfigurasi Database

Buat database:

   sql
CREATE DATABASE db_bimbingan;


### Atur File application.properties

   properties
spring.datasource.url=jdbc:mysql://localhost:3306/db_bimbingan
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update


### Jalankan Aplikasi

   bash
mvn spring-boot:run


Aplikasi dapat diakses melalui:

   text
http://localhost:8080

## 👨‍💻 Pengembang

**Muhamad Iskandar Annur**

Program Studi Informatika



## 📄 Lisensi

Proyek ini dibuat untuk keperluan pembelajaran, pengembangan sistem informasi, dan tugas akademik.
