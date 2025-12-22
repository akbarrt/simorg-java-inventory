package com.simorg.view;

import com.simorg.controller.ItemController;
import com.simorg.controller.LoanController;
import com.simorg.model.Item;
import com.simorg.model.Loan;
import com.simorg.util.UIConstants;
import com.simorg.util.ValidationHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Panel Peminjaman - kelola data peminjaman barang
 * Menampilkan daftar peminjaman dan form input
 */
public class LoanListPanel extends JPanel {
    private MainFrame mainFrame;
    private LoanController loanController;
    private ItemController itemController;

    private JTable loanTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> filterCombo;

    private final String[] columns = { "ID", "ID Barang", "Nama Barang", "Peminjam", "Jumlah", "Tgl Pinjam",
            "Jatuh Tempo", "Tgl Kembali", "Status" };

    public LoanListPanel(MainFrame mainFrame, LoanController loanController, ItemController itemController) {
        this.mainFrame = mainFrame;
        this.loanController = loanController;
        this.itemController = itemController;

        setBackground(UIConstants.BACKGROUND_COLOR);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initComponents();
        refreshData();
    }

    private void initComponents() {
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel titleLabel = new JLabel("Data Peminjaman");
        titleLabel.setFont(UIConstants.TITLE_FONT);
        titleLabel.setForeground(UIConstants.TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JButton addButton = UIConstants.createButton("+ Pinjam Barang", UIConstants.PRIMARY_COLOR);
        addButton.addActionListener(e -> showLoanForm());
        headerPanel.add(addButton, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filterPanel.setOpaque(false);

        JLabel searchLabel = UIConstants.createLabel("Cari:");
        searchField = UIConstants.createTextField();
        searchField.setPreferredSize(new Dimension(200, 35));
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchData();
            }
        });

        JLabel filterLabel = UIConstants.createLabel("Filter:");
        filterCombo = new JComboBox<>(new String[] { "Semua", "Dipinjam", "Dikembalikan", "Terlambat" });
        filterCombo.setFont(UIConstants.REGULAR_FONT);
        filterCombo.addActionListener(e -> filterData());

        JButton refreshBtn = UIConstants.createButton("Refresh", UIConstants.SECONDARY_COLOR);
        refreshBtn.addActionListener(e -> refreshData());

        filterPanel.add(searchLabel);
        filterPanel.add(searchField);
        filterPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        filterPanel.add(filterLabel);
        filterPanel.add(filterCombo);
        filterPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        filterPanel.add(refreshBtn);

        // Table
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        loanTable = new JTable(tableModel);
        loanTable.setFont(UIConstants.REGULAR_FONT);
        loanTable.setRowHeight(30);
        loanTable.getTableHeader().setFont(UIConstants.BUTTON_FONT);
        loanTable.getTableHeader().setBackground(UIConstants.WARNING_COLOR);
        loanTable.getTableHeader().setForeground(Color.WHITE);
        loanTable.setSelectionBackground(UIConstants.WARNING_COLOR.brighter());
        loanTable.setGridColor(new Color(230, 230, 230));

        JScrollPane scrollPane = new JScrollPane(loanTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        actionPanel.setOpaque(false);

        JButton returnButton = UIConstants.createButton("Kembalikan", UIConstants.SUCCESS_COLOR);
        returnButton.addActionListener(e -> returnLoan());

        JButton deleteButton = UIConstants.createButton("Hapus", UIConstants.DANGER_COLOR);
        deleteButton.addActionListener(e -> deleteLoan());

        actionPanel.add(returnButton);
        actionPanel.add(deleteButton);

        // Center panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(filterPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(actionPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Tampilkan form peminjaman
     */
    private void showLoanForm() {
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        // Item selection
        ArrayList<Item> items = itemController.getAllItems();
        String[] itemOptions = items.stream()
                .map(i -> i.getId() + " - " + i.getName())
                .toArray(String[]::new);

        if (itemOptions.length == 0) {
            UIConstants.showError(this, "Tidak ada barang tersedia untuk dipinjam");
            return;
        }

        JComboBox<String> itemCombo = new JComboBox<>(itemOptions);
        JTextField borrowerField = new JTextField();
        JTextField contactField = new JTextField();
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JSpinner dueDaysSpinner = new JSpinner(new SpinnerNumberModel(7, 1, 365, 1));
        JTextArea notesArea = new JTextArea(3, 20);

        formPanel.add(new JLabel("Pilih Barang:"));
        formPanel.add(itemCombo);
        formPanel.add(new JLabel("Nama Peminjam:"));
        formPanel.add(borrowerField);
        formPanel.add(new JLabel("Kontak Peminjam:"));
        formPanel.add(contactField);
        formPanel.add(new JLabel("Jumlah:"));
        formPanel.add(quantitySpinner);
        formPanel.add(new JLabel("Durasi (hari):"));
        formPanel.add(dueDaysSpinner);
        formPanel.add(new JLabel("Catatan:"));
        formPanel.add(new JScrollPane(notesArea));

        int result = JOptionPane.showConfirmDialog(this, formPanel, "Form Peminjaman",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String selectedItem = (String) itemCombo.getSelectedItem();
                String itemId = selectedItem.split(" - ")[0];
                String borrower = borrowerField.getText().trim();
                String contact = contactField.getText().trim();
                int qty = (Integer) quantitySpinner.getValue();
                int days = (Integer) dueDaysSpinner.getValue();
                String notes = notesArea.getText().trim();

                ValidationHelper.validateNotEmpty(borrower, "Nama peminjam");

                Loan loan = new Loan();
                loan.setItemId(itemId);
                loan.setBorrowerName(borrower);
                loan.setBorrowerContact(contact.isEmpty() ? "-" : contact);
                loan.setQuantity(qty);
                loan.setLoanDate(LocalDate.now());
                loan.setDueDate(LocalDate.now().plusDays(days));
                loan.setNotes(notes.isEmpty() ? "-" : notes);

                loanController.addLoan(loan);
                refreshData();
                UIConstants.showSuccess(this, "Peminjaman berhasil dicatat!");

            } catch (Exception e) {
                UIConstants.showError(this, "Gagal: " + e.getMessage());
            }
        }
    }

