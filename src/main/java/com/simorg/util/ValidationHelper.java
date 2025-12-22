package com.simorg.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Utility class untuk validasi input
 * Exception handling untuk berbagai jenis validasi
 */
public class ValidationHelper {

    /**
     * Validasi string tidak kosong
     * 
     * @throws IllegalArgumentException jika kosong
     */
    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " tidak boleh kosong");
        }
    }

    /**
     * Validasi angka positif
     * 
     * @throws IllegalArgumentException jika tidak positif
     */
    public static void validatePositiveNumber(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " harus lebih dari 0");
        }
    }

    /**
     * Validasi angka tidak negatif
     * 
     * @throws IllegalArgumentException jika negatif
     */
    public static void validateNonNegative(int value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " tidak boleh negatif");
        }
    }

    /**
     * Parse dan validasi integer dari string
     * 
     * @throws NumberFormatException jika format salah
     */
    public static int parseInteger(String value, String fieldName) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException(fieldName + " harus berupa angka");
        }
    }

    /**
     * Parse dan validasi tanggal dari string
     * 
     * @throws IllegalArgumentException jika format salah
     */
    public static LocalDate parseDate(String value, String fieldName) {
        try {
            return LocalDate.parse(value.trim());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(fieldName + " harus format YYYY-MM-DD");
        }
    }

    /**
     * Validasi tanggal tidak di masa lalu (untuk due date)
     */
    public static void validateFutureDate(LocalDate date, String fieldName) {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(fieldName + " tidak boleh di masa lalu");
        }
    }

    /**
     * Validasi nomor telepon (sederhana)
     */
    public static void validatePhoneNumber(String phone, String fieldName) {
        if (phone == null || !phone.matches("^[0-9+\\-\\s]{8,15}$")) {
            throw new IllegalArgumentException(fieldName + " format tidak valid (8-15 digit)");
        }
    }

    /**
     * Validasi panjang string
     */
    public static void validateLength(String value, String fieldName, int min, int max) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " tidak boleh kosong");
        }
        int len = value.trim().length();
        if (len < min || len > max) {
            throw new IllegalArgumentException(fieldName + " harus " + min + "-" + max + " karakter");
        }
    }
}
