package com.booking.bookingplatform.controller;

import com.booking.bookingplatform.entity.BusBooking;
import com.booking.bookingplatform.entity.HotelBooking;
import com.booking.bookingplatform.entity.BookingStatus;
import com.booking.bookingplatform.entity.Train;
import com.booking.bookingplatform.service.HotelBookingService;
import com.booking.bookingplatform.repository.HotelBookingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/hotel-bookings")
public class HotelBookingController {

    private final HotelBookingService hotelBookingService;
    private final HotelBookingRepository hotelBookingRepository;

    public HotelBookingController(HotelBookingService hotelBookingService,
                                  HotelBookingRepository hotelBookingRepository) {
        this.hotelBookingService = hotelBookingService;
        this.hotelBookingRepository = hotelBookingRepository;
    }

    // ================= CREATE BOOKING =================

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestParam Long roomId,
                                           @RequestParam String checkIn,
                                           @RequestParam String checkOut,
                                           Authentication authentication) {

        HotelBooking booking = hotelBookingService.createBooking(
                roomId,
                LocalDate.parse(checkIn),
                LocalDate.parse(checkOut),
                authentication.getName()
        );

        return ResponseEntity.ok(booking);
    }

    // ================= MY BOOKINGS =================


    @GetMapping("/my")
    public List<HotelBooking> myBookings(Authentication auth) {

        String email = auth.getName();

        return hotelBookingRepository.findByUser_Email(email);
    }

    // ================= CANCEL BOOKING =================

    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id,
                                           Authentication authentication) {

        HotelBooking booking = hotelBookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getUser().getEmail().equals(authentication.getName())) {
            return ResponseEntity.status(403).body("Not allowed");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return ResponseEntity.badRequest().body("Already cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        hotelBookingRepository.save(booking);

        return ResponseEntity.ok("Hotel booking cancelled successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBooking(@PathVariable Long id) {

        HotelBooking booking = hotelBookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel booking not found"));

        return ResponseEntity.ok(booking);
    }
    // ================= CHECK-IN =================
    @PutMapping("/checkin/{id}")
    public ResponseEntity<?> checkIn(@PathVariable Long id,
                                     Authentication authentication) {

        HotelBooking booking = hotelBookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getUser().getEmail().equals(authentication.getName())) {
            return ResponseEntity.status(403).body("Not allowed");
        }

        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            return ResponseEntity.badRequest().body("Booking not confirmed");
        }

        LocalDate today = LocalDate.now();

        if (today.isBefore(booking.getCheckInDate()) ||
                today.isAfter(booking.getCheckOutDate())) {
            return ResponseEntity.badRequest().body("Check-in not allowed today");
        }

        booking.setStatus(BookingStatus.CHECKED_IN);
        hotelBookingRepository.save(booking);

        return ResponseEntity.ok("Checked-in successfully");
    }



}
