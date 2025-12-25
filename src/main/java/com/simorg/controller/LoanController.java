package com.simorg.controller;

import com.simorg.model.Item;
import com.simorg.model.Loan;
import com.simorg.model.Loan.LoanStatus;
import com.simorg.util.IdGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller untuk mengelola operasi CRUD Loan (Peminjaman).
 * Data disimpan dalam memory (ArrayList).
 */
public class LoanController {

    private ArrayList<Loan> loans;
    private ItemController itemController;

    public LoanController(ItemController itemController) {
        this.loans = new ArrayList<>();
        this.itemController = itemController;
    }

    /**
     * Tambah peminjaman baru.
     */
    public boolean addLoan(Loan loan) {
        // Validasi item exists
        if (itemController != null) {
            Item item = itemController.getItemById(loan.getItemId());
            if (item == null) {
                return false;
            }
        }

        if (loan.getId() == null || loan.getId().isEmpty()) {
            loan.setId(IdGenerator.generateLoanId());
        }
        if (loan.getLoanDate() == null) {
            loan.setLoanDate(LocalDate.now());
        }
        if (loan.getStatus() == null) {
            loan.setStatus(LoanStatus.DIPINJAM);
        }

        loans.add(loan);
        return true;
    }

    /**
     * Kembalikan barang (set returnDate dan status).
     */
    public boolean returnLoan(String id) {
        Loan loan = getLoanById(id);
        if (loan != null && loan.getStatus() != LoanStatus.DIKEMBALIKAN) {
            loan.returnItem();
            return true;
        }
        return false;
    }

    /**
     * Hapus record peminjaman.
     */
    public boolean deleteLoan(String id) {
        return loans.removeIf(loan -> loan.getId().equals(id));
    }

    /**
     * Cari loan berdasarkan ID.
     */
    public Loan getLoanById(String id) {
        for (Loan loan : loans) {
            if (loan.getId().equals(id)) {
                return loan;
            }
        }
        return null;
    }

    /**
     * Ambil semua loans.
     */
    public List<Loan> getAllLoans() {
        // Update status jika terlambat
        for (Loan loan : loans) {
            loan.checkAndUpdateStatus();
        }
        return new ArrayList<>(loans);
    }

    /**
     * Filter loans dengan status DIPINJAM.
     */
    public List<Loan> getActiveLoans() {
        return loans.stream()
                .peek(Loan::checkAndUpdateStatus)
                .filter(loan -> loan.getStatus() == LoanStatus.DIPINJAM)
                .collect(Collectors.toList());
    }

    /**
     * Filter loans dengan status TERLAMBAT.
     */
    public List<Loan> getOverdueLoans() {
        return loans.stream()
                .peek(Loan::checkAndUpdateStatus)
                .filter(loan -> loan.getStatus() == LoanStatus.TERLAMBAT)
                .collect(Collectors.toList());
    }

    /**
     * Filter loans dengan status DIKEMBALIKAN.
     */
    public List<Loan> getReturnedLoans() {
        return loans.stream()
                .filter(loan -> loan.getStatus() == LoanStatus.DIKEMBALIKAN)
                .collect(Collectors.toList());
    }

    /**
     * Filter berdasarkan status string.
     */
    public List<Loan> filterByStatus(String status) {
        if (status == null || status.equalsIgnoreCase("Semua")) {
            return getAllLoans();
        }

        // Update status dulu
        for (Loan loan : loans) {
            loan.checkAndUpdateStatus();
        }

        return loans.stream()
                .filter(loan -> {
                    switch (status.toLowerCase()) {
                        case "aktif":
                        case "dipinjam":
                            return loan.getStatus() == LoanStatus.DIPINJAM;
                        case "selesai":
                        case "dikembalikan":
                            return loan.getStatus() == LoanStatus.DIKEMBALIKAN;
                        case "terlambat":
                            return loan.getStatus() == LoanStatus.TERLAMBAT;
                        default:
                            return true;
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * Cari berdasarkan nama peminjam atau ID barang.
     */
    public List<Loan> searchLoans(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllLoans();
        }
        String lowerKeyword = keyword.toLowerCase().trim();
        return loans.stream()
                .peek(Loan::checkAndUpdateStatus)
                .filter(loan -> loan.getBorrowerName().toLowerCase().contains(lowerKeyword) ||
                        loan.getItemId().toLowerCase().contains(lowerKeyword) ||
                        (loan.getNotes() != null && loan.getNotes().toLowerCase().contains(lowerKeyword)))
                .collect(Collectors.toList());
    }

    /**
     * Hitung total peminjaman aktif.
     */
    public int getActiveLoanCount() {
        return (int) loans.stream()
                .peek(Loan::checkAndUpdateStatus)
                .filter(loan -> loan.getStatus() == LoanStatus.DIPINJAM)
                .count();
    }

    /**
     * Hitung total peminjaman terlambat.
     */
    public int getOverdueLoanCount() {
        return (int) loans.stream()
                .peek(Loan::checkAndUpdateStatus)
                .filter(loan -> loan.getStatus() == LoanStatus.TERLAMBAT)
                .count();
    }

    /**
     * Hitung total peminjaman yang sudah dikembalikan.
     */
    public int getReturnedLoanCount() {
        return (int) loans.stream()
                .filter(loan -> loan.getStatus() == LoanStatus.DIKEMBALIKAN)
                .count();
    }

    /**
     * Get nama barang dari Item ID.
     */
    public String getItemNameById(String itemId) {
        if (itemController != null) {
            Item item = itemController.getItemById(itemId);
            if (item != null) {
                return item.getName();
            }
        }
        return itemId; // Return ID if item not found
    }

    /**
     * Clear semua data.
     */
    public void clearAll() {
        loans.clear();
    }
}
