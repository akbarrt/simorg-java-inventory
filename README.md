# Feature Branch: UI Implementation

Branch `feature-ui` ini fokus pada implementasi **User Interface (GUI)** menggunakan **Java Swing**. Branch ini berisi semua komponen visual aplikasi SIMORG tanpa logika bisnis (controller/model).

## 📋 Fitur Utama di Branch Ini

### 1. Main Application Window

**Class**: `MainFrame.java`

Frame utama aplikasi dengan komponen:

- **Sidebar Navigation** - Menu navigasi dengan 5 panel utama
- **Header Bar** - Menampilkan judul aplikasi dan info user
- **Content Area** - Area konten dinamis menggunakan `CardLayout`

### 2. Panel-Panel Utama

#### Dashboard Panel

**File**: `DashboardPanel.java`

Halaman utama yang menampilkan:

- Statistik ringkasan (total barang, kategori, dll)
- Quick action buttons
- Welcome message

#### Item List Panel

**File**: `ItemListPanel.java`

Panel untuk menampilkan data inventaris dalam bentuk tabel:

- `JTable` untuk menampilkan data
- Search bar untuk filtering
- Sorting dropdown
- Action buttons (Edit, Delete, Detail)

#### Item Form Panel

**File**: `ItemFormPanel.java`

Form input untuk menambah/edit barang:

- Text fields untuk input data
- Dropdown kategori
- Date picker
- Buttons (Simpan, Reset)

#### Loan List Panel

**File**: `LoanListPanel.java`

Panel manajemen peminjaman:

- Tabel data peminjaman
- Filter berdasarkan status
- Action buttons (Pinjam, Kembalikan, Hapus)

#### Report Panel

**File**: `ReportPanel.java`

Halaman laporan dan statistik:

- Ringkasan data inventaris
- Breakdown per kategori
- Grafik/chart (jika ada)

---

## 🎨 Design System

### Color Palette

```java
// Sidebar
Background: #2D3440 (Dark Blue-Gray)
Active Button: #3C4655
Hover: #373E4A

// Content Area
Background: #FFFFFF (White)
Text: #2C3E50 (Dark Gray)
Accent: #3498DB (Blue)
```

### Typography

- **Font Family**: Segoe UI
- **Title**: Bold, 22px
- **Menu**: Plain, 14px
- **Content**: Plain, 13-14px

### Layout Structure

```
┌─────────────────────────────────────────┐
│           Header Bar (50px)             │
├──────────┬──────────────────────────────┤
│          │                              │
│ Sidebar  │      Content Panel           │
│ (220px)  │      (CardLayout)            │
│          │                              │
│          │                              │
└──────────┴──────────────────────────────┘
```

---

## 🛠️ Implementasi Teknis

### Navigation System

Menggunakan **CardLayout** untuk switching antar panel:

```java
CardLayout cardLayout = new CardLayout();
JPanel contentPanel = new JPanel(cardLayout);

// Add panels
contentPanel.add(dashboardPanel, "Dashboard");
contentPanel.add(itemListPanel, "Data Inventaris");

// Switch panel
cardLayout.show(contentPanel, "Dashboard");
```

### Event Handling

- **Button Click**: `ActionListener` untuk navigasi dan aksi
- **Mouse Hover**: `MouseAdapter` untuk efek hover pada sidebar
- **Table Selection**: `ListSelectionListener` untuk aksi pada baris tabel

### Component Styling

Semua komponen di-styling secara manual menggunakan:

- `setBackground()`, `setForeground()`
- `setFont()`
- `setBorder()`
- Custom `Dimension` untuk sizing

---

## 📐 Panel Specifications

| Panel              | Komponen Utama                 | Ukuran/Layout           |
| ------------------ | ------------------------------ | ----------------------- |
| **MainFrame**      | Sidebar, Header, Content       | BorderLayout (1400x800) |
| **DashboardPanel** | Stats Cards, Buttons           | GridLayout / FlowLayout |
| **ItemListPanel**  | JTable, JScrollPane, Search    | BorderLayout            |
| **ItemFormPanel**  | JTextField, JComboBox, JButton | GridBagLayout           |
| **LoanListPanel**  | JTable, Filter Buttons         | BorderLayout            |
| **ReportPanel**    | Labels, Stats Display          | BoxLayout               |

---

## ✅ Status Implementasi

- [x] Main window structure (MainFrame)
- [x] Sidebar navigation with hover effects
- [x] Header bar
- [x] Dashboard panel layout
- [x] Item list panel with table
- [x] Item form panel with input fields
- [x] Loan list panel
- [x] Report panel
- [x] CardLayout navigation system
- [x] Consistent color scheme & typography

---

## 🚀 Cara Menjalankan (UI Only)

```bash
cd simorg-java-inventory
javac -d bin src/main/java/com/simorg/view/*.java
java -cp bin com.simorg.view.MainFrame
```

> **Note**: Branch ini hanya berisi UI components. Untuk fitur lengkap (dengan data & logic), merge dengan branch `feature-crud` dan `feature-file-handling`.

---

## 🎯 Next Steps

Setelah UI selesai, branch ini akan di-merge dengan:

1. `feature-crud` - Untuk menambahkan logika CRUD
2. `feature-file-handling` - Untuk menambahkan persistence data
3. `main` - Final integration
