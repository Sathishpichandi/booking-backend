package com.booking.bookingplatform.controller;

import com.booking.bookingplatform.entity.Flight;
import com.booking.bookingplatform.repository.FlightRepository;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightRepository flightRepository;

    public FlightController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }


    @GetMapping
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();

    }
    @GetMapping("/search")
    public List<Flight> searchFlights(
            @RequestParam String source,
            @RequestParam String destination
    ) {
        return flightRepository
                .findBySourceIgnoreCaseAndDestinationIgnoreCase(source, destination);
    }




}
