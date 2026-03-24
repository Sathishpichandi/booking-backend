package com.booking.bookingplatform.service;

import com.booking.bookingplatform.dto.AdminDashboardResponse;
import com.booking.bookingplatform.entity.PaymentStatus;
import com.booking.bookingplatform.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminDashboardService {

    private final PaymentRepository paymentRepository;

    public AdminDashboardService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public AdminDashboardResponse getDashboardData() {

        BigDecimal totalRevenue = paymentRepository.getTotalRevenue();

        List<Object[]> bookingData = paymentRepository.getBookingsGroupedByModule();
        List<Object[]> revenueData = paymentRepository.getRevenueGroupedByModule();

        Map<String, Long> bookingsByModule = new HashMap<>();
        Map<String, BigDecimal> revenueByModule = new HashMap<>();

        for (Object[] row : bookingData) {
            bookingsByModule.put(row[0].toString(), (Long) row[1]);
        }

        for (Object[] row : revenueData) {
            revenueByModule.put(row[0].toString(), (BigDecimal) row[1]);
        }

        long totalBookings = bookingsByModule.values()
                .stream()
                .mapToLong(Long::longValue)
                .sum();

        long activeBookings = totalBookings; // SUCCESS = confirmed bookings

        return new AdminDashboardResponse(
                totalRevenue,
                totalBookings,
                bookingsByModule,
                revenueByModule,
                activeBookings
        );
    }
}