    /**
     * Proses pengembalian
     */
    private void returnLoan() {
        int selectedRow = loanTable.getSelectedRow();
        if (selectedRow == -1) {
            UIConstants.showError(this, "Pilih peminjaman yang ingin dikembalikan");
            return;
        }

        String status = (String) tableModel.getValueAt(selectedRow, 8);
        if ("DIKEMBALIKAN".equals(status)) {
            UIConstants.showError(this, "Peminjaman ini sudah dikembalikan");
            return;
        }

        if (!UIConstants.showConfirm(this, "Konfirmasi pengembalian barang?")) {
            return;
        }

        String loanId = (String) tableModel.getValueAt(selectedRow, 0);

        try {
            loanController.returnLoan(loanId);
            refreshData();
            UIConstants.showSuccess(this, "Barang berhasil dikembalikan!");
        } catch (Exception e) {
            UIConstants.showError(this, "Gagal: " + e.getMessage());
        }
    }

    /**
     * Hapus data peminjaman
     */
    private void deleteLoan() {
        int selectedRow = loanTable.getSelectedRow();
        if (selectedRow == -1) {
            UIConstants.showError(this, "Pilih data yang ingin dihapus");
            return;
        }

        if (!UIConstants.showConfirm(this, "Yakin ingin menghapus data peminjaman ini?")) {
            return;
        }

        String loanId = (String) tableModel.getValueAt(selectedRow, 0);

        try {
            loanController.deleteLoan(loanId);
            refreshData();
            UIConstants.showSuccess(this, "Data berhasil dihapus");
        } catch (Exception e) {
            UIConstants.showError(this, "Gagal: " + e.getMessage());
        }
    }

    /**
     * Search data
     */
    private void searchData() {
        String keyword = searchField.getText().trim();
        ArrayList<Loan> loans = loanController.searchLoans(keyword);
        populateTable(loans);
    }

    /**
     * Filter berdasarkan status
     */
    private void filterData() {
        String filter = (String) filterCombo.getSelectedItem();
        ArrayList<Loan> loans;

        switch (filter) {
            case "Dipinjam":
                loans = loanController.getActiveLoans();
                break;
            case "Dikembalikan":
                loans = loanController.getReturnedLoans();
                break;
            case "Terlambat":
                loans = loanController.getOverdueLoans();
                break;
            default:
                loans = loanController.getAllLoans();
        }
        populateTable(loans);
    }

    /**
     * Populate table
     */
    private void populateTable(ArrayList<Loan> loans) {
        tableModel.setRowCount(0);
        for (Loan loan : loans) {
            Item item = itemController.getItemById(loan.getItemId());
            String itemName = item != null ? item.getName() : "Unknown";

            // Update status jika overdue
            String status = loan.getStatus();
            if ("DIPINJAM".equals(status) && loan.isOverdue()) {
                status = "TERLAMBAT";
            }

            tableModel.addRow(new Object[] {
                    loan.getId(),
                    loan.getItemId(),
                    itemName,
                    loan.getBorrowerName(),
                    loan.getQuantity(),
                    loan.getLoanDate().toString(),
                    loan.getDueDate().toString(),
                    loan.getReturnDate() != null ? loan.getReturnDate().toString() : "-",
                    status
            });
        }
    }

    /**
     * Refresh data
     */
    public void refreshData() {
        loanController.loadLoans();
        itemController.loadItems();
        filterCombo.setSelectedIndex(0);
        searchField.setText("");
        populateTable(loanController.getAllLoans());
    }
}
