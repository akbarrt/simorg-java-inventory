package com.simorg.util;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class untuk menangani operasi file CSV
 * Menyediakan method CRUD untuk file handling
 */
public class FileHandler {
    private static final String DATA_DIR = "data";

    /**
     * Membaca semua baris dari file CSV
     * 
     * @param filename nama file (tanpa path)
     * @return List of String (setiap baris)
     */
    public static List<String> readAllLines(String filename) throws IOException {
        Path filePath = Paths.get(DATA_DIR, filename);

        // Buat file jika belum ada
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
            return new ArrayList<>();
        }

        return Files.readAllLines(filePath);
    }

    /**
     * Membaca data (skip header)
     * 
     * @param filename nama file CSV
     * @return List of String tanpa header
     */
    public static List<String> readDataLines(String filename) throws IOException {
        List<String> lines = readAllLines(filename);
        if (lines.size() > 0) {
            lines.remove(0); // Remove header
        }
        return lines;
    }

    /**
     * Menulis semua baris ke file CSV (overwrite)
     * 
     * @param filename nama file
     * @param lines    semua baris termasuk header
     */
    public static void writeAllLines(String filename, List<String> lines) throws IOException {
        Path filePath = Paths.get(DATA_DIR, filename);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, lines);
    }

    /**
     * Menambah satu baris ke file CSV (append)
     * 
     * @param filename nama file
     * @param line     baris baru
     */
    public static void appendLine(String filename, String line) throws IOException {
        Path filePath = Paths.get(DATA_DIR, filename);

        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(filePath,
                StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
            writer.write(line);
            writer.newLine();
        }
    }

    /**
     * Cek apakah file ada
     */
    public static boolean fileExists(String filename) {
        return Files.exists(Paths.get(DATA_DIR, filename));
    }

    /**
     * Buat file dengan header jika belum ada
     */
    public static void initializeFile(String filename, String header) throws IOException {
        Path filePath = Paths.get(DATA_DIR, filename);

        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent());
            List<String> lines = new ArrayList<>();
            lines.add(header);
            Files.write(filePath, lines);
        }
    }

    /**
     * Generate ID unik berdasarkan prefix dan timestamp
     */
    public static String generateId(String prefix) {
        return prefix + System.currentTimeMillis();
    }
}
