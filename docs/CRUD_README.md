# CRUD Implementation - SIMORG

Dokumentasi implementasi CRUD operations untuk aplikasi inventory management SIMORG.

## Arsitektur

```mermaid
graph TD
    View[View Layer] --> Controller[Controller Layer]
    Controller --> Model[Model Layer]
    Controller --> Util[Utility Classes]
```

---

## Struktur File

### Model Layer

Model class untuk merepresentasikan data Item dan Loan.

#### Item.java

**Path**: `src/main/java/com/simorg/model/Item.java`

- Fields: `id`, `name`, `category`, `quantity`, `condition`, `location`, `dateAdded`, `description`
- Constructor, getters, setters
- Method `toCSVString()` dan `fromCSVString()` untuk file handling

#### Loan.java

**Path**: `src/main/java/com/simorg/model/Loan.java`

- Fields: `id`, `itemId`, `borrowerName`, `borrowerContact`, `quantity`, `loanDate`, `dueDate`, `returnDate`, `status`, `notes`
- Enum `LoanStatus`: DIPINJAM, DIKEMBALIKAN, TERLAMBAT
- Constructor, getters, setters, `toCSVString()`, `fromCSVString()`

---

### Utility Layer

#### IdGenerator.java

**Path**: `src/main/java/com/simorg/util/IdGenerator.java`

- Method `generateItemId()` - format: `ITMyyyyMMddHHmmssSSS`
- Method `generateLoanId()` - format: `LNyyyyMMddHHmmssSSS`

---

### Controller Layer

Controller class untuk manage data dalam memory (ArrayList).

#### ItemController.java

**Path**: `src/main/java/com/simorg/controller/ItemController.java`

- Field: `ArrayList<Item> items`
- Methods:
  - `addItem(Item)` - tambah item baru
  - `updateItem(String id, Item)` - update item berdasarkan ID
  - `deleteItem(String id)` - hapus item
  - `getItemById(String id)` - cari item berdasarkan ID
  - `getAllItems()` - ambil semua item
  - `searchItems(String keyword)` - cari berdasarkan nama/kategori
  - `sortItems(String field, boolean ascending)` - sorting
  - `getTotalQuantity()` - total quantity semua item

#### LoanController.java

**Path**: `src/main/java/com/simorg/controller/LoanController.java`

- Field: `ArrayList<Loan> loans`, referensi ke `ItemController`
- Methods:
  - `addLoan(Loan)` - tambah peminjaman baru
  - `returnLoan(String id)` - kembalikan barang (set returnDate & status)
  - `deleteLoan(String id)` - hapus record
  - `getLoanById(String id)` - cari loan berdasarkan ID
  - `getAllLoans()` - ambil semua loans
  - `getActiveLoans()` - filter status DIPINJAM
  - `getOverdueLoans()` - filter status TERLAMBAT
  - `filterByStatus(String status)` - filter berdasarkan status
  - `searchLoans(String keyword)` - cari berdasarkan nama peminjam

---

### View Integration

Integrasi UI dengan controller **tanpa mengubah layout/styling UI**.

| File                  | Perubahan                                               |
| --------------------- | ------------------------------------------------------- |
| `ItemFormPanel.java`  | Controller integration, save/update/clear functionality |
| `ItemListPanel.java`  | Search, sort, refresh, edit, delete, detail             |
| `LoanListPanel.java`  | Add loan dialog, return, delete, filter, search         |
| `DashboardPanel.java` | Dynamic statistics, navigation buttons                  |
| `ReportPanel.java`    | Dynamic stats, category summary, recent activities      |
| `MainFrame.java`      | Controller initialization, panel callbacks              |

---

## Cara Menjalankan

```bash
cd /home/manca/github/simorg-java-inventory
mvn compile exec:java
```

Atau menggunakan IDE:

```bash
java -cp target/classes com.simorg.view.MainFrame
```

---

## Manual Testing

1. **Test Create Item**

   - Buka menu "Tambah Barang"
   - Isi form dengan data valid
   - Klik "Simpan"
   - Buka "Data Inventaris" → item baru tampil di tabel

2. **Test Read/List Items**

   - Buka "Data Inventaris"
   - Test search field dengan keyword
   - Test sorting dropdown

3. **Test Update Item**

   - Pilih item di tabel → Klik "Edit"
   - Ubah data lalu simpan
   - Verifikasi perubahan di tabel

4. **Test Delete Item**

   - Pilih item di tabel → Klik "Hapus"
   - Konfirmasi dialog
   - Verifikasi item hilang dari tabel

5. **Test Loan Operations**

   - Buka "Peminjaman"
   - Klik "Pinjam" → tambah peminjaman baru
   - Pilih loan aktif → klik "Kembalikan"
   - Verifikasi status berubah

6. **Test Dashboard**
   - Buka Dashboard
   - Verifikasi statistik menampilkan data real
