# SIMORG  
**Smart Inventory Management for Organization**

SIMORG adalah aplikasi desktop berbasis **Java Swing** yang dikembangkan untuk membantu organisasi mengelola data inventaris secara terstruktur. Aplikasi ini mendukung operasi CRUD, sorting, searching, dan penyimpanan data permanen menggunakan **File Handling (.csv)**.

---

## 🎯 Tujuan Pengembangan
- Mengimplementasikan konsep **Object-Oriented Programming (OOP)**
- Mengembangkan aplikasi **GUI berbasis Java Swing**
- Melatih penggunaan **Git & GitHub** dalam kerja tim
- Menerapkan **File Handling** untuk penyimpanan data permanen
- Menerapkan **validasi input** dan **exception handling**

---

## 🛠️ Teknologi yang Digunakan
| Komponen | Detail |
|----------|--------|
| **Bahasa Pemrograman** | Java |
| **GUI Framework** | Java Swing |
| **Penyimpanan Data** | File Handling (.csv) |
| **Struktur Data** | ArrayList |
| **Utility API** | LocalDate, Comparator |
| **Version Control** | Git & GitHub |

---

## ✨ Fitur Aplikasi
- ✅ Dashboard dengan menu navigasi utama
- ✅ Manajemen inventaris (CRUD - Create, Read, Update, Delete)
- ✅ Tabel data dengan fitur **sorting** dan **searching**
- ✅ Form input untuk tambah dan edit data
- ✅ Halaman laporan dan riwayat data
- ✅ Penyimpanan data permanen dalam format `.csv`
- ✅ Validasi input dan exception handling

---

## 🖥️ Struktur Halaman (4 Screens)

### 1. Dashboard
Menampilkan menu navigasi utama aplikasi dan quick access ke fitur-fitur utama.

### 2. Halaman List Data
Menampilkan data inventaris dalam bentuk tabel (`JTable`) dengan fitur:
- Sorting berdasarkan kolom
- Searching/filtering data
- Action buttons (Edit, Delete)

### 3. Halaman Input Data
Form untuk menambah dan mengedit data inventaris dengan validasi input.

### 4. Halaman Laporan / History
Menampilkan ringkasan data dan riwayat transaksi inventaris.

---

## 💾 Penyimpanan Data

Data disimpan dalam format `.csv` dan tetap persisten setelah aplikasi ditutup.

**Contoh format file:**
```csv
id,nama,kategori,jumlah,kondisi
BRG01,Mic Wireless,Elektronik,2,Baik
BRG02,Proyektor,Elektronik,1,Baik
```

---

## ⚠️ Exception Handling

Aplikasi menerapkan penanganan error menggunakan try-catch untuk:
- Validasi input angka dan data
- Penanganan file tidak ditemukan
- Data kosong atau tidak valid
- IOException saat read/write file

---

## 👥 Tim Pengembang
- Mohamad Akbar Noviandi
- Figa Brilliant Daffa

---

## 🌿 Git Workflow

**Branching Strategy:**
- `feature-ui` - Pengembangan UI/GUI
- `feature-crud` - Fitur CRUD
- `feature-file-handling` - Fitur penyimpanan data
- `main` - Branch utama (production-ready)

**Practices:**
- Push dilakukan secara berkala
- Pull request untuk setiap fitur
- Code review sebelum merge ke main

---

## 🧪 Testing & Code Review

**Pengujian Manual:**
- Tambah, edit, hapus data
- Penyimpanan data ke file
- Validasi input (angka, teks, dll)
- Sorting dan searching

**Code Review:**
- Penamaan variabel dan method yang konsisten
- Duplikasi kode
- Struktur program dan best practices

---

## 📌 Informasi Proyek

| Detail | Keterangan |
|--------|-----------|
| **Tujuan** | Ujian Akhir Praktikum (UAP) |
| **Mata Kuliah** | Pemrograman Lanjut |
| **Institusi** | Universitas Muhammadiyah Malang |

---

## 📸 Dokumentasi

Screenshot UI, diagram class, dan database schema tersedia dalam folder dokumentasi atau laporan UAP.

---

## 📄 Lisensi
Project ini dibuat untuk keperluan akademik dan pembelajaran.

---

## 🏁 Penutup
SIMORG dikembangkan sebagai solusi sederhana namun efektif untuk manajemen inventaris organisasi, sekaligus sebagai media pembelajaran pemrograman Java berbasis OOP dan kolaborasi menggunakan GitHub.
