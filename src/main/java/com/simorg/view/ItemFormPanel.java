package com.simorg.view;

import javax.swing.*;
import java.awt.*;

import com.simorg.controller.ItemController;
import com.simorg.model.Item;

public class ItemFormPanel extends JPanel {
    private JTextField idField, namaField, lokasiField;
    private JComboBox<String> kategoriComboBox, kondisiComboBox;
    private JSpinner jumlahSpinner;
    private JTextArea deskripsiArea;

    // Controller and state
    private ItemController controller;
    private Item editingItem;
    private boolean isEditMode = false;

    // Buttons stored as fields for adding listeners
    private JButton saveBtn, clearBtn, cancelBtn;

    // Callback for after save
    private Runnable onSaveCallback;

    public ItemFormPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(236, 240, 241));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Title
        JLabel titleLabel = new JLabel("Form Input Barang");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(44, 62, 80));
        add(titleLabel, BorderLayout.NORTH);

        // Form panel
        add(createFormPanel(), BorderLayout.CENTER);

        // Required field note
        JLabel noteLabel = new JLabel("* = Field wajib diisi");
        noteLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        noteLabel.setForeground(new Color(231, 76, 60));
        noteLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        add(noteLabel, BorderLayout.SOUTH);

        // Setup button actions
        setupButtonActions();
    }

    private JPanel createFormPanel() {
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)));
        formPanel.setPreferredSize(new Dimension(550, 500));

        // ID Barang
        formPanel.add(createFormRow("ID Barang:", false));
        idField = new JTextField("(Auto-generated)");
        idField.setEnabled(false);
        idField.setMaximumSize(new Dimension(450, 30));
        idField.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(idField);
        formPanel.add(Box.createVerticalStrut(15));

        // Nama Barang
        formPanel.add(createFormRow("Nama Barang: *", true));
        namaField = new JTextField();
        namaField.setMaximumSize(new Dimension(450, 30));
        namaField.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(namaField);
        formPanel.add(Box.createVerticalStrut(15));

        // Kategori
        formPanel.add(createFormRow("Kategori: *", true));
        kategoriComboBox = new JComboBox<>(new String[] { "Elektronik", "Furniture", "Alat Tulis", "Lainnya" });
        kategoriComboBox.setMaximumSize(new Dimension(450, 30));
        kategoriComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(kategoriComboBox);
        formPanel.add(Box.createVerticalStrut(15));

        // Jumlah
        formPanel.add(createFormRow("Jumlah: *", true));
        jumlahSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));
        jumlahSpinner.setMaximumSize(new Dimension(450, 30));
        jumlahSpinner.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(jumlahSpinner);
        formPanel.add(Box.createVerticalStrut(15));

        // Kondisi
        formPanel.add(createFormRow("Kondisi: *", true));
        kondisiComboBox = new JComboBox<>(new String[] { "Baik", "Rusak Ringan", "Rusak Berat" });
        kondisiComboBox.setMaximumSize(new Dimension(450, 30));
        kondisiComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(kondisiComboBox);
        formPanel.add(Box.createVerticalStrut(15));

        // Lokasi Penyimpanan
        formPanel.add(createFormRow("Lokasi Penyimpanan:", false));
        lokasiField = new JTextField();
        lokasiField.setMaximumSize(new Dimension(450, 30));
        lokasiField.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(lokasiField);
        formPanel.add(Box.createVerticalStrut(15));

        // Deskripsi
        formPanel.add(createFormRow("Deskripsi:", false));
        deskripsiArea = new JTextArea(4, 20);
        deskripsiArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        deskripsiArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(deskripsiArea);
        scrollPane.setMaximumSize(new Dimension(450, 80));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(scrollPane);
        formPanel.add(Box.createVerticalStrut(25));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setMaximumSize(new Dimension(450, 50));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        saveBtn = createFormButton("Simpan", new Color(46, 204, 113));
        clearBtn = createFormButton("Bersihkan", new Color(243, 156, 18));
        cancelBtn = createFormButton("Batal", new Color(52, 73, 94));

        buttonPanel.add(saveBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(cancelBtn);
        formPanel.add(buttonPanel);

        mainPanel.add(formPanel);
        return mainPanel;
    }

    private JLabel createFormRow(String text, boolean required) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (required) {
            label.setForeground(new Color(44, 62, 80));
        }
        return label;
    }

    private JButton createFormButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(110, 35));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ===================== CRUD INTEGRATION =====================

    private void setupButtonActions() {
        saveBtn.addActionListener(e -> saveItem());
        clearBtn.addActionListener(e -> clearForm());
        cancelBtn.addActionListener(e -> cancelEdit());
    }

    /**
     * Set controller untuk panel ini.
     */
    public void setController(ItemController controller) {
        this.controller = controller;
    }

    /**
     * Set callback yang dipanggil setelah save berhasil.
     */
    public void setOnSaveCallback(Runnable callback) {
        this.onSaveCallback = callback;
    }

    /**
     * Set item untuk mode edit.
     */
    public void setItemForEdit(Item item) {
        if (item != null) {
            this.editingItem = item;
            this.isEditMode = true;

            idField.setText(item.getId());
            namaField.setText(item.getName());
            kategoriComboBox.setSelectedItem(item.getCategory());
            jumlahSpinner.setValue(item.getQuantity());
            kondisiComboBox.setSelectedItem(item.getCondition());
            lokasiField.setText(item.getLocation() != null ? item.getLocation() : "");
            deskripsiArea.setText(item.getDescription() != null ? item.getDescription() : "");

            saveBtn.setText("Update");
        }
    }

    /**
     * Simpan atau update item.
     */
    private void saveItem() {
        // Validasi
        String nama = namaField.getText().trim();
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nama barang wajib diisi!",
                    "Validasi Error",
                    JOptionPane.ERROR_MESSAGE);
            namaField.requestFocus();
            return;
        }

        String kategori = (String) kategoriComboBox.getSelectedItem();
        int jumlah = (Integer) jumlahSpinner.getValue();
        String kondisi = (String) kondisiComboBox.getSelectedItem();
        String lokasi = lokasiField.getText().trim();
        String deskripsi = deskripsiArea.getText().trim();

        if (controller == null) {
            JOptionPane.showMessageDialog(this,
                    "Controller belum diinisialisasi!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (isEditMode && editingItem != null) {
            // Update existing item
            Item updatedItem = new Item(
                    editingItem.getId(),
                    nama, kategori, jumlah, kondisi, lokasi,
                    editingItem.getDateAdded(),
                    deskripsi);

            boolean success = controller.updateItem(editingItem.getId(), updatedItem);
            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Barang berhasil diupdate!",
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                if (onSaveCallback != null) {
                    onSaveCallback.run();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Gagal mengupdate barang!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Add new item
            Item newItem = new Item(nama, kategori, jumlah, kondisi, lokasi, deskripsi);
            controller.addItem(newItem);

            JOptionPane.showMessageDialog(this,
                    "Barang berhasil ditambahkan dengan ID: " + newItem.getId(),
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            if (onSaveCallback != null) {
                onSaveCallback.run();
            }
        }
    }

    /**
     * Bersihkan form.
     */
    public void clearForm() {
        idField.setText("(Auto-generated)");
        namaField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        jumlahSpinner.setValue(1);
        kondisiComboBox.setSelectedIndex(0);
        lokasiField.setText("");
        deskripsiArea.setText("");

        isEditMode = false;
        editingItem = null;
        saveBtn.setText("Simpan");
    }

    /**
     * Batal edit.
     */
    private void cancelEdit() {
        clearForm();
    }

    // Getters untuk form fields (jika diperlukan)
    public JTextField getNamaField() {
        return namaField;
    }

    public JComboBox<String> getKategoriComboBox() {
        return kategoriComboBox;
    }

    public JSpinner getJumlahSpinner() {
        return jumlahSpinner;
    }

    public JComboBox<String> getKondisiComboBox() {
        return kondisiComboBox;
    }

    public JTextField getLokasiField() {
        return lokasiField;
    }

    public JTextArea getDeskripsiArea() {
        return deskripsiArea;
    }
}
