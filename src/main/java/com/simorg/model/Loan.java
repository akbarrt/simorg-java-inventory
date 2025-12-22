package com.simorg.model;

import java.time.LocalDate;

/**
 * Model class untuk data peminjaman barang
 * Merepresentasikan satu transaksi peminjaman
 */
public class Loan {
    private String id;
    private String itemId;
    private String borrowerName;
    private String borrowerContact;
    private int quantity;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate; // null jika belum dikembalikan
    private String status; // DIPINJAM, DIKEMBALIKAN, TERLAMBAT
    private String notes;

    // Constructor kosong
    public Loan() {
        this.loanDate = LocalDate.now();
        this.status = "DIPINJAM";
    }

    // Constructor lengkap
    public Loan(String id, String itemId, String borrowerName, String borrowerContact,
            int quantity, LocalDate loanDate, LocalDate dueDate,
            LocalDate returnDate, String status, String notes) {
        this.id = id;
        this.itemId = itemId;
        this.borrowerName = borrowerName;
        this.borrowerContact = borrowerContact;
        this.quantity = quantity;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
        this.notes = notes;
    }

    // Getters dan Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getBorrowerContact() {
        return borrowerContact;
    }

    public void setBorrowerContact(String borrowerContact) {
        this.borrowerContact = borrowerContact;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Cek apakah peminjaman sudah melewati jatuh tempo
     */
    public boolean isOverdue() {
        if (returnDate != null)
            return false;
        return LocalDate.now().isAfter(dueDate);
    }

    /**
     * Konversi ke format CSV
     */
    public String toCsvString() {
        return String.join(",",
                id,
                itemId,
                borrowerName,
                borrowerContact,
                String.valueOf(quantity),
                loanDate.toString(),
                dueDate.toString(),
                returnDate != null ? returnDate.toString() : "",
                status,
                notes != null ? notes.replace(",", ";") : "");
    }

    /**
     * Parse dari baris CSV
     */
    public static Loan fromCsvString(String csvLine) {
        String[] parts = csvLine.split(",", 10);
        if (parts.length < 10) {
            throw new IllegalArgumentException("Invalid CSV format for Loan");
        }
        return new Loan(
                parts[0].trim(),
                parts[1].trim(),
                parts[2].trim(),
                parts[3].trim(),
                Integer.parseInt(parts[4].trim()),
                LocalDate.parse(parts[5].trim()),
                LocalDate.parse(parts[6].trim()),
                parts[7].trim().isEmpty() ? null : LocalDate.parse(parts[7].trim()),
                parts[8].trim(),
                parts[9].trim().replace(";", ","));
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id='" + id + '\'' +
                ", itemId='" + itemId + '\'' +
                ", borrowerName='" + borrowerName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
