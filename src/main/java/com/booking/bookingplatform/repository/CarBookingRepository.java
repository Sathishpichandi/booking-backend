package com.booking.bookingplatform.repository;

import com.booking.bookingplatform.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import com.booking.bookingplatform.entity.BookingStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarBookingRepository extends JpaRepository<CarBooking, Long> {

    List<CarBooking> findByUser(User user);

    Optional<CarBooking> findByBookingCode(String bookingCode);
    long countByUser_EmailAndStatus(String email, BookingStatus status);
    @Query("""
SELECT COALESCE(SUM(c.car.pricePerDay),0)
FROM CarBooking c
WHERE c.user.email = :email
AND c.status = 'CONFIRMED'
""")
    double totalSpent(@Param("email") String email);

    List<CarBooking> findByUser_Email(String email);
}
