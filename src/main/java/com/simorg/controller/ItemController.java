package com.simorg.controller;

import com.simorg.model.Item;
import com.simorg.util.IdGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller untuk mengelola operasi CRUD Item.
 * Data disimpan dalam memory (ArrayList).
 */
public class ItemController {

    private ArrayList<Item> items;

    public ItemController() {
        this.items = new ArrayList<>();
    }

    /**
     * Tambah item baru.
     */
    public void addItem(Item item) {
        if (item.getId() == null || item.getId().isEmpty()) {
            item.setId(IdGenerator.generateItemId());
        }
        if (item.getDateAdded() == null) {
            item.setDateAdded(LocalDate.now());
        }
        items.add(item);
    }

    /**
     * Update item berdasarkan ID.
     */
    public boolean updateItem(String id, Item updatedItem) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                updatedItem.setId(id);
                items.set(i, updatedItem);
                return true;
            }
        }
        return false;
    }

    /**
     * Hapus item berdasarkan ID.
     */
    public boolean deleteItem(String id) {
        return items.removeIf(item -> item.getId().equals(id));
    }

    /**
     * Cari item berdasarkan ID.
     */
    public Item getItemById(String id) {
        for (Item item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Ambil semua item.
     */
    public List<Item> getAllItems() {
        return new ArrayList<>(items);
    }

    /**
     * Cari item berdasarkan keyword (nama atau kategori).
     */
    public List<Item> searchItems(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllItems();
        }
        String lowerKeyword = keyword.toLowerCase().trim();
        return items.stream()
                .filter(item -> item.getName().toLowerCase().contains(lowerKeyword) ||
                        item.getCategory().toLowerCase().contains(lowerKeyword) ||
                        (item.getDescription() != null && item.getDescription().toLowerCase().contains(lowerKeyword)))
                .collect(Collectors.toList());
    }

    /**
     * Sort item berdasarkan field.
     */
    public List<Item> sortItems(String field, boolean ascending) {
        List<Item> sortedList = new ArrayList<>(items);

        Comparator<Item> comparator;
        switch (field.toLowerCase()) {
            case "nama":
            case "name":
                comparator = Comparator.comparing(Item::getName, String.CASE_INSENSITIVE_ORDER);
                break;
            case "kategori":
            case "category":
                comparator = Comparator.comparing(Item::getCategory, String.CASE_INSENSITIVE_ORDER);
                break;
            case "jumlah":
            case "quantity":
                comparator = Comparator.comparingInt(Item::getQuantity);
                break;
            case "tanggal":
            case "date":
                comparator = Comparator.comparing(Item::getDateAdded, Comparator.nullsLast(Comparator.naturalOrder()));
                break;
            default:
                comparator = Comparator.comparing(Item::getName, String.CASE_INSENSITIVE_ORDER);
        }

        if (!ascending) {
            comparator = comparator.reversed();
        }

        sortedList.sort(comparator);
        return sortedList;
    }

    /**
     * Hitung total jenis barang.
     */
    public int getTotalItemTypes() {
        return items.size();
    }

    /**
     * Hitung total quantity semua item.
     */
    public int getTotalQuantity() {
        return items.stream().mapToInt(Item::getQuantity).sum();
    }

    /**
     * Hitung jumlah item per kategori.
     */
    public java.util.Map<String, Long> getItemCountByCategory() {
        return items.stream()
                .collect(Collectors.groupingBy(Item::getCategory, Collectors.counting()));
    }

    /**
     * Hitung quantity per kategori.
     */
    public java.util.Map<String, Integer> getQuantityByCategory() {
        return items.stream()
                .collect(Collectors.groupingBy(Item::getCategory,
                        Collectors.summingInt(Item::getQuantity)));
    }

    /**
     * Clear semua data.
     */
    public void clearAll() {
        items.clear();
    }
}
