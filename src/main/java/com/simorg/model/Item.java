package com.simorg.model;

import java.time.LocalDate;

/**
 * Model class untuk data inventaris/barang
 * Merepresentasikan satu item dalam sistem inventory
 */
public class Item {
    private String id;
    private String name;
    private String category;
    private int quantity;
    private String condition; // Baik, Rusak Ringan, Rusak Berat
    private String location;
    private LocalDate dateAdded;
    private String description;

    // Constructor kosong
    public Item() {
        this.dateAdded = LocalDate.now();
    }

    // Constructor lengkap
    public Item(String id, String name, String category, int quantity,
            String condition, String location, LocalDate dateAdded, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.condition = condition;
        this.location = location;
        this.dateAdded = dateAdded;
        this.description = description;
    }

    // Getters dan Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Konversi ke format CSV
     */
    public String toCsvString() {
        return String.join(",",
                id,
                name,
                category,
                String.valueOf(quantity),
                condition,
                location,
                dateAdded.toString(),
                description.replace(",", ";") // Escape koma dalam description
        );
    }

    /**
     * Parse dari baris CSV
     */
    public static Item fromCsvString(String csvLine) {
        String[] parts = csvLine.split(",", 8);
        if (parts.length < 8) {
            throw new IllegalArgumentException("Invalid CSV format for Item");
        }
        return new Item(
                parts[0].trim(),
                parts[1].trim(),
                parts[2].trim(),
                Integer.parseInt(parts[3].trim()),
                parts[4].trim(),
                parts[5].trim(),
                LocalDate.parse(parts[6].trim()),
                parts[7].trim().replace(";", ","));
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
