# SIMORG  
**Smart Inventory Management for Organization**

SIMORG adalah aplikasi desktop berbasis **Java Swing** yang dikembangkan untuk membantu organisasi dalam mengelola data inventaris secara terstruktur. Aplikasi ini mendukung pengelolaan data barang, proses CRUD, serta penyimpanan data permanen menggunakan **File Handling (.csv)** sesuai dengan ketentuan Ujian Akhir Praktikum (UAP) mata kuliah Pemrograman Lanjut.

---

## 🎯 Tujuan Pengembangan
- Mengimplementasikan materi Modul 1–6 Pemrograman Lanjut
- Menerapkan konsep **Object-Oriented Programming (OOP)**
- Mengembangkan aplikasi **GUI berbasis Java Swing**
- Melatih penggunaan **Git & GitHub** dalam kerja tim
- Memenuhi seluruh spesifikasi teknis UAP

---

## 🛠️ Teknologi yang Digunakan
- **Bahasa Pemrograman** : Java  
- **GUI Framework** : Java Swing  
- **Penyimpanan Data** : File Handling (.csv)  
- **Struktur Data** : ArrayList  
- **Utility API** :
  - `LocalDate` untuk tanggal
  - `Comparator` untuk sorting data
- **Version Control** : Git & GitHub  

---

## 📌 Fitur Aplikasi
- Dashboard sebagai halaman utama dan navigasi
- Manajemen data inventaris (CRUD)
- Tabel data dengan fitur **sorting** dan **searching**
- Form input untuk tambah dan edit data
- Halaman laporan / riwayat data
- Penyimpanan data permanen menggunakan file `.csv`
- Validasi input dan **Exception Handling**

---

## 🖥️ Struktur Halaman (Minimal 4 Screen)
1. **Dashboard**  
   Menampilkan menu navigasi utama aplikasi.

2. **Halaman List Data**  
   Menampilkan data inventaris dalam bentuk tabel (`JTable`) dengan fitur sorting dan searching.

3. **Halaman Input Data**  
   Form untuk menambah dan mengedit data inventaris.

4. **Halaman Laporan / History**  
   Menampilkan ringkasan data atau riwayat transaksi.

---

## 💾 Penyimpanan Data
Aplikasi menggunakan **File Handling** dengan format `.csv`.  
Data disimpan secara permanen dan tetap tersedia meskipun aplikasi ditutup dan dibuka kembali.

Contoh format file:
```csv
id,nama,kategori,jumlah,kondisi
BRG01,Mic Wireless,Elektronik,2,Baik
## ⚠️ Exception Handling
Aplikasi menerapkan penanganan error menggunakan try-catch, antara lain:
Validasi input angka
File tidak ditemukan
Data kosong atau tidak valid
## 👥 Tim Pengembang
- Mohamad Akbar Noviandi
- Figa Brilliant Daffa
## 🌿 Git Workflow
Repository dikelola menggunakan GitHub
Push dilakukan secara berkala
Menggunakan branching fitur:
feature-ui
feature-crud
feature-file-handling
Seluruh fitur digabungkan ke branch main
Repository dilengkapi dengan file README.md
## 🧪 Testing & Code Review
Pengujian dilakukan secara manual
Skenario testing meliputi:
Tambah, edit, hapus data
Penyimpanan data ke file
Validasi input
Code review dilakukan untuk:
Penamaan variabel
Duplikasi kode
Struktur program
## 📌 Catatan
Project ini dibuat untuk memenuhi Ujian Akhir Praktikum (UAP)
Mata Kuliah: Pemrograman Lanjut
Universitas Muhammadiyah Malang

---

## 📸 Dokumentasi
> Screenshot UI, diagram class, dan database schema dapat dilihat pada folder dokumentasi atau pada laporan UAP.

---

## 📄 Lisensi
Project ini dibuat untuk keperluan akademik dan pembelajaran.

---

## 🏁 Penutup
SIMORG dikembangkan sebagai solusi sederhana namun efektif untuk manajemen inventaris organisasi, sekaligus sebagai media pembelajaran pemrograman Java berbasis OOP dan kolaborasi menggunakan GitHub.
