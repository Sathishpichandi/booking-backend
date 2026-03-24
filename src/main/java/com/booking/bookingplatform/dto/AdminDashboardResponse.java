package com.booking.bookingplatform.dto;

import java.math.BigDecimal;
import java.util.Map;

public class AdminDashboardResponse {

    private BigDecimal totalRevenue;
    private long totalBookings;
    private Map<String, Long> bookingsByModule;
    private Map<String, BigDecimal> revenueByModule;
    private long activeBookings;

    public AdminDashboardResponse(BigDecimal totalRevenue,
                                  long totalBookings,
                                  Map<String, Long> bookingsByModule,
                                  Map<String, BigDecimal> revenueByModule,
                                  long activeBookings) {
        this.totalRevenue = totalRevenue;
        this.totalBookings = totalBookings;
        this.bookingsByModule = bookingsByModule;
        this.revenueByModule = revenueByModule;
        this.activeBookings = activeBookings;
    }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public long getTotalBookings() { return totalBookings; }
    public Map<String, Long> getBookingsByModule() { return bookingsByModule; }
    public Map<String, BigDecimal> getRevenueByModule() { return revenueByModule; }
    public long getActiveBookings() { return activeBookings; }
}
