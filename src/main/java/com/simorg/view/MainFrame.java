package com.simorg.view;

import com.simorg.controller.ItemController;
import com.simorg.controller.LoanController;
import com.simorg.util.UIConstants;

import javax.swing.*;
import java.awt.*;

/**
 * Main JFrame untuk aplikasi SIMORG
 * Menggunakan CardLayout untuk navigasi antar panel
 */
public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;

    // Controllers (shared across panels)
    private ItemController itemController;
    private LoanController loanController;

    // Panel names for navigation
    public static final String DASHBOARD = "DASHBOARD";
    public static final String ITEM_LIST = "ITEM_LIST";
    public static final String ITEM_FORM = "ITEM_FORM";
    public static final String LOAN_LIST = "LOAN_LIST";
    public static final String REPORT = "REPORT";

    // Panels
    private DashboardPanel dashboardPanel;
    private ItemListPanel itemListPanel;
    private ItemFormPanel itemFormPanel;
    private LoanListPanel loanListPanel;
    private ReportPanel reportPanel;

    public MainFrame() {
        // Initialize controllers
        itemController = new ItemController();
        loanController = new LoanController();

        // Setup frame
        setTitle("SIMORG - Smart Inventory Management for Organization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(UIConstants.WINDOW_WIDTH, UIConstants.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 600));

        // Initialize layout
        initComponents();
    }

    private void initComponents() {
        // Main layout
        setLayout(new BorderLayout());

        // Sidebar navigation
        JPanel sidebarPanel = createSidebar();
        add(sidebarPanel, BorderLayout.WEST);

        // Content area with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(UIConstants.BACKGROUND_COLOR);

        // Initialize panels
        dashboardPanel = new DashboardPanel(this, itemController, loanController);
        itemListPanel = new ItemListPanel(this, itemController);
        itemFormPanel = new ItemFormPanel(this, itemController);
        loanListPanel = new LoanListPanel(this, loanController, itemController);
        reportPanel = new ReportPanel(this, itemController, loanController);

        // Add panels to CardLayout
        contentPanel.add(dashboardPanel, DASHBOARD);
        contentPanel.add(itemListPanel, ITEM_LIST);
        contentPanel.add(itemFormPanel, ITEM_FORM);
        contentPanel.add(loanListPanel, LOAN_LIST);
        contentPanel.add(reportPanel, REPORT);

        add(contentPanel, BorderLayout.CENTER);

        // Show dashboard by default
        showPanel(DASHBOARD);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UIConstants.SECONDARY_COLOR);
        sidebar.setPreferredSize(new Dimension(UIConstants.SIDEBAR_WIDTH, 0));

        // Logo/Title
        JLabel titleLabel = new JLabel("SIMORG");
        titleLabel.setFont(UIConstants.TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        sidebar.add(titleLabel);

        // Navigation buttons
        sidebar.add(createNavButton("📊 Dashboard", DASHBOARD));
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebar.add(createNavButton("📦 Data Inventaris", ITEM_LIST));
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebar.add(createNavButton("➕ Tambah Barang", ITEM_FORM));
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebar.add(createNavButton("📋 Peminjaman", LOAN_LIST));
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));
        sidebar.add(createNavButton("📈 Laporan", REPORT));

        // Push remaining space down
        sidebar.add(Box.createVerticalGlue());

        // Footer
        JLabel footerLabel = new JLabel("UAP Pemrograman Lanjut");
        footerLabel.setFont(UIConstants.SMALL_FONT);
        footerLabel.setForeground(new Color(150, 150, 150));
        footerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        sidebar.add(footerLabel);

        return sidebar;
    }

    private JButton createNavButton(String text, String panelName) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(UIConstants.SIDEBAR_WIDTH - 20, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(UIConstants.REGULAR_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(UIConstants.SECONDARY_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(UIConstants.PRIMARY_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(UIConstants.SECONDARY_COLOR);
            }
        });

        button.addActionListener(e -> showPanel(panelName));

        return button;
    }

    /**
     * Navigasi ke panel tertentu
     */
    public void showPanel(String panelName) {
        // Refresh panel data jika diperlukan
        switch (panelName) {
            case DASHBOARD:
                dashboardPanel.refreshData();
                break;
            case ITEM_LIST:
                itemListPanel.refreshData();
                break;
            case ITEM_FORM:
                itemFormPanel.clearForm();
                break;
            case LOAN_LIST:
                loanListPanel.refreshData();
                break;
            case REPORT:
                reportPanel.refreshData();
                break;
        }
        cardLayout.show(contentPanel, panelName);
    }

    /**
     * Navigasi ke form edit item
     */
    public void showEditItemForm(String itemId) {
        itemFormPanel.loadItemForEdit(itemId);
        cardLayout.show(contentPanel, ITEM_FORM);
    }

    // Getters for controllers
    public ItemController getItemController() {
        return itemController;
    }

    public LoanController getLoanController() {
        return loanController;
    }
}
