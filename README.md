# Feature Branch: File Handling Implementation

Branch `feature-file-handling` ini fokus pada implementasi **Data Persistence** (penyimpanan data permanen) menggunakan file CSV. Fitur ini memungkinkan data inventaris tetap tersimpan meskipun aplikasi ditutup.

## 📋 Fitur Utama di Branch Ini

### 1. CSV Read/Write Operations

Mekanisme penyimpanan dan pembacaan data ke/dari file .csv lokal.

- **Auto-create Directory**: Membuat folder `data/` secara otomatis jika belum ada.
- **Load Items**: Membaca data item saat aplikasi dimulai.
- **Save Items**: Menyimpan perubahan data (add/edit/delete) secara real-time.
- **Robust Parsing**: Mencegah crash jika ada satu baris data yang corrupt (skip line).

---

## 🛠️ Implementasi Teknis

### Class `FileHandler`

**Path**: `src/main/java/com/simorg/util/FileHandler.java`

Ini adalah utility class static yang menangani semua operasi I/O file.

#### Format Data CSV

Header file `items.csv`:

```csv
id,name,category,quantity,condition,location,dateAdded,description
```

#### Method Utama

| Method                               | Deskripsi                                                                  |
| :----------------------------------- | :------------------------------------------------------------------------- |
| `ensureDataDirectory()`              | Mengecek dan membuat folder `data/` jika belum ada.                        |
| `loadItems(String path)`             | Membaca file CSV baris per baris, mem-parsing string menjadi objek `Item`. |
| `saveItems(String path, List<Item>)` | Mengkonversi list objek `Item` menjadi format CSV dan menulisnya ke file.  |

### Data Parsing Strategy

1.  **Reading (`fromCSVString`)**:

    - Membaca baris teks.
    - Split berdasarkan koma (`,`).
    - Handle exception per baris agar 1 data error tidak menggagalkan seluruh load process.

2.  **Writing (`toCSVString`)**:
    - Menggabungkan field object menjadi satu string CSV.
    - Menangani karakter kusus (jika ada, misal koma dalam deskripsi) - _Currently basic implementation_.

---

## ⚠️ Error Handling & Safety

Branch ini juga mengimplementasikan langkah-langkah keamanan data:

- **Try-with-resources**: Memastikan `BufferedReader` dan `BufferedWriter` selalu ditutup (mencegah memory leak).
- **Line Skipping**: Jika parsing gagal pada baris ke-X, baris tersebut dilewati, error dicetak ke log, dan lanjut ke baris berikutnya.
- **Directory Check**: Mencegah `FileNotFoundException` dengan selalu memastikan folder tujuan ada sebelum menulis.

---

## ✅ Status Implementasi

- [x] Create `data/` directory
- [x] Read `items.csv` -> `List<Item>`
- [x] Write `List<Item>` -> `items.csv`
- [x] Parse CSV String to Object
- [x] Convert Object to CSV String
- [x] Handle basic IO Exceptions
