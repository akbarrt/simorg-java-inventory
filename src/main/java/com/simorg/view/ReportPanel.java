package com.simorg.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

import com.simorg.controller.ItemController;

public class ReportPanel extends JPanel {

    // Controllers
    private ItemController itemController;

    // Stat card value labels
    private JLabel jenisBarangValue;
    private JLabel totalUnitValue;

    // Tables
    private DefaultTableModel kategoriTableModel;

    // Refresh button
    private JButton refreshBtn;

    public ReportPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(236, 240, 241));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        add(createHeader(), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);

        setupButtonActions();
    }

    // ================= HEADER =================
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Laporan & Ringkasan");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(44, 62, 80));

        refreshBtn = new JButton("Refresh");
        refreshBtn.setFocusPainted(false);

        header.add(title, BorderLayout.WEST);
        header.add(refreshBtn, BorderLayout.EAST);

        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        return header;
    }

    // ================= CONTENT =================
    private JPanel createContent() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        content.add(createStatsSection());
        content.add(Box.createVerticalStrut(25));
        content.add(createKategoriTablePanel());

        return content;
    }

    // ================= STAT CARDS =================
    private JPanel createStatsSection() {
        JPanel grid = new JPanel(new GridLayout(1, 2, 15, 15));
        grid.setOpaque(false);
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        JPanel card1 = statCard("0", "Jenis Barang", new Color(52, 152, 219));
        JPanel card2 = statCard("0", "Total Unit", new Color(46, 204, 113));

        // Simpan referensi untuk update
        jenisBarangValue = getValueLabel(card1);
        totalUnitValue = getValueLabel(card2);

        grid.add(card1);
        grid.add(card2);

        return grid;
    }

    private JLabel getValueLabel(JPanel card) {
        JPanel body = (JPanel) card.getComponent(1);
        return (JLabel) body.getComponent(0);
    }

    private JPanel statCard(String value, String label, Color accent) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JPanel accentLine = new JPanel();
        accentLine.setBackground(accent);
        accentLine.setPreferredSize(new Dimension(0, 4));
        card.add(accentLine, BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 28));
        val.setForeground(new Color(44, 62, 80));

        JLabel lab = new JLabel(label);
        lab.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lab.setForeground(new Color(127, 140, 141));

        body.add(val);
        body.add(Box.createVerticalStrut(5));
        body.add(lab);

        card.add(body, BorderLayout.CENTER);
        return card;
    }

    // ================= KATEGORI TABLE =================
    private JPanel createKategoriTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

        JLabel lbl = new JLabel("Ringkasan per Kategori");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = { "Kategori", "Jumlah Item", "Total Qty" };
        kategoriTableModel = new DefaultTableModel(columns, 0);

        JTable table = new JTable(kategoriTableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        table.setRowHeight(22);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // ===================== INTEGRATION =====================

    private void setupButtonActions() {
        refreshBtn.addActionListener(e -> refreshData());
    }

    /**
     * Set controller.
     */
    public void setController(ItemController itemController) {
        this.itemController = itemController;
    }

    /**
     * Refresh semua data dari controller.
     */
    public void refreshData() {
        if (itemController != null) {
            // Update stat cards
            jenisBarangValue.setText(String.valueOf(itemController.getTotalItemTypes()));
            totalUnitValue.setText(String.valueOf(itemController.getTotalQuantity()));

            // Update kategori table
            kategoriTableModel.setRowCount(0);
            Map<String, Long> countByCategory = itemController.getItemCountByCategory();
            Map<String, Integer> qtyByCategory = itemController.getQuantityByCategory();

            for (String kategori : countByCategory.keySet()) {
                kategoriTableModel.addRow(new Object[] {
                        kategori,
                        countByCategory.get(kategori),
                        qtyByCategory.getOrDefault(kategori, 0)
                });
            }
        }
    }
}
