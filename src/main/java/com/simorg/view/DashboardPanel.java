package com.simorg.view;

import javax.swing.*;
import java.awt.*;

import com.simorg.controller.ItemController;

public class DashboardPanel extends JPanel {

    // Controllers
    private ItemController itemController;

    // Stat card value labels (untuk update dinamis)
    private JLabel totalJenisValue;
    private JLabel totalQtyValue;

    // Action buttons
    private JButton tambahBtn, lihatInventoryBtn, lihatLaporanBtn;

    // Callbacks untuk navigasi
    private Runnable onTambahCallback;
    private Runnable onLihatInventoryCallback;
    private Runnable onLihatLaporanCallback;

    public DashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(236, 240, 241));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Title
        JLabel titleLabel = new JLabel("Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(44, 62, 80));
        add(titleLabel, BorderLayout.NORTH);

        // Main content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Statistics cards
        contentPanel.add(createStatsPanel());
        contentPanel.add(Box.createVerticalStrut(30));

        // Quick actions
        contentPanel.add(createQuickActionsPanel());

        add(contentPanel, BorderLayout.CENTER);

        // Footer message
        JLabel footerLabel = new JLabel(
                "<html>Selamat datang di SIMORG - Sistem Manajemen Inventaris Organisasi.<br>" +
                        "Kelola data inventaris dan lihat laporan dengan mudah.</html>");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setForeground(new Color(127, 140, 141));
        footerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        add(footerLabel, BorderLayout.SOUTH);

        setupButtonActions();
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        statsPanel.setOpaque(false);
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        JPanel card1 = createStatCard("0", "Total Jenis Barang", new Color(52, 152, 219));
        JPanel card2 = createStatCard("0", "Total Quantity", new Color(46, 204, 113));

        // Simpan referensi ke value labels
        totalJenisValue = (JLabel) ((JPanel) card1.getComponent(1)).getComponent(0);
        totalQtyValue = (JLabel) ((JPanel) card2.getComponent(1)).getComponent(0);

        statsPanel.add(card1);
        statsPanel.add(card2);

        return statsPanel;
    }

    private JPanel createStatCard(String value, String label, Color accentColor) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        // Accent line at top
        JPanel accentLine = new JPanel();
        accentLine.setBackground(accentColor);
        accentLine.setPreferredSize(new Dimension(0, 4));
        card.add(accentLine, BorderLayout.NORTH);

        // Content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        valueLabel.setForeground(new Color(44, 62, 80));
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelLabel = new JLabel(label);
        labelLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelLabel.setForeground(new Color(127, 140, 141));
        labelLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.add(valueLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(labelLabel);

        card.add(contentPanel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createQuickActionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel titleLabel = new JLabel("Quick Actions");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        buttonsPanel.setOpaque(false);

        tambahBtn = createActionButton("Tambah Barang", new Color(52, 152, 219));
        lihatInventoryBtn = createActionButton("Lihat Inventory", new Color(52, 73, 94));
        lihatLaporanBtn = createActionButton("Lihat Laporan", new Color(46, 204, 113));

        buttonsPanel.add(tambahBtn);
        buttonsPanel.add(lihatInventoryBtn);
        buttonsPanel.add(lihatLaporanBtn);

        panel.add(buttonsPanel, BorderLayout.CENTER);

        return panel;
    }

    private JButton createActionButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setPreferredSize(new Dimension(140, 40));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });

        return btn;
    }

    // ===================== INTEGRATION =====================

    private void setupButtonActions() {
        tambahBtn.addActionListener(e -> {
            if (onTambahCallback != null)
                onTambahCallback.run();
        });
        lihatInventoryBtn.addActionListener(e -> {
            if (onLihatInventoryCallback != null)
                onLihatInventoryCallback.run();
        });
        lihatLaporanBtn.addActionListener(e -> {
            if (onLihatLaporanCallback != null)
                onLihatLaporanCallback.run();
        });
    }

    /**
     * Set controller.
     */
    public void setController(ItemController itemController) {
        this.itemController = itemController;
    }

    /**
     * Set navigation callbacks.
     */
    public void setOnTambahCallback(Runnable callback) {
        this.onTambahCallback = callback;
    }

    public void setOnLihatInventoryCallback(Runnable callback) {
        this.onLihatInventoryCallback = callback;
    }

    public void setOnLihatLaporanCallback(Runnable callback) {
        this.onLihatLaporanCallback = callback;
    }

    /**
     * Refresh statistics dari data controller.
     */
    public void refreshStats() {
        if (itemController != null) {
            totalJenisValue.setText(String.valueOf(itemController.getTotalItemTypes()));
            totalQtyValue.setText(String.valueOf(itemController.getTotalQuantity()));
        }
    }
}