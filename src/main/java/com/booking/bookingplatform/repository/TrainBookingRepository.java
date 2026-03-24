package com.booking.bookingplatform.repository;

import com.booking.bookingplatform.entity.TrainBooking;
import com.booking.bookingplatform.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainBookingRepository extends JpaRepository<TrainBooking, Long> {

    List<TrainBooking> findByUserEmail(String email);

    long countByUser_EmailAndStatus(String email, BookingStatus status);
    @Query("""
SELECT COALESCE(SUM(b.amount),0)
FROM TrainBooking b
WHERE b.user.email = :email
AND b.status = 'CONFIRMED'
""")
    double totalSpent(@Param("email") String email);

    List<TrainBooking> findByUser_Email(String email);
}