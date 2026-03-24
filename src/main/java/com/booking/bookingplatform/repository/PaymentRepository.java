package com.booking.bookingplatform.repository;

import com.booking.bookingplatform.entity.Payment;
import com.booking.bookingplatform.entity.PaymentStatus;
import com.booking.bookingplatform.entity.PaymentReferenceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByStatus(PaymentStatus status);

    @Query("SELECT COALESCE(SUM(p.amount),0) FROM Payment p WHERE p.status = 'SUCCESS'")
    BigDecimal getTotalRevenue();

    @Query("SELECT p.referenceType, COUNT(p) FROM Payment p WHERE p.status = 'SUCCESS' GROUP BY p.referenceType")
    List<Object[]> getBookingsGroupedByModule();

    @Query("SELECT p.referenceType, COALESCE(SUM(p.amount),0) FROM Payment p WHERE p.status = 'SUCCESS' GROUP BY p.referenceType")
    List<Object[]> getRevenueGroupedByModule();
}
