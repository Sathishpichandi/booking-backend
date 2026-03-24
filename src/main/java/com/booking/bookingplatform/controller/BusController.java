package com.booking.bookingplatform.controller;

import com.booking.bookingplatform.entity.Bus;
import com.booking.bookingplatform.entity.BusBooking;
import com.booking.bookingplatform.entity.BookingStatus;
import com.booking.bookingplatform.repository.BusBookingRepository;
import com.booking.bookingplatform.repository.BusRepository;
import com.booking.bookingplatform.service.BusBookingService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buses")
public class BusController {

    private final BusRepository busRepository;
    private final BusBookingService busBookingService;
    private final BusBookingRepository busBookingRepository;

    public BusController(BusRepository busRepository,
                         BusBookingService busBookingService,
                         BusBookingRepository busBookingRepository) {

        this.busRepository = busRepository;
        this.busBookingService = busBookingService;
        this.busBookingRepository = busBookingRepository;
    }

    // ================= GET ALL BUSES =================

    @GetMapping
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    // ================= SEARCH BUSES =================

    @GetMapping("/search")
    public List<Bus> search(@RequestParam String source,
                            @RequestParam String destination) {

        return busRepository
                .findBySourceIgnoreCaseAndDestinationIgnoreCase(source, destination);
    }

    // ================= CREATE BOOKING =================

    @PostMapping("/book")
    public BusBooking book(@RequestParam Long busId,
                           @RequestParam String seatNumber,
                           Authentication authentication) {

        return busBookingService.createBooking(
                busId,
                seatNumber,
                authentication.getName()
        );
    }

    // ================= GET BOOKED SEATS =================

    @GetMapping("/{busId}/seats")
    public List<String> getBookedSeats(@PathVariable Long busId) {

        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        return busBookingRepository
                .findByBusAndStatus(bus, BookingStatus.CONFIRMED)
                .stream()
                .map(BusBooking::getSeatNumber)
                .toList();
    }

}