package com.simorg.view;

import com.simorg.controller.ItemController;
import com.simorg.model.Item;
import com.simorg.util.UIConstants;
import com.simorg.util.ValidationHelper;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Panel Form Input - untuk tambah dan edit data barang
 * Form dengan validasi input
 */
public class ItemFormPanel extends JPanel {
    private MainFrame mainFrame;
    private ItemController itemController;

    // Form fields
    private JTextField idField;
    private JTextField nameField;
    private JComboBox<String> categoryCombo;
    private JSpinner quantitySpinner;
    private JComboBox<String> conditionCombo;
    private JTextField locationField;
    private JTextArea descriptionArea;

    // Mode: tambah atau edit
    private boolean isEditMode = false;
    private String editItemId = null;

    // Kategori default
    private final String[] categories = { "Elektronik", "Furnitur", "ATK", "Peralatan", "Kendaraan", "Lainnya" };
    private final String[] conditions = { "Baik", "Rusak Ringan", "Rusak Berat" };

    public ItemFormPanel(MainFrame mainFrame, ItemController itemController) {
        this.mainFrame = mainFrame;
        this.itemController = itemController;

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

        JLabel titleLabel = new JLabel("Form Input Barang");
        titleLabel.setFont(UIConstants.TITLE_FONT);
        titleLabel.setForeground(UIConstants.TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        // Form panel
        JPanel formWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formWrapper.setOpaque(false);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        // ID (auto-generated, readonly)
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(UIConstants.createLabel("ID Barang:"), gbc);

        gbc.gridx = 1;
        idField = UIConstants.createTextField();
        idField.setEditable(false);
        idField.setBackground(new Color(245, 245, 245));
        idField.setPreferredSize(new Dimension(300, 35));
        formPanel.add(idField, gbc);

        // Nama
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(UIConstants.createLabel("Nama Barang: *"), gbc);

        gbc.gridx = 1;
        nameField = UIConstants.createTextField();
        nameField.setPreferredSize(new Dimension(300, 35));
        formPanel.add(nameField, gbc);

        // Kategori
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(UIConstants.createLabel("Kategori: *"), gbc);

        gbc.gridx = 1;
        categoryCombo = new JComboBox<>(categories);
        categoryCombo.setFont(UIConstants.REGULAR_FONT);
        categoryCombo.setEditable(true); // Allow custom category
        categoryCombo.setPreferredSize(new Dimension(300, 35));
        formPanel.add(categoryCombo, gbc);

        // Jumlah
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(UIConstants.createLabel("Jumlah: *"), gbc);

        gbc.gridx = 1;
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
        quantitySpinner.setFont(UIConstants.REGULAR_FONT);
        quantitySpinner.setPreferredSize(new Dimension(300, 35));
        formPanel.add(quantitySpinner, gbc);

        // Kondisi
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(UIConstants.createLabel("Kondisi: *"), gbc);

        gbc.gridx = 1;
        conditionCombo = new JComboBox<>(conditions);
        conditionCombo.setFont(UIConstants.REGULAR_FONT);
        conditionCombo.setPreferredSize(new Dimension(300, 35));
        formPanel.add(conditionCombo, gbc);

        // Lokasi
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(UIConstants.createLabel("Lokasi Penyimpanan:"), gbc);

        gbc.gridx = 1;
        locationField = UIConstants.createTextField();
        locationField.setPreferredSize(new Dimension(300, 35));
        formPanel.add(locationField, gbc);

        // Deskripsi
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(UIConstants.createLabel("Deskripsi:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        descriptionArea = new JTextArea(4, 25);
        descriptionArea.setFont(UIConstants.REGULAR_FONT);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descriptionArea);
        descScroll.setPreferredSize(new Dimension(300, 100));
        formPanel.add(descScroll, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);

        JButton saveButton = UIConstants.createButton("Simpan", UIConstants.SUCCESS_COLOR);
        saveButton.setPreferredSize(new Dimension(120, 40));
        saveButton.addActionListener(e -> saveItem());

        JButton clearButton = UIConstants.createButton("Bersihkan", UIConstants.WARNING_COLOR);
        clearButton.setPreferredSize(new Dimension(120, 40));
        clearButton.addActionListener(e -> clearForm());

        JButton cancelButton = UIConstants.createButton("Batal", UIConstants.SECONDARY_COLOR);
        cancelButton.setPreferredSize(new Dimension(120, 40));
        cancelButton.addActionListener(e -> {
            clearForm();
            mainFrame.showPanel(MainFrame.ITEM_LIST);
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(cancelButton);

        formPanel.add(buttonPanel, gbc);

        formWrapper.add(formPanel);
        add(formWrapper, BorderLayout.CENTER);

        // Required fields note
        JLabel noteLabel = new JLabel("* = Field wajib diisi");
        noteLabel.setFont(UIConstants.SMALL_FONT);
        noteLabel.setForeground(UIConstants.DANGER_COLOR);
        noteLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        southPanel.setOpaque(false);
        southPanel.add(noteLabel);
        add(southPanel, BorderLayout.SOUTH);
    }

    /**
     * Simpan data barang (tambah atau update)
     */
    private void saveItem() {
        try {
            // Validasi input
            String name = nameField.getText().trim();
            String category = (String) categoryCombo.getSelectedItem();
            int quantity = (Integer) quantitySpinner.getValue();
            String condition = (String) conditionCombo.getSelectedItem();
            String location = locationField.getText().trim();
            String description = descriptionArea.getText().trim();

            ValidationHelper.validateNotEmpty(name, "Nama barang");
            ValidationHelper.validateNotEmpty(category, "Kategori");
            ValidationHelper.validatePositiveNumber(quantity, "Jumlah");

            Item item = new Item();
            item.setName(name);
            item.setCategory(category);
            item.setQuantity(quantity);
            item.setCondition(condition);
            item.setLocation(location.isEmpty() ? "-" : location);
            item.setDescription(description.isEmpty() ? "-" : description);
            item.setDateAdded(LocalDate.now());

            if (isEditMode && editItemId != null) {
                // Mode edit
                item.setId(editItemId);
                item.setDateAdded(itemController.getItemById(editItemId).getDateAdded());
                itemController.updateItem(item);
                UIConstants.showSuccess(this, "Barang berhasil diupdate!");
            } else {
                // Mode tambah
                itemController.addItem(item);
                UIConstants.showSuccess(this, "Barang berhasil ditambahkan!");
            }

            clearForm();
            mainFrame.showPanel(MainFrame.ITEM_LIST);

        } catch (IllegalArgumentException e) {
            UIConstants.showError(this, e.getMessage());
        } catch (Exception e) {
            UIConstants.showError(this, "Gagal menyimpan: " + e.getMessage());
        }
    }

    /**
     * Bersihkan form
     */
    public void clearForm() {
        isEditMode = false;
        editItemId = null;
        idField.setText("(Auto-generated)");
        nameField.setText("");
        categoryCombo.setSelectedIndex(0);
        quantitySpinner.setValue(1);
        conditionCombo.setSelectedIndex(0);
        locationField.setText("");
        descriptionArea.setText("");
    }

    /**
     * Load item untuk mode edit
     */
    public void loadItemForEdit(String itemId) {
        Item item = itemController.getItemById(itemId);
        if (item == null) {
            UIConstants.showError(this, "Item tidak ditemukan");
            return;
        }

        isEditMode = true;
        editItemId = itemId;

        idField.setText(item.getId());
        nameField.setText(item.getName());
        categoryCombo.setSelectedItem(item.getCategory());
        quantitySpinner.setValue(item.getQuantity());
        conditionCombo.setSelectedItem(item.getCondition());
        locationField.setText(item.getLocation());
        descriptionArea.setText(item.getDescription());
    }
}
