# SIMORG

**Smart Inventory Management for Organization**

SIMORG adalah aplikasi desktop berbasis **Java Swing** yang dikembangkan untuk membantu organisasi mengelola data inventaris dan peminjaman secara terstruktur. Aplikasi ini mendukung operasi CRUD, sorting, searching, dan penyimpanan data permanen menggunakan **File Handling (.csv)**.

---

## 📁 Struktur Project

```
simorg-java-inventory/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── simorg/
│       │           ├── app/                    # Entry point aplikasi
│       │           │   └── Main.java
│       │           ├── controller/             # Logic bisnis (MVC)
│       │           │   ├── ItemController.java
│       │           │   └── LoanController.java
│       │           ├── model/                  # Data classes (OOP)
│       │           │   ├── Item.java
│       │           │   └── Loan.java
│       │           ├── util/                   # Helper utilities
│       │           │   ├── FileHandler.java
│       │           │   ├── ItemComparators.java
│       │           │   ├── UIConstants.java
│       │           │   └── ValidationHelper.java
│       │           └── view/                   # Java Swing UI
│       │               ├── MainFrame.java
│       │               ├── DashboardPanel.java
│       │               ├── ItemListPanel.java
│       │               ├── ItemFormPanel.java
│       │               ├── LoanListPanel.java
│       │               └── ReportPanel.java
│       └── resources/
├── data/                                       # CSV data storage
│   ├── items.csv
│   └── loans.csv
├── README.md
└── LICENSE
```

---

## 🎯 Tujuan Pengembangan

- Mengimplementasikan konsep **Object-Oriented Programming (OOP)**
- Mengembangkan aplikasi **GUI berbasis Java Swing**
- Melatih penggunaan **Git & GitHub** dalam kerja tim
- Menerapkan **File Handling** untuk penyimpanan data permanen
- Menerapkan **validasi input** dan **exception handling**

---

## 🛠️ Teknologi yang Digunakan

| Komponen               | Detail                      |
| ---------------------- | --------------------------- |
| **Bahasa Pemrograman** | Java                        |
| **GUI Framework**      | Java Swing                  |
| **Arsitektur**         | MVC (Model-View-Controller) |
| **Penyimpanan Data**   | File Handling (.csv)        |
| **Struktur Data**      | ArrayList                   |
| **Utility API**        | LocalDate, Comparator       |
| **Version Control**    | Git & GitHub                |

---

## ✨ Fitur Aplikasi

- ✅ Dashboard dengan statistik dan quick actions
- ✅ Manajemen inventaris (CRUD - Create, Read, Update, Delete)
- ✅ Manajemen peminjaman barang
- ✅ Tabel data dengan fitur **sorting** dan **searching**
- ✅ Form input dengan validasi data
- ✅ Halaman laporan dan riwayat peminjaman
- ✅ Penyimpanan data permanen dalam format `.csv`
- ✅ Exception handling untuk berbagai skenario error

---

## 🖥️ Struktur Halaman (5 Screens)

### 1. Dashboard

Menampilkan ringkasan statistik (total barang, quantity, peminjaman aktif, terlambat) dan quick access buttons.

### 2. Data Inventaris (ItemListPanel)

Tabel data inventaris dengan fitur:

- Sorting berdasarkan nama, kategori, jumlah, tanggal
- Real-time searching/filtering
- Action buttons (Edit, Hapus, Detail)

### 3. Form Input Barang (ItemFormPanel)

Form untuk menambah dan mengedit data barang dengan:

- Validasi input wajib
- Kategori dropdown dengan opsi custom
- Auto-generated ID

### 4. Data Peminjaman (LoanListPanel)

Kelola peminjaman dengan fitur:

- Filter by status (Semua, Dipinjam, Dikembalikan, Terlambat)
- Form input peminjaman baru
- Proses pengembalian barang

### 5. Laporan (ReportPanel)

Menampilkan:

- Statistik ringkasan inventaris
- Breakdown per kategori
- Riwayat aktivitas peminjaman terbaru

---

## 📦 Penjelasan Package

| Package                 | Fungsi                     | Class                                                                                           |
| ----------------------- | -------------------------- | ----------------------------------------------------------------------------------------------- |
| `com.simorg.app`        | Entry point aplikasi       | `Main.java`                                                                                     |
| `com.simorg.model`      | Data class / entity (OOP)  | `Item.java`, `Loan.java`                                                                        |
| `com.simorg.view`       | UI components (Java Swing) | `MainFrame`, `DashboardPanel`, `ItemListPanel`, `ItemFormPanel`, `LoanListPanel`, `ReportPanel` |
| `com.simorg.controller` | Business logic             | `ItemController.java`, `LoanController.java`                                                    |
| `com.simorg.util`       | Helper/utilities           | `FileHandler`, `UIConstants`, `ItemComparators`, `ValidationHelper`                             |

---

## 💾 Format File CSV

### items.csv

```csv
id,name,category,quantity,condition,location,dateAdded,description
ITM1734847200001,Laptop Dell Inspiron,Elektronik,5,Baik,Ruang IT,2024-12-01,Laptop untuk keperluan kerja staff
```

### loans.csv

```csv
id,itemId,borrowerName,borrowerContact,quantity,loanDate,dueDate,returnDate,status,notes
LN1734847300001,ITM1734847200001,Ahmad Fauzi,081234567890,1,2024-12-15,2024-12-22,,DIPINJAM,Untuk presentasi
```

---

## 🔄 Alur Kerja Aplikasi

```
[Dashboard]
    ├── Klik "Tambah Barang" → [ItemFormPanel] → Submit → Data tersimpan ke items.csv
    ├── Klik "Lihat Inventaris" → [ItemListPanel] → Edit/Hapus → Update items.csv
    ├── Klik "Kelola Peminjaman" → [LoanListPanel] → Pinjam/Kembalikan → Update loans.csv
    └── Klik "Lihat Laporan" → [ReportPanel] → Statistik dari kedua CSV
```

---

## ⚠️ Exception Handling

Aplikasi menerapkan penanganan error untuk:

- Validasi input (nama kosong, angka tidak valid)
- File tidak ditemukan (auto-create)
- Format CSV tidak valid
- IOException saat read/write file
- Data duplikat atau tidak ditemukan

---

## 🚀 Cara Menjalankan

### Compile

```bash
cd simorg-java-inventory
javac -d out src/main/java/com/simorg/**/*.java
```

### Run

```bash
java -cp out com.simorg.app.Main
```

### Atau menggunakan IDE

1. Buka project di IntelliJ IDEA / Eclipse / NetBeans
2. Set `src/main/java` sebagai Source Root
3. Run `Main.java`

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

## 📌 Informasi Proyek

| Detail          | Keterangan                      |
| --------------- | ------------------------------- |
| **Tujuan**      | Ujian Akhir Praktikum (UAP)     |
| **Mata Kuliah** | Pemrograman Lanjut              |
| **Institusi**   | Universitas Muhammadiyah Malang |

---

## 📄 Lisensi

Project ini dibuat untuk keperluan akademik dan pembelajaran.
