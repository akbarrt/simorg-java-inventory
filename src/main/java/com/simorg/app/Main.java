package com.simorg.app;

import com.simorg.view.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Entry point aplikasi SIMORG
 * Smart Inventory Management for Organization
 */
public class Main {

    public static void main(String[] args) {
        // Set Look and Feel sesuai sistem operasi
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Gagal mengatur Look and Feel: " + e.getMessage());
        }

        // Jalankan GUI di Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
