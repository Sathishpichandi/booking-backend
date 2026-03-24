package com.booking.bookingplatform.dto;

import java.math.BigDecimal;

public class BookingSummaryDTO {

    private long upcoming;
    private long completed;
    private long cancelled;
    private BigDecimal totalSpent;

    public BookingSummaryDTO(long upcoming, long completed, long cancelled, BigDecimal totalSpent) {
        this.upcoming = upcoming;
        this.completed = completed;
        this.cancelled = cancelled;
        this.totalSpent = totalSpent;
    }

    public long getUpcoming() { return upcoming; }
    public long getCompleted() { return completed; }
    public long getCancelled() { return cancelled; }
    public BigDecimal getTotalSpent() { return totalSpent; }
}