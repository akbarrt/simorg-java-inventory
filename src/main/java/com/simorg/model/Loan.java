package com.simorg.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Model class untuk merepresentasikan data peminjaman barang.
 */
public class Loan {

    /**
     * Enum untuk status peminjaman.
     */
    public enum LoanStatus {
        DIPINJAM("DIPINJAM"),
        DIKEMBALIKAN("DIKEMBALIKAN"),
        TERLAMBAT("TERLAMBAT");

        private final String value;

        LoanStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static LoanStatus fromString(String text) {
            for (LoanStatus status : LoanStatus.values()) {
                if (status.value.equalsIgnoreCase(text)) {
                    return status;
                }
            }
            return DIPINJAM; // default
        }
    }

    private String id;
    private String itemId;
    private String borrowerName;
    private String borrowerContact;
    private int quantity;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private LoanStatus status;
    private String notes;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Default constructor
    public Loan() {
        this.loanDate = LocalDate.now();
        this.status = LoanStatus.DIPINJAM;
    }

    // Full constructor
    public Loan(String id, String itemId, String borrowerName, String borrowerContact,
            int quantity, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate,
            LoanStatus status, String notes) {
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

    // Constructor untuk peminjaman baru
    public Loan(String itemId, String borrowerName, String borrowerContact,
            int quantity, LocalDate dueDate, String notes) {
        this.itemId = itemId;
        this.borrowerName = borrowerName;
        this.borrowerContact = borrowerContact;
        this.quantity = quantity;
        this.loanDate = LocalDate.now();
        this.dueDate = dueDate;
        this.status = LoanStatus.DIPINJAM;
        this.notes = notes;
    }

    // Getters and Setters
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

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Check dan update status jika terlambat.
     */
    public void checkAndUpdateStatus() {
        if (status == LoanStatus.DIPINJAM && dueDate != null) {
            if (LocalDate.now().isAfter(dueDate)) {
                this.status = LoanStatus.TERLAMBAT;
            }
        }
    }

    /**
     * Kembalikan barang.
     */
    public void returnItem() {
        this.returnDate = LocalDate.now();
        this.status = LoanStatus.DIKEMBALIKAN;
    }

    /**
     * Konversi ke format CSV string.
     */
    public String toCSVString() {
        return String.join(",",
                id,
                itemId,
                borrowerName,
                borrowerContact != null ? borrowerContact : "",
                String.valueOf(quantity),
                loanDate != null ? loanDate.format(DATE_FORMATTER) : "",
                dueDate != null ? dueDate.format(DATE_FORMATTER) : "",
                returnDate != null ? returnDate.format(DATE_FORMATTER) : "",
                status != null ? status.getValue() : "",
                notes != null ? notes : "");
    }

    /**
     * Parse dari CSV string ke object Loan.
     */
    public static Loan fromCSVString(String csvLine) {
        String[] parts = csvLine.split(",", -1);
        if (parts.length < 10) {
            throw new IllegalArgumentException("Invalid CSV format for Loan");
        }

        Loan loan = new Loan();
        loan.setId(parts[0]);
        loan.setItemId(parts[1]);
        loan.setBorrowerName(parts[2]);
        loan.setBorrowerContact(parts[3].isEmpty() ? null : parts[3]);
        loan.setQuantity(Integer.parseInt(parts[4]));
        loan.setLoanDate(parts[5].isEmpty() ? null : LocalDate.parse(parts[5], DATE_FORMATTER));
        loan.setDueDate(parts[6].isEmpty() ? null : LocalDate.parse(parts[6], DATE_FORMATTER));
        loan.setReturnDate(parts[7].isEmpty() ? null : LocalDate.parse(parts[7], DATE_FORMATTER));
        loan.setStatus(parts[8].isEmpty() ? LoanStatus.DIPINJAM : LoanStatus.fromString(parts[8]));
        loan.setNotes(parts[9].isEmpty() ? null : parts[9]);

        return loan;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id='" + id + '\'' +
                ", itemId='" + itemId + '\'' +
                ", borrowerName='" + borrowerName + '\'' +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}
