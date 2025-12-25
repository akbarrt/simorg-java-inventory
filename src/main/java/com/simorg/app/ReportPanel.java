package com.simorg.app;

import javax.swing.*;
import java.awt.*;

public class ReportPanel extends JPanel {

    public ReportPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(236, 240, 241));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        add(createHeader(), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);
    }

    // ================= HEADER =================
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Laporan & Ringkasan");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(44, 62, 80));

        JButton refresh = new JButton("Refresh");
        refresh.setFocusPainted(false);

        header.add(title, BorderLayout.WEST);
        header.add(refresh, BorderLayout.EAST);

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
        content.add(createBottomSection());

        return content;
    }

    // ================= STAT CARDS =================
    private JPanel createStatsSection() {
        JPanel grid = new JPanel(new GridLayout(2, 3, 15, 15));
        grid.setOpaque(false);
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));

        grid.add(statCard("8", "Jenis Barang", new Color(52, 152, 219)));
        grid.add(statCard("45", "Total Unit", new Color(46, 204, 113)));
        grid.add(statCard("6", "Dipinjam", new Color(241, 196, 15)));
        grid.add(statCard("3", "Sedang Dipinjam", new Color(52, 73, 94)));
        grid.add(statCard("2", "Dikembalikan", new Color(39, 174, 96)));
        grid.add(statCard("1", "Terlambat", new Color(231, 76, 60)));

        return grid;
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

    // ================= BOTTOM SECTION =================
    private JPanel createBottomSection() {
        JPanel bottom = new JPanel(new GridLayout(1, 2, 20, 0));
        bottom.setOpaque(false);

        bottom.add(createTablePanel(
                "Ringkasan per Kategori",
                new String[]{"Kategori", "Jumlah", "Qty"}
        ));

        bottom.add(createTablePanel(
                "Aktivitas Terbaru",
                new String[]{"Tanggal", "Aksi", "Peminjam"}
        ));

        bottom.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        return bottom;
    }

    private JPanel createTablePanel(String title, String[] columns) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTable table = new JTable(new Object[][]{}, columns);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        table.setRowHeight(22);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }
}
