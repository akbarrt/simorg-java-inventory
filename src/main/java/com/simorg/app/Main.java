package com.simorg.app;

import com.simorg.view.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Main entry point untuk aplikasi SIMORG
 * Smart Inventory Management for Organization
 */
public class Main {
    public static void main(String[] args) {
        // Set Look and Feel ke sistem operasi
        // try {
        // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        // Jalankan GUI di Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
