package com.simorg.view;

import com.simorg.controller.ItemController;
import com.simorg.controller.LoanController;
import com.simorg.util.UIConstants;

import javax.swing.*;
import java.awt.*;

/**
 * Panel Dashboard - halaman utama dengan ringkasan data
 * Menampilkan statistik dan quick actions
 */
public class DashboardPanel extends JPanel {
    private MainFrame mainFrame;
    private ItemController itemController;
    private LoanController loanController;

    // Stat labels
    private JLabel totalItemsLabel;
    private JLabel totalQuantityLabel;
    private JLabel activeLoansLabel;
    private JLabel overdueLoansLabel;

    public DashboardPanel(MainFrame mainFrame, ItemController itemController, LoanController loanController) {
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

        JLabel titleLabel = new JLabel("Dashboard");
        titleLabel.setFont(UIConstants.TITLE_FONT);
        titleLabel.setForeground(UIConstants.TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        // Stats cards panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setOpaque(false);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Total items card
        totalItemsLabel = new JLabel("0");
        statsPanel.add(createStatCard("Total Jenis Barang", totalItemsLabel, UIConstants.PRIMARY_COLOR));

        // Total quantity card
        totalQuantityLabel = new JLabel("0");
        statsPanel.add(createStatCard("Total Quantity", totalQuantityLabel, UIConstants.SUCCESS_COLOR));

        // Active loans card
        activeLoansLabel = new JLabel("0");
        statsPanel.add(createStatCard("Peminjaman Aktif", activeLoansLabel, UIConstants.WARNING_COLOR));

        // Overdue loans card
        overdueLoansLabel = new JLabel("0");
        statsPanel.add(createStatCard("Terlambat", overdueLoansLabel, UIConstants.DANGER_COLOR));

        // Center content
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(statsPanel, BorderLayout.NORTH);

        // Quick actions
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        actionsPanel.setOpaque(false);
        actionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel actionsTitle = new JLabel("Quick Actions");
        actionsTitle.setFont(UIConstants.SUBTITLE_FONT);
        actionsTitle.setForeground(UIConstants.TEXT_COLOR);

        JButton addItemBtn = UIConstants.createButton("Tambah Barang", UIConstants.PRIMARY_COLOR);
        addItemBtn.addActionListener(e -> mainFrame.showPanel(MainFrame.ITEM_FORM));

        JButton viewItemsBtn = UIConstants.createButton("Lihat Inventaris", UIConstants.SECONDARY_COLOR);
        viewItemsBtn.addActionListener(e -> mainFrame.showPanel(MainFrame.ITEM_LIST));

        JButton viewLoansBtn = UIConstants.createButton("Kelola Peminjaman", UIConstants.WARNING_COLOR);
        viewLoansBtn.addActionListener(e -> mainFrame.showPanel(MainFrame.LOAN_LIST));

        JButton viewReportBtn = UIConstants.createButton("Lihat Laporan", UIConstants.SUCCESS_COLOR);
        viewReportBtn.addActionListener(e -> mainFrame.showPanel(MainFrame.REPORT));

        JPanel quickActionsWrapper = new JPanel(new BorderLayout());
        quickActionsWrapper.setOpaque(false);

        JPanel actionsHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionsHeader.setOpaque(false);
        actionsHeader.add(actionsTitle);
        quickActionsWrapper.add(actionsHeader, BorderLayout.NORTH);

        actionsPanel.add(addItemBtn);
        actionsPanel.add(viewItemsBtn);
        actionsPanel.add(viewLoansBtn);
        actionsPanel.add(viewReportBtn);
        quickActionsWrapper.add(actionsPanel, BorderLayout.CENTER);

        centerPanel.add(quickActionsWrapper, BorderLayout.CENTER);

        // Welcome message
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setOpaque(false);
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel welcomeLabel = new JLabel("<html><p style='font-size:12px;color:#666;'>" +
                "Selamat datang di <b>SIMORG</b> - Sistem Manajemen Inventaris Organisasi.<br>" +
                "Kelola data inventaris, pantau peminjaman, dan lihat laporan dengan mudah.</p></html>");
        welcomePanel.add(welcomeLabel, BorderLayout.NORTH);
        centerPanel.add(welcomePanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createStatCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        // Color bar at top
        JPanel colorBar = new JPanel();
        colorBar.setBackground(color);
        colorBar.setPreferredSize(new Dimension(0, 4));
        card.add(colorBar, BorderLayout.NORTH);

        // Content
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleL = new JLabel(title);
        titleL.setFont(UIConstants.REGULAR_FONT);
        titleL.setForeground(UIConstants.TEXT_COLOR);
        titleL.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(valueLabel);
        content.add(Box.createRigidArea(new Dimension(0, 5)));
        content.add(titleL);

        card.add(content, BorderLayout.CENTER);

        return card;
    }

    /**
     * Refresh data statistik dari controllers
     */
    public void refreshData() {
        totalItemsLabel.setText(String.valueOf(itemController.getTotalItemCount()));
        totalQuantityLabel.setText(String.valueOf(itemController.getTotalQuantity()));
        activeLoansLabel.setText(String.valueOf(loanController.getActiveLoanCount()));
        overdueLoansLabel.setText(String.valueOf(loanController.getOverdueLoanCount()));
    }
}
