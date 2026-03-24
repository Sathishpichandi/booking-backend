package com.booking.bookingplatform.repository;

import com.booking.bookingplatform.entity.Booking;
import com.booking.bookingplatform.entity.BookingStatus;
import com.booking.bookingplatform.entity.Flight;
import com.booking.bookingplatform.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // ================= FLIGHT SEAT VALIDATION =================
    boolean existsByFlightAndSeatNumber(Flight flight, String seatNumber);


    // ================= GET ALL BOOKED SEATS FOR FLIGHT =================
    @Query("SELECT b.seatNumber FROM Booking b WHERE b.flight.id = :flightId")
    List<String> findBookedSeats(@Param("flightId") Long flightId);


    // ================= HOTEL BOOKING VALIDATION =================
    @Query("""
        SELECT COUNT(b)
        FROM HotelBooking b
        WHERE b.room.id = :roomId
        AND b.status IN ('PAYMENT_PENDING','CONFIRMED')
        AND (:checkIn < b.checkOutDate AND :checkOut > b.checkInDate)
    """)
    long countOverlappingBookings(
            @Param("roomId") Long roomId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
    );


    // ================= USER BOOKINGS =================
    List<Booking> findByUser(User user);


    // ================= SEARCH BY PNR =================
    Optional<Booking> findByPnr(String pnr);
    long countByUser_EmailAndStatus(String email, BookingStatus status);
    @Query("""
SELECT COALESCE(SUM(b.flight.price),0)
FROM Booking b
WHERE b.user.email = :email
AND b.status = 'CONFIRMED'
""")
    double totalSpent(@Param("email") String email);

}