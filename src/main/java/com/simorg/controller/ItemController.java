package com.simorg.controller;

import com.simorg.model.Item;
import com.simorg.util.FileHandler;
import com.simorg.util.ItemComparators;
import com.simorg.util.UIConstants;
import com.simorg.util.ValidationHelper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller untuk mengelola data Item (inventaris)
 * Menyediakan operasi CRUD dan bisnis logic
 */
public class ItemController {
    private ArrayList<Item> items;
    private static final String FILE_NAME = UIConstants.ITEMS_FILE;

    public ItemController() {
        items = new ArrayList<>();
        loadItems();
    }

    /**
     * Load semua item dari file CSV
     */
    public void loadItems() {
        items.clear();
        try {
            FileHandler.initializeFile(FILE_NAME, UIConstants.ITEMS_HEADER);
            List<String> lines = FileHandler.readDataLines(FILE_NAME);
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    try {
                        items.add(Item.fromCsvString(line));
                    } catch (Exception e) {
                        System.err.println("Error parsing line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading items: " + e.getMessage());
        }
    }

    /**
     * Simpan semua item ke file CSV
     */
    public void saveItems() throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(UIConstants.ITEMS_HEADER);
        for (Item item : items) {
            lines.add(item.toCsvString());
        }
        FileHandler.writeAllLines(FILE_NAME, lines);
    }

    /**
     * Tambah item baru
     */
    public void addItem(Item item) throws IOException {
        // Validasi
        ValidationHelper.validateNotEmpty(item.getName(), "Nama barang");
        ValidationHelper.validateNotEmpty(item.getCategory(), "Kategori");
        ValidationHelper.validatePositiveNumber(item.getQuantity(), "Jumlah");

        // Generate ID jika belum ada
        if (item.getId() == null || item.getId().isEmpty()) {
            item.setId(FileHandler.generateId("ITM"));
        }

        // Set tanggal jika belum ada
        if (item.getDateAdded() == null) {
            item.setDateAdded(LocalDate.now());
        }

        items.add(item);
        saveItems();
    }

    /**
     * Update item yang sudah ada
     */
    public void updateItem(Item updatedItem) throws IOException {
        ValidationHelper.validateNotEmpty(updatedItem.getId(), "ID Item");

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(updatedItem.getId())) {
                items.set(i, updatedItem);
                saveItems();
                return;
            }
        }
        throw new IllegalArgumentException("Item dengan ID " + updatedItem.getId() + " tidak ditemukan");
    }

    /**
     * Hapus item berdasarkan ID
     */
    public void deleteItem(String id) throws IOException {
        boolean removed = items.removeIf(item -> item.getId().equals(id));
        if (!removed) {
            throw new IllegalArgumentException("Item dengan ID " + id + " tidak ditemukan");
        }
        saveItems();
    }

    /**
     * Get item berdasarkan ID
     */
    public Item getItemById(String id) {
        return items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get semua item
     */
    public ArrayList<Item> getAllItems() {
        return new ArrayList<>(items);
    }

    /**
     * Search item berdasarkan keyword
     */
    public ArrayList<Item> searchItems(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllItems();
        }

        String lowerKeyword = keyword.toLowerCase();
        return items.stream()
                .filter(item -> item.getName().toLowerCase().contains(lowerKeyword) ||
                        item.getCategory().toLowerCase().contains(lowerKeyword) ||
                        item.getLocation().toLowerCase().contains(lowerKeyword) ||
                        item.getId().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Sort items dengan comparator
     */
    public ArrayList<Item> sortItems(String field, boolean ascending) {
        ArrayList<Item> sorted = getAllItems();
        Comparator<Item> comparator = ItemComparators.getComparator(field, ascending);
        sorted.sort(comparator);
        return sorted;
    }

    /**
     * Get items by category
     */
    public ArrayList<Item> getItemsByCategory(String category) {
        return items.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Get daftar kategori unik
     */
    public ArrayList<String> getCategories() {
        return items.stream()
                .map(Item::getCategory)
                .distinct()
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Get total jumlah item
     */
    public int getTotalItemCount() {
        return items.size();
    }

    /**
     * Get total quantity semua item
     */
    public int getTotalQuantity() {
        return items.stream().mapToInt(Item::getQuantity).sum();
    }
}
