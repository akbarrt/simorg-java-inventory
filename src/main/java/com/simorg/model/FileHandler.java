package com.simorg.model;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class untuk operasi baca/tulis file CSV.
 */
public class FileHandler {

    // Default file paths
    public static final String DATA_DIR = "data";
    public static final String ITEMS_FILE = DATA_DIR + "/items.csv";

    // CSV Headers
    private static final String ITEMS_HEADER = "id,name,category,quantity,condition,location,dateAdded,description";

    /**
     * Pastikan folder data ada.
     */
    public static void ensureDataDirectory() {
        try {
            Path dataPath = Paths.get(DATA_DIR);
            if (!Files.exists(dataPath)) {
                Files.createDirectories(dataPath);
            }
        } catch (IOException e) {
            System.err.println("Error creating data directory: " + e.getMessage());
        }
    }

    // ===================== ITEM OPERATIONS =====================

    /**
     * Load items dari file CSV.
     * 
     * @param filePath path ke file CSV
     * @return List of Items, empty list jika file tidak ada
     */
    public static List<Item> loadItems(String filePath) {
        List<Item> items = new ArrayList<>();
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            return items; // Return empty list jika file belum ada
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                // Skip header
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                try {
                    Item item = Item.fromCSVString(line);
                    items.add(item);
                } catch (Exception e) {
                    System.err.println("Warning: Gagal parse item dari line: " + line);
                    System.err.println("Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading items file: " + e.getMessage());
        }

        return items;
    }

    /**
     * Simpan items ke file CSV.
     * 
     * @param filePath path ke file CSV
     * @param items    List of Items untuk disimpan
     * @return true jika berhasil, false jika gagal
     */
    public static boolean saveItems(String filePath, List<Item> items) {
        ensureDataDirectory();

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            // Write header
            writer.write(ITEMS_HEADER);
            writer.newLine();

            // Write data
            for (Item item : items) {
                writer.write(item.toCSVString());
                writer.newLine();
            }

            return true;
        } catch (IOException e) {
            System.err.println("Error saving items file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Load items dari default file path.
     */
    public static List<Item> loadItems() {
        return loadItems(ITEMS_FILE);
    }

    /**
     * Simpan items ke default file path.
     */
    public static boolean saveItems(List<Item> items) {
        return saveItems(ITEMS_FILE, items);
    }
}
