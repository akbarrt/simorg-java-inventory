# SIMORG Project - Walkthrough

## ✅ Project Structure Created

```
simorg-java-inventory/
├── src/main/java/com/simorg/
│   ├── app/Main.java                  # Entry point
│   ├── model/
│   │   ├── Item.java                  # Data inventaris
│   │   └── Loan.java                  # Data peminjaman
│   ├── controller/
│   │   ├── ItemController.java        # CRUD + search + sort items
│   │   └── LoanController.java        # CRUD loans + return logic
│   ├── util/
│   │   ├── FileHandler.java           # CSV read/write operations
│   │   ├── UIConstants.java           # Warna, font, styling
│   │   ├── ItemComparators.java       # Sorting dengan Comparator
│   │   └── ValidationHelper.java      # Input validation + exceptions
│   └── view/
│       ├── MainFrame.java             # Frame utama + CardLayout
│       ├── DashboardPanel.java        # Statistik + quick actions
│       ├── ItemListPanel.java         # JTable + search + sort
│       ├── ItemFormPanel.java         # Form add/edit barang
│       ├── LoanListPanel.java         # Kelola peminjaman
│       └── ReportPanel.java           # Laporan + history
├── data/
│   ├── items.csv                      # Sample data inventaris
│   └── loans.csv                      # Sample data peminjaman
└── README.md                          # Updated documentation
```

---

## 📊 Fitur OOP yang Diterapkan

| Konsep            | Implementasi                              |
| ----------------- | ----------------------------------------- |
| **Encapsulation** | Private fields + getters/setters di model |
| **Inheritance**   | Panels extend JPanel                      |
| **Polymorphism**  | Comparator interface untuk sorting        |
| **Abstraction**   | Controller layer memisahkan logic dari UI |

---

## 🎯 Fitur Teknis

- **ArrayList** - Menyimpan data Item dan Loan
- **Comparator** - Sorting dengan `ItemComparators`
- **LocalDate** - Tanggal dengan java.time API
- **Exception Handling** - ValidationHelper + try-catch di controllers
- **File I/O** - FileHandler dengan java.nio.file

---

## 🚀 Cara Menjalankan

```bash
# Compile
javac -d out src/main/java/com/simorg/**/*.java

# Run
java -cp out com.simorg.app.Main
```

**Atau buka di IDE** → Run `Main.java`
