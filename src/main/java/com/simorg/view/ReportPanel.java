package com.simorg.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

import com.simorg.controller.ItemController;
import com.simorg.controller.LoanController;
import com.simorg.model.Loan;

public class ReportPanel extends JPanel {

    // Controllers
    private ItemController itemController;
    private LoanController loanController;

    // Stat card value labels
    private JLabel jenisBarangValue;
    private JLabel totalUnitValue;
    private JLabel dipinjamValue;
    private JLabel sedangDipinjamValue;
    private JLabel dikembalikanValue;
    private JLabel terlambatValue;

    // Tables
    private DefaultTableModel kategoriTableModel;
    private DefaultTableModel aktivitasTableModel;

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
        content.add(createBottomSection());

        return content;
    }

    // ================= STAT CARDS =================
    private JPanel createStatsSection() {
        JPanel grid = new JPanel(new GridLayout(2, 3, 15, 15));
        grid.setOpaque(false);
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));

        JPanel card1 = statCard("0", "Jenis Barang", new Color(52, 152, 219));
        JPanel card2 = statCard("0", "Total Unit", new Color(46, 204, 113));
        JPanel card3 = statCard("0", "Total Dipinjam", new Color(241, 196, 15));
        JPanel card4 = statCard("0", "Sedang Dipinjam", new Color(52, 73, 94));
        JPanel card5 = statCard("0", "Dikembalikan", new Color(39, 174, 96));
        JPanel card6 = statCard("0", "Terlambat", new Color(231, 76, 60));

        // Simpan referensi untuk update
        jenisBarangValue = getValueLabel(card1);
        totalUnitValue = getValueLabel(card2);
        dipinjamValue = getValueLabel(card3);
        sedangDipinjamValue = getValueLabel(card4);
        dikembalikanValue = getValueLabel(card5);
        terlambatValue = getValueLabel(card6);

        grid.add(card1);
        grid.add(card2);
        grid.add(card3);
        grid.add(card4);
        grid.add(card5);
        grid.add(card6);

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

    // ================= BOTTOM SECTION =================
    private JPanel createBottomSection() {
        JPanel bottom = new JPanel(new GridLayout(1, 2, 20, 0));
        bottom.setOpaque(false);

        bottom.add(createKategoriTablePanel());
        bottom.add(createAktivitasTablePanel());

        bottom.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        return bottom;
    }

    private JPanel createKategoriTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JLabel lbl = new JLabel("Ringkasan per Kategori");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = { "Kategori", "Jumlah", "Qty" };
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

    private JPanel createAktivitasTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JLabel lbl = new JLabel("Aktivitas Terbaru");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = { "Tanggal", "Aksi", "Peminjam" };
        aktivitasTableModel = new DefaultTableModel(columns, 0);

        JTable table = new JTable(aktivitasTableModel);
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
     * Set controllers.
     */
    public void setControllers(ItemController itemController, LoanController loanController) {
        this.itemController = itemController;
        this.loanController = loanController;
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

        if (loanController != null) {
            // Update loan stats
            int totalLoans = loanController.getAllLoans().size();
            int activeLoans = loanController.getActiveLoanCount();
            int returnedLoans = loanController.getReturnedLoanCount();
            int overdueLoans = loanController.getOverdueLoanCount();

            dipinjamValue.setText(String.valueOf(totalLoans));
            sedangDipinjamValue.setText(String.valueOf(activeLoans));
            dikembalikanValue.setText(String.valueOf(returnedLoans));
            terlambatValue.setText(String.valueOf(overdueLoans));

            // Update aktivitas table (show recent loans)
            aktivitasTableModel.setRowCount(0);
            List<Loan> allLoans = loanController.getAllLoans();

            // Show last 10 activities
            int count = 0;
            for (int i = allLoans.size() - 1; i >= 0 && count < 10; i--) {
                Loan loan = allLoans.get(i);
                String tanggal = loan.getLoanDate() != null ? loan.getLoanDate().toString() : "";
                String aksi = loan.getStatus() != null ? loan.getStatus().getValue() : "";

                aktivitasTableModel.addRow(new Object[] {
                        tanggal,
                        aksi,
                        loan.getBorrowerName()
                });
                count++;
            }
        }
    }
}
