package com.simorg.view;

import com.simorg.controller.ItemController;
import com.simorg.controller.LoanController;
import com.simorg.model.Item;
import com.simorg.model.Loan;
import com.simorg.util.UIConstants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Panel Laporan - ringkasan data dan riwayat
 * Menampilkan statistik dan history peminjaman
 */
public class ReportPanel extends JPanel {
    private MainFrame mainFrame;
    private ItemController itemController;
    private LoanController loanController;

    // Stats labels
    private JLabel totalItemTypesLabel;
    private JLabel totalQuantityLabel;
    private JLabel totalLoansLabel;
    private JLabel activeLoansLabel;
    private JLabel returnedLoansLabel;
    private JLabel overdueLoansLabel;

    // Category table
    private JTable categoryTable;
    private DefaultTableModel categoryTableModel;

    // Recent activity table
    private JTable activityTable;
    private DefaultTableModel activityTableModel;

    public ReportPanel(MainFrame mainFrame, ItemController itemController, LoanController loanController) {
        this.mainFrame = mainFrame;
        this.itemController = itemController;
        this.loanController = loanController;

        setBackground(UIConstants.BACKGROUND_COLOR);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initComponents();
    }

    private void initComponents() {
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel("Laporan & Ringkasan");
        titleLabel.setFont(UIConstants.TITLE_FONT);
        titleLabel.setForeground(UIConstants.TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JButton refreshBtn = UIConstants.createButton("Refresh", UIConstants.PRIMARY_COLOR);
        refreshBtn.addActionListener(e -> refreshData());
        headerPanel.add(refreshBtn, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Main content - vertical split
        JPanel contentPanel = new JPanel(new GridLayout(2, 1, 0, 15));
        contentPanel.setOpaque(false);

        // Top section - stats
        JPanel statsSection = new JPanel(new BorderLayout());
        statsSection.setOpaque(false);

        JLabel statsTitle = new JLabel("Ringkasan Inventaris");
        statsTitle.setFont(UIConstants.SUBTITLE_FONT);
        statsTitle.setForeground(UIConstants.TEXT_COLOR);
        statsTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        statsSection.add(statsTitle, BorderLayout.NORTH);

        JPanel statsGrid = new JPanel(new GridLayout(2, 3, 15, 10));
        statsGrid.setOpaque(false);

        totalItemTypesLabel = new JLabel("0");
        statsGrid.add(createStatCard("Jenis Barang", totalItemTypesLabel, UIConstants.PRIMARY_COLOR));

        totalQuantityLabel = new JLabel("0");
        statsGrid.add(createStatCard("Total Unit", totalQuantityLabel, UIConstants.SUCCESS_COLOR));

        totalLoansLabel = new JLabel("0");
        statsGrid.add(createStatCard("Total Peminjaman", totalLoansLabel, UIConstants.SECONDARY_COLOR));

        activeLoansLabel = new JLabel("0");
        statsGrid.add(createStatCard("Sedang Dipinjam", activeLoansLabel, UIConstants.WARNING_COLOR));

        returnedLoansLabel = new JLabel("0");
        statsGrid.add(createStatCard("Sudah Kembali", returnedLoansLabel, UIConstants.SUCCESS_COLOR));

        overdueLoansLabel = new JLabel("0");
        statsGrid.add(createStatCard("Terlambat", overdueLoansLabel, UIConstants.DANGER_COLOR));

        statsSection.add(statsGrid, BorderLayout.CENTER);
        contentPanel.add(statsSection);

        // Bottom section - tables
        JPanel tablesSection = new JPanel(new GridLayout(1, 2, 15, 0));
        tablesSection.setOpaque(false);

        // Category summary table
        JPanel categoryPanel = new JPanel(new BorderLayout());
        categoryPanel.setBackground(Color.WHITE);
        categoryPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel catTitle = new JLabel("Ringkasan per Kategori");
        catTitle.setFont(UIConstants.BUTTON_FONT);
        catTitle.setForeground(UIConstants.TEXT_COLOR);
        catTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        categoryPanel.add(catTitle, BorderLayout.NORTH);

        String[] catColumns = { "Kategori", "Jumlah Jenis", "Total Unit" };
        categoryTableModel = new DefaultTableModel(catColumns, 0);
        categoryTable = new JTable(categoryTableModel);
        categoryTable.setFont(UIConstants.REGULAR_FONT);
        categoryTable.setRowHeight(28);
        categoryTable.getTableHeader().setFont(UIConstants.REGULAR_FONT);
        categoryTable.getTableHeader().setBackground(UIConstants.PRIMARY_COLOR);
        categoryTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane catScroll = new JScrollPane(categoryTable);
        catScroll.setBorder(BorderFactory.createEmptyBorder());
        categoryPanel.add(catScroll, BorderLayout.CENTER);

        tablesSection.add(categoryPanel);

        // Recent activity table
        JPanel activityPanel = new JPanel(new BorderLayout());
        activityPanel.setBackground(Color.WHITE);
        activityPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel actTitle = new JLabel("Aktivitas Peminjaman Terbaru");
        actTitle.setFont(UIConstants.BUTTON_FONT);
        actTitle.setForeground(UIConstants.TEXT_COLOR);
        actTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        activityPanel.add(actTitle, BorderLayout.NORTH);

        String[] actColumns = { "Tanggal", "Aktivitas", "Peminjam", "Barang" };
        activityTableModel = new DefaultTableModel(actColumns, 0);
        activityTable = new JTable(activityTableModel);
        activityTable.setFont(UIConstants.REGULAR_FONT);
        activityTable.setRowHeight(28);
        activityTable.getTableHeader().setFont(UIConstants.REGULAR_FONT);
        activityTable.getTableHeader().setBackground(UIConstants.WARNING_COLOR);
        activityTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane actScroll = new JScrollPane(activityTable);
        actScroll.setBorder(BorderFactory.createEmptyBorder());
        activityPanel.add(actScroll, BorderLayout.CENTER);

        tablesSection.add(activityPanel);

        contentPanel.add(tablesSection);

        add(contentPanel, BorderLayout.CENTER);

        // Footer with date
        JLabel dateLabel = new JLabel("Laporan dihasilkan: " + LocalDate.now().toString());
        dateLabel.setFont(UIConstants.SMALL_FONT);
        dateLabel.setForeground(new Color(128, 128, 128));
        dateLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setOpaque(false);
        footerPanel.add(dateLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createStatCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleL = new JLabel(title);
        titleL.setFont(UIConstants.SMALL_FONT);
        titleL.setForeground(UIConstants.TEXT_COLOR);
        titleL.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(valueLabel);
        content.add(Box.createRigidArea(new Dimension(0, 5)));
        content.add(titleL);

        card.add(content, BorderLayout.CENTER);
        return card;
    }

    /**
     * Refresh semua data laporan
     */
    public void refreshData() {
        itemController.loadItems();
        loanController.loadLoans();

        ArrayList<Item> items = itemController.getAllItems();
        ArrayList<Loan> loans = loanController.getAllLoans();

        // Update stats
        totalItemTypesLabel.setText(String.valueOf(items.size()));
        totalQuantityLabel.setText(String.valueOf(itemController.getTotalQuantity()));
        totalLoansLabel.setText(String.valueOf(loans.size()));
        activeLoansLabel.setText(String.valueOf(loanController.getActiveLoanCount()));
        returnedLoansLabel.setText(String.valueOf(loanController.getReturnedLoans().size()));
        overdueLoansLabel.setText(String.valueOf(loanController.getOverdueLoanCount()));

        // Update category table
        updateCategoryTable(items);

        // Update activity table
        updateActivityTable(loans);
    }

    private void updateCategoryTable(ArrayList<Item> items) {
        categoryTableModel.setRowCount(0);

        // Group by category
        Map<String, java.util.List<Item>> byCategory = items.stream()
                .collect(Collectors.groupingBy(Item::getCategory));

        for (Map.Entry<String, java.util.List<Item>> entry : byCategory.entrySet()) {
            int typeCount = entry.getValue().size();
            int totalQty = entry.getValue().stream().mapToInt(Item::getQuantity).sum();
            categoryTableModel.addRow(new Object[] {
                    entry.getKey(),
                    typeCount,
                    totalQty
            });
        }
    }

    private void updateActivityTable(ArrayList<Loan> loans) {
        activityTableModel.setRowCount(0);

        // Sort by date descending, take last 10
        loans.stream()
                .sorted((a, b) -> {
                    LocalDate dateA = a.getReturnDate() != null ? a.getReturnDate() : a.getLoanDate();
                    LocalDate dateB = b.getReturnDate() != null ? b.getReturnDate() : b.getLoanDate();
                    return dateB.compareTo(dateA);
                })
                .limit(10)
                .forEach(loan -> {
                    Item item = itemController.getItemById(loan.getItemId());
                    String itemName = item != null ? item.getName() : "Unknown";

                    String activity;
                    LocalDate date;
                    if (loan.getReturnDate() != null) {
                        activity = "Dikembalikan";
                        date = loan.getReturnDate();
                    } else {
                        activity = "Dipinjam";
                        date = loan.getLoanDate();
                    }

                    activityTableModel.addRow(new Object[] {
                            date.toString(),
                            activity,
                            loan.getBorrowerName(),
                            itemName
                    });
                });
    }
}
