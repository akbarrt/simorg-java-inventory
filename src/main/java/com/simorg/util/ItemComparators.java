package com.simorg.util;

import com.simorg.model.Item;
import java.util.Comparator;

/**
 * Kumpulan Comparator untuk sorting data Item
 * Digunakan dengan Collections.sort() atau ArrayList.sort()
 */
public class ItemComparators {

    /**
     * Sort berdasarkan nama (A-Z)
     */
    public static Comparator<Item> byNameAsc() {
        return Comparator.comparing(Item::getName, String.CASE_INSENSITIVE_ORDER);
    }

    /**
     * Sort berdasarkan nama (Z-A)
     */
    public static Comparator<Item> byNameDesc() {
        return byNameAsc().reversed();
    }

    /**
     * Sort berdasarkan kategori (A-Z)
     */
    public static Comparator<Item> byCategoryAsc() {
        return Comparator.comparing(Item::getCategory, String.CASE_INSENSITIVE_ORDER);
    }

    /**
     * Sort berdasarkan kategori (Z-A)
     */
    public static Comparator<Item> byCategoryDesc() {
        return byCategoryAsc().reversed();
    }

    /**
     * Sort berdasarkan jumlah (kecil ke besar)
     */
    public static Comparator<Item> byQuantityAsc() {
        return Comparator.comparingInt(Item::getQuantity);
    }

    /**
     * Sort berdasarkan jumlah (besar ke kecil)
     */
    public static Comparator<Item> byQuantityDesc() {
        return byQuantityAsc().reversed();
    }

    /**
     * Sort berdasarkan tanggal ditambahkan (terlama)
     */
    public static Comparator<Item> byDateAsc() {
        return Comparator.comparing(Item::getDateAdded);
    }

    /**
     * Sort berdasarkan tanggal ditambahkan (terbaru)
     */
    public static Comparator<Item> byDateDesc() {
        return byDateAsc().reversed();
    }

    /**
     * Get comparator berdasarkan field name dan direction
     */
    public static Comparator<Item> getComparator(String field, boolean ascending) {
        switch (field.toLowerCase()) {
            case "name":
                return ascending ? byNameAsc() : byNameDesc();
            case "category":
                return ascending ? byCategoryAsc() : byCategoryDesc();
            case "quantity":
                return ascending ? byQuantityAsc() : byQuantityDesc();
            case "dateadded":
            case "date":
                return ascending ? byDateAsc() : byDateDesc();
            default:
                return byNameAsc();
        }
    }
}
