package com.simorg.view;

import com.simorg.controller.ItemController;
import com.simorg.model.Item;
import com.simorg.util.UIConstants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panel List Data Inventaris
 * Menampilkan JTable dengan fitur sorting dan searching
 */
public class ItemListPanel extends JPanel {
    private MainFrame mainFrame;
    private ItemController itemController;

    private JTable itemTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> sortComboBox;
    private JCheckBox ascCheckBox;
    private TableRowSorter<DefaultTableModel> sorter;

    // Column names
    private final String[] columns = { "ID", "Nama", "Kategori", "Jumlah", "Kondisi", "Lokasi", "Tanggal",
            "Deskripsi" };

    public ItemListPanel(MainFrame mainFrame, ItemController itemController) {
        this.mainFrame = mainFrame;
        this.itemController = itemController;

        setBackground(UIConstants.BACKGROUND_COLOR);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initComponents();
        refreshData();
    }

    private void initComponents() {
        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel titleLabel = new JLabel("Data Inventaris");
        titleLabel.setFont(UIConstants.TITLE_FONT);
        titleLabel.setForeground(UIConstants.TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Add button
        JButton addButton = UIConstants.createButton("+ Tambah Barang", UIConstants.PRIMARY_COLOR);
        addButton.addActionListener(e -> mainFrame.showPanel(MainFrame.ITEM_FORM));
        headerPanel.add(addButton, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Search and filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filterPanel.setOpaque(false);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel searchLabel = UIConstants.createLabel("Cari:");
        searchField = UIConstants.createTextField();
        searchField.setPreferredSize(new Dimension(200, 35));
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterTable();
            }
        });

        JLabel sortLabel = UIConstants.createLabel("Urutkan:");
        sortComboBox = new JComboBox<>(new String[] { "Nama", "Kategori", "Jumlah", "Tanggal" });
        sortComboBox.setFont(UIConstants.REGULAR_FONT);
        sortComboBox.addActionListener(e -> sortData());

        ascCheckBox = new JCheckBox("Ascending", true);
        ascCheckBox.setFont(UIConstants.REGULAR_FONT);
        ascCheckBox.setOpaque(false);
        ascCheckBox.addActionListener(e -> sortData());

        JButton refreshBtn = UIConstants.createButton("Refresh", UIConstants.SECONDARY_COLOR);
        refreshBtn.addActionListener(e -> refreshData());

        filterPanel.add(searchLabel);
        filterPanel.add(searchField);
        filterPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        filterPanel.add(sortLabel);
        filterPanel.add(sortComboBox);
        filterPanel.add(ascCheckBox);
        filterPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        filterPanel.add(refreshBtn);

        // Table
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        itemTable = new JTable(tableModel);
        itemTable.setFont(UIConstants.REGULAR_FONT);
        itemTable.setRowHeight(30);
        itemTable.getTableHeader().setFont(UIConstants.BUTTON_FONT);
        itemTable.getTableHeader().setBackground(UIConstants.PRIMARY_COLOR);
        itemTable.getTableHeader().setForeground(Color.WHITE);
        itemTable.setSelectionBackground(UIConstants.PRIMARY_COLOR.brighter());
        itemTable.setGridColor(new Color(230, 230, 230));

        // Enable table sorting
        sorter = new TableRowSorter<>(tableModel);
        itemTable.setRowSorter(sorter);

        // Set column widths
        itemTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        itemTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        itemTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        itemTable.getColumnModel().getColumn(3).setPreferredWidth(60);
        itemTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        itemTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        itemTable.getColumnModel().getColumn(6).setPreferredWidth(90);
        itemTable.getColumnModel().getColumn(7).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(itemTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        // Action buttons panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        actionPanel.setOpaque(false);

        JButton editButton = UIConstants.createButton("Edit", UIConstants.WARNING_COLOR);
        editButton.addActionListener(e -> editSelectedItem());

        JButton deleteButton = UIConstants.createButton("Hapus", UIConstants.DANGER_COLOR);
        deleteButton.addActionListener(e -> deleteSelectedItem());

        JButton detailButton = UIConstants.createButton("Detail", UIConstants.SECONDARY_COLOR);
        detailButton.addActionListener(e -> showItemDetail());

        actionPanel.add(editButton);
        actionPanel.add(deleteButton);
        actionPanel.add(detailButton);

        // Center panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(filterPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(actionPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Filter tabel berdasarkan keyword search
     */
    private void filterTable() {
        String keyword = searchField.getText().toLowerCase();
        if (keyword.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword));
        }
    }

    /**
     * Sort data berdasarkan pilihan
     */
    private void sortData() {
        String field = (String) sortComboBox.getSelectedItem();
        boolean asc = ascCheckBox.isSelected();

        ArrayList<Item> sortedItems = itemController.sortItems(field, asc);
        populateTable(sortedItems);
    }

    /**
     * Isi tabel dengan data items
     */
    private void populateTable(ArrayList<Item> items) {
        tableModel.setRowCount(0);
        for (Item item : items) {
            tableModel.addRow(new Object[] {
                    item.getId(),
                    item.getName(),
                    item.getCategory(),
                    item.getQuantity(),
                    item.getCondition(),
                    item.getLocation(),
                    item.getDateAdded().toString(),
                    item.getDescription()
            });
        }
    }

    /**
     * Refresh data dari controller
     */
    public void refreshData() {
        itemController.loadItems();
        populateTable(itemController.getAllItems());
        searchField.setText("");
    }

    /**
     * Edit item yang dipilih
     */
    private void editSelectedItem() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow == -1) {
            UIConstants.showError(this, "Pilih item yang ingin diedit");
            return;
        }
        int modelRow = itemTable.convertRowIndexToModel(selectedRow);
        String itemId = (String) tableModel.getValueAt(modelRow, 0);
        mainFrame.showEditItemForm(itemId);
    }

    /**
     * Hapus item yang dipilih
     */
    private void deleteSelectedItem() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow == -1) {
            UIConstants.showError(this, "Pilih item yang ingin dihapus");
            return;
        }

        if (!UIConstants.showConfirm(this, "Yakin ingin menghapus item ini?")) {
            return;
        }

        int modelRow = itemTable.convertRowIndexToModel(selectedRow);
        String itemId = (String) tableModel.getValueAt(modelRow, 0);

        try {
            itemController.deleteItem(itemId);
            refreshData();
            UIConstants.showSuccess(this, "Item berhasil dihapus");
        } catch (Exception e) {
            UIConstants.showError(this, "Gagal menghapus: " + e.getMessage());
        }
    }

    /**
     * Tampilkan detail item
     */
    private void showItemDetail() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow == -1) {
            UIConstants.showError(this, "Pilih item untuk melihat detail");
            return;
        }

        int modelRow = itemTable.convertRowIndexToModel(selectedRow);
        String itemId = (String) tableModel.getValueAt(modelRow, 0);
        Item item = itemController.getItemById(itemId);

        if (item != null) {
            String detail = String.format(
                    "ID: %s\nNama: %s\nKategori: %s\nJumlah: %d\nKondisi: %s\nLokasi: %s\nTanggal Ditambahkan: %s\nDeskripsi: %s",
                    item.getId(), item.getName(), item.getCategory(), item.getQuantity(),
                    item.getCondition(), item.getLocation(), item.getDateAdded(), item.getDescription());
            JOptionPane.showMessageDialog(this, detail, "Detail Item", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
