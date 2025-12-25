package com.simorg.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

import com.simorg.controller.ItemController;
import com.simorg.model.Item;

public class ItemListPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> sortComboBox;
    private JCheckBox ascendingCheckBox;

    // Controller
    private ItemController controller;

    // Buttons stored as fields
    private JButton refreshBtn, addBtn, editBtn, deleteBtn, detailBtn;

    // Callback untuk navigasi ke form panel
    private Runnable onAddCallback;
    private java.util.function.Consumer<Item> onEditCallback;

    private void configureColumnWidths() {
        table.getColumnModel().getColumn(0).setPreferredWidth(150); // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(180); // Nama
        table.getColumnModel().getColumn(2).setPreferredWidth(140); // Kategori
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // Jumlah
        table.getColumnModel().getColumn(4).setPreferredWidth(120); // Kondisi
        table.getColumnModel().getColumn(5).setPreferredWidth(120); // Lokasi
        table.getColumnModel().getColumn(6).setPreferredWidth(140); // Tanggal
        table.getColumnModel().getColumn(7).setPreferredWidth(250); // Deskripsi
    }

    public ItemListPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(236, 240, 241));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titleLabel = new JLabel("Data Inventaris");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(44, 62, 80));
        add(titleLabel, BorderLayout.NORTH);

        add(createControlPanel(), BorderLayout.CENTER);

        setupButtonActions();
    }

    private JPanel createControlPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JPanel leftControls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftControls.setOpaque(false);

        JLabel searchLabel = new JLabel("Cari:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        leftControls.add(searchLabel);

        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        leftControls.add(searchField);

        JLabel sortLabel = new JLabel("Urutkan:");
        sortLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        leftControls.add(sortLabel);

        sortComboBox = new JComboBox<>(new String[] { "Nama", "Kategori", "Jumlah", "Tanggal" });
        sortComboBox.setPreferredSize(new Dimension(120, 30));
        leftControls.add(sortComboBox);

        ascendingCheckBox = new JCheckBox("Ascending");
        ascendingCheckBox.setOpaque(false);
        ascendingCheckBox.setSelected(true);
        leftControls.add(ascendingCheckBox);

        refreshBtn = new JButton("Refresh");
        refreshBtn.setPreferredSize(new Dimension(100, 32));
        refreshBtn.setBackground(new Color(52, 73, 94));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setBorderPainted(false);
        leftControls.add(refreshBtn);

        topPanel.add(leftControls, BorderLayout.WEST);

        addBtn = new JButton("Tambahkan");
        addBtn.setPreferredSize(new Dimension(120, 35));
        addBtn.setBackground(new Color(52, 152, 219));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        addBtn.setFocusPainted(false);
        addBtn.setBorderPainted(false);
        topPanel.add(addBtn, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);
        mainPanel.add(createBottomPanel(), BorderLayout.SOUTH);

        return mainPanel;
    }

    private JScrollPane createTablePanel() {
        String[] columns = { "ID", "Nama", "Kategori", "Jumlah", "Kondisi", "Lokasi", "Tanggal", "Deskripsi" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        configureColumnWidths();
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
            lbl.setBackground(new Color(52, 152, 219)); // biru
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

        editBtn = createBottomButton("Edit", new Color(243, 156, 18));
        deleteBtn = createBottomButton("Hapus", new Color(231, 76, 60));
        detailBtn = createBottomButton("Detail", new Color(52, 73, 94));

        panel.add(editBtn);
        panel.add(deleteBtn);
        panel.add(detailBtn);

        return panel;
    }

    private JButton createBottomButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(100, 35));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ===================== CRUD INTEGRATION =====================

    private void setupButtonActions() {
        refreshBtn.addActionListener(e -> refreshTable());

        addBtn.addActionListener(e -> {
            if (onAddCallback != null) {
                onAddCallback.run();
            }
        });

        editBtn.addActionListener(e -> editSelectedItem());
        deleteBtn.addActionListener(e -> deleteSelectedItem());
        detailBtn.addActionListener(e -> showItemDetail());

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

        // Sort listeners
        sortComboBox.addActionListener(e -> performSort());
        ascendingCheckBox.addActionListener(e -> performSort());
    }

    /**
     * Set controller untuk panel ini.
     */
    public void setController(ItemController controller) {
        this.controller = controller;
    }

    /**
     * Set callback untuk navigasi ke add form.
     */
    public void setOnAddCallback(Runnable callback) {
        this.onAddCallback = callback;
    }

    /**
     * Set callback untuk edit item.
     */
    public void setOnEditCallback(java.util.function.Consumer<Item> callback) {
        this.onEditCallback = callback;
    }

    /**
     * Refresh data dari controller ke table.
     */
    public void refreshTable() {
        tableModel.setRowCount(0);

        if (controller == null)
            return;

        List<Item> items = controller.getAllItems();
        for (Item item : items) {
            tableModel.addRow(new Object[] {
                    item.getId(),
                    item.getName(),
                    item.getCategory(),
                    item.getQuantity(),
                    item.getCondition(),
                    item.getLocation() != null ? item.getLocation() : "",
                    item.getDateAdded() != null ? item.getDateAdded().toString() : "",
                    item.getDescription() != null ? item.getDescription() : ""
            });
        }
    }

    /**
     * Perform search based on keyword.
     */
    private void performSearch() {
        if (controller == null)
            return;

        String keyword = searchField.getText().trim();
        List<Item> items = controller.searchItems(keyword);

        tableModel.setRowCount(0);
        for (Item item : items) {
            tableModel.addRow(new Object[] {
                    item.getId(),
                    item.getName(),
                    item.getCategory(),
                    item.getQuantity(),
                    item.getCondition(),
                    item.getLocation() != null ? item.getLocation() : "",
                    item.getDateAdded() != null ? item.getDateAdded().toString() : "",
                    item.getDescription() != null ? item.getDescription() : ""
            });
        }
    }

    /**
     * Perform sorting.
     */
    private void performSort() {
        if (controller == null)
            return;

        String sortField = (String) sortComboBox.getSelectedItem();
        boolean ascending = ascendingCheckBox.isSelected();

        List<Item> items = controller.sortItems(sortField, ascending);

        tableModel.setRowCount(0);
        for (Item item : items) {
            tableModel.addRow(new Object[] {
                    item.getId(),
                    item.getName(),
                    item.getCategory(),
                    item.getQuantity(),
                    item.getCondition(),
                    item.getLocation() != null ? item.getLocation() : "",
                    item.getDateAdded() != null ? item.getDateAdded().toString() : "",
                    item.getDescription() != null ? item.getDescription() : ""
            });
        }
    }

    /**
     * Get selected item from table.
     */
    private Item getSelectedItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Pilih item terlebih dahulu!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }

        String id = (String) tableModel.getValueAt(selectedRow, 0);
        return controller != null ? controller.getItemById(id) : null;
    }

    /**
     * Edit selected item.
     */
    private void editSelectedItem() {
        Item item = getSelectedItem();
        if (item != null && onEditCallback != null) {
            onEditCallback.accept(item);
        }
    }

    /**
     * Delete selected item.
     */
    private void deleteSelectedItem() {
        Item item = getSelectedItem();
        if (item == null)
            return;

        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus barang:\n" + item.getName() + "?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = controller.deleteItem(item.getId());
            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Barang berhasil dihapus!",
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Gagal menghapus barang!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Show item detail.
     */
    private void showItemDetail() {
        Item item = getSelectedItem();
        if (item == null)
            return;

        String detail = String.format(
                "<html><b>ID:</b> %s<br>" +
                        "<b>Nama:</b> %s<br>" +
                        "<b>Kategori:</b> %s<br>" +
                        "<b>Jumlah:</b> %d<br>" +
                        "<b>Kondisi:</b> %s<br>" +
                        "<b>Lokasi:</b> %s<br>" +
                        "<b>Tanggal Ditambahkan:</b> %s<br>" +
                        "<b>Deskripsi:</b> %s</html>",
                item.getId(),
                item.getName(),
                item.getCategory(),
                item.getQuantity(),
                item.getCondition(),
                item.getLocation() != null ? item.getLocation() : "-",
                item.getDateAdded() != null ? item.getDateAdded().toString() : "-",
                item.getDescription() != null ? item.getDescription() : "-");

        JOptionPane.showMessageDialog(this,
                detail,
                "Detail Barang",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Getters
    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getTable() {
        return table;
    }
}