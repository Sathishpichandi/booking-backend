package com.booking.bookingplatform.controller;

import com.booking.bookingplatform.entity.Bus;
import com.booking.bookingplatform.entity.BusBooking;
import com.booking.bookingplatform.entity.BookingStatus;
import com.booking.bookingplatform.repository.BusBookingRepository;
import com.booking.bookingplatform.repository.BusRepository;
import com.booking.bookingplatform.service.BusBookingService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bus-bookings")
public class BusBookingController {

    private final BusBookingService busBookingService;
    private final BusRepository busRepository;
    private final BusBookingRepository busBookingRepository;

    public BusBookingController(BusBookingService busBookingService,
                                BusRepository busRepository,
                                BusBookingRepository busBookingRepository) {

        this.busBookingService = busBookingService;
        this.busRepository = busRepository;
        this.busBookingRepository = busBookingRepository;
    }

    // ================= CREATE BOOKING =================

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestParam Long busId,
                                           @RequestBody BusBooking booking,
                                           Authentication authentication) {

        BusBooking saved = busBookingService.createBooking(
                busId,
                booking.getSeatNumber(),
                authentication.getName()
        );

        return ResponseEntity.ok(saved);
    }

    // ================= GET BOOKED SEATS =================

    @GetMapping("/bus/{busId}/seats")
    public List<String> getBookedSeats(@PathVariable Long busId) {

        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        return busBookingRepository
                .findByBusAndStatus(bus, BookingStatus.CONFIRMED)
                .stream()
                .map(BusBooking::getSeatNumber)
                .toList();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getBooking(@PathVariable Long id) {

        BusBooking booking = busBookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus booking not found"));

        return ResponseEntity.ok(booking);
    }
    @GetMapping("/my")
    public List<BusBooking> myBookings(Authentication auth) {

        String email = auth.getName();

        return busBookingRepository.findByUser_Email(email);
    }

}