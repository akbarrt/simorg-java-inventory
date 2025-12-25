package com.simorg.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.simorg.controller.ItemController;
import com.simorg.model.Item;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JButton activeButton;

    // Panels
    private DashboardPanel dashboardPanel;
    private ItemListPanel itemListPanel;
    private ItemFormPanel itemFormPanel;
    private ReportPanel reportPanel;

    // Controllers
    private ItemController itemController;

    public MainFrame() {
        setTitle("SIMORG - Smart Inventory Management for Organization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize controllers dan load data dari file
        initializeControllers();

        add(createSidebar(), BorderLayout.WEST);
        add(createHeader(), BorderLayout.NORTH);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);

        initializePanels();

        contentPanel.add(dashboardPanel, "Dashboard");
        contentPanel.add(itemListPanel, "Data Inventaris");
        contentPanel.add(itemFormPanel, "Tambah Barang");
        contentPanel.add(reportPanel, "Laporan");

        add(contentPanel, BorderLayout.CENTER);

        cardLayout.show(contentPanel, "Dashboard");

        // Refresh data saat startup
        refreshAllData();
    }

    // ================= SIDEBAR =================
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(new Color(45, 52, 64));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        JLabel titleLabel = new JLabel("SIMORG");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(titleLabel);
        sidebar.add(Box.createVerticalStrut(30));

        String[] menus = {
                "Dashboard",
                "Data Inventaris",
                "Tambah Barang",
                "Laporan"
        };

        for (String menu : menus) {
            JButton btn = createSidebarButton(menu);
            sidebar.add(btn);
            sidebar.add(Box.createVerticalStrut(10));

            if (menu.equals("Dashboard")) {
                activeButton = btn;
                btn.setBackground(new Color(60, 70, 85));
            }
        }

        return sidebar;
    }

    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 45));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(45, 52, 64));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (btn != activeButton) {
                    btn.setBackground(new Color(55, 62, 74));
                }
            }

            public void mouseExited(MouseEvent e) {
                if (btn != activeButton) {
                    btn.setBackground(new Color(45, 52, 64));
                }
            }
        });

        btn.addActionListener(e -> switchPanel(text, btn));
        return btn;
    }

    // ================= HEADER =================
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(0, 50));
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel title = new JLabel("Smart Inventory Management for Organization");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel user = new JLabel("Admin");
        user.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        header.add(title, BorderLayout.WEST);
        header.add(user, BorderLayout.EAST);

        return header;
    }

    private void switchPanel(String panelName, JButton clickedButton) {
        if (activeButton != null) {
            activeButton.setBackground(new Color(45, 52, 64));
        }
        activeButton = clickedButton;
        clickedButton.setBackground(new Color(60, 70, 85));
        cardLayout.show(contentPanel, panelName);

        // Refresh data saat panel dibuka
        refreshPanelData(panelName);
    }

    private void refreshPanelData(String panelName) {
        switch (panelName) {
            case "Dashboard":
                dashboardPanel.refreshStats();
                break;
            case "Data Inventaris":
                itemListPanel.refreshTable();
                break;
            case "Laporan":
                reportPanel.refreshData();
                break;
        }
    }

    // ================= CONTROLLER INITIALIZATION =================
    private void initializeControllers() {
        itemController = new ItemController();

        // Load data dari file
        itemController.loadFromFile();
    }

    private void refreshAllData() {
        dashboardPanel.refreshStats();
        itemListPanel.refreshTable();
        reportPanel.refreshData();
    }

    private void initializePanels() {
        // Initialize panels
        dashboardPanel = new DashboardPanel();
        itemListPanel = new ItemListPanel();
        itemFormPanel = new ItemFormPanel();
        reportPanel = new ReportPanel();

        // Set controllers
        itemFormPanel.setController(itemController);
        itemListPanel.setController(itemController);
        dashboardPanel.setController(itemController);
        reportPanel.setController(itemController);

        // Setup callbacks untuk ItemFormPanel
        itemFormPanel.setOnSaveCallback(() -> {
            itemListPanel.refreshTable();
            dashboardPanel.refreshStats();
            reportPanel.refreshData();
        });

        // Setup callbacks untuk ItemListPanel
        itemListPanel.setOnAddCallback(() -> {
            itemFormPanel.clearForm();
            switchToPanel("Tambah Barang");
        });

        itemListPanel.setOnEditCallback((Item item) -> {
            itemFormPanel.setItemForEdit(item);
            switchToPanel("Tambah Barang");
        });

        // Setup callbacks untuk DashboardPanel
        dashboardPanel.setOnTambahCallback(() -> {
            itemFormPanel.clearForm();
            switchToPanel("Tambah Barang");
        });
        dashboardPanel.setOnLihatInventoryCallback(() -> switchToPanel("Data Inventaris"));
        dashboardPanel.setOnLihatLaporanCallback(() -> switchToPanel("Laporan"));
    }

    /**
     * Helper method untuk switch panel programmatically.
     */
    private void switchToPanel(String panelName) {
        cardLayout.show(contentPanel, panelName);
        refreshPanelData(panelName);

        // Update active button styling (find button by text)
        for (Component comp : ((JPanel) getContentPane().getComponent(0)).getComponents()) {
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                if (btn.getText().equals(panelName)) {
                    if (activeButton != null) {
                        activeButton.setBackground(new Color(45, 52, 64));
                    }
                    activeButton = btn;
                    btn.setBackground(new Color(60, 70, 85));
                    break;
                }
            }
        }
    }
}
