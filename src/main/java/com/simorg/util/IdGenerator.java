package com.simorg.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class untuk generate ID unik.
 */
public class IdGenerator {

    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    private static int itemCounter = 0;
    private static int loanCounter = 0;

    /**
     * Generate unique ID untuk Item.
     * Format: ITM + timestamp + counter
     */
    public static synchronized String generateItemId() {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        itemCounter++;
        return "ITM" + timestamp + String.format("%03d", itemCounter % 1000);
    }

    /**
     * Generate unique ID untuk Loan.
     * Format: LN + timestamp + counter
     */
    public static synchronized String generateLoanId() {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        loanCounter++;
        return "LN" + timestamp + String.format("%03d", loanCounter % 1000);
    }

    /**
     * Reset counters (untuk testing).
     */
    public static void resetCounters() {
        itemCounter = 0;
        loanCounter = 0;
    }
}
