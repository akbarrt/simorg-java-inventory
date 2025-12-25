package com.simorg.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

import com.simorg.controller.ItemController;
import com.simorg.controller.LoanController;
import com.simorg.model.Item;
import com.simorg.model.Loan;
import com.simorg.model.Loan.LoanStatus;

public class LoanListPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> filterComboBox;

    // Controllers
    private LoanController loanController;
    private ItemController itemController;

    // Buttons
    private JButton refreshBtn, addBtn, returnBtn, deleteBtn;

    public LoanListPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(236, 240, 241));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Title
        JLabel titleLabel = new JLabel("Data Peminjaman");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(44, 62, 80));
        add(titleLabel, BorderLayout.NORTH);

        // Control panel
        add(createControlPanel(), BorderLayout.CENTER);

        setupButtonActions();
    }

    private JPanel createControlPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Top controls
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Left controls
        JPanel leftControls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftControls.setOpaque(false);

        JLabel searchLabel = new JLabel("Cari:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        leftControls.add(searchLabel);

        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        leftControls.add(searchField);

        JLabel filterLabel = new JLabel("Filter:");
        filterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        leftControls.add(filterLabel);

        filterComboBox = new JComboBox<>(new String[] { "Semua", "Aktif", "Selesai", "Terlambat" });
        filterComboBox.setPreferredSize(new Dimension(120, 30));
        leftControls.add(filterComboBox);

        refreshBtn = new JButton("Refresh");
        refreshBtn.setPreferredSize(new Dimension(100, 32));
        refreshBtn.setBackground(new Color(52, 73, 94));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setBorderPainted(false);
        leftControls.add(refreshBtn);

        topPanel.add(leftControls, BorderLayout.WEST);

        // Right control (add button)
        addBtn = new JButton("Pinjam");
        addBtn.setPreferredSize(new Dimension(120, 35));
        addBtn.setBackground(new Color(52, 152, 219));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        addBtn.setFocusPainted(false);
        addBtn.setBorderPainted(false);
        topPanel.add(addBtn, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Table
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);

        // Bottom buttons
        mainPanel.add(createBottomPanel(), BorderLayout.SOUTH);

        return mainPanel;
    }

    private JScrollPane createTablePanel() {
        String[] columns = { "ID", "ID Barang", "Nama Barang", "Peminjam", "Jumlah",
                "Tgl Pinjam", "Jatuh Tempo", "Tgl Kembali", "Status" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(189, 195, 199));
        table.setGridColor(new Color(220, 220, 220));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ToolTipManager.sharedInstance().unregisterComponent(table.getTableHeader());

        // === HEADER STYLING (STABIL) ===
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 38));
        header.setReorderingAllowed(false);

        header.setDefaultRenderer((tbl, value, isSelected, hasFocus, row, col) -> {
            JLabel lbl = new JLabel(value.toString());
            lbl.setOpaque(true);
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            lbl.setBackground(new Color(213, 96, 33)); // orange
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
            lbl.setBorder(BorderFactory.createMatteBorder(
                    0, 0, 1, 1, new Color(220, 220, 220)));
            return lbl;
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        return scrollPane;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        panel.setOpaque(false);

        returnBtn = createBottomButton("Kembalikan", new Color(46, 204, 113));
        deleteBtn = createBottomButton("Hapus", new Color(231, 76, 60));

        panel.add(returnBtn);
        panel.add(deleteBtn);

        return panel;
    }

    private JButton createBottomButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ===================== CRUD INTEGRATION =====================

    private void setupButtonActions() {
        refreshBtn.addActionListener(e -> refreshTable());
        addBtn.addActionListener(e -> showAddLoanDialog());
        returnBtn.addActionListener(e -> returnSelectedLoan());
        deleteBtn.addActionListener(e -> deleteSelectedLoan());

        // Filter listener
        filterComboBox.addActionListener(e -> performFilter());

        // Search listener
        searchField.addActionListener(e -> performSearch());
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                performSearch();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                performSearch();
            }

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                performSearch();
            }
        });
    }

    /**
     * Set controllers untuk panel ini.
     */
    public void setControllers(LoanController loanController, ItemController itemController) {
        this.loanController = loanController;
        this.itemController = itemController;
    }

    /**
     * Refresh data dari controller ke table.
     */
    public void refreshTable() {
        tableModel.setRowCount(0);

        if (loanController == null)
            return;

        List<Loan> loans = loanController.getAllLoans();
        for (Loan loan : loans) {
            String itemName = loanController.getItemNameById(loan.getItemId());
            tableModel.addRow(new Object[] {
                    loan.getId(),
                    loan.getItemId(),
                    itemName,
                    loan.getBorrowerName(),
                    loan.getQuantity(),
                    loan.getLoanDate() != null ? loan.getLoanDate().toString() : "",
                    loan.getDueDate() != null ? loan.getDueDate().toString() : "",
                    loan.getReturnDate() != null ? loan.getReturnDate().toString() : "",
                    loan.getStatus() != null ? loan.getStatus().getValue() : ""
            });
        }
    }

    /**
     * Perform filter by status.
     */
    private void performFilter() {
        if (loanController == null)
            return;

        String filter = (String) filterComboBox.getSelectedItem();
        List<Loan> loans = loanController.filterByStatus(filter);

        tableModel.setRowCount(0);
        for (Loan loan : loans) {
            String itemName = loanController.getItemNameById(loan.getItemId());
            tableModel.addRow(new Object[] {
                    loan.getId(),
                    loan.getItemId(),
                    itemName,
                    loan.getBorrowerName(),
                    loan.getQuantity(),
                    loan.getLoanDate() != null ? loan.getLoanDate().toString() : "",
                    loan.getDueDate() != null ? loan.getDueDate().toString() : "",
                    loan.getReturnDate() != null ? loan.getReturnDate().toString() : "",
                    loan.getStatus() != null ? loan.getStatus().getValue() : ""
            });
        }
    }

    /**
     * Perform search.
     */
    private void performSearch() {
        if (loanController == null)
            return;

        String keyword = searchField.getText().trim();
        List<Loan> loans = loanController.searchLoans(keyword);

        tableModel.setRowCount(0);
        for (Loan loan : loans) {
            String itemName = loanController.getItemNameById(loan.getItemId());
            tableModel.addRow(new Object[] {
                    loan.getId(),
                    loan.getItemId(),
                    itemName,
                    loan.getBorrowerName(),
                    loan.getQuantity(),
                    loan.getLoanDate() != null ? loan.getLoanDate().toString() : "",
                    loan.getDueDate() != null ? loan.getDueDate().toString() : "",
                    loan.getReturnDate() != null ? loan.getReturnDate().toString() : "",
                    loan.getStatus() != null ? loan.getStatus().getValue() : ""
            });
        }
    }

    /**
     * Show dialog untuk add loan baru.
     */
    private void showAddLoanDialog() {
        if (itemController == null || loanController == null) {
            JOptionPane.showMessageDialog(this, "Controller belum diinisialisasi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Item> items = itemController.getAllItems();
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada barang yang tersedia untuk dipinjam!", "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Create dialog components
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        JComboBox<String> itemCombo = new JComboBox<>();
        for (Item item : items) {
            itemCombo.addItem(item.getId() + " - " + item.getName());
        }

        JTextField borrowerField = new JTextField();
        JTextField contactField = new JTextField();
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JSpinner dueDaysSpinner = new JSpinner(new SpinnerNumberModel(7, 1, 365, 1));
        JTextArea notesArea = new JTextArea(3, 20);

        panel.add(new JLabel("Pilih Barang:"));
        panel.add(itemCombo);
        panel.add(new JLabel("Nama Peminjam:"));
        panel.add(borrowerField);
        panel.add(new JLabel("Kontak:"));
        panel.add(contactField);
        panel.add(new JLabel("Jumlah:"));
        panel.add(quantitySpinner);
        panel.add(new JLabel("Durasi (hari):"));
        panel.add(dueDaysSpinner);
        panel.add(new JLabel("Catatan:"));
        panel.add(new JScrollPane(notesArea));

        int result = JOptionPane.showConfirmDialog(this, panel, "Tambah Peminjaman",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String borrowerName = borrowerField.getText().trim();
            if (borrowerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama peminjam wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String selectedItem = (String) itemCombo.getSelectedItem();
            String itemId = selectedItem.split(" - ")[0];
            String contact = contactField.getText().trim();
            int quantity = (Integer) quantitySpinner.getValue();
            int dueDays = (Integer) dueDaysSpinner.getValue();
            String notes = notesArea.getText().trim();

            LocalDate dueDate = LocalDate.now().plusDays(dueDays);

            Loan newLoan = new Loan(itemId, borrowerName, contact, quantity, dueDate, notes);
            boolean success = loanController.addLoan(newLoan);

            if (success) {
                JOptionPane.showMessageDialog(this, "Peminjaman berhasil ditambahkan!\nID: " + newLoan.getId(),
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan peminjaman!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Get selected loan from table.
     */
    private Loan getSelectedLoan() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih peminjaman terlebih dahulu!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        String id = (String) tableModel.getValueAt(selectedRow, 0);
        return loanController != null ? loanController.getLoanById(id) : null;
    }

    /**
     * Return selected loan.
     */
    private void returnSelectedLoan() {
        Loan loan = getSelectedLoan();
        if (loan == null)
            return;

        if (loan.getStatus() == LoanStatus.DIKEMBALIKAN) {
            JOptionPane.showMessageDialog(this, "Barang sudah dikembalikan!",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Konfirmasi pengembalian barang oleh:\n" + loan.getBorrowerName(),
                "Konfirmasi Pengembalian",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = loanController.returnLoan(loan.getId());
            if (success) {
                JOptionPane.showMessageDialog(this, "Barang berhasil dikembalikan!",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengembalikan barang!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Delete selected loan.
     */
    private void deleteSelectedLoan() {
        Loan loan = getSelectedLoan();
        if (loan == null)
            return;

        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus record peminjaman ini?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = loanController.deleteLoan(loan.getId());
            if (success) {
                JOptionPane.showMessageDialog(this, "Record peminjaman berhasil dihapus!",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus record!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Getters
    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getTable() {
        return table;
    }
}
