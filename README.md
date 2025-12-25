# SIMORG

**Smart Inventory Management for Organization**

SIMORG adalah aplikasi desktop berbasis **Java Swing** yang dikembangkan untuk membantu organisasi mengelola data inventaris secara terstruktur. Aplikasi ini mendukung operasi CRUD, sorting, searching, dan penyimpanan data permanen menggunakan **File Handling (.csv)**.

---

## 📁 Struktur Project

```
simorg-java-inventory/
├── src/main/java/com/simorg/
│   ├── app/
│   │   └── Main.java                  ← Entry point aplikasi
│   ├── model/
│   │   └── Item.java                  ← Model barang (OOP + CSV parse)
│   ├── controller/
│   │   └── ItemController.java        ← CRUD + search + sort items
│   ├── util/
│   │   ├── FileHandler.java           ← Read/write CSV
│   │   └── IdGenerator.java           ← Generate unique ID
│   └── view/
│       ├── MainFrame.java             ← Frame utama + CardLayout navigasi
│       ├── DashboardPanel.java        ← Statistik + quick actions
│       ├── ItemListPanel.java         ← JTable + sorting + searching
│       ├── ItemFormPanel.java         ← Form tambah/edit barang
│       └── ReportPanel.java           ← Laporan ringkasan
├── data/
│   └── items.csv                      ← Data inventaris
└── README.md                          ← Dokumentasi lengkap
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
- ✅ Tabel data dengan fitur **sorting** dan **searching**
- ✅ Form input dengan validasi data
- ✅ Halaman laporan ringkasan
- ✅ Penyimpanan data permanen dalam format `.csv`
- ✅ Exception handling untuk berbagai skenario error

---

## 🖥️ Struktur Halaman (4 Screens)

### 1. Dashboard

Menampilkan ringkasan statistik (total jenis barang, total quantity) dan quick access buttons.

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

### 4. Laporan (ReportPanel)

Menampilkan:

- Statistik ringkasan inventaris
- Breakdown per kategori

---

## 📦 Penjelasan Package

| Package                 | Fungsi                     | Class                                                                          |
| ----------------------- | -------------------------- | ------------------------------------------------------------------------------ |
| `com.simorg.app`        | Entry point aplikasi       | `Main.java`                                                                    |
| `com.simorg.model`      | Data class / entity (OOP)  | `Item.java`                                                                    |
| `com.simorg.view`       | UI components (Java Swing) | `MainFrame`, `DashboardPanel`, `ItemListPanel`, `ItemFormPanel`, `ReportPanel` |
| `com.simorg.controller` | Business logic             | `ItemController.java`                                                          |
| `com.simorg.util`       | Helper/utilities           | `FileHandler.java`, `IdGenerator.java`                                         |

---

## 💾 Format File CSV

### items.csv

```csv
id,name,category,quantity,condition,location,dateAdded,description
ITM1734847200001,Laptop Dell Inspiron,Elektronik,5,Baik,Ruang IT,2024-12-01,Laptop untuk keperluan kerja staff
```

---

## 🔄 Alur Kerja Aplikasi

```
[Dashboard]
    ├── Klik "Tambah Barang" → [ItemFormPanel] → Submit → Data tersimpan ke items.csv
    ├── Klik "Lihat Inventaris" → [ItemListPanel] → Edit/Hapus → Update items.csv
    └── Klik "Lihat Laporan" → [ReportPanel] → Statistik dari CSV
```

---

## ⚠️ Exception Handling

Aplikasi menerapkan penanganan error untuk:

- Validasi input (nama kosong, angka tidak valid)
- File tidak ditemukan (auto-create)
- Format CSV tidak valid
- IOException saat read/write file

---

## 🚀 Cara Menjalankan

### Menggunakan Maven

```bash
cd simorg-java-inventory
mvn compile exec:java
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

---

## 📊 Arsitektur Aplikasi

<p align="center">
  <img src="docs/architecture_diagram.png" alt="Architecture Diagram SIMORG" width="800">
</p>

<p align="center">
  <em>Diagram arsitektur aplikasi SIMORG dengan pola MVC (Model-View-Controller)</em>
</p>
