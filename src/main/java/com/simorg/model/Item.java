package com.simorg.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Model class untuk merepresentasikan barang inventaris.
 */
public class Item {
    private String id;
    private String name;
    private String category;
    private int quantity;
    private String condition;
    private String location;
    private LocalDate dateAdded;
    private String description;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Default constructor
    public Item() {
        this.dateAdded = LocalDate.now();
    }

    // Full constructor
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

    // Constructor tanpa ID (untuk create baru)
    public Item(String name, String category, int quantity,
            String condition, String location, String description) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.condition = condition;
        this.location = location;
        this.dateAdded = LocalDate.now();
        this.description = description;
    }

    // Getters and Setters
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
     * Konversi ke format CSV string untuk persiapan file handling.
     */
    public String toCSVString() {
        return String.join(",",
                id,
                name,
                category,
                String.valueOf(quantity),
                condition,
                location != null ? location : "",
                dateAdded != null ? dateAdded.format(DATE_FORMATTER) : "",
                description != null ? description : "");
    }

    /**
     * Parse dari CSV string ke object Item.
     */
    public static Item fromCSVString(String csvLine) {
        String[] parts = csvLine.split(",", -1);
        if (parts.length < 8) {
            throw new IllegalArgumentException("Invalid CSV format for Item");
        }

        Item item = new Item();
        item.setId(parts[0]);
        item.setName(parts[1]);
        item.setCategory(parts[2]);
        item.setQuantity(Integer.parseInt(parts[3]));
        item.setCondition(parts[4]);
        item.setLocation(parts[5].isEmpty() ? null : parts[5]);
        item.setDateAdded(parts[6].isEmpty() ? null : LocalDate.parse(parts[6], DATE_FORMATTER));
        item.setDescription(parts[7].isEmpty() ? null : parts[7]);

        return item;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                ", condition='" + condition + '\'' +
                ", location='" + location + '\'' +
                ", dateAdded=" + dateAdded +
                ", description='" + description + '\'' +
                '}';
    }
}
