package com.booking.bookingplatform.controller;

import com.booking.bookingplatform.entity.Flight;
import com.booking.bookingplatform.repository.FlightRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/flights")
public class AdminFlightController {

    private final FlightRepository flightRepository;

    public AdminFlightController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    // ================= ADD FLIGHT =================
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addFlight(@RequestBody Flight flight) {
        Flight saved = flightRepository.save(flight);
        return ResponseEntity.ok(saved);
    }

    // ================= GET ALL FLIGHTS =================
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(flightRepository.findAll());
    }

    // ================= UPDATE FLIGHT =================
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateFlight(@PathVariable Long id,
                                          @RequestBody Flight updatedFlight) {

        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        flight.setSource(updatedFlight.getSource());
        flight.setDestination(updatedFlight.getDestination());
        flight.setPrice(updatedFlight.getPrice());
        flight.setAvailableSeats(updatedFlight.getAvailableSeats());

        flightRepository.save(flight);

        return ResponseEntity.ok(flight);
    }

    // ================= DELETE FLIGHT =================
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteFlight(@PathVariable Long id) {

        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        flightRepository.delete(flight);

        return ResponseEntity.ok("Flight deleted successfully");
    }

    // ================= UPDATE PRICE ONLY =================
    @PutMapping("/{id}/price")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updatePrice(@PathVariable Long id,
                                         @RequestParam double price) {

        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        flight.setPrice(price);
        flightRepository.save(flight);

        return ResponseEntity.ok("Price updated");
    }

    // ================= UPDATE SEATS ONLY =================
    @PutMapping("/{id}/seats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateSeats(@PathVariable Long id,
                                         @RequestParam int seats) {

        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        flight.setAvailableSeats(seats);
        flightRepository.save(flight);

        return ResponseEntity.ok("Seat count updated");
    }

}
