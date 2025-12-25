package com.simorg.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JButton activeButton;

    // Panels
    private DashboardPanel dashboardPanel;
    private ItemListPanel itemListPanel;
    private ItemFormPanel itemFormPanel;
    private LoanListPanel loanListPanel;
    private ReportPanel reportPanel;

    public MainFrame() {
        setTitle("SIMORG - Smart Inventory Management for Organization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createHeader(), BorderLayout.NORTH);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);

        initializePanels();

        contentPanel.add(dashboardPanel, "Dashboard");
        contentPanel.add(itemListPanel, "Data Inventaris");
        contentPanel.add(itemFormPanel, "Tambah Barang");
        contentPanel.add(loanListPanel, "Peminjaman");
        contentPanel.add(reportPanel, "Laporan");

        add(contentPanel, BorderLayout.CENTER);

        cardLayout.show(contentPanel, "Dashboard");
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
        sidebar.add(titleLabel);
        sidebar.add(Box.createVerticalStrut(30));

        String[] menus = {
                "Dashboard",
                "Data Inventaris",
                "Tambah Barang",
                "Peminjaman",
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
    }

    private void initializePanels() {
        dashboardPanel = new DashboardPanel();
        itemListPanel = new ItemListPanel();
        itemFormPanel = new ItemFormPanel();
        loanListPanel = new LoanListPanel();
        reportPanel = new ReportPanel();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new MainFrame().setVisible(true);
        });
    }
}
