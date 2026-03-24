package com.booking.bookingplatform.controller;

import com.booking.bookingplatform.dto.BookingResponse;
import com.booking.bookingplatform.dto.BookingSummaryDTO;
import com.booking.bookingplatform.dto.FlightResponse;
import com.booking.bookingplatform.entity.*;
import com.booking.bookingplatform.repository.BookingRepository;
import com.booking.bookingplatform.repository.FlightRepository;
import com.booking.bookingplatform.repository.UserRepository;
import com.booking.bookingplatform.service.BookingSummaryService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final BookingSummaryService bookingSummaryService;

    public BookingController(
            BookingRepository bookingRepository,
            UserRepository userRepository,
            FlightRepository flightRepository,
            BookingSummaryService bookingSummaryService) {

        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
        this.bookingSummaryService = bookingSummaryService;
    }

    // ================= CREATE BOOKING =================
    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking,
                                           Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long flightId = booking.getFlight().getId();

        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        if (flight.getAvailableSeats() <= 0) {
            return ResponseEntity.badRequest().body("No seats available");
        }

        boolean seatExists = bookingRepository
                .existsByFlightAndSeatNumber(flight, booking.getSeatNumber());

        if (seatExists) {
            return ResponseEntity.badRequest().body("Seat already booked");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);

        booking.setUser(user);
        booking.setFlight(flight);
        booking.setStatus(BookingStatus.PAYMENT_PENDING);
        booking.setPnr(generatePNR());

        Booking savedBooking = bookingRepository.save(booking);

        return ResponseEntity.ok(mapToResponse(savedBooking));
    }

    // ================= MY BOOKINGS =================
    @GetMapping("/my")
    public ResponseEntity<?> myBookings(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<BookingResponse> responses = bookingRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    // ================= GET BOOKING =================
    @GetMapping("/{id}")
    public ResponseEntity<?> getBooking(@PathVariable Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        return ResponseEntity.ok(booking);
    }

    // ================= BOOKED SEATS =================
    @GetMapping("/seats/{flightId}")
    public ResponseEntity<List<String>> getBookedSeats(@PathVariable Long flightId) {

        List<String> seats = bookingRepository.findBookedSeats(flightId);

        return ResponseEntity.ok(seats);
    }

    // ================= CONFIRM BOOKING =================
    @PutMapping("/confirm/{id}")
    public ResponseEntity<?> confirmBooking(@PathVariable Long id,
                                            Authentication authentication) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        String email = authentication.getName();

        if (!booking.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(403).body("Not allowed");
        }

        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            return ResponseEntity.ok("Already confirmed");
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        return ResponseEntity.ok("Booking confirmed");
    }

    // ================= CANCEL BOOKING =================
    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id,
                                           Authentication authentication) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        String email = authentication.getName();

        if (!booking.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(403).body("Not allowed");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return ResponseEntity.badRequest().body("Already cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);

        Flight flight = booking.getFlight();
        flight.setAvailableSeats(flight.getAvailableSeats() + 1);
        flightRepository.save(flight);

        bookingRepository.save(booking);

        return ResponseEntity.ok(mapToResponse(booking));
    }

    // ================= PNR LOOKUP =================
    @GetMapping("/pnr/{pnr}")
    public ResponseEntity<?> getBookingByPnr(@PathVariable String pnr,
                                             Authentication authentication) {

        Booking booking = bookingRepository.findByPnr(pnr)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        String email = authentication.getName();

        if (!booking.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(403).body("Not allowed");
        }

        return ResponseEntity.ok(mapToResponse(booking));
    }

    // ================= BOOKING SUMMARY =================
    @GetMapping("/summary")
    public ResponseEntity<?> getBookingSummary(Authentication authentication) {

        String email = authentication.getName();

        BookingSummaryDTO summary = bookingSummaryService.getSummary(email);

        return ResponseEntity.ok(summary);
    }

    // ================= UTILITIES =================
    private String generatePNR() {
        return "MMT-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }

    private BookingResponse mapToResponse(Booking booking) {

        FlightResponse flightResponse = new FlightResponse(
                booking.getFlight().getId(),
                booking.getFlight().getSource(),
                booking.getFlight().getDestination(),
                booking.getFlight().getPrice()
        );

        return new BookingResponse(
                booking.getId(),
                booking.getPnr(),
                booking.getStatus().name(),
                flightResponse
        );
    }

    @GetMapping
    public String test() {
        return "Working";
    }
}