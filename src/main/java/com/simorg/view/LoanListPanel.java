package com.simorg.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class LoanListPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> filterComboBox;

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

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setPreferredSize(new Dimension(100, 32));
        refreshBtn.setBackground(new Color(52, 73, 94));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setBorderPainted(false);
        leftControls.add(refreshBtn);

        topPanel.add(leftControls, BorderLayout.WEST);

        // Right control (add button)
        JButton addBtn = new JButton("Pinjam");
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

        // Empty data for now

        table = new JTable(tableModel);
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
            lbl.setBackground(new Color(213, 96, 33)); // biru
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

        JButton returnBtn = createBottomButton("KembalikanBarang", new Color(46, 204, 113));
        JButton deleteBtn = createBottomButton("Hapus", new Color(231, 76, 60));

        panel.add(returnBtn);
        panel.add(deleteBtn);

        return panel;
    }

    private JButton createBottomButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 10));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
