package com.booking.bookingplatform.repository;

import com.booking.bookingplatform.entity.BookingStatus;
import com.booking.bookingplatform.entity.HotelBooking;
import com.booking.bookingplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.booking.bookingplatform.entity.BookingStatus;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HotelBookingRepository extends JpaRepository<HotelBooking, Long> {
    @Query("""
    SELECT COUNT(b)
    FROM HotelBooking b
    WHERE b.room.id = :roomId
    AND b.status IN ('PAYMENT_PENDING','CONFIRMED')
    AND (:checkIn < b.checkOutDate AND :checkOut > b.checkInDate)
""")
    long countOverlappingBookings(Long roomId,
                                  LocalDate checkIn,
                                  LocalDate checkOut);

    List<HotelBooking> findByUserEmail(String email);

    List<HotelBooking> findByUser(User user);
    long countByUser_EmailAndStatus(String email, BookingStatus status);
    @Query("""
SELECT COALESCE(SUM(h.totalAmount),0)
FROM HotelBooking h
WHERE h.user.email = :email
AND h.status = 'CONFIRMED'
""")
    double totalSpent(@Param("email") String email);

    List<HotelBooking> findByUser_Email(String email);
}
