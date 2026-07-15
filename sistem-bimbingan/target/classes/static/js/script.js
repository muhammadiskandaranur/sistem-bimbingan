// ================================
// SISTEM BIMBINGAN MAHASISWA
// script.js
// ================================

// Konfirmasi logout
function konfirmasiLogout() {
    return confirm("Apakah Anda yakin ingin logout?");
}

// Konfirmasi hapus data
function konfirmasiHapus() {
    return confirm("Data yang dihapus tidak dapat dikembalikan. Lanjutkan?");
}

// Menampilkan notifikasi sukses
window.onload = function () {

    const success = document.getElementById("success-alert");

    if (success) {
        setTimeout(() => {
            success.style.display = "none";
        }, 3000);
    }

    const error = document.getElementById("error-alert");

    if (error) {
        setTimeout(() => {
            error.style.display = "none";
        }, 5000);
    }
};

// Preview tanggal hari ini
function setTanggalHariIni(idElement) {

    const today = new Date();

    const yyyy = today.getFullYear();

    let mm = today.getMonth() + 1;
    let dd = today.getDate();

    if (dd < 10) dd = '0' + dd;
    if (mm < 10) mm = '0' + mm;

    document.getElementById(idElement).value =
        yyyy + "-" + mm + "-" + dd;
}

// Validasi form pengajuan bimbingan
function validasiPengajuan() {

    let judul =
        document.getElementById("judul").value;

    let topik =
        document.getElementById("topik").value;

    if (judul.trim() === "") {

        alert("Judul bimbingan harus diisi");

        return false;
    }

    if (topik.trim() === "") {

        alert("Topik bimbingan harus diisi");

        return false;
    }

    return true;
}