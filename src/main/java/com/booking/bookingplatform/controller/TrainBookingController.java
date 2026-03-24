package com.booking.bookingplatform.controller;

import com.booking.bookingplatform.entity.TrainBooking;
import com.booking.bookingplatform.repository.TrainBookingRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.List;

@RestController
@RequestMapping("/api/train-bookings")
public class TrainBookingController {

    private final TrainBookingRepository trainBookingRepository;

    public TrainBookingController(TrainBookingRepository trainBookingRepository) {
        this.trainBookingRepository = trainBookingRepository;
    }

    // Get booking by ID (used by TicketPage)
    @GetMapping("/{id}")
    public ResponseEntity<?> getBooking(@PathVariable Long id) {

        TrainBooking booking = trainBookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Train booking not found"));

        return ResponseEntity.ok(booking);
    }
    @GetMapping("/my")
    public List<TrainBooking> myBookings(Authentication auth) {

        String email = auth.getName();

        return trainBookingRepository.findByUser_Email(email);
    }
}