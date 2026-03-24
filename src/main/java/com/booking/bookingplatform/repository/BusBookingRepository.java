package com.booking.bookingplatform.repository;

import com.booking.bookingplatform.entity.Bus;
import com.booking.bookingplatform.entity.BusBooking;
import com.booking.bookingplatform.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BusBookingRepository extends JpaRepository<BusBooking, Long> {

    boolean existsByBusAndSeatNumber(Bus bus, String seatNumber);

    List<BusBooking> findByBusAndStatus(Bus bus, BookingStatus status);

    long countByUser_EmailAndStatus(String email, BookingStatus status);

    List<BusBooking> findByUser_Email(String email);   // ⭐ ADD THIS

    @Query("""
    SELECT COALESCE(SUM(b.amount),0)
    FROM BusBooking b
    WHERE b.user.email = :email
    AND b.status = 'CONFIRMED'
    """)
    double totalSpent(@Param("email") String email);

}