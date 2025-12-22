package com.simorg.controller;

import com.simorg.model.Loan;
import com.simorg.util.FileHandler;
import com.simorg.util.UIConstants;
import com.simorg.util.ValidationHelper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller untuk mengelola data Peminjaman
 * Menyediakan operasi CRUD dan bisnis logic untuk loans
 */
public class LoanController {
    private ArrayList<Loan> loans;
    private static final String FILE_NAME = UIConstants.LOANS_FILE;

    public LoanController() {
        loans = new ArrayList<>();
        loadLoans();
    }

    /**
     * Load semua loan dari file CSV
     */
    public void loadLoans() {
        loans.clear();
        try {
            FileHandler.initializeFile(FILE_NAME, UIConstants.LOANS_HEADER);
            List<String> lines = FileHandler.readDataLines(FILE_NAME);
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    try {
                        loans.add(Loan.fromCsvString(line));
                    } catch (Exception e) {
                        System.err.println("Error parsing loan line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading loans: " + e.getMessage());
        }
    }

    /**
     * Simpan semua loan ke file CSV
     */
    public void saveLoans() throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(UIConstants.LOANS_HEADER);
        for (Loan loan : loans) {
            lines.add(loan.toCsvString());
        }
        FileHandler.writeAllLines(FILE_NAME, lines);
    }

    /**
     * Tambah peminjaman baru
     */
    public void addLoan(Loan loan) throws IOException {
        // Validasi
        ValidationHelper.validateNotEmpty(loan.getItemId(), "ID Barang");
        ValidationHelper.validateNotEmpty(loan.getBorrowerName(), "Nama Peminjam");
        ValidationHelper.validatePositiveNumber(loan.getQuantity(), "Jumlah");

        // Generate ID jika belum ada
        if (loan.getId() == null || loan.getId().isEmpty()) {
            loan.setId(FileHandler.generateId("LN"));
        }

        // Set default values
        if (loan.getLoanDate() == null) {
            loan.setLoanDate(LocalDate.now());
        }
        if (loan.getStatus() == null) {
            loan.setStatus("DIPINJAM");
        }

        loans.add(loan);
        saveLoans();
    }

    /**
     * Update peminjaman
     */
    public void updateLoan(Loan updatedLoan) throws IOException {
        ValidationHelper.validateNotEmpty(updatedLoan.getId(), "ID Peminjaman");

        for (int i = 0; i < loans.size(); i++) {
            if (loans.get(i).getId().equals(updatedLoan.getId())) {
                loans.set(i, updatedLoan);
                saveLoans();
                return;
            }
        }
        throw new IllegalArgumentException("Peminjaman dengan ID " + updatedLoan.getId() + " tidak ditemukan");
    }

    /**
     * Proses pengembalian barang
     */
    public void returnLoan(String loanId) throws IOException {
        Loan loan = getLoanById(loanId);
        if (loan == null) {
            throw new IllegalArgumentException("Peminjaman tidak ditemukan");
        }

        loan.setReturnDate(LocalDate.now());
        loan.setStatus("DIKEMBALIKAN");
        updateLoan(loan);
    }

    /**
     * Hapus peminjaman
     */
    public void deleteLoan(String id) throws IOException {
        boolean removed = loans.removeIf(loan -> loan.getId().equals(id));
        if (!removed) {
            throw new IllegalArgumentException("Peminjaman dengan ID " + id + " tidak ditemukan");
        }
        saveLoans();
    }

    /**
     * Get loan berdasarkan ID
     */
    public Loan getLoanById(String id) {
        return loans.stream()
                .filter(loan -> loan.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get semua loans
     */
    public ArrayList<Loan> getAllLoans() {
        return new ArrayList<>(loans);
    }

    /**
     * Get loans yang masih aktif (belum dikembalikan)
     */
    public ArrayList<Loan> getActiveLoans() {
        return loans.stream()
                .filter(loan -> "DIPINJAM".equals(loan.getStatus()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Get loans yang sudah dikembalikan
     */
    public ArrayList<Loan> getReturnedLoans() {
        return loans.stream()
                .filter(loan -> "DIKEMBALIKAN".equals(loan.getStatus()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Get loans yang terlambat
     */
    public ArrayList<Loan> getOverdueLoans() {
        LocalDate today = LocalDate.now();
        return loans.stream()
                .filter(loan -> "DIPINJAM".equals(loan.getStatus()) &&
                        loan.getDueDate().isBefore(today))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Get loans by item ID
     */
    public ArrayList<Loan> getLoansByItemId(String itemId) {
        return loans.stream()
                .filter(loan -> loan.getItemId().equals(itemId))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Search loans
     */
    public ArrayList<Loan> searchLoans(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllLoans();
        }

        String lowerKeyword = keyword.toLowerCase();
        return loans.stream()
                .filter(loan -> loan.getBorrowerName().toLowerCase().contains(lowerKeyword) ||
                        loan.getId().toLowerCase().contains(lowerKeyword) ||
                        loan.getItemId().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Get total active loans count
     */
    public int getActiveLoanCount() {
        return (int) loans.stream().filter(l -> "DIPINJAM".equals(l.getStatus())).count();
    }

    /**
     * Get overdue loans count
     */
    public int getOverdueLoanCount() {
        return getOverdueLoans().size();
    }
}
