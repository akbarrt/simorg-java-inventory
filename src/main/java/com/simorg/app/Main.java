package com.simorg.app;

import javax.swing.*;

import com.simorg.view.MainFrame;

/**
 * Main entry point untuk aplikasi SIMORG.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            new MainFrame().setVisible(true);
        });
    }
}
