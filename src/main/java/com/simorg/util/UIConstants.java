package com.simorg.util;

import javax.swing.*;
import java.awt.*;

/**
 * Utility class untuk konstanta dan helper UI
 * Menyediakan warna, font, dan method helper untuk konsistensi tampilan
 */
public class UIConstants {
    // Warna tema aplikasi
    public static final Color PRIMARY_COLOR = new Color(41, 128, 185); // Biru
    public static final Color SECONDARY_COLOR = new Color(52, 73, 94); // Abu gelap
    public static final Color SUCCESS_COLOR = new Color(39, 174, 96); // Hijau
    public static final Color WARNING_COLOR = new Color(243, 156, 18); // Kuning/Orange
    public static final Color DANGER_COLOR = new Color(231, 76, 60); // Merah
    public static final Color BACKGROUND_COLOR = new Color(236, 240, 241); // Abu terang
    public static final Color WHITE = Color.WHITE;
    public static final Color TEXT_COLOR = new Color(44, 62, 80); // Gelap

    // Font
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);

    // Ukuran
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 700;
    public static final int SIDEBAR_WIDTH = 220;
    public static final int PADDING = 15;

    // File paths
    public static final String ITEMS_FILE = "items.csv";
    public static final String LOANS_FILE = "loans.csv";

    // Headers CSV
    public static final String ITEMS_HEADER = "id,name,category,quantity,condition,location,dateAdded,description";
    public static final String LOANS_HEADER = "id,itemId,borrowerName,borrowerContact,quantity,loanDate,dueDate,returnDate,status,notes";

    /**
     * Membuat styled button
     */
    public static JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(WHITE);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 40));
        return button;
    }

    /**
     * Membuat styled text field
     */
    public static JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(REGULAR_FONT);
        textField.setPreferredSize(new Dimension(200, 35));
        return textField;
    }

    /**
     * Membuat styled label
     */
    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(REGULAR_FONT);
        label.setForeground(TEXT_COLOR);
        return label;
    }

    /**
     * Tampilkan pesan error
     */
    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Tampilkan pesan sukses
     */
    public static void showSuccess(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Tampilkan konfirmasi
     */
    public static boolean showConfirm(Component parent, String message) {
        int result = JOptionPane.showConfirmDialog(parent, message, "Konfirmasi",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }
}
