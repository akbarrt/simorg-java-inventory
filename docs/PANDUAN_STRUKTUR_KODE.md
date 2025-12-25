# SIMORG - Panduan Struktur Kode

Dokumentasi untuk memahami setiap file dalam proyek SIMORG.

---

## Struktur Folder

```
src/main/java/com/simorg/
├── app/           → Entry point aplikasi
├── model/         → Class data (POJO)
├── controller/    → Logika bisnis & CRUD
├── util/          → Helper utilities
└── view/          → Tampilan UI Swing
```

---

## 📦 Package `app` - Entry Point

| File        | Deskripsi                               |
| ----------- | --------------------------------------- |
| `Main.java` | Titik awal program, memanggil MainFrame |

---

## 📦 Package `model` - Class Data

| File        | Deskripsi                                                                                 |
| ----------- | ----------------------------------------------------------------------------------------- |
| `Item.java` | Model barang inventaris (id, nama, kategori, jumlah, kondisi, lokasi, tanggal, deskripsi) |

**Catatan**: Model hanya berisi data + getter/setter, tidak ada logika bisnis.

---

## 📦 Package `controller` - Logika CRUD

| File                  | Deskripsi                                                   |
| --------------------- | ----------------------------------------------------------- |
| `ItemController.java` | CRUD untuk Item: tambah, edit, hapus, cari, sort, statistik |

**Method penting:**

- `addItem()` → Tambah data baru
- `updateItem()` → Edit data
- `deleteItem()` → Hapus data
- `searchItems()` → Cari berdasarkan keyword
- `loadFromFile()` / `saveToFile()` → Baca/tulis CSV

---

## 📦 Package `util` - Utilitas

| File               | Deskripsi                                         |
| ------------------ | ------------------------------------------------- |
| `IdGenerator.java` | Generate ID unik dengan format timestamp (ITM...) |
| `FileHandler.java` | Baca/tulis file CSV untuk persistensi data        |

---

## 📦 Package `view` - Tampilan UI

| File                  | Deskripsi                                            |
| --------------------- | ---------------------------------------------------- |
| `MainFrame.java`      | Frame utama dengan sidebar navigasi dan CardLayout   |
| `DashboardPanel.java` | Panel dashboard dengan stat cards dan quick actions  |
| `ItemListPanel.java`  | Tabel daftar barang dengan search, sort, edit, hapus |
| `ItemFormPanel.java`  | Form input/edit barang dengan validasi               |
| `ReportPanel.java`    | Panel laporan dengan ringkasan per kategori          |

---

## 📁 Folder `data`

| File        | Deskripsi                                 |
| ----------- | ----------------------------------------- |
| `items.csv` | Penyimpanan data barang (dibuat otomatis) |

---

## 🔄 Alur Program

```
1. Main.java                    → Start aplikasi
2. MainFrame                    → Inisialisasi controller & load data dari CSV
3. Controller.loadFromFile()   → Baca CSV ke ArrayList (memory)
4. User melakukan operasi      → CRUD di memory
5. Controller.saveToFile()     → Simpan ArrayList ke CSV (otomatis)
```

---

## 🚀 Cara Menjalankan

```bash
mvn compile exec:java
```

Atau manual:

```bash
java -cp target/classes com.simorg.app.Main
```
