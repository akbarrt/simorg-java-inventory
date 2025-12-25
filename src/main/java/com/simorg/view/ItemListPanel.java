package com.simorg.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ItemListPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> sortComboBox;
    private JCheckBox ascendingCheckBox;

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

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setPreferredSize(new Dimension(100, 32));
        refreshBtn.setBackground(new Color(52, 73, 94));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setBorderPainted(false);
        leftControls.add(refreshBtn);

        topPanel.add(leftControls, BorderLayout.WEST);

        JButton addBtn = new JButton("Tambahkan");
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

        tableModel.addRow(new Object[] {
                "ITM176664363419", "Mouse", "Elektronik", "5", "Baik", "Rack 1", "2025-12-25", "Mouse Fantech"
        });

        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        configureColumnWidths();
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(189, 195, 199));
        table.setGridColor(new Color(220, 220, 220));

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

        JButton editBtn = createBottomButton("Edit", new Color(243, 156, 18));
        JButton deleteBtn = createBottomButton("Hapus", new Color(231, 76, 60));
        JButton detailBtn = createBottomButton("Detail", new Color(52, 73, 94));

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
}